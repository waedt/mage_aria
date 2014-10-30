package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.DelayedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;

public class EmeraldMystic extends CardImpl {

    public EmeraldMystic(UUID ownerId) {
        super(ownerId, 170, "Emerald Mystic", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Kithkin");
        this.subtype.add("Druid");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);

        // T: The next time you activate an ability of a land you control this turn, copy it. You may choose new targets for the copy.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new EmeraldMysticEffect(), new TapSourceCost()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/lAT7O2p.jpg
        T: The next time you activate an ability of a land you control this turn, copy it. You may choose new targets for the copy.
        */
    }

    public EmeraldMystic(final EmeraldMystic card) {
        super(card);
    }

    @Override
    public EmeraldMystic copy() {
        return new EmeraldMystic(this);
    }

}

class EmeraldMysticEffect extends OneShotEffect {

    public EmeraldMysticEffect() {
        super(Outcome.Copy);
        this.staticText = "The next time you activate an ability of a land you control this turn, copy it. You may choose new targets for the copy.";
    }

    public EmeraldMysticEffect(final EmeraldMysticEffect other) {
        super(other);
    }

    @Override
    public EmeraldMysticEffect copy() {
        return new EmeraldMysticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // We need to install two linked triggered abilities: one for regular
        // abilities that use the stack and one for mana abilities which do not
        // use the stack. When one triggers, it disables other so it won't also
        // trigger. Storing the shared active flag in the game state allows
        // 'undo' to behave correctly/as you would expect.

        String activeFlagKey = UUID.randomUUID().toString();
        game.getState().setValue(activeFlagKey, Boolean.TRUE);

        DelayedTriggeredAbility delayedAbility = new EmeraldMysticDelayedTriggeredAbility(activeFlagKey);
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);

        delayedAbility = new EmeraldMysticDelayedTriggeredManaAbility(activeFlagKey);
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);

        return true;
    }
}

class EmeraldMysticDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public String activeFlagKey;

    public EmeraldMysticDelayedTriggeredAbility(String activeFlagKey) {
        super(new EmeraldMysticDelayedEffect(), Duration.EndOfTurn, false);
        this.activeFlagKey = activeFlagKey;
    }

    public EmeraldMysticDelayedTriggeredAbility(final EmeraldMysticDelayedTriggeredAbility other) {
        super(other);
        this.activeFlagKey = other.activeFlagKey;
    }

    @Override
    public EmeraldMysticDelayedTriggeredAbility copy() {
        return new EmeraldMysticDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(!((Boolean)game.getState().getValue(this.activeFlagKey))) {
            return false;
        }

        Permanent card = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if(card == null || !card.getCardType().contains(CardType.LAND) || !card.getControllerId().equals(getControllerId())) {
            return false;
        }

        if(event.getType() == EventType.ACTIVATED_ABILITY) {
            StackAbility ability = (StackAbility)game.getStack().getStackObject(event.getSourceId());
            this.getEffects().get(0).setValue("abilityToCopy", ability);
            return true;
        }
        return false;
    }

}

class EmeraldMysticDelayedTriggeredManaAbility extends DelayedTriggeredManaAbility {

    public String activeFlagKey;

    public EmeraldMysticDelayedTriggeredManaAbility(String activeFlagKey) {
        super(new EmeraldMysticDelayedManaEffect(), Duration.EndOfTurn, false);
        this.activeFlagKey = activeFlagKey;
    }

    public EmeraldMysticDelayedTriggeredManaAbility(final EmeraldMysticDelayedTriggeredManaAbility other) {
        super(other);
        this.activeFlagKey = other.activeFlagKey;
    }

    @Override
    public EmeraldMysticDelayedTriggeredManaAbility copy() {
        return new EmeraldMysticDelayedTriggeredManaAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(!((Boolean)game.getState().getValue(this.activeFlagKey))) {
            return false;
        }

        Permanent card = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if(card == null || !card.getCardType().contains(CardType.LAND) || !card.getControllerId().equals(getControllerId())) {
            return false;
        }

        if(event.getType() == EventType.ACTIVATED_MANA_ABILITY) {
            Ability ability = game.getAbility(event.getTargetId(), event.getSourceId());
            this.getEffects().get(0).setValue("abilityToCopy", ability);
            return true;
        }
        return false;
    }

}

class EmeraldMysticDelayedEffect extends OneShotEffect {

    public EmeraldMysticDelayedEffect() {
        super(Outcome.Copy);
    }

    public EmeraldMysticDelayedEffect(final EmeraldMysticDelayedEffect other) {
        super(other);
    }

    @Override
    public EmeraldMysticDelayedEffect copy() {
        return new EmeraldMysticDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Ability ability = (Ability)this.getValue("abilityToCopy");
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (ability == null || controller == null) {
            return false;
        }

        Ability newAbility = ability.copy();
        newAbility.newId();
        game.getStack().push(new StackAbility(newAbility, source.getControllerId()));
        if (newAbility.getTargets().size() > 0) {
            if (controller.chooseUse(newAbility.getEffects().get(0).getOutcome(), "Choose new targets?", game)) {
                newAbility.getTargets().clearChosen();
                if (newAbility.getTargets().chooseTargets(newAbility.getEffects().get(0).getOutcome(), source.getControllerId(), newAbility, game) == false) {
                    return false;
                }
            }
        }
        game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(": ").append(controller.getName()).append(" copied activated ability").toString());

        EmeraldMysticDelayedTriggeredAbility triggeredAbility = (EmeraldMysticDelayedTriggeredAbility)source;
        game.getState().setValue(triggeredAbility.activeFlagKey, Boolean.FALSE);
        return true;
    }

}

class EmeraldMysticDelayedManaEffect extends OneShotEffect {

    public EmeraldMysticDelayedManaEffect() {
        super(Outcome.Copy);
    }

    public EmeraldMysticDelayedManaEffect(final EmeraldMysticDelayedManaEffect other) {
        super(other);
    }

    @Override
    public EmeraldMysticDelayedManaEffect copy() {
        return new EmeraldMysticDelayedManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Ability newAbility = (Ability)this.getValue("abilityToCopy");
        newAbility.newId();
        newAbility.resolve(game);

        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(": ").append(controller.getName()).append(" copied activated ability").toString());

        EmeraldMysticDelayedTriggeredManaAbility triggeredAbility = (EmeraldMysticDelayedTriggeredManaAbility)source;
        game.getState().setValue(triggeredAbility.activeFlagKey, Boolean.FALSE);
        return true;
    }

}

