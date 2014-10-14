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

package mage.game;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.GemstoneCavernsAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffects;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.continious.SourceEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.DelayedTriggeredManaAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.actions.impl.MageAction;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.combat.Combat;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.command.Emblem;
import mage.game.events.*;
import mage.game.events.TableEvent.EventType;
import mage.game.permanent.Battlefield;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.permanent.PermanentImpl;
import mage.game.stack.Spell;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.game.turn.Phase;
import mage.game.turn.Step;
import mage.game.turn.Turn;
import mage.players.Player;
import mage.players.PlayerList;
import mage.players.Players;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.util.functions.ApplyToPermanent;
import mage.watchers.common.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import mage.abilities.keyword.MorphAbility;

public abstract class GameImpl implements Game, Serializable {

    private static final transient Logger logger = Logger.getLogger(GameImpl.class);

    private static final FilterPermanent filterAura = new FilterPermanent();
    private static final FilterPermanent filterEquipment = new FilterPermanent();
    private static final FilterPermanent filterFortification = new FilterPermanent();
    private static final FilterPermanent filterLegendary = new FilterPermanent();

    static {
        filterAura.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAura.add(new SubtypePredicate("Aura"));

        filterEquipment.add(new CardTypePredicate(CardType.ARTIFACT));
        filterEquipment.add(new SubtypePredicate("Equipment"));

        filterFortification.add(new CardTypePredicate(CardType.ARTIFACT));
        filterFortification.add(new SubtypePredicate("Fortification"));

        filterLegendary.add(new SupertypePredicate("Legendary"));
    }

    private static Random rnd = new Random();


    private transient Object customData;
    protected boolean simulation = false;

    protected final UUID id;
    protected boolean ready;
    protected transient TableEventSource tableEventSource = new TableEventSource();
    protected transient PlayerQueryEventSource playerQueryEventSource = new PlayerQueryEventSource();

    protected Map<UUID, Card> gameCards = new HashMap<>();
    protected Map<Zone,HashMap<UUID, MageObject>> lki = new EnumMap<>(Zone.class);
    protected Map<UUID, Map<Integer, MageObject>> lkiExtended = new HashMap<>();
    protected Map<Zone,HashMap<UUID, MageObject>> shortLivingLKI = new EnumMap<>(Zone.class);

    protected GameState state;
    private transient Stack<Integer> savedStates = new Stack<>();
    protected transient GameStates gameStates = new GameStates();

    protected Date startTime;
    protected Date endTime;
    protected UUID startingPlayerId;
    protected UUID winnerId;

    protected RangeOfInfluence range;
    protected int freeMulligans;
    protected Map<UUID, Integer> usedFreeMulligans = new LinkedHashMap<>();
    protected MultiplayerAttackOption attackOption;
    protected GameOptions gameOptions;
    protected String startMessage;

    public static volatile int copyCount = 0;
    public static volatile long copyTime = 0;

    // private final transient LinkedList<MageAction> actions;
    private Player scorePlayer;
    // private int score = 0;
    private Player losingPlayer;
    private boolean stateCheckRequired = false;

    // used to indicate that currently applied replacement effects have to check for scope relevance (614.12 13/01/18)
    private boolean scopeRelevant = false;
    private boolean saveGame = false;
    private int priorityTime;

    public GameImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans) {
        this.id = UUID.randomUUID();
        this.range = range;
        this.freeMulligans = freeMulligans;
        this.attackOption = attackOption;
        this.state = new GameState();
        // this.actions = new LinkedList<MageAction>();
    }

    public GameImpl(final GameImpl game) {
        long t1 = 0;
        if (logger.isDebugEnabled()) {
            t1 = System.currentTimeMillis();
        }
        this.id = game.id;
        this.ready = game.ready;
        this.startingPlayerId = game.startingPlayerId;
        this.winnerId = game.winnerId;
        this.range = game.range;
        this.freeMulligans = game.freeMulligans;
        this.attackOption = game.attackOption;
        this.state = game.state.copy();
        // Ai simulation modifies e.g. zoneChangeCounter so copy is needed if AI active
        for (Map.Entry<UUID, Card> entry: game.gameCards.entrySet()) {
            this.gameCards.put(entry.getKey(), entry.getValue().copy());
        }
        this.simulation = game.simulation;
        this.gameOptions = game.gameOptions;
        this.lki.putAll(game.lki);
        this.lkiExtended.putAll(game.lkiExtended);
        this.shortLivingLKI.putAll(game.shortLivingLKI);
        if (logger.isDebugEnabled()) {
            copyCount++;
            copyTime += (System.currentTimeMillis() - t1);
        }
//        this.actions = new LinkedList<MageAction>();
        this.stateCheckRequired = game.stateCheckRequired;
        this.scorePlayer = game.scorePlayer;
        this.scopeRelevant = game.scopeRelevant;
        this.priorityTime = game.priorityTime;
        this.saveGame = game.saveGame;
    }

    @Override
    public boolean isSimulation() {
        return simulation;
    }

    @Override
    public void setSimulation(boolean simulation) {
        this.simulation = simulation;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Object getCustomData() {
        return customData;
    }

    @Override
    public void setCustomData(Object data) {
        this.customData = data;
    }

    @Override
    public GameOptions getOptions() {
        return gameOptions;
    }

    @Override
    public void loadCards(Set<Card> cards, UUID ownerId) {
        for (Card card: cards) {
            if (card instanceof PermanentCard) {
                card = ((PermanentCard)card).getCard();
            }
            card.setOwnerId(ownerId);
            card.setFaceDown(false); // can be set face dwon from previous game
            gameCards.put(card.getId(), card);
            state.addCard(card);
            if (card.isSplitCard()) {
                Card leftCard = ((SplitCard)card).getLeftHalfCard();
                leftCard.setOwnerId(ownerId);
                gameCards.put(leftCard.getId(), leftCard);
                state.addCard(leftCard);
                Card rightCard = ((SplitCard)card).getRightHalfCard();
                rightCard.setOwnerId(ownerId);
                gameCards.put(rightCard.getId(), rightCard);
                state.addCard(rightCard);
            }
        }
    }

    @Override
    public Collection<Card> getCards() {
        return gameCards.values();
    }

    @Override
    public void addPlayer(Player player, Deck deck) throws GameException {
        player.useDeck(deck, this);
        state.addPlayer(player);
    }

    @Override
    public RangeOfInfluence getRangeOfInfluence() {
        return range;
    }

    @Override
    public MultiplayerAttackOption getAttackOption() {
        return attackOption;
    }

    @Override
    public Player getPlayer(UUID playerId) {
        if (playerId == null) {
            return null;
        }
        return state.getPlayer(playerId);
    }

   @Override
    public MageObject getObject(UUID objectId) {
        if (objectId == null) {
            return null;
        }
        MageObject object;
        if (state.getBattlefield().containsPermanent(objectId)) {
            object = state.getBattlefield().getPermanent(objectId);
            state.setZone(objectId, Zone.BATTLEFIELD);
            return object;
        }
        for (StackObject item: state.getStack()) {
            if (item.getId().equals(objectId)) {
                state.setZone(objectId, Zone.STACK);
                return item;
            }
            if (item.getSourceId().equals(objectId) && item instanceof Spell) {
                return item;
            }
        }

        for (CommandObject commandObject : state.getCommand()) {
            if (commandObject instanceof Commander && commandObject.getId().equals(objectId)) {
                return commandObject;
            }
        }

        object = getCard(objectId);

        if (object == null) {
            for (CommandObject commandObject : state.getCommand()) {
                if (commandObject.getId().equals(objectId)) {
                    return commandObject;
                }
            }
            // can be an ability of a sacrificed Token trying to get it's source object
            object = getLastKnownInformation(objectId, Zone.BATTLEFIELD);
        }

        return object;
    }

    @Override
    public MageObject getEmblem(UUID objectId) {
        if (objectId == null) {
            return null;
        }
        for (CommandObject commandObject : state.getCommand()) {
            if (commandObject.getId().equals(objectId)) {
                return commandObject;
            }
        }
        return null;
    }

    @Override
    public UUID getControllerId(UUID objectId) {
        if (objectId == null) {
            return null;
        }
        MageObject object = getObject(objectId);
        if (object != null) {
            if (object instanceof Permanent) {
                return ((Permanent)object).getControllerId();
            }
            if (object instanceof Card) {
                return ((Card)object).getOwnerId();
            }
        }
        return null;
    }

    @Override
    public Permanent getPermanent(UUID permanentId) {
        return state.getPermanent(permanentId);
    }

    @Override
    public Permanent getPermanentOrLKIBattlefield(UUID permanentId) {
        Permanent permanent = state.getPermanent(permanentId);
        if (permanent == null) {
            permanent = (Permanent) this.getLastKnownInformation(permanentId, Zone.BATTLEFIELD);
        }
        return permanent;
    }

    @Override
    public Card getCard(UUID cardId) {
        if (cardId == null) {
            return null;
        }
        return gameCards.get(cardId);
    }

    @Override
    public Ability getAbility(UUID abilityId, UUID sourceId) {
        MageObject object = getObject(sourceId);
        if (object != null) {
            return object.getAbilities().get(abilityId);
        }
        return null;
    }

//    @Override
//    public Zone getZone(UUID objectId) {
//        return state.getZone(objectId);
//    }

    @Override
    public void setZone(UUID objectId, Zone zone) {
        state.setZone(objectId, zone);
    }

    @Override
    public GameStates getGameStates() {
        return gameStates;
    }

    @Override
    public void loadGameStates(GameStates states) {
        this.gameStates = states;
    }

    @Override
    public void saveState(boolean bookmark) {
        if (!simulation && gameStates != null) {
            if (bookmark || saveGame) {
                gameStates.save(state);
            }
        }
    }

    /**
     * Starts check if game is over or
     * if playerId is given let the player concede.
     *
     * @param playerId
     * @return
     */
    @Override
    public synchronized boolean gameOver(UUID playerId) {
        if (playerId == null) {
            boolean result = checkIfGameIsOver();
            return result;
        }  else {
            logger.debug("Game over for player Id: " + playerId + " gameId " + getId());
            leave(playerId);
            return true;
        }
    }


    private boolean checkIfGameIsOver() {
        if (state.isGameOver()) {
            return true;
        }
        int remainingPlayers = 0;
        int numLosers = 0;
        for (Player player: state.getPlayers().values()) {
            if (!player.hasLeft()) {
                remainingPlayers++;
            }
            if (player.hasLost()) {
                numLosers++;
            }
        }
        if (remainingPlayers <= 1 || numLosers >= state.getPlayers().size() - 1) {
            end();
            if (remainingPlayers == 0 && logger.isDebugEnabled()) {
                logger.debug("DRAW for gameId: " + getId());
                for (Player player: state.getPlayers().values()) {
                    logger.debug("-- " + player.getName() + " left: " + (player.hasLeft() ? "Y":"N") + " lost: " + (player.hasLost()? "Y":"N"));
                }                
            }
            for (Player player: state.getPlayers().values()) {
                if (!player.hasLeft() && !player.hasLost()) {
                    logger.debug(new StringBuilder("Player ").append(player.getName()).append(" has won gameId: ").append(this.getId()));
                    player.won(this);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasEnded() {
        return endTime != null;
    }

    @Override
    public boolean isADraw() {
        return hasEnded() && winnerId == null;
    }

    @Override
    public String getWinner() {
        if (winnerId == null) {
            return "Game is a draw";
        }
        return new StringBuilder("Player ").append(state.getPlayer(winnerId).getName()).append(" is the winner").toString();
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public int bookmarkState() {
        if (!simulation) {
            saveState(true);
            if (logger.isTraceEnabled()) {
                logger.trace("Bookmarking state: " + gameStates.getSize());
            }
            savedStates.push(gameStates.getSize() - 1);
            return savedStates.size();
        }
        return savedStates.size();
    }

    @Override
    public void restoreState(int bookmark) {
        if (!simulation && !this.hasEnded()) { // if player left or game is over no undo is possible - this could lead to wrong winner
            if (bookmark != 0) {
                if (!savedStates.contains(bookmark - 1)) {
                    throw new UnsupportedOperationException("It was not possible to do the requested undo operation (bookmark " + (bookmark -1) + " does not exist)");
                }
                int stateNum = savedStates.get(bookmark - 1);
                removeBookmark(bookmark);
                GameState restore = gameStates.rollback(stateNum);
                if (restore != null) {
                    state.restore(restore);
                }
            }
        }
    }

    @Override
    public void removeBookmark(int bookmark) {
        if (!simulation) {
            if (bookmark != 0) {
                while (savedStates.size() > bookmark) {
                    savedStates.pop();
                }
                gameStates.remove(bookmark);
            }
        }
    }

    private void clearAllBookmarks() {
        if (!simulation) {
            while (!savedStates.isEmpty()) {
                savedStates.pop();
            }
            gameStates.remove(0);
            for (Player player : getPlayers().values()) {
                player.setStoredBookmark(-1);
            }
        }
    }

    @Override
    public int getSavedStateSize() {
        if (!simulation) {
            return savedStates.size();
        }
        return 0;
    }

    @Override
    public void start(UUID choosingPlayerId) {
        start(choosingPlayerId, this.gameOptions != null ? gameOptions : GameOptions.getDefault());
    }

    @Override
    public void cleanUp() {
        gameCards.clear();
    }

    @Override
    public void start(UUID choosingPlayerId, GameOptions options) {
        startTime = new Date();
        this.gameOptions = options;
        if (state.getPlayers().values().iterator().hasNext()) {
            scorePlayer = state.getPlayers().values().iterator().next();
            init(choosingPlayerId, options);
            play(startingPlayerId);
        }
    }

    @Override
    public void resume() {
        PlayerList players = state.getPlayerList(state.getActivePlayerId());
        Player player = getPlayer(players.get());
        boolean wasPaused = state.isPaused();
        state.resume();
        if (!gameOver(null)) {
            fireInformEvent(new StringBuilder("Turn ").append(state.getTurnNum()).toString());
            if (checkStopOnTurnOption()) {
                return;
            }
            state.getTurn().resumePlay(this, wasPaused);
            if (!isPaused() && !gameOver(null)) {
                endOfTurn();
                player = players.getNext(this);
                state.setTurnNum(state.getTurnNum() + 1);
            }
        }
        play(player.getId());
    }

    protected void play(UUID nextPlayerId) {
        if (!isPaused() && !gameOver(null)) {
            PlayerList players = state.getPlayerList(nextPlayerId);
            Player player = getPlayer(players.get());
            while (!isPaused() && !gameOver(null)) {
                playExtraTurns(player);
                GameEvent event = new GameEvent(GameEvent.EventType.PLAY_TURN, null, null, player.getId());
                if (!replaceEvent(event)) {
                    if (!playTurn(player)) {
                        break;
                    }
                    state.setTurnNum(state.getTurnNum() + 1);
                }
                playExtraTurns(player);
                player = players.getNext(this);
            }
        }
        if (gameOver(null)) {
            winnerId = findWinnersAndLosers();
            logger.info("GAME ended  gameId: " + this.getId());
        }
    }

    private void playExtraTurns(Player player) {
        //20091005 - 500.7
        UUID extraTurnId = getNextExtraTurn(player.getId());
        while (extraTurnId != null) {
            GameEvent event = new GameEvent(GameEvent.EventType.PLAY_TURN, null, null, player.getId());
            if (!replaceEvent(event)) {
                state.setExtraTurn(true);
                state.setTurnId(extraTurnId);
                informPlayers(player.getName() + " takes an extra turn");
                playTurn(player);
                state.setTurnNum(state.getTurnNum() + 1);
            }
            extraTurnId = getNextExtraTurn(player.getId());
        }
        state.setTurnId(null);
        state.setExtraTurn(false);
        
    }
    
    private UUID getNextExtraTurn(UUID playerId) {
        Player player = this.getPlayer(playerId);
        if (player != null) {            
            boolean checkForExtraTurn = true;
            while(checkForExtraTurn) {
                UUID extraTurnId = getState().getTurnMods().getExtraTurn(playerId);
                if (extraTurnId != null) {
                    GameEvent event = new GameEvent(GameEvent.EventType.EXTRA_TURN, extraTurnId, null, playerId);
                    if (!replaceEvent(event)) {
                        return extraTurnId;
                    }                    
                } else {
                    checkForExtraTurn = false;
                }
                if (!player.isInGame()) {
                    return null;
                }
            }
        }
        return null;
    }
    
    private boolean playTurn(Player player) {
        this.logStartOfTurn(player);
        if (checkStopOnTurnOption()) {
            return false;
        }
        state.setActivePlayerId(player.getId());
        player.becomesActivePlayer();
        state.getTurn().play(this, player.getId());
        if (isPaused() || gameOver(null)) {
            return false;
        }
        endOfTurn();

        return true;
    }


    private void logStartOfTurn(Player player) {
        StringBuilder sb = new StringBuilder("Turn ").append(state.getTurnNum()).append(" ");
        sb.append(player.getName());
        sb.append(" (");
        int delimiter = this.getPlayers().size() - 1;
        for (Player gamePlayer : this.getPlayers().values()) {
            sb.append(gamePlayer.getLife());
            int poison = gamePlayer.getCounters().getCount(CounterType.POISON);
            if (poison > 0) {
                sb.append("[P:").append(poison).append("]");
            }
            if (delimiter > 0) {
                sb.append(" - ");
                delimiter--;
            }
        }
        sb.append(")");
        fireStatusEvent(sb.toString(), true);
    }

    private boolean checkStopOnTurnOption() {
        if (gameOptions.stopOnTurn != null && gameOptions.stopAtStep == PhaseStep.UNTAP) {
            if (gameOptions.stopOnTurn.equals(state.getTurnNum())) {
                winnerId = null; //DRAW
                saveState(false);
                return true;
            }
        }
        return false;
    }

    protected void init(UUID choosingPlayerId, GameOptions gameOptions) {
        for (Player player: state.getPlayers().values()) {
            player.beginTurn(this);
            // init only if match is with timer (>0) and time left was not set yet (== MAX_VALUE).
            // otherwise the priorityTimeLeft is set in {@link MatchImpl.initGame)
            if (priorityTime > 0 && player.getPriorityTimeLeft() == Integer.MAX_VALUE) {
                initTimer(player.getId());
            }
        }
        if (startMessage == null || startMessage.isEmpty()) {
            startMessage = "Game has started";
        }
        fireStatusEvent(startMessage, false);

        saveState(false);

        //20091005 - 103.1
        if (!gameOptions.skipInitShuffling) { //don't shuffle in test mode for card injection on top of player's libraries
            for (Player player: state.getPlayers().values()) {
                player.shuffleLibrary(this);
            }
        }

        //20091005 - 103.2
        TargetPlayer targetPlayer = new TargetPlayer();
        targetPlayer.setTargetName("starting player");
        Player choosingPlayer = null;
        if (choosingPlayerId != null) {
            choosingPlayer = this.getPlayer(choosingPlayerId);
        }
        if (choosingPlayer == null) {
            choosingPlayer = getPlayer(pickChoosingPlayer());
        }

        if (choosingPlayer != null && choosingPlayer.choose(Outcome.Benefit, targetPlayer, null, this)) {
            startingPlayerId = targetPlayer.getTargets().get(0);
            Player startingPlayer = state.getPlayer(startingPlayerId);
            StringBuilder message = new StringBuilder(choosingPlayer.getName()).append(" chooses that ");
            if (choosingPlayer.getId().equals(startingPlayerId)) {
                message.append("he or she");
            } else {
                message.append(startingPlayer.getName());
            }
            message.append(" takes the first turn");

            this.informPlayers(message.toString());
        } else {
            // not possible to choose starting player, stop here
            return;
        }


        //20091005 - 103.3
        for (UUID playerId: state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if (!gameOptions.testMode || player.getLife() == 0) {
                player.initLife(this.getLife());
            }
            if (!gameOptions.testMode) {
                player.drawCards(7, this);
            }
        }

        //20091005 - 103.4
        List<UUID> keepPlayers = new ArrayList<>();
        List<UUID> mulliganPlayers = new ArrayList<>();
        do {
            mulliganPlayers.clear();
            for (UUID playerId : state.getPlayerList(startingPlayerId)) {
                if (!keepPlayers.contains(playerId)) {
                    Player player = getPlayer(playerId);
                    boolean keep = true;
                    while (true) {
                        if (player.getHand().isEmpty()) {
                            break;
                        }
                        GameEvent event = new GameEvent(GameEvent.EventType.CAN_TAKE_MULLIGAN, null, null, playerId);
                        if (!replaceEvent(event)) {
                            fireEvent(event);
                            if (player.chooseMulligan(this)) {
                                keep = false;
                            }
                            break;
                        }
                    }
                    if (keep) {
                        endMulligan(player.getId());
                        keepPlayers.add(playerId);
                        fireInformEvent(player.getName() + " keeps hand");
                    } else {
                        mulliganPlayers.add(playerId);
                        fireInformEvent(player.getName() + " decides to take mulligan");
                    }
                }
            }
            for (UUID mulliganPlayerId : mulliganPlayers) {
                mulligan(mulliganPlayerId);
            }
            saveState(false);
        } while (!mulliganPlayers.isEmpty());

        // add watchers
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            state.getWatchers().add(new PlayerDamagedBySourceWatcher(playerId));
        }
        state.getWatchers().add(new MorbidWatcher());
        state.getWatchers().add(new CastSpellLastTurnWatcher());
        state.getWatchers().add(new SpellsCastThisTurnWatcher());
        state.getWatchers().add(new MiracleWatcher());
        state.getWatchers().add(new SoulbondWatcher());
        state.getWatchers().add(new PlayerLostLifeWatcher());

        //20100716 - 103.5
        for (UUID playerId: state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            for (Card card: player.getHand().getCards(this)) {
                if (player.getHand().contains(card.getId())) {
                    if (card.getAbilities().containsKey(LeylineAbility.getInstance().getId())) {
                        if (player.chooseUse(Outcome.PutCardInPlay, "Do you wish to put " + card.getName() + " on the battlefield?", this)) {
                            card.putOntoBattlefield(this, Zone.HAND, null, player.getId());
                        }
                    }
                    for (Ability ability: card.getAbilities()) {
                        if (ability instanceof ChancellorAbility) {
                            if (player.chooseUse(Outcome.PutCardInPlay, "Do you wish to reveal " + card.getName() + "?", this)) {
                                Cards cards = new CardsImpl();
                                cards.add(card);
                                player.revealCards("Revealed", cards, this);
                                ability.resolve(this);
                            }
                        }
                        if (ability instanceof GemstoneCavernsAbility) {
                            if (!playerId.equals(startingPlayerId)) {
                                if (player.chooseUse(Outcome.PutCardInPlay, "Do you wish to put " + card.getName() + " into play?", this)) {
                                    Cards cards = new CardsImpl();
                                    cards.add(card);
                                    player.revealCards("Revealed", cards, this);
                                    ability.resolve(this);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected UUID findWinnersAndLosers() {
        UUID winnerIdFound = null;
        for (Player player: state.getPlayers().values()) {
            if (player.hasWon()) {
                logger.debug(player.getName() + " has won gameId: " + getId());
                winnerIdFound = player.getId();
                break;
            }
            if (!player.hasLost() && !player.hasLeft()) {
                logger.debug(player.getName() + " has not lost so he won gameId: " + this.getId());
                player.won(this);
                winnerIdFound = player.getId();
                break;
            }
        }
        for (Player player: state.getPlayers().values()) {
            if (winnerIdFound != null && !player.getId().equals(winnerIdFound) && !player.hasLost()) {
                player.lost(this);
            }
        }
        return winnerIdFound;
    }

    protected void endOfTurn() {
        for (Player player: getPlayers().values()) {
            player.endOfTurn(this);
        }
        state.getWatchers().reset();
    }

    protected UUID pickChoosingPlayer() {
        UUID[] players = getPlayers().keySet().toArray(new UUID[0]);
        UUID playerId = players[rnd.nextInt(players.length)];
        fireInformEvent(state.getPlayer(playerId).getName() + " won the toss");
        return playerId;
    }

    @Override
    public void pause() {
        state.pause();
    }

    @Override
    public boolean isPaused() {
        return state.isPaused();
    }

    @Override
    public void end() {
        if (!state.isGameOver()) {
            endTime = new Date();
            state.endGame();
            for (Player player: state.getPlayers().values()) {
                player.abort();
            }
        }
    }

    @Override
    public void addTableEventListener(Listener<TableEvent> listener) {
        tableEventSource.addListener(listener);
    }

    @Override
    public int mulliganDownTo(UUID playerId) {
        Player player = getPlayer(playerId);
        int deduction = 1;
        if (freeMulligans > 0) {
            if (usedFreeMulligans != null && usedFreeMulligans.containsKey(player.getId())) {
                int used = usedFreeMulligans.get(player.getId()).intValue();
                if (used < freeMulligans ) {
                    deduction = 0;
                }
            } else {
                deduction = 0;
            }
        }
        return player.getHand().size() - deduction;
    }

    @Override
    public void endMulligan(UUID playerId){
    }

    @Override
    public void mulligan(UUID playerId) {
        Player player = getPlayer(playerId);
        int numCards = player.getHand().size();
        player.getLibrary().addAll(player.getHand().getCards(this), this);
        player.getHand().clear();
        player.shuffleLibrary(this);
        int deduction = 1;
        if (freeMulligans > 0) {
            if (usedFreeMulligans != null && usedFreeMulligans.containsKey(player.getId())) {
                int used = usedFreeMulligans.get(player.getId());
                if (used < freeMulligans ) {
                    deduction = 0;
                    usedFreeMulligans.put(player.getId(), used+1);
                }
            } else {
                deduction = 0;
                usedFreeMulligans.put(player.getId(), 1);
            }
        }
        fireInformEvent(new StringBuilder(player.getName())
                .append(" mulligans")
                .append(deduction == 0 ? " for free and draws ":" down to ")
                .append(Integer.toString(numCards - deduction))
                .append(numCards - deduction == 1? " card":" cards").toString());
        player.drawCards(numCards - deduction, this);
    }

    @Override
    public void quit(UUID playerId) {
        if (state != null) {
            Player player = state.getPlayer(playerId);
            if (player != null && player.isInGame()) {
                player.quit(this);
            }
        }
    }

    @Override
    public synchronized void timerTimeout(UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.timerTimeout(this);
        } else {
            logger.error(new StringBuilder("timerTimeout - player not found - playerId: ").append(playerId));
        }
    }

    @Override
    public synchronized void idleTimeout(UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.idleTimeout(this);
        } else {
            logger.error(new StringBuilder("idleTimeout - player not found - playerId: ").append(playerId));
        }
    }

    @Override
    public synchronized void concede(UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            logger.debug(new StringBuilder("Player ").append(player.getName()).append(" concedes game ").append(this.getId()));
            fireInformEvent(player.getName() + " has conceded.");
            player.concede(this);
        }
    }

    @Override
    public synchronized void undo(UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            int bookmark = player.getStoredBookmark();
            if (bookmark != -1) {
                restoreState(bookmark);
                player.setStoredBookmark(-1);
                fireUpdatePlayersEvent();
            }
        }
    }
   
    @Override
    public void sendPlayerAction(PlayerAction playerAction, UUID playerId) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.sendPlayerAction(playerAction, this);
        }                    
    }
    

    @Override
    public synchronized void setManaPoolMode(UUID playerId, boolean autoPayment) {
        Player player = state.getPlayer(playerId);
        if (player != null) {
            player.getManaPool().setAutoPayment(autoPayment);
        }
    }

    @Override
    public void playPriority(UUID activePlayerId, boolean resuming) {
        int bookmark = 0;
        clearAllBookmarks();
        try {
            while (!isPaused() && !gameOver(null) && !this.getTurn().isEndTurnRequested()) {
                if (!resuming) {
                    state.getPlayers().resetPassed();
                    state.getPlayerList().setCurrent(activePlayerId);
                }
                else {
                    state.getPlayerList().setCurrent(this.getPriorityPlayerId());
                }
                fireUpdatePlayersEvent();
                Player player;
                while (!isPaused() && !gameOver(null)) {
                    try {
                        if (bookmark == 0) {
                            bookmark = bookmarkState();
                        }
                        player = getPlayer(state.getPlayerList().get());
                        state.setPriorityPlayerId(player.getId());
                        while (!player.isPassed() && player.isInGame() && !isPaused() && !gameOver(null)) {
                            if (!resuming) {
                                // 603.3. Once an ability has triggered, its controller puts it on the stack as an object thats not a card the next time a player would receive priority
                                checkStateAndTriggered();
                                applyEffects();
                                saveState(false);
                                if (isPaused() || gameOver(null)) {
                                    return;
                                }
                                // resetPassed should be called if player performs any action
                                if (player.priority(this)) {
                                    applyEffects();
                                }
                                if (isPaused()) {
                                    return;
                                }
                            }
                            resuming = false;
                        }
                        resetShortLivingLKI();
                        resuming = false;
                        if (isPaused() || gameOver(null)) {
                            return;
                        }
                        if (allPassed()) {
                            if (!state.getStack().isEmpty()) {
                                //20091005 - 115.4
                                resolve();
                                applyEffects();
                                state.getPlayers().resetPassed();
                                fireUpdatePlayersEvent();
                                state.getRevealed().reset();
                                resetShortLivingLKI();
                                break;
                            } else {
                                resetLKI();
                                return;
                            }
                        }
                    }
                    catch (Exception ex) {
                        logger.fatal("Game exception gameId: " + getId(), ex);
                        ex.printStackTrace();
                        this.fireErrorEvent("Game exception occurred: ", ex);
                        restoreState(bookmark);
                        bookmark = 0;
                        continue;
                    }
                    state.getPlayerList().getNext();
                }
            }
        } catch (Exception ex) {
            logger.fatal("Game exception ", ex);
            this.fireErrorEvent("Game exception occurred: ", ex);
        } finally {
            resetLKI();
            clearAllBookmarks();
        }
    }

    //resolve top StackObject
    protected void resolve() {
        StackObject top = null;
        try {
            top = state.getStack().peek();
            top.resolve(this);
        } finally {
            if (top != null) {
                state.getStack().remove(top);
                rememberLKI(top.getSourceId(), Zone.STACK, top);
                if (!getTurn().isEndTurnRequested()) {
                    while (state.hasSimultaneousEvents()) {
                        state.handleSimultaneousEvent(this);
                        checkTriggered();
                    }
                }                
            }
        }
    }

    protected boolean allPassed() {
        for (Player player: state.getPlayers().values()) {
            if (!player.isPassed() && player.isInGame()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void emptyManaPools() {
        if (!replaceEvent(new GameEvent(GameEvent.EventType.EMPTY_MANA_POOLS, null, null, null))) {
            for (Player player: getPlayers().values()) {
                if (!replaceEvent(new GameEvent(GameEvent.EventType.EMPTY_MANA_POOL, player.getId(), null, player.getId()))) {
                    player.getManaPool().emptyPool();
                }
            }
        }
    }

    @Override
    public synchronized void applyEffects() {
        state.applyEffects(this);
    }

    @Override
    public void addEffect(ContinuousEffect continuousEffect, Ability source) {
        Ability newAbility = source.copy();

        ContinuousEffect newEffect = (ContinuousEffect)continuousEffect.copy();
        newEffect.newId();
        newEffect.setTimestamp();
        newEffect.init(newAbility, this);

        state.addEffect(newEffect, newAbility);
    }

    @Override
    public void addEmblem(Emblem emblem, Ability source) {
        addEmblem(emblem, source, null);
    }

    @Override
    public void addEmblem(Emblem emblem, Ability source, UUID toPlayerId) {
        Emblem newEmblem = emblem.copy();
        newEmblem.setSourceId(source.getSourceId());
        if (toPlayerId == null) {
            newEmblem.setControllerId(source.getControllerId());
        } else {
            newEmblem.setControllerId(toPlayerId);
        }
        newEmblem.assignNewId();
        newEmblem.getAbilities().newId();
        for (Ability ability : newEmblem.getAbilities()) {
            ability.setSourceId(newEmblem.getId());
        }
        state.addCommandObject(newEmblem);
    }


    @Override
    public void addCommander(Commander commander){
        state.addCommandObject(commander);
    }

    @Override
    public void addPermanent(Permanent permanent) {
        getBattlefield().addPermanent(permanent);
    }

    @Override
    public Permanent copyPermanent(Permanent copyFromPermanent, Permanent copyToPermanent, Ability source, ApplyToPermanent applier) {
        return copyPermanent(Duration.Custom, copyFromPermanent, copyToPermanent, source, applier);
    }

    @Override
    public Permanent copyPermanent(Duration duration, Permanent copyFromPermanent, Permanent copyToPermanent, Ability source, ApplyToPermanent applier) {
        Permanent permanent = copyFromPermanent.copy();

        //getState().addCard(permanent);
        permanent.reset(this);
        if (copyFromPermanent.isMorphCard()) {
            MorphAbility.setPermanentToMorph(permanent);
        }
        permanent.assignNewId();
        if (copyFromPermanent.isTransformed()) {
            TransformAbility.transform(permanent, copyFromPermanent.getSecondCardFace(), this);
        }
        applier.apply(this, permanent);

        Ability newAbility = source.copy();

        CopyEffect newEffect = new CopyEffect(duration, permanent, copyToPermanent.getId());
        newEffect.newId();
        newEffect.setTimestamp();
        newEffect.init(newAbility, this);

        // handle copies of copies
        for (Effect effect : getState().getContinuousEffects().getLayeredEffects(this)) {
            if (effect instanceof CopyEffect) {
                CopyEffect copyEffect = (CopyEffect) effect;
                // there is another copy effect that our targetPermanent copies stats from
                if (copyEffect.getSourceId().equals(copyFromPermanent.getId())) {
                    MageObject object = ((CopyEffect) effect).getTarget();
                    if (object instanceof Permanent) {
                        // so we will use original card instead of target
                        Permanent original = (Permanent)object;
                        // copy it and apply changes we need
                        original = original.copy();
                        applier.apply(this, original);
                        newEffect.setTarget(object);
                    }
                }
            }
        }

        state.addEffect(newEffect, newAbility);
        return permanent;
    }

    @Override
    public Card copyCard(Card cardToCopy, Ability source, UUID newController) {
        Card copiedCard = cardToCopy.copy();
        copiedCard.assignNewId();
        copiedCard.setControllerId(newController);
        copiedCard.setCopy(true);
        Set<Card> cards = new HashSet<>();
        cards.add(copiedCard);
        loadCards(cards, source.getControllerId());

        return copiedCard;
    }

    @Override
    public void addTriggeredAbility(TriggeredAbility ability) {
        if (ability instanceof TriggeredManaAbility || ability instanceof DelayedTriggeredManaAbility) {
            // 20110715 - 605.4
            Ability manaAbiltiy = ability.copy();
            manaAbiltiy.activate(this, false);
            manaAbiltiy.resolve(this);
        }
        else {
            TriggeredAbility newAbility = ability.copy();
            newAbility.newId();
            state.addTriggeredAbility(newAbility);
        }
    }

    @Override
    public void addDelayedTriggeredAbility(DelayedTriggeredAbility delayedAbility) {
        DelayedTriggeredAbility newAbility = delayedAbility.copy();
        newAbility.newId();
        newAbility.init(this);
        state.addDelayedTriggeredAbility(newAbility);
    }

    @Override
    public boolean checkStateAndTriggered() {
        boolean trigger = !getTurn().isEndTurnRequested();
        boolean somethingHappened = false;
        //20091005 - 115.5
        while (!isPaused() && !gameOver(null)) {
            if (!checkStateBasedActions() ) {
                // nothing happened so check triggers
                if (trigger) {
                    state.handleSimultaneousEvent(this);
                }
                if (isPaused() || gameOver(null) || !trigger  || !checkTriggered()) {
                    break;
                }
            }
            applyEffects(); // needed e.g if boost effects end and cause creatures to die
            somethingHappened = true;
        }
        return somethingHappened;
    }
    /**
     * Sets the waiting triggered abilities (if there are any)
     * to the stack in the choosen order by player
     *
     * @return
     */
    public boolean checkTriggered() {
        boolean played = false;
        for (UUID playerId: state.getPlayerList(state.getActivePlayerId())) {
            Player player = getPlayer(playerId);
            while (player.isInGame()) { // player can die or win caused by triggered abilities or leave the game
                List<TriggeredAbility> abilities = state.getTriggered(player.getId());
                if (abilities.isEmpty()) {
                    break;
                }
                // triggered abilities that don't use the stack have to be executed first (e.g. Banisher Priest Return exiled creature
                for (Iterator<TriggeredAbility> it = abilities.iterator(); it.hasNext();) {
                    TriggeredAbility triggeredAbility = it.next();
                    if (!triggeredAbility.isUsesStack()) {
                        state.removeTriggeredAbility(triggeredAbility);
                        played |= player.triggerAbility(triggeredAbility, this);
                        it.remove();
                    }
                }
                if (abilities.isEmpty()) {
                    break;
                }
                if (abilities.size() == 1) {
                    state.removeTriggeredAbility(abilities.get(0));
                    played |= player.triggerAbility(abilities.get(0), this);
                }
                else {
                    TriggeredAbility ability = player.chooseTriggeredAbility(abilities, this);
                    if (ability != null) {
                        state.removeTriggeredAbility(ability);
                        played |= player.triggerAbility(ability, this);
                    }
                }
            }
        }
        return played;
    }

    protected boolean checkStateBasedActions() {
        boolean somethingHappened = false;

        //20091005 - 704.5a/704.5b/704.5c
        for (Player player: state.getPlayers().values()) {
            if (!player.hasLost()
                    && ((player.getLife() <= 0 && player.canLoseByZeroOrLessLife())
                    || player.isEmptyDraw()
                    || player.getCounters().getCount(CounterType.POISON) >= 10)) {
                player.lost(this);
            }
        }

        List<Permanent> planeswalkers = new ArrayList<>();
        List<Permanent> legendary = new ArrayList<>();
        for (Permanent perm: getBattlefield().getAllActivePermanents()) {
            if (perm.getCardType().contains(CardType.CREATURE)) {
                //20091005 - 704.5f
                if (perm.getToughness().getValue() <= 0) {
                    if (movePermanentToGraveyardWithInfo(perm)) {
                        somethingHappened = true;
                        continue;
                    }
                }
                //20091005 - 704.5g/704.5h
                else if (perm.getToughness().getValue() <= perm.getDamage() || perm.isDeathtouched()) {
                    if (perm.destroy(null, this, false)) {
                        somethingHappened = true;
                        continue;
                    }
                }
                if (perm.getPairedCard() != null) {
                    //702.93e.: ...another player gains control
                    // ...or the creature it's paired with leaves the battlefield.
                    Permanent paired = getPermanent(perm.getPairedCard());
                    if (paired == null || !perm.getControllerId().equals(paired.getControllerId()) || paired.getPairedCard() == null) {
                        perm.setPairedCard(null);
                        if (paired != null) {
                            paired.setPairedCard(null);
                        }
                        somethingHappened = true;
                    }
                }
            } else if (perm.getPairedCard() != null) {
                //702.93e.: ...stops being a creature
                Permanent paired = getPermanent(perm.getPairedCard());
                perm.setPairedCard(null);
                if (paired != null) {
                    paired.setPairedCard(null);
                }
                somethingHappened = true;
            }
            if (perm.getCardType().contains(CardType.PLANESWALKER)) {
                //20091005 - 704.5i
                if (perm.getCounters().getCount(CounterType.LOYALTY) == 0) {
                    if (movePermanentToGraveyardWithInfo(perm)) {
                        somethingHappened = true;
                        continue;
                    }
                }
                planeswalkers.add(perm);
            }
            if (filterAura.match(perm, this)) {
                //20091005 - 704.5n, 702.14c
                if (perm.getAttachedTo() == null) {
                    Card card = this.getCard(perm.getId());
                    if (card != null && !card.getCardType().contains(CardType.CREATURE)) { // no bestow creature
                        if (movePermanentToGraveyardWithInfo(perm)) {
                            somethingHappened = true;
                        }
                    }
                }
                else {
                    Target target = perm.getSpellAbility().getTargets().get(0);
                    if (target instanceof TargetPermanent) {
                        Permanent attachedTo = getPermanent(perm.getAttachedTo());
                        if (attachedTo == null || !attachedTo.getAttachments().contains(perm.getId())) {
                            // handle bestow unattachment
                            Card card = this.getCard(perm.getId());
                            if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                                UUID wasAttachedTo = perm.getAttachedTo();
                                perm.attachTo(null, this);
                                fireEvent(new GameEvent(GameEvent.EventType.UNATTACHED, wasAttachedTo, perm.getId(), perm.getControllerId()));
                            } else {
                                if (movePermanentToGraveyardWithInfo(perm)) {
                                    somethingHappened = true;
                                }
                            }
                        }
                        else {
                            Filter auraFilter = perm.getSpellAbility().getTargets().get(0).getFilter();
                            if (auraFilter instanceof FilterControlledCreaturePermanent) {
                                if (!((FilterControlledCreaturePermanent)auraFilter).match(attachedTo, perm.getId(), perm.getControllerId(), this)
                                        || attachedTo.cantBeEnchantedBy(perm, this)) {
                                    if (movePermanentToGraveyardWithInfo(perm)) {
                                        somethingHappened = true;
                                    }
                                }
                            } else {
                                if (!auraFilter.match(attachedTo, this) || attachedTo.cantBeEnchantedBy(perm, this)) {
                                    // handle bestow unattachment
                                    Card card = this.getCard(perm.getId());
                                    if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                                        UUID wasAttachedTo = perm.getAttachedTo();
                                        perm.attachTo(null, this);
                                        fireEvent(new GameEvent(GameEvent.EventType.UNATTACHED, wasAttachedTo, perm.getId(), perm.getControllerId()));
                                    } else {
                                        if (movePermanentToGraveyardWithInfo(perm)) {
                                            somethingHappened = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if (target instanceof TargetPlayer) {
                        Player attachedToPlayer = getPlayer(perm.getAttachedTo());
                        if (attachedToPlayer == null) {
                            if (movePermanentToGraveyardWithInfo(perm)) {
                                somethingHappened = true;
                            }
                        }
                        else {
                            Filter auraFilter = perm.getSpellAbility().getTargets().get(0).getFilter();
                            if (!auraFilter.match(attachedToPlayer, this) || attachedToPlayer.hasProtectionFrom(perm, this)) {
                                if (movePermanentToGraveyardWithInfo(perm)) {
                                    somethingHappened = true;
                                }
                            }
                        }
                    }
                }
            }
            if (this.getState().isLegendaryRuleActive() && filterLegendary.match(perm, this)) {
                legendary.add(perm);
            }
            if (filterEquipment.match(perm, this)) {
                //20091005 - 704.5p, 702.14d
                if (perm.getAttachedTo() != null) {
                    Permanent creature = getPermanent(perm.getAttachedTo());
                    if (creature == null || !creature.getAttachments().contains(perm.getId())) {
                        UUID wasAttachedTo = perm.getAttachedTo();
                        perm.attachTo(null, this);
                        fireEvent(new GameEvent(GameEvent.EventType.UNATTACHED, wasAttachedTo, perm.getId(), perm.getControllerId()));
                    } else if (!creature.getCardType().contains(CardType.CREATURE) || creature.hasProtectionFrom(perm, this)) {
                        if (creature.removeAttachment(perm.getId(), this)) {
                            somethingHappened = true;
                        }
                    }
                }
            }
            if (filterFortification.match(perm, this)) {
                if (perm.getAttachedTo() != null) {
                    Permanent land = getPermanent(perm.getAttachedTo());
                    if (land == null || !land.getAttachments().contains(perm.getId())) {
                        perm.attachTo(null, this);
                    }
                    else if (!land.getCardType().contains(CardType.LAND) || land.hasProtectionFrom(perm, this)) {
                        if (land.removeAttachment(perm.getId(), this)) {
                            somethingHappened = true;
                        }
                    }
                }
            }
            //20091005 - 704.5q If a creature is attached to an object or player, it becomes unattached and remains on the battlefield.
            // Similarly, if a permanent thats neither an Aura, an Equipment, nor a Fortification is attached to an object or player,
            // it becomes unattached and remains on the battlefield.
            if (perm.getAttachments().size() > 0) {
                for (UUID attachmentId : perm.getAttachments()) {
                    Permanent attachment = getPermanent(attachmentId);
                    if (attachment != null &&
                            (attachment.getCardType().contains(CardType.CREATURE) ||
                            !(attachment.getSubtype().contains("Aura")
                               || attachment.getSubtype().contains("Equipment")
                               || attachment.getSubtype().contains("Fortification")))) {
                        if (perm.removeAttachment(attachment.getId(), this)) {
                            somethingHappened = true;
                            break;
                        }
                    }
                }
            }

            //20110501 - 704.5r
            if (perm.getCounters().containsKey(CounterType.P1P1) && perm.getCounters().containsKey(CounterType.M1M1)) {
                int p1p1 = perm.getCounters().getCount(CounterType.P1P1);
                int m1m1 = perm.getCounters().getCount(CounterType.M1M1);
                int min = Math.min(p1p1, m1m1);
                perm.getCounters().removeCounter(CounterType.P1P1, min);
                perm.getCounters().removeCounter(CounterType.M1M1, min);
            }

        }
        //201300713 - 704.5j
        // If a player controls two or more planeswalkers that share a planeswalker type, that player
        // chooses one of them, and the rest are put into their owners' graveyards.
        // This is called the "planeswalker uniqueness rule."
        if (planeswalkers.size() > 1) {  //don't bother checking if less than 2 planeswalkers in play
            for (Permanent planeswalker: planeswalkers) {
                for (String planeswalkertype: planeswalker.getSubtype()) {
                    FilterPlaneswalkerPermanent filterPlaneswalker = new FilterPlaneswalkerPermanent();
                    filterPlaneswalker.add(new SubtypePredicate(planeswalkertype));
                    filterPlaneswalker.add(new ControllerIdPredicate(planeswalker.getControllerId()));
                    if (getBattlefield().contains(filterPlaneswalker, planeswalker.getControllerId(), this, 2)) {
                        Player controller = this.getPlayer(planeswalker.getControllerId());
                        if (controller != null) {
                            Target targetPlaneswalkerToKeep = new TargetPermanent(filterPlaneswalker);
                            targetPlaneswalkerToKeep.setTargetName(new StringBuilder(planeswalker.getName()).append(" to keep?").toString());
                            controller.chooseTarget(Outcome.Benefit, targetPlaneswalkerToKeep, null, this);
                            for (Permanent dupPlaneswalker: this.getBattlefield().getActivePermanents(filterPlaneswalker, planeswalker.getControllerId(), this)) {
                                if (!targetPlaneswalkerToKeep.getTargets().contains(dupPlaneswalker.getId())) {
                                    movePermanentToGraveyardWithInfo(dupPlaneswalker);
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        //201300713 - 704.5k
        // If a player controls two or more legendary permanents with the same name, that player
        // chooses one of them, and the rest are put into their owners' graveyards.
        // This is called the "legend rule."

        if (legendary.size() > 1) {  //don't bother checking if less than 2 legends in play
            for (Permanent legend: legendary) {
                FilterPermanent filterLegendName = new FilterPermanent();
                filterLegendName.add(new SupertypePredicate("Legendary"));
                filterLegendName.add(new NamePredicate(legend.getName()));
                filterLegendName.add(new ControllerIdPredicate(legend.getControllerId()));
                if (getBattlefield().contains(filterLegendName, legend.getControllerId(), this, 2)) {
                    Player controller = this.getPlayer(legend.getControllerId());
                    if (controller != null) {
                        Target targetLegendaryToKeep = new TargetPermanent(filterLegendName);
                        targetLegendaryToKeep.setTargetName(new StringBuilder(legend.getName()).append(" to keep (Legendary Rule)?").toString());
                        controller.chooseTarget(Outcome.Benefit, targetLegendaryToKeep, null, this);
                        for (Permanent dupLegend: getBattlefield().getActivePermanents(filterLegendName, legend.getControllerId(), this)) {
                            if (!targetLegendaryToKeep.getTargets().contains(dupLegend.getId())) {
                                movePermanentToGraveyardWithInfo(dupLegend);
                            }
                        }
                    }
                    return true;
                }
            }
        }

        // (Isochron Scepter) 12/1/2004: If you don't want to cast the copy, you can choose not to; the copy ceases to exist the next time state-based actions are checked.
        for(Card card: this.getState().getExile().getAllCards(this)) {
            if (card.isCopy()) {
                this.getState().getExile().removeCard(card, this);
                this.removeCard(card.getId());
            }
        }
        //TODO: implement the rest

        return somethingHappened;
    }

    private boolean movePermanentToGraveyardWithInfo(Permanent permanent) {
        boolean result = false;
        if (permanent.moveToZone(Zone.GRAVEYARD, null, this, false)) {
            this.informPlayers(new StringBuilder(permanent.getLogName())
                    .append(" is put into graveyard from battlefield").toString());
            result = true;
        }
        return result;
    }

    @Override
    public void addPlayerQueryEventListener(Listener<PlayerQueryEvent> listener) {
        playerQueryEventSource.addListener(listener);
    }

    @Override
    public synchronized void firePriorityEvent(UUID playerId) {
        if (simulation) {
            return;
        }
        String message = this.state.getTurn().getStepType().toString();
        if (this.canPlaySorcery(playerId)) {
            message += " - play spells and abilities.";
        }
        else {
            message +=  " - play instants and activated abilities.";
        }

        playerQueryEventSource.select(playerId, message);
    }

    @Override
    public synchronized void fireSelectEvent(UUID playerId, String message) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.select(playerId, message);
    }


    @Override
    public synchronized void fireSelectEvent(UUID playerId, String message, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.select(playerId, message, options);
    }

    @Override
    public void firePlayManaEvent(UUID playerId, String message) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.playMana(playerId, message);
    }

    @Override
    public void firePlayXManaEvent(UUID playerId, String message) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.playXMana(playerId, message);
    }

    @Override
    public void fireAskPlayerEvent(UUID playerId, String message) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.ask(playerId, message);
    }

    @Override
    public void fireGetChoiceEvent(UUID playerId, String message, MageObject object, List<? extends ActivatedAbility> choices) {
        if (simulation) {
            return;
        }
        String objectName = null;
        if (object != null) {
            objectName = object.getName();
        }
        playerQueryEventSource.chooseAbility(playerId, message, objectName, choices);
    }

    @Override
    public void fireGetModeEvent(UUID playerId, String message, Map<UUID, String> modes) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.chooseMode(playerId, message, modes);
    }

    @Override
    public void fireSelectTargetEvent(UUID playerId, String message, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.target(playerId, message, targets, required, options);
    }

    @Override
    public void fireSelectTargetEvent(UUID playerId, String message, Cards cards, boolean required, Map<String, Serializable> options) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.target(playerId, message, cards, required, options);
    }

    @Override
    public void fireSelectTargetEvent(UUID playerId, String message, List<TriggeredAbility> abilities) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.target(playerId, message, abilities);
    }

    @Override
    public void fireSelectTargetEvent(UUID playerId, String message, List<Permanent> perms, boolean required) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.target(playerId, message, perms, required);
    }

    @Override
    public void fireGetAmountEvent(UUID playerId, String message, int min, int max) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.amount(playerId, message, min, max);
    }

    @Override
    public void fireChooseEvent(UUID playerId, Choice choice) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.choose(playerId, choice.getMessage(), choice.getChoices());
    }

    @Override
    public void fireChoosePileEvent(UUID playerId, String message, List<? extends Card> pile1, List<? extends Card> pile2) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.choosePile(playerId, message, pile1, pile2);
    }

    @Override
    public void informPlayers(String message) {
        if (simulation) {
            return;
        }
        fireInformEvent(message);
    }

    @Override
    public void debugMessage(String message) {
        logger.warn(message);
    }

    @Override
    public void fireInformEvent(String message) {
        if (simulation) {
            return;
        }
        tableEventSource.fireTableEvent(EventType.INFO, message, this);
    }

    @Override
    public void fireStatusEvent(String message, boolean withTime) {
        if (simulation) {
            return;
        }
        tableEventSource.fireTableEvent(EventType.STATUS, message, withTime, this);
    }

    @Override
    public void fireUpdatePlayersEvent() {
        if (simulation) {
            return;
        }
        logger.trace("fireUpdatePlayersEvent");
        tableEventSource.fireTableEvent(EventType.UPDATE, null, this);
    }

    @Override
    public void fireGameEndInfo() {
        if (simulation) {
            return;
        }
        logger.trace("fireGameEndIfo");
        tableEventSource.fireTableEvent(EventType.END_GAME_INFO, null, this);
    }

    @Override
    public void fireErrorEvent(String message, Exception ex) {
        tableEventSource.fireTableEvent(EventType.ERROR, message, ex, this);
    }

    @Override
    public Players getPlayers() {
        return state.getPlayers();
    }

    @Override
    public PlayerList getPlayerList() {
        return state.getPlayerList();
    }

    @Override
    public Turn getTurn() {
        return state.getTurn();
    }

    @Override
    public Phase getPhase() {
        return state.getTurn().getPhase();
    }

    @Override
    public Step getStep() {
        return state.getTurn().getStep();
    }

    @Override
    public Battlefield getBattlefield() {
        return state.getBattlefield();
    }

    @Override
    public SpellStack getStack() {
        return state.getStack();
    }

    @Override
    public Exile getExile() {
        return state.getExile();
    }

    @Override
    public Combat getCombat() {
        return state.getCombat();
    }

    @Override
    public int getTurnNum() {
        return state.getTurnNum();
    }

    @Override
    public boolean isMainPhase() {
        return state.getTurn().getStepType() == PhaseStep.PRECOMBAT_MAIN || state.getTurn().getStepType() == PhaseStep.POSTCOMBAT_MAIN;
    }

    @Override
    public boolean canPlaySorcery(UUID playerId) {
        return isMainPhase() && getActivePlayerId().equals(playerId) && getStack().isEmpty();
    }

    /**
     * 800.4a When a player leaves the game, all objects (see rule 109) owned by that player leave
     * the game and any effects which give that player control of any objects or players end. Then,
     * if that player controlled any objects on the stack not represented by cards, those objects
     * cease to exist. Then, if there are any objects still controlled by that player, those objects
     * are exiled. This is not a state-based action. It happens as soon as the player leaves the game.
     * If the player who left the game had priority at the time he or she left, priority passes to
     * the next player in turn order who's still in the game. #
     *
     * @param playerId
     */

    protected void leave(UUID playerId) {

        Player player = getPlayer(playerId);
        if (player == null || player.hasLeft()) {
            logger.debug("Player already left " + (player != null ? player.getName():playerId));
            return;
        }
        logger.debug("Start leave game: " + player.getName());
        player.leave();
        if (checkIfGameIsOver()) {
            // no need to remove objects if only one player is left so the game is over
            return;
        }
        //20100423 - 800.4a
        for (Iterator<Permanent> it = getBattlefield().getAllPermanents().iterator(); it.hasNext();) {
            Permanent perm = it.next();
            if (perm.getOwnerId().equals(playerId)) {
                if (perm.getAttachedTo() != null) {
                    Permanent attachedTo = getPermanent(perm.getAttachedTo());
                    if (attachedTo != null) {
                        attachedTo.removeAttachment(perm.getId(), this);
                    }
                }
                // check if it's a creature and must be removed from combat
                if (perm.getCardType().contains(CardType.CREATURE) && this.getCombat() != null) {
                    this.getCombat().removeFromCombat(perm.getId(), this);
                }
                it.remove();
            }
        }
        // Then, if that player controlled any objects on the stack not represented by cards, those objects cease to exist.
        this.getState().getContinuousEffects().removeInactiveEffects(this);
        for (Iterator<StackObject> it = getStack().iterator(); it.hasNext();) {
            StackObject object = it.next();
            if (object.getControllerId().equals(playerId)) {
                it.remove();
            }
        }
        // Then, if there are any objects still controlled by that player, those objects are exiled.
        List<Permanent> permanents = this.getBattlefield().getAllActivePermanents(playerId);
        for(Permanent permanent : permanents) {
            permanent.moveToExile(null, "", null, this);
        }

        // Remove cards from the player in all exile zones
        for (ExileZone exile: this.getExile().getExileZones()) {
            for (Iterator<UUID> it = exile.iterator(); it.hasNext();) {
                Card card = this.getCard(it.next());
                if (card != null && card.getOwnerId().equals(playerId)) {
                    it.remove();
                }
            }
        }

        Iterator it = gameCards.entrySet().iterator();
        while(it.hasNext()) {
            Entry<UUID,Card> entry = (Entry<UUID,Card>) it.next();
            Card card = entry.getValue();
            if (card.getOwnerId().equals(playerId)) {
                it.remove();
            }
        }

        // Update players in range of
        for (Player leftPlayer :this.getPlayers().values()) {
            if (leftPlayer.isInGame()) {
                leftPlayer.otherPlayerLeftGame(this);
            }
        }
    }

    @Override
    public UUID getActivePlayerId() {
        return state.getActivePlayerId();
    }

    @Override
    public UUID getPriorityPlayerId() {
        return state.getPriorityPlayerId();
    }

    @Override
    public void addSimultaneousEvent(GameEvent event) {
        state.addSimultaneousEvent(event, this);
    }

    @Override
    public void fireEvent(GameEvent event) {
        state.handleEvent(event, this);
    }

    @Override
    public boolean replaceEvent(GameEvent event) {
        return state.replaceEvent(event, this);
    }

    @Override
    public PreventionEffectData preventDamage(GameEvent event, Ability source, Game game, boolean preventAllDamage) {
        return preventDamage(event, source, game, Integer.MAX_VALUE);
    }

    @Override
    public PreventionEffectData preventDamage(GameEvent event, Ability source, Game game, int amountToPrevent) {
        PreventionEffectData result = new PreventionEffectData(amountToPrevent);
        if (!(event instanceof DamageEvent)) {
            result.setError(true);
            return result;
        }
        DamageEvent damageEvent = (DamageEvent) event;
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, damageEvent.getTargetId(), damageEvent.getSourceId(), source.getControllerId(), damageEvent.getAmount(), false);
        if (game.replaceEvent(preventEvent)) {
            result.setReplaced(true);
            return result;
        }

        if (event.getAmount() > amountToPrevent) {
            result.setPreventedDamage(amountToPrevent);
            damageEvent.setAmount(event.getAmount() - amountToPrevent);
        } else {
            result.setPreventedDamage(event.getAmount());
            damageEvent.setAmount(0);

        }
        if (amountToPrevent != Integer.MAX_VALUE) {
            // set remaining amount
            result.setRemainingAmount(amountToPrevent -= result.getPreventedDamage());
        }
        MageObject damageSource = game.getObject(damageEvent.getSourceId());
        MageObject preventionSource = game.getObject(source.getSourceId());

        if (damageSource != null && preventionSource != null) {
            MageObject targetObject = game.getObject(event.getTargetId());
            String targetName = "";
            if (targetObject == null) {
                Player targetPlayer = game.getPlayer(event.getTargetId());
                if (targetPlayer != null) {
                    targetName = targetPlayer.getName();
                }
            } else {
                targetName = targetObject.getLogName();
            }
            StringBuilder message = new StringBuilder(preventionSource.getLogName()).append(": Prevented ");
            message.append(Integer.toString(result.getPreventedDamage())).append(" damage from ").append(damageSource.getName());
            if (!targetName.isEmpty()) {
                message.append(" to ").append(targetName);
            }
            game.informPlayers(message.toString());
        }
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, damageEvent.getTargetId(), source.getSourceId(), source.getControllerId(), result.getPreventedDamage()));
        return result;

    }


    protected void removeCreaturesFromCombat() {
        //20091005 - 511.3
        getCombat().endCombat(this);
    }

    @Override
    public ContinuousEffects getContinuousEffects() {
        return state.getContinuousEffects();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        //initialize transient objects during deserialization
        in.defaultReadObject();
        savedStates = new Stack<>();
        tableEventSource = new TableEventSource();
        playerQueryEventSource = new PlayerQueryEventSource();
        gameStates = new GameStates();
    }

    /**
     * Gets last known information about object in the zone.
     * At the moment doesn't take into account zone (it is expected that it doesn't really matter, if not, then Map<UUID, Map<Zone, Card>> should be used instead).
     *
     * Can return null.
     *
     * @param objectId
     * @param zone
     * @return
     */
    @Override
    public MageObject getLastKnownInformation(UUID objectId, Zone zone) {
        /*if (!lki.containsKey(objectId)) {
            return getCard(objectId);
        }*/
        Map<UUID, MageObject> lkiMap = lki.get(zone);
        if (lkiMap != null) {
            MageObject object = lkiMap.get(objectId);
            if (object != null) {
                return object.copy();
            }
        }
        return null;
    }

    @Override
    public MageObject getLastKnownInformation(UUID objectId, Zone zone, int zoneChangeCounter) {
        if (zone.equals(Zone.BATTLEFIELD)) {
            Map<Integer, MageObject> lkiMapExtended = lkiExtended.get(objectId);

            if (lkiMapExtended != null) {
                MageObject object = lkiMapExtended.get(zoneChangeCounter);
                if (object != null) {
                    return object.copy();
                }
            }
        }

        return getLastKnownInformation(objectId, zone);
    }

    @Override
    public MageObject getShortLivingLKI(UUID objectId, Zone zone) {
        Map<UUID, MageObject> shortLivingLkiMap = shortLivingLKI.get(zone);
        if (shortLivingLkiMap != null) {
            MageObject object = shortLivingLkiMap.get(objectId);
            if (object != null) {
                return object.copy();
            }
        }
        return null;
    }

    /**
     * Remembers object state to be used as Last Known Information.
     *
     * @param objectId
     * @param zone
     * @param object
     */
    @Override
    public void rememberLKI(UUID objectId, Zone zone, MageObject object) {
        if (object instanceof Permanent || object instanceof StackObject) {
            MageObject copy = object.copy();

            Map<UUID, MageObject> lkiMap = lki.get(zone);
            if (lkiMap != null) {
                lkiMap.put(objectId, copy);
            } else {
                HashMap<UUID, MageObject> newMap = new HashMap<>();
                newMap.put(objectId, copy);
                lki.put(zone, newMap);
            }

            Map<UUID, MageObject> shortLivingLkiMap = shortLivingLKI.get(zone);
            if (shortLivingLkiMap != null) {
                shortLivingLkiMap.put(objectId, copy);
            } else {
                HashMap<UUID, MageObject> newMap = new HashMap<>();
                newMap.put(objectId, copy);
                shortLivingLKI.put(zone, newMap);
            }

            if (object instanceof Permanent) {
                Map<Integer, MageObject> lkiExtendedMap = lkiExtended.get(objectId);
                if (lkiExtendedMap != null) {
                    lkiExtendedMap.put(((Permanent) object).getZoneChangeCounter(), copy);
                } else {
                    lkiExtendedMap = new HashMap<>();
                    lkiExtendedMap.put(((Permanent) object).getZoneChangeCounter(), copy);
                    lkiExtended.put(objectId, lkiExtendedMap);
                }
            }
        }
    }

    /**
     * Reset objects stored for Last Known Information.
     */
    @Override
    public void resetLKI() {
        lki.clear();
        lkiExtended.clear();
    }

    @Override
    public void resetShortLivingLKI() {
        shortLivingLKI.clear();
    }

    @Override
    public void resetForSourceId(UUID sourceId) {
        // make sure that all effects don't touch this card once it returns back to battlefield
        // e.g. this prevents that effects affect creature with undying return from graveyard
        for (ContinuousEffect effect : getContinuousEffects().getLayeredEffects(this)) {
            if (effect.getAffectedObjects().contains(sourceId)) {
                effect.getAffectedObjects().remove(sourceId);
                if (effect instanceof SourceEffect) {
                    effect.discard();
                }
            }
        }
        getContinuousEffects().removeGainedEffectsForSource(sourceId);
        // remove gained triggered abilities
        getState().resetTriggersForSourceId(sourceId);
    }

    @Override
    public void cheat(UUID ownerId, Map<Zone, String> commands) {
        if (commands != null) {
            Player player = getPlayer(ownerId);
            if (player != null) {
                for (Map.Entry<Zone, String> command : commands.entrySet()) {
                    switch (command.getKey()) {
                        case HAND:
                            if (command.getValue().equals("clear")) {
                                removeCards(player.getHand());
                            }
                            break;
                        case LIBRARY:
                            if (command.getValue().equals("clear")) {
                                for (UUID card : player.getLibrary().getCardList()) {
                                    removeCard(card);
                                }
                                player.getLibrary().clear();
                            }
                            break;
                        case OUTSIDE:
                            if (command.getValue().contains("life:")) {
                                String[] s = command.getValue().split(":");
                                if (s.length == 2) {
                                    try {
                                        Integer amount = Integer.parseInt(s[1]);
                                        player.setLife(amount, this);
                                        logger.info("Setting player's life: ");
                                    } catch (NumberFormatException e) {
                                        logger.fatal("error setting life", e);
                                    }
                                }


                            }
                            break;
                    }
                }
            }
        }
    }

    private void removeCards(Cards cards) {
        for (UUID card : cards) {
            removeCard(card);
        }
        cards.clear();
    }

    private void removeCard(UUID cardId) {
        Card card = this.getCard(cardId);
        if(card != null && card.isSplitCard()) {
            gameCards.remove(((SplitCard)card).getLeftHalfCard().getId());
            gameCards.remove(((SplitCard)card).getRightHalfCard().getId());
        }
        gameCards.remove(cardId);
    }

    @Override
    public void cheat(UUID ownerId, List<Card> library, List<Card> hand, List<PermanentCard> battlefield, List<Card> graveyard) {
        Player player = getPlayer(ownerId);
        if (player != null) {
            loadCards(ownerId, library);
            loadCards(ownerId, hand);
            loadCards(ownerId, battlefield);
            loadCards(ownerId, graveyard);

            for (Card card : library) {
                setZone(card.getId(), Zone.LIBRARY);
                player.getLibrary().putOnTop(card, this);
            }
            for (Card card : hand) {
                setZone(card.getId(), Zone.HAND);
                player.getHand().add(card);
            }
            for (Card card : graveyard) {
                setZone(card.getId(), Zone.GRAVEYARD);
                player.getGraveyard().add(card);
            }
            for (PermanentCard card : battlefield) {
                setZone(card.getId(), Zone.BATTLEFIELD);
                card.setOwnerId(ownerId);
                PermanentCard permanent = new PermanentCard(card.getCard(), ownerId);
                getBattlefield().addPermanent(permanent);
                permanent.entersBattlefield(permanent.getId(), this, Zone.OUTSIDE, false);
                ((PermanentImpl)permanent).removeSummoningSickness();
                if (card.isTapped()) {
                    permanent.setTapped(true);
                }
            }
            applyEffects();
        }
    }

    private void loadCards(UUID ownerId, List<? extends Card> cards) {
        if (cards == null) {
            return;
        }
        Set<Card> set = new HashSet<>(cards);
        loadCards(set, ownerId);
    }

    public void replaceLibrary(List<Card> cardsDownToTop, UUID ownerId) {
        Player player = getPlayer(ownerId);
        if (player != null) {
            for (UUID card : player.getLibrary().getCardList()) {
                removeCard(card);
            }
            player.getLibrary().clear();
            Set<Card> cards = new HashSet<>();
            for (Card card : cardsDownToTop) {
                cards.add(card);
            }
            loadCards(cards, ownerId);

            for (Card card : cards) {
                player.getLibrary().putOnTop(card, this);
            }
        }
    }

    @Override
    public boolean endTurn() {
        getTurn().endTurn(this, getActivePlayerId());
        return true;
    }

    @Override
    public int doAction(MageAction action) {
        //actions.add(action);
        int value = action.doAction(this);
//        score += action.getScore(scorePlayer);
        return value;
    }

    @Override
    public Date getStartTime() {
        if (startTime == null) {
            return null;
        }
        return new Date(startTime.getTime());
    }

    @Override
    public Date getEndTime() {
        if (endTime == null) {
            return null;
        }
        return new Date(endTime.getTime());
    }

    @Override
    public void setGameOptions(GameOptions options) {
        this.gameOptions = options;
    }

    @Override
    public void setLosingPlayer(Player player) {
        this.losingPlayer = player;
    }

    @Override
    public Player getLosingPlayer() {
        return this.losingPlayer;
    }

    @Override
    public void informPlayer(Player player, String message) {
        if (simulation) {
            return;
        }
        playerQueryEventSource.informPlayer(player.getId(), message);
    }

    @Override
    public boolean getStateCheckRequired() {
        return stateCheckRequired;
    }

    @Override
    public void setStateCheckRequired() {
        stateCheckRequired = true;
    }

    /**
     * If true, only self scope replacement effects are applied
     *
     * @param scopeRelevant
     */
    @Override
    public void setScopeRelevant(boolean scopeRelevant) {
        this.scopeRelevant = scopeRelevant;
    }

    /**
     * @return - true if only self scope replacement effects have to be applied
     */
    @Override
    public boolean getScopeRelevant() {
        return this.scopeRelevant;
    }

    @Override
    public boolean isSaveGame() {
        return saveGame;
    }

    @Override
    public void setSaveGame(boolean saveGame) {
        this.saveGame = saveGame;
    }


    public void setStartMessage(String startMessage) {
        this.startMessage = startMessage;
    }

    @Override
    public void initTimer(UUID playerId) {
        if (priorityTime > 0) {
            tableEventSource.fireTableEvent(EventType.INIT_TIMER, playerId, null, this);
        }
    }

    @Override
    public void resumeTimer(UUID playerId) {
        if (priorityTime > 0) {
            tableEventSource.fireTableEvent(EventType.RESUME_TIMER, playerId, null, this);
        }
    }

    @Override
    public void pauseTimer(UUID playerId) {
        if (priorityTime > 0) {
            tableEventSource.fireTableEvent(EventType.PAUSE_TIMER, playerId, null, this);
        }
    }

    @Override
    public int getPriorityTime() {
        return priorityTime;
    }

    @Override
    public void setPriorityTime(int priorityTime) {
        this.priorityTime = priorityTime;
    }
}
