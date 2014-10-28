package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

public class PontiffoftheVoid extends CardImpl {

    public PontiffoftheVoid(UUID ownerId) {
        super(ownerId, 107, "Pontiff of the Void", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        // Whenever a player casts a spell, that player sacrifices a permanent.
        Effect effect = new SacrificeEffect(new FilterPermanent(), 1, null);
        effect.setText("that player sacrifices a permanent");
        this.addAbility(new PontiffOfTheVoidTriggeredAbility(effect));

        /*
        Card Text:
        ----------
        http://i.imgur.com/xmbvn19.jpg
        Whenever a player casts a spell, that player sacrifices a permanent.
        */
    }

    public PontiffoftheVoid(final PontiffoftheVoid card) {
        super(card);
    }

    @Override
    public PontiffoftheVoid copy() {
        return new PontiffoftheVoid(this);
    }

}

class PontiffOfTheVoidTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public PontiffOfTheVoidTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public PontiffOfTheVoidTriggeredAbility(final PontiffOfTheVoidTriggeredAbility other) {
        super(other);
    }

    @Override
    public PontiffOfTheVoidTriggeredAbility copy() {
        return new PontiffOfTheVoidTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if(spell != null && filter.match(spell, getControllerId(), game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts " + filter.getMessage() + ", " + super.getRule();
    }
}
