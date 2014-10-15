/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.server.game;

import mage.MageException;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.ManaType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import mage.game.Table;
import mage.game.events.Listener;
import mage.game.events.PlayerQueryEvent;
import mage.game.events.TableEvent;
import mage.game.permanent.Permanent;
import mage.interfaces.Action;
import mage.players.Player;
import mage.server.*;
import mage.server.util.ConfigSettings;
import mage.server.util.Splitter;
import mage.server.util.SystemUtil;
import mage.server.util.ThreadExecutor;
import mage.utils.timer.PriorityTimer;
import mage.view.*;
import mage.view.ChatMessage.MessageColor;
import mage.view.ChatMessage.MessageType;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.zip.GZIPOutputStream;
import mage.constants.PlayerAction;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameController implements GameCallback {

    private static final ExecutorService gameExecutor = ThreadExecutor.getInstance().getGameExecutor();
    private static final Logger logger = Logger.getLogger(GameController.class);

    private ConcurrentHashMap<UUID, GameSession> gameSessions = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, GameWatcher> watchers = new ConcurrentHashMap<>();
    private ConcurrentHashMap<UUID, PriorityTimer> timers = new ConcurrentHashMap<>();
    
    private ConcurrentHashMap<UUID, UUID> userPlayerMap;
    private UUID gameSessionId;
    private Game game;
    private UUID chatId;
    private UUID tableId;
    private UUID choosingPlayerId;
    private Future<?> gameFuture;
    private boolean useTimeout = true;

    public GameController(Game game, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId, UUID choosingPlayerId) {
        gameSessionId = UUID.randomUUID();
        this.userPlayerMap = userPlayerMap;
        chatId = ChatManager.getInstance().createChatSession("Game " + game.getId());
        this.game = game;
        this.game.setSaveGame(ConfigSettings.getInstance().isSaveGameActivated());
        this.tableId = tableId;
        this.choosingPlayerId = choosingPlayerId;
        for (Player player: game.getPlayers().values()) {
            if (!player.isHuman()) {
                useTimeout = false; // no timeout because of beeing idle if playing against AI
                break;
            }
        }
        init();
    }

    public void cleanUp() {
        ChatManager.getInstance().destroyChatSession(chatId);
    }

    private void init() {
        game.addTableEventListener(
            new Listener<TableEvent> () {
                @Override
                public void event(TableEvent event) {
                    try {
                        PriorityTimer timer;
                        UUID playerId;
                        switch (event.getEventType()) {
                            case UPDATE:
                                updateGame();
                                break;
                            case INFO:
                                ChatManager.getInstance().broadcast(chatId, "", event.getMessage(), MessageColor.BLACK, true, ChatMessage.MessageType.GAME);
                                logger.trace(game.getId() + " " + event.getMessage());
                                break;
                            case STATUS:
                                ChatManager.getInstance().broadcast(chatId, "", event.getMessage(), MessageColor.ORANGE, event.getWithTime(), ChatMessage.MessageType.GAME);
                                logger.trace(game.getId() + " " + event.getMessage());
                                break;
                            case ERROR:
                                error(event.getMessage(), event.getException());
                                break;
                            case END_GAME_INFO:
                                endGameInfo();
                                break;
                            case INIT_TIMER:
                                final UUID initPlayerId = event.getPlayerId();
                                if (initPlayerId == null) {
                                    throw new MageException("INIT_TIMER: playerId can't be null");
                                }
                                createPlayerTimer(event.getPlayerId(), game.getPriorityTime());
                                break;
                            case RESUME_TIMER:
                                playerId = event.getPlayerId();
                                if (playerId == null) {
                                    throw new MageException("RESUME_TIMER: playerId can't be null");
                                }
                                timer = timers.get(playerId);
                                if (timer == null) {
                                    Player player = game.getState().getPlayer(playerId);
                                    if (player != null) {
                                        timer = createPlayerTimer(event.getPlayerId(), player.getPriorityTimeLeft());
                                    } else {
                                        throw new MageException("RESUME_TIMER: player can't be null");
                                    }
                                }
                                timer.resume();
                                break;
                            case PAUSE_TIMER:
                                playerId = event.getPlayerId();
                                if (playerId == null) {
                                    throw new MageException("PAUSE_TIMER: playerId can't be null");
                                }
                                timer = timers.get(playerId);
                                if (timer == null) {
                                    throw new MageException("PAUSE_TIMER: couldn't find timer for player: " + playerId);
                                }
                                timer.pause();
                                break;
                        }
                    } catch (MageException ex) {
                        logger.fatal("Table event listener error ", ex);
                    }
                }
            }
        );
        game.addPlayerQueryEventListener(
            new Listener<PlayerQueryEvent> () {
                @Override
                public void event(PlayerQueryEvent event) {
                    logger.trace(new StringBuilder(event.getPlayerId().toString()).append("--").append(event.getQueryType()).append("--").append(event.getMessage()).toString());
                    try {
                        switch (event.getQueryType()) {
                            case ASK:
                                ask(event.getPlayerId(), event.getMessage());
                                break;
                            case PICK_TARGET:
                                target(event.getPlayerId(), event.getMessage(), event.getCards(), event.getPerms(), event.getTargets(), event.isRequired(), event.getOptions());
                                break;
                            case PICK_ABILITY:
                                target(event.getPlayerId(), event.getMessage(), event.getAbilities(), event.isRequired(), event.getOptions());
                                break;
                            case SELECT:
                                select(event.getPlayerId(), event.getMessage(), event.getOptions());
                                break;
                            case PLAY_MANA:
                                playMana(event.getPlayerId(), event.getMessage());
                                break;
                            case PLAY_X_MANA:
                                playXMana(event.getPlayerId(), event.getMessage());
                                break;
                            case CHOOSE_ABILITY:
                                String objectName = null;
                                if(event.getChoices() != null && event.getChoices().size() > 0) {
                                    objectName = event.getChoices().iterator().next();
                                }
                                chooseAbility(event.getPlayerId(), objectName, event.getAbilities());
                                break;
                            case CHOOSE_PILE:
                                choosePile(event.getPlayerId(), event.getMessage(), event.getPile1(), event.getPile2());
                                break;
                            case CHOOSE_MODE:
                                chooseMode(event.getPlayerId(), event.getModes());
                                break;
                            case CHOOSE:
                                choose(event.getPlayerId(), event.getMessage(), event.getChoices());
                                break;
                            case AMOUNT:
                                amount(event.getPlayerId(), event.getMessage(), event.getMin(), event.getMax());
                                break;
                            case PERSONAL_MESSAGE:
                                informPersonal(event.getPlayerId(), event.getMessage());
                                break;
                        }
                    } catch (MageException ex) {
                        logger.fatal("Player event listener error ", ex);
                    }
                }
            }
        );

        checkStart();
    }

    /**
     * We create a timer that will run every 250 ms individually for a player decreasing his internal game counter.
     * Later on this counter is used to get time left to play the whole match.
     *
     * What we also do here is passing Action to PriorityTimer that is the action that will be executed once game timer is over.
     *
     * @param playerId
     * @param count
     * @return
     */
    private PriorityTimer createPlayerTimer(UUID playerId, int count) {
        final UUID initPlayerId = playerId;
        long delayMs = 250L; // run each 250 ms

        Action executeOnNoTimeLeft = new Action() {
            @Override
            public void execute() throws MageException {
                game.timerTimeout(initPlayerId);
                logger.debug("Player has no time left to end the match: " + initPlayerId + ". Conceding.");
            }
        };

        PriorityTimer timer = new PriorityTimer(count, delayMs, executeOnNoTimeLeft);
        timers.put(playerId, timer);
        timer.init();
        return timer;
    }

    private UUID getPlayerId(UUID userId) {
        return userPlayerMap.get(userId);
    }

    public void join(UUID userId) {
        UUID playerId = userPlayerMap.get(userId);
        User user = UserManager.getInstance().getUser(userId);
        if (userId == null || playerId == null) {
            logger.fatal("Join game failed!");
            logger.fatal("- gameId: " +  game.getId());
            logger.fatal("- userId: " +  userId);
            return;
        }
        Player player = game.getPlayer(playerId);
        if (player == null) {
            logger.fatal("Player not found - playerId: " +playerId);
            return;
        }
        GameSession gameSession = gameSessions.get(playerId);
        String joinType;
        if (gameSession == null) {
            gameSession = new GameSession(game, userId, playerId, useTimeout);
            gameSessions.put(playerId, gameSession);
            gameSession.setUserData(user.getUserData());
            joinType = "joined";
        } else {
            joinType = "rejoined";
        }
        user.addGame(playerId, gameSession);
        logger.debug("Player " + playerId + " has " + joinType + " gameId: " + game.getId());
        ChatManager.getInstance().broadcast(chatId, "", game.getPlayer(playerId).getName() + " has " + joinType + " the game", MessageColor.ORANGE, true, MessageType.GAME);
        checkStart();
    }

    private synchronized void startGame() {
        if (gameFuture == null) {
            for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
                if (!entry.getValue().init()) {
                    logger.fatal("Unable to initialize client");
                    //TODO: generate client error message
                    GameManager.getInstance().removeGame(game.getId());
                    return;
                }
            }
            GameWorker worker = new GameWorker(game, choosingPlayerId, this);
            gameFuture = gameExecutor.submit(worker);
        }
    }

    private void checkStart() {
        if (allJoined()) {
            ThreadExecutor.getInstance().getCallExecutor().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        startGame();
                    }
            });
        }
    }

    private boolean allJoined() {
        for (Player player: game.getPlayers().values()) {
            if (player.isHuman() && gameSessions.get(player.getId()) == null) {
                return false;
            }
        }
        return true;
    }

    public void watch(UUID userId) {
        if (userPlayerMap.get(userId) != null) {
            // You can't watch a game if you already a player in it
            return;
        }
        if (watchers.get(userId) != null) {
            // You can't watch a game if you already watch it
            return;
        }
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            GameWatcher gameWatcher = new GameWatcher(userId, game, false);
            watchers.put(userId, gameWatcher);
            gameWatcher.init();
            user.addGameWatchInfo(game.getId());
            ChatManager.getInstance().broadcast(chatId, user.getName(), " has started watching", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS);
        }
    }

    public void stopWatching(UUID userId) {
        watchers.remove(userId);
        User user = UserManager.getInstance().getUser(userId);
        if (user != null) {
            ChatManager.getInstance().broadcast(chatId, user.getName(), " has stopped watching", MessageColor.BLUE, true, ChatMessage.MessageType.STATUS);
        }
    }

//    public void removeUser(UUID userId) {
//        UUID playerId = userPlayerMap.get(userId);
//        if (playerId != null) {
//            GameSession gameSession = gameSessions.get(playerId);
//            if (gameSession != null) {
//                gameSession.setKilled();
//                gameSessions.remove(playerId);
//                quitMatch(userId);
//                userPlayerMap.remove(userId);
//            }
//        }
//        GameWatcher gameWatcher = watchers.get(userId);
//        if (gameWatcher != null) {
//            gameWatcher.setKilled();
//            watchers.remove(userId);
//        }
//    }

    public void quitMatch(UUID userId) {
        UUID playerId = getPlayerId(userId);
        if (playerId != null) {
            GameSession gameSession = gameSessions.get(playerId);
            if (gameSession != null) {
                gameSession.quitGame();
            }
        }
    }

    public void sendPlayerAction(PlayerAction playerAction, UUID userId) {
        switch(playerAction) {
            case UNDO:
                game.undo(getPlayerId(userId));
                break;
            case CONCEDE:
                game.concede(getPlayerId(userId));
                break;
            case MANA_AUTO_PAYMENT_OFF:
                game.setManaPoolMode(getPlayerId(userId), false);
                break;
            case MANA_AUTO_PAYMENT_ON:
                game.setManaPoolMode(getPlayerId(userId), true);
                break;
            default:        
                game.sendPlayerAction(playerAction, getPlayerId(userId));
        }
    }
    

    public void cheat(UUID userId, UUID playerId, DeckCardLists deckList) {
        Deck deck;
        try {
            deck = Deck.load(deckList, false, false);
            game.loadCards(deck.getCards(), playerId);
            for (Card card: deck.getCards()) {
                card.putOntoBattlefield(game, Zone.OUTSIDE, null, playerId);
            }
        } catch (GameException ex) {
            logger.warn(ex.getMessage());
        }
        addCardsForTesting(game);
        updateGame();
    }

    public boolean cheat(UUID userId, UUID playerId, String cardName) {
        CardInfo cardInfo = CardRepository.instance.findCard(cardName);
        Card card = cardInfo != null ? cardInfo.getCard() : null;
        if (card != null) {
            Set<Card> cards = new HashSet<>();
            cards.add(card);
            game.loadCards(cards, playerId);
            card.moveToZone(Zone.HAND, null, game, false);
            return true;
        } else {
            return false;
        }
    }

    public void timeout(UUID userId) {
        if (userPlayerMap.containsKey(userId)) {
            String sb = game.getPlayer(userPlayerMap.get(userId)).getName() +
                    " has timed out (player had priority and was not active for " +
                    ConfigSettings.getInstance().getMaxSecondsIdle() + " seconds ) - Auto concede.";
            ChatManager.getInstance().broadcast(chatId, "", sb, MessageColor.BLACK, true, MessageType.STATUS);
            game.idleTimeout(getPlayerId(userId));
        }
    }

    public void endGame(final String message) throws MageException {
        for (final GameSession gameSession: gameSessions.values()) {
            gameSession.gameOver(message);
            gameSession.removeGame();
        }
        for (final GameWatcher gameWatcher: watchers.values()) {
            gameWatcher.gameOver(message);
        }
        TableManager.getInstance().endGame(tableId);
    }

    public UUID getSessionId() {
        return this.gameSessionId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void sendPlayerUUID(UUID userId, final UUID data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerUUID(data);
            }
        });
    }

    public void sendPlayerString(UUID userId, final String data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerString(data);
            }
        });
    }

    public void sendPlayerManaType(UUID userId, final UUID manaTypePlayerId, final ManaType data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerManaType(data, manaTypePlayerId);
            }
        });
    }

    public void sendPlayerBoolean(UUID userId, final Boolean data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerBoolean(data);
            }
        });

    }

    public void sendPlayerInteger(UUID userId, final Integer data) {
        sendMessage(userId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).sendPlayerInteger(data);
            }
        });

    }

    private synchronized void updateGame() {
        if (!timers.isEmpty()) {
            for (Player player: game.getState().getPlayers().values()) {
                PriorityTimer timer = timers.get(player.getId());
                if (timer != null) {
                    player.setPriorityTimeLeft(timer.getCount());
                }
            }
        }
        for (final GameSession gameSession: gameSessions.values()) {
            gameSession.update();
        }
        for (final GameWatcher gameWatcher: watchers.values()) {
            gameWatcher.update();
        }
    }

    private synchronized void endGameInfo() {
        Table table = TableManager.getInstance().getTable(tableId);
        if (table != null) {
            if (table.getMatch() != null) {
                for (final GameSession gameSession: gameSessions.values()) {
                    gameSession.endGameInfo(table);
                }
            }
        }
        // TODO: inform watchers
//        for (final GameWatcher gameWatcher: watchers.values()) {
//                gameWatcher.update();
//        }
    }

    private synchronized void ask(UUID playerId, final String question) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).ask(question);
            }
        });

    }

    private synchronized void chooseAbility(UUID playerId, final String objectName, final List<? extends Ability> choices) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).chooseAbility(new AbilityPickerView(objectName, choices));
            }
        });
    }

    private synchronized void choosePile(UUID playerId, final String message, final List<? extends Card> pile1, final List<? extends Card> pile2) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).choosePile(message, new CardsView(pile1), new CardsView(pile2));
            }
        });
    }

    private synchronized void chooseMode(UUID playerId, final Map<UUID, String> modes) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).chooseAbility(new AbilityPickerView(modes));
            }
        });
    }

    private synchronized void choose(UUID playerId, final String message, final Set<String> choices) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).choose(message, choices);
            }
        });
    }

    private synchronized void target(UUID playerId, final String question, final Cards cards, final List<Permanent> perms, final Set<UUID> targets, final boolean required, final Map<String, Serializable> options) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                if (cards != null) {
                    getGameSession(playerId).target(question, new CardsView(cards.getCards(game)), targets, required, options);
                } else if (perms != null) {
                    CardsView permsView = new CardsView();
                    for (Permanent perm: perms) {
                        permsView.put(perm.getId(), new PermanentView(perm, game.getCard(perm.getId()), playerId, game));
                    }
                    getGameSession(playerId).target(question, permsView, targets, required, options);
                } else {
                    getGameSession(playerId).target(question, new CardsView(), targets, required, options);
                }
            }
        });

    }

    private synchronized void target(UUID playerId, final String question, final Collection<? extends Ability> abilities, final boolean required, final Map<String, Serializable> options) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).target(question, new CardsView(abilities, game), null, required, options);
            }
        });
    }

    private synchronized void select(final UUID playerId, final String message, final Map<String, Serializable> options) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).select(message, options);
            }
        });
    }

    private synchronized void playMana(UUID playerId, final String message) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).playMana(message);
            }
        });
    }

    private synchronized void playXMana(UUID playerId, final String message) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).playXMana(message);
                }
        });
    }

    private synchronized void amount(UUID playerId, final String message, final int min, final int max) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).getAmount(message, min, max);
            }
        });
    }

    private void informOthers(UUID playerId) throws MageException {
        StringBuilder message = new StringBuilder();
        if (game.getStep() != null) {
            message.append(game.getStep().getType().toString()).append(" - ");
        }
        message.append("Waiting for ").append(game.getPlayer(playerId).getName());
        for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
            if (!entry.getKey().equals(playerId)) {
                entry.getValue().inform(message.toString());
            }
        }
        for (final GameWatcher watcher: watchers.values()) {
            watcher.inform(message.toString());
        }
    }

    private void informOthers(List<UUID> players) throws MageException {
        // first player is always original controller
        Player controller = null;
        if (players != null && players.size() > 0) {
            controller = game.getPlayer(players.get(0));
        }
        if (controller == null || game.getStep() == null || game.getStep().getType() == null) {
            return;
        }
        final String message = new StringBuilder(game.getStep().getType().toString()).append(" - Waiting for ").append(controller.getName()).toString();
        for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
            boolean skip = false;
            for (UUID uuid : players) {
                if (entry.getKey().equals(uuid)) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                entry.getValue().inform(message);
            }
        }
        for (final GameWatcher watcher: watchers.values()) {
            watcher.inform(message);
        }
    }

    private synchronized void informPersonal(UUID playerId, final String message) throws MageException {
        perform(playerId, new Command() {
            @Override
            public void execute(UUID playerId) {
                getGameSession(playerId).informPersonal(message);
            }
        });
    }

    private void error(String message, Exception ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(message).append(ex.toString());
        sb.append("\nServer version: ").append(Main.getVersion().toString());
        sb.append("\n");
        for (StackTraceElement e: ex.getStackTrace()) {
            sb.append(e.toString()).append("\n");
        }
        for (final Entry<UUID, GameSession> entry: gameSessions.entrySet()) {
            entry.getValue().gameError(sb.toString());
        }
    }

    public GameView getGameView(UUID playerId) {
        return getGameSession(playerId).getGameView();
    }

    @Override
    public void gameResult(String result) {
        try {
            endGame(result);
        } catch (MageException ex) {
            logger.fatal("Game Result error", ex);
        }
    }

    public boolean saveGame() {
        try {
            OutputStream file = new FileOutputStream("saved/" + game.getId().toString() + ".game");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(new GZIPOutputStream(buffer));
            try {
                output.writeObject(game);
                output.writeObject(game.getGameStates());
            }
            finally {
                output.close();
            }
            logger.debug("Saved game:" + game.getId());
            return true;
        }
        catch(IOException ex) {
            logger.fatal("Cannot save game.", ex);
        }
        return false;
    }

    /**
     * Adds cards in player's hands that are specified in config/init.txt.
     */
    private void addCardsForTesting(Game game) {
        SystemUtil.addCardsForTesting(game);
    }

    private void perform(UUID playerId, Command command) throws MageException {
        perform(playerId, command, true);
    }

    private void perform(UUID playerId, Command command, boolean informOthers) throws MageException {
        if (game.getPlayer(playerId).isGameUnderControl()) {
            if (gameSessions.containsKey(playerId)) {
                command.execute(playerId);
            }
            if (informOthers) {
                informOthers(playerId);
            }
        } else {
            List<UUID> players = Splitter.split(game, playerId);
            for (UUID uuid : players) {
                if (gameSessions.containsKey(uuid)) {
                    command.execute(uuid);
                }
            }
            if (informOthers) {
                informOthers(players);
            }
        }
    }

    private void sendMessage(UUID userId, Command command) {
        final UUID playerId = userPlayerMap.get(userId);
        // player has game under control (is not cotrolled by other player)
        if (game.getPlayer(playerId).isGameUnderControl()) {
                // if it's your priority (or game not started yet in which case it will be null)
                // then execute only your action
                if (game.getPriorityPlayerId() == null || game.getPriorityPlayerId().equals(playerId)) {
                    if (gameSessions.containsKey(playerId)) {
                        command.execute(playerId);
                    }
                } else {
                    // otherwise execute the action under other player's control
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        for (UUID controlled : player.getPlayersUnderYourControl()) {
                            if (gameSessions.containsKey(controlled) && game.getPriorityPlayerId().equals(controlled)) {
                                command.execute(controlled);
                            }
                        }
                    }
                    // else player has no priority to do something, so ignore the command
                    // e.g. you click at one of your cards, but you can't play something at that moment
                }
        } else {
            // ignore - no control over the turn
        }
    }

    interface Command {
        void execute(UUID player);
    }

    private GameSession getGameSession(UUID playerId) {
        if (!timers.isEmpty()) {
            Player player = game.getState().getPlayer(playerId);
            PriorityTimer timer = timers.get(playerId);
            if (timer != null) {
                //logger.warn("Timer Player " + player.getName()+ " " + player.getPriorityTimeLeft() + " Timer: " + timer.getCount());
                player.setPriorityTimeLeft(timer.getCount());
            }
        }
        return gameSessions.get(playerId);
    }

    public String getPlayerNameList() {
        StringBuilder sb = new StringBuilder(" [");
        for (UUID playerId: userPlayerMap.values()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                sb.append(player.getName()).append("(Left=").append(player.hasLeft() ? "Y":"N").append(") ");
            } else {
                sb.append("player missing: ").append(playerId).append(" ");
            }
        }
        return sb.append("]").toString();
    }
}
