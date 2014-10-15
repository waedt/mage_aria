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

package mage.cards;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectImpl;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.mana.ManaAbility;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import static mage.constants.Zone.BATTLEFIELD;
import static mage.constants.Zone.COMMAND;
import static mage.constants.Zone.EXILED;
import static mage.constants.Zone.GRAVEYARD;
import static mage.constants.Zone.HAND;
import static mage.constants.Zone.LIBRARY;
import static mage.constants.Zone.OUTSIDE;
import static mage.constants.Zone.PICK;
import static mage.constants.Zone.STACK;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

public abstract class CardImpl extends MageObjectImpl implements Card {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(CardImpl.class);

    protected UUID ownerId;
    protected int cardNumber;
    protected List<Watcher> watchers = new ArrayList<>();
    protected String expansionSetCode;
    protected String tokenSetCode;
    protected Rarity rarity;
    protected boolean faceDown;
    protected boolean canTransform;
    protected Card secondSideCard;
    protected boolean nightCard;
    protected SpellAbility spellAbility;
    protected boolean flipCard;
    protected String flipCardName;
    protected int zoneChangeCounter = 1;
    protected Map<String, String> info;
    protected boolean usesVariousArt = false;
    protected Counters counters;
    protected boolean splitCard;
    protected boolean morphCard;

    public CardImpl(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs) {
        this(ownerId, cardNumber, name, rarity, cardTypes, costs, SpellAbilityType.BASE);
    }

    public CardImpl(UUID ownerId, int cardNumber, String name, Rarity rarity, CardType[] cardTypes, String costs, SpellAbilityType spellAbilityType) {
        this(ownerId, name);
        this.rarity = rarity;
        this.cardNumber = cardNumber;
        this.cardType.addAll(Arrays.asList(cardTypes));
        this.manaCost.load(costs);
        if (cardType.contains(CardType.LAND)) {
            Ability ability = new PlayLandAbility(name);
            ability.setSourceId(this.getId());
            abilities.add(ability);
        }
        else {            
            SpellAbility ability = new SpellAbility(manaCost, name, Zone.HAND, spellAbilityType);
            if (!cardType.contains(CardType.INSTANT)) {
                ability.setTiming(TimingRule.SORCERY);
            }
            ability.setSourceId(this.getId());
            abilities.add(ability);            
        }
        this.usesVariousArt = Character.isDigit(this.getClass().getName().charAt(this.getClass().getName().length()-1));
        this.counters = new Counters();
        this.morphCard = false;
    }

    protected CardImpl(UUID ownerId, String name) {
        this.ownerId = ownerId;
        this.name = name;
        this.counters = new Counters();
    }

    protected CardImpl(UUID id, UUID ownerId, String name) {
        super(id);
        this.ownerId = ownerId;
        this.name = name;
        this.counters = new Counters();
    }

    public CardImpl(final CardImpl card) {
        super(card);
        ownerId = card.ownerId;
        cardNumber = card.cardNumber;
        expansionSetCode = card.expansionSetCode;
        rarity = card.rarity;
        for (Watcher watcher: (List<Watcher>)card.getWatchers()) {
            watchers.add(watcher.copy());
        }
        faceDown = card.faceDown;

        canTransform = card.canTransform;
        if (canTransform) {
            secondSideCard = card.secondSideCard;
            nightCard = card.nightCard;
        }
        zoneChangeCounter = card.zoneChangeCounter;
        if (card.info != null) {
            info = new HashMap<>();
            info.putAll(card.info);
        }
        flipCard = card.flipCard;
        flipCardName = card.flipCardName;
        splitCard = card.splitCard;
        usesVariousArt = card.usesVariousArt;
        counters = card.counters.copy();
        morphCard = card.isMorphCard();
    }

    @Override
    public void assignNewId() {
        this.objectId = UUID.randomUUID();
        this.abilities.newOriginalId();
        this.abilities.setSourceId(objectId);
    }

    public static Card createCard(String name) {
        try {
            return createCard(Class.forName(name));
        } catch (ClassNotFoundException ex) {
            logger.fatal("Error loading card: " + name, ex);
            return null;
        }
    }

    public static Card createCard(Class<?> clazz) {
        try {
            Constructor<?> con = clazz.getConstructor(new Class[]{UUID.class});
            Card card = (Card) con.newInstance(new Object[]{null});
            card.build();
            return card;
        } catch (Exception e) {
            logger.fatal("Error loading card: " + clazz.getCanonicalName(), e);
            return null;
        }
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public int getCardNumber() {
        return cardNumber;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public List<String> getRules() {
        try {
            List<String> rules = abilities.getRules(this.getLogName());
            if (info != null) {
                for (String data : info.values()) {
                    rules.add(data);
                }
            }
            return rules;
        } catch (Exception e) {
            System.out.println("Exception in rules generation for card: " + this.getName());
            e.printStackTrace();
        }
        ArrayList<String> rules = new ArrayList<>();
        rules.add("Exception occured in rules generation");
        return rules;
    }

    @Override
    public void addAbility(Ability ability) {
        ability.setSourceId(this.getId());
        abilities.add(ability);
    }
    
    @Override
    public void addWatcher(Watcher watcher) {
        watcher.setSourceId(this.getId());
        watcher.setControllerId(this.ownerId);
        watchers.add(watcher);
    }

    @Override
    public SpellAbility getSpellAbility() {
        if (spellAbility == null) {
            for (Ability ability : abilities.getActivatedAbilities(Zone.HAND)) {
                if (ability instanceof SpellAbility) {
                    spellAbility = (SpellAbility) ability;
                }
            }
        }
        return spellAbility;
    }

    @Override
    public void setControllerId(UUID controllerId) {
        abilities.setControllerId(controllerId);
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        abilities.setControllerId(ownerId);
    }

    @Override
    public List<Watcher> getWatchers() {
        return watchers;
    }

    @Override
    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    @Override
    public String getTokenSetCode() {
        return tokenSetCode;
    }

    @Override
    public void setExpansionSetCode(String expansionSetCode) {
        this.expansionSetCode = expansionSetCode;
    }

    @Override
    public List<Mana> getMana() {
        List<Mana> mana = new ArrayList<>();
        for (ManaAbility ability : this.abilities.getManaAbilities(Zone.BATTLEFIELD)) {
            mana.add(ability.getNetMana(null));
        }
        return mana;
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag) {
        return this.moveToZone(toZone, sourceId, game, flag, null);
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, ArrayList<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, toZone, appliedEffects);
        if (!game.replaceEvent(event)) {
            if (event.getFromZone() != null) {
                switch (event.getFromZone()) {
                    case GRAVEYARD:
                        game.getPlayer(ownerId).removeFromGraveyard(this, game);
                        break;
                    case HAND:
                        game.getPlayer(ownerId).removeFromHand(this, game);
                        break;
                    case LIBRARY:
                        game.getPlayer(ownerId).removeFromLibrary(this, game);
                        break;
                    case EXILED:
                        game.getExile().removeCard(this, game);
                        break;
                    case OUTSIDE:
                        game.getPlayer(ownerId).getSideboard().remove(this);
                        break;
                    case COMMAND:
                        game.getState().getCommand().remove((Commander)game.getObject(objectId));
                        break;
                    case STACK:
                        StackObject stackObject = game.getStack().getSpell(getId());
                        if (stackObject != null) {
                            game.getStack().remove(stackObject);
                        }
                        break;
                    case PICK:
                    case BATTLEFIELD: // for sacrificing permanents or putting to library
                        break;
                    default:
                        Card sourceCard = game.getCard(sourceId);
                        logger.fatal(new StringBuilder("Invalid from zone [").append(fromZone)
                                .append("] for card [").append(this.getName())
                                .append("] to zone [").append(toZone)
                                .append("] source [").append(sourceCard != null ? sourceCard.getName():"null").append("]").toString());
                        break;
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
            }
            
            if (isFaceDown() && !event.getToZone().equals(Zone.BATTLEFIELD)) { // to battlefield is possible because of Morph
                setFaceDown(false);
                game.getCard(this.getId()).setFaceDown(false);
            }
            updateZoneChangeCounter();
            switch (event.getToZone()) {
                case GRAVEYARD:
                    game.getPlayer(ownerId).putInGraveyard(this, game, !flag);
                    break;
                case HAND:
                    game.getPlayer(ownerId).getHand().add(this);
                    break;
                case STACK:
                    game.getStack().push(new Spell(this, this.getSpellAbility().copy(), ownerId, event.getFromZone()));
                    break;
                case EXILED:
                    game.getExile().getPermanentExile().add(this);
                    break;
                case COMMAND:
                    game.addCommander(new Commander(this));
                    break;
                case LIBRARY:
                    if (flag) {
                        game.getPlayer(ownerId).getLibrary().putOnTop(this, game);
                    }
                    else {
                        game.getPlayer(ownerId).getLibrary().putOnBottom(this, game);
                    }
                    break;
                case BATTLEFIELD:
                    PermanentCard permanent = new PermanentCard(this, ownerId);
                    game.resetForSourceId(permanent.getId());
                    game.addPermanent(permanent);
                    game.setZone(objectId, Zone.BATTLEFIELD);
                    game.setScopeRelevant(true);
                    game.applyEffects();
                    permanent.entersBattlefield(sourceId, game, event.getFromZone(), true);
                    game.setScopeRelevant(false);
                    game.applyEffects();
                    if (flag) {
                        permanent.setTapped(true);
                    }
                    event.setTarget(permanent);
                    break;
                default:
                    Card sourceCard = game.getCard(sourceId);
                    logger.fatal(new StringBuilder("Invalid to zone [").append(toZone)
                                .append("] for card [").append(this.getName())
                                .append("] to zone [").append(toZone)
                                .append("] source [").append(sourceCard != null ? sourceCard.getName():"null").append("]").toString());
                    return false;
            }
            setControllerId(ownerId);
            game.setZone(objectId, event.getToZone());
            game.addSimultaneousEvent(event);
            return game.getState().getZone(objectId) == toZone;
        }
        return false;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, ability.getId(), controllerId, fromZone, Zone.STACK);
        if (!game.replaceEvent(event)) {
            if (event.getFromZone() != null) {
                switch (event.getFromZone()) {
                    case GRAVEYARD:
                        game.getPlayer(ownerId).removeFromGraveyard(this, game);
                        break;
                    case HAND:
                        game.getPlayer(ownerId).removeFromHand(this, game);
                        break;
                    case LIBRARY:
                        game.getPlayer(ownerId).removeFromLibrary(this, game);
                        break;
                    case EXILED:
                        game.getExile().removeCard(this, game);
                        break;
                    case OUTSIDE:
                        game.getPlayer(ownerId).getSideboard().remove(this);
                        break;
                        
                    case COMMAND:
                        game.getState().getCommand().remove((Commander)game.getObject(objectId));
                        break;
                    default:
                        //logger.warning("moveToZone, not fully implemented: from="+event.getFromZone() + ", to="+event.getToZone());
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
            }
            game.getStack().push(new Spell(this, ability.copy(), controllerId, event.getFromZone()));
            game.setZone(objectId, event.getToZone());
            game.fireEvent(event);
            return game.getState().getZone(objectId) == Zone.STACK;
        }
        return false;
    }
    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        return moveToExile(exileId, name, sourceId, game, null);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, ArrayList<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(objectId);
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, ownerId, fromZone, Zone.EXILED, appliedEffects);
        if (!game.replaceEvent(event)) {
            if (fromZone != null) {
                switch (fromZone) {
                    case GRAVEYARD:
                        game.getPlayer(ownerId).removeFromGraveyard(this, game);
                        break;
                    case HAND:
                        game.getPlayer(ownerId).removeFromHand(this, game);
                        break;
                    case LIBRARY:
                        game.getPlayer(ownerId).removeFromLibrary(this, game);
                        break;
                    case EXILED:
                        game.getExile().removeCard(this, game);
                        break;
                    case STACK:
                    case PICK:
                        // nothing to do
                        break;
                    default:
                        MageObject object = game.getObject(sourceId);
                        logger.warn(new StringBuilder("moveToExile, not fully implemented: from = ").append(fromZone).append(" - ").append(object != null ? object.getName():"null"));
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
            }

            if (exileId == null) {
                game.getExile().getPermanentExile().add(this);
            } else {
                game.getExile().createZone(exileId, name).add(this);
            }
            updateZoneChangeCounter();
            game.setZone(objectId, event.getToZone());
            game.addSimultaneousEvent(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId) {
        return this.putOntoBattlefield(game, fromZone, sourceId, controllerId, false);
    }
        
    @Override
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped){
        return this.putOntoBattlefield(game, fromZone, sourceId, controllerId, tapped, null);

    }
     
    @Override   
    public boolean putOntoBattlefield(Game game, Zone fromZone, UUID sourceId, UUID controllerId, boolean tapped, ArrayList<UUID> appliedEffects){
        ZoneChangeEvent event = new ZoneChangeEvent(this.objectId, sourceId, controllerId, fromZone, Zone.BATTLEFIELD, appliedEffects, tapped);
        if (!game.replaceEvent(event)) {
            if (fromZone != null) {
                boolean removed = false;
                switch (fromZone) {
                    case GRAVEYARD:
                        removed = game.getPlayer(ownerId).removeFromGraveyard(this, game);
                        break;
                    case HAND:
                        removed = game.getPlayer(ownerId).removeFromHand(this, game);
                        break;
                    case LIBRARY:
                        removed = game.getPlayer(ownerId).removeFromLibrary(this, game);
                        break;
                    case EXILED:
                        game.getExile().removeCard(this, game);
                        if (isFaceDown()) {
                            // 110.6b Permanents enter the battlefield untapped, unflipped, face up, and phased in unless a spell or ability says otherwise.
                            this.setFaceDown(false);
                        }
                        removed = true;
                        break;
                    case COMMAND:
                        // command object (commander) is only on the stack, so no removing neccessary here
                        removed = true;
                        break;
                    case PICK:
                        removed = true;
                        break;
                    default:
                        logger.warn("putOntoBattlefield, not fully implemented: fromZone="+fromZone);
                }
                game.rememberLKI(objectId, event.getFromZone(), this);
                if (!removed) {
                    logger.warn("Couldn't find card in fromZone, card=" + getName() + ", fromZone=" + fromZone);
                }
            }
            updateZoneChangeCounter();
            PermanentCard permanent = new PermanentCard(this, controllerId);
            // reset is done to end continuous effects from previous instances of that permanent (e.g undying)
            game.resetForSourceId(permanent.getId());
            game.addPermanent(permanent);
            game.setZone(objectId, Zone.BATTLEFIELD);
            game.setScopeRelevant(true);
            permanent.setTapped(tapped);
            permanent.entersBattlefield(sourceId, game, event.getFromZone(), true);
            game.setScopeRelevant(false);
            game.applyEffects();
            game.fireEvent(new ZoneChangeEvent(permanent, controllerId, fromZone, Zone.BATTLEFIELD));
            return true;
        }
        return false;
    }

    @Override
    public void setCardNumber(int cid) {
        this.cardNumber = cid;
    }

    @Override
    public void setFaceDown(boolean value) {
        faceDown = value;
    }

    @Override
    public boolean isFaceDown() {
        return faceDown;
    }

    @Override
    public boolean turnFaceUp(Game game, UUID playerId) {
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.TURNFACEUP, getId(), playerId);
        if (!game.replaceEvent(event)) {
            setFaceDown(false);
            game.getCard(objectId).setFaceDown(false); // Another instance?
            for (Ability ability :abilities) { // abilities that were set to not visible face down must be set to visible again
                if (ability.getWorksFaceDown() && !ability.getRuleVisible()) {
                    ability.setRuleVisible(true);
                }
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.TURNEDFACEUP, getId(), playerId));
            return true;
        }
        return false;
    }

    @Override
    public boolean turnFaceDown(Game game, UUID playerId) {
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.TURNFACEDOWN, getId(), playerId);
        if (!game.replaceEvent(event)) {
            setFaceDown(true);
            game.getCard(objectId).setFaceDown(true); // Another instance?
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.TURNEDFACEDOWN, getId(), playerId));
            return true;
        }
        return false;
    }

    @Override
    public boolean canTransform() {
        return this.canTransform;
    }

    @Override
    public Card getSecondCardFace() {
        return this.secondSideCard;
    }

    @Override
    public void setSecondCardFace(Card card) {
        this.secondSideCard = card;
    }

    @Override
    public boolean isNightCard() {
        return this.nightCard;
    }

    @Override
    public boolean isFlipCard() {
        return flipCard;
    }

    @Override
    public String getFlipCardName() {
        return flipCardName;
    }

    @Override
    public void setFlipCard(boolean flipCard) {
        this.flipCard = flipCard;
    }

    @Override
    public void setFlipCardName(String flipCardName) {
        this.flipCardName = flipCardName;
    }


    @Override
    public boolean isSplitCard() {
        return splitCard;
    }


    @Override
    public int getZoneChangeCounter() {
        return zoneChangeCounter;
    }

    private void updateZoneChangeCounter() {
        zoneChangeCounter++;
    }

    @Override
    public void addInfo(String key, String value) {
        if (info == null) {
            info = new HashMap<>();
        }
        if (value == null || value.isEmpty()) {
            info.remove(key);
        } else {
            info.put(key, value);
        }
    }

    @Override
    public void build() {}

    @Override
    public void setUsesVariousArt(boolean usesVariousArt) {
        this.usesVariousArt = usesVariousArt;
    }

    @Override
    public boolean getUsesVariousArt() {
        return usesVariousArt;
    }

    @Override
    public Counters getCounters() {
        return counters;
    }

    @Override
    public void addCounters(String name, int amount, Game game) {
        addCounters(name, amount, game, null);
    }

    @Override
    public void addCounters(String name, int amount, Game game, ArrayList<UUID> appliedEffects) {
        GameEvent countersEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTERS, objectId, ownerId, name, amount);
        countersEvent.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(countersEvent)) {
            for (int i = 0; i < countersEvent.getAmount(); i++) {
                GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, ownerId, name, 1);
                event.setAppliedEffects(appliedEffects);
                if (!game.replaceEvent(event)) {
                    counters.addCounter(name, 1);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, ownerId, name, 1));
                }
            }
        }
    }

    @Override
    public void addCounters(Counter counter, Game game) {
        addCounters(counter, game, null);
    }

    @Override
    public void addCounters(Counter counter, Game game, ArrayList<UUID> appliedEffects) {
        GameEvent countersEvent = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTERS, objectId, ownerId, counter.getName(), counter.getCount());
        countersEvent.setAppliedEffects(appliedEffects);
        if (!game.replaceEvent(countersEvent)) {
            int amount = countersEvent.getAmount();
            for (int i = 0; i < amount; i++) {
                Counter eventCounter = counter.copy();
                eventCounter.remove(amount - 1);
                GameEvent event = GameEvent.getEvent(GameEvent.EventType.ADD_COUNTER, objectId, ownerId, counter.getName(), 1);
                event.setAppliedEffects(appliedEffects);
                if (!game.replaceEvent(event)) {
                    counters.addCounter(eventCounter);
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER_ADDED, objectId, ownerId, counter.getName(), 1));
                }
            }
        }
    }

    @Override
    public void removeCounters(String name, int amount, Game game) {
        for (int i = 0; i < amount; i++) {
            counters.removeCounter(name, 1);
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.COUNTER_REMOVED, objectId, ownerId);
            event.setData(name);
            game.fireEvent(event);
        }
    }

    @Override
    public void removeCounters(Counter counter, Game game) {
        removeCounters(counter.getName(), counter.getCount(), game);
    }

    @Override
    public void setMorphCard(boolean morphCard) {
        this.morphCard = morphCard;
    }

    @Override
    public boolean isMorphCard() {
        return morphCard;
    }

    @Override
    public String getLogName() {
        if (this.isFaceDown()) {
            return "facedown card";
        }
        return name;
    }
}
