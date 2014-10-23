package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.combat.CantAttackAllAnyPlayerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

public class FrozenWorld extends CardImpl {

    public FrozenWorld(UUID ownerId) {
        super(ownerId, 53, "Frozen World", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        // Creatures can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAllAnyPlayerEffect(Duration.WhileOnBattlefield, new FilterCreaturePermanent("Creatures"))));

        // Permanents can't untap.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FrozenWorldEffect()));

        // At the beginning of your upkeep, pay 1U or sacrifice Frozen World.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{1}{U}")), TargetController.YOU, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/Ld6iqvb.jpg
        Creatures can't attack.
        Permanents can't untap.
        At the beginning of your upkeep, pay 1U or sacrifice Frozen World.
        */
    }

    public FrozenWorld(final FrozenWorld card) {
        super(card);
    }

    @Override
    public FrozenWorld copy() {
        return new FrozenWorld(this);
    }

}

class FrozenWorldEffect extends ReplacementEffectImpl {
    public FrozenWorldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        this.staticText = "Permanents can't untap";
    }

    public FrozenWorldEffect(final FrozenWorldEffect other) {
        super(other);
    }

    @Override
    public FrozenWorldEffect copy() {
        return new FrozenWorldEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == EventType.UNTAP;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
