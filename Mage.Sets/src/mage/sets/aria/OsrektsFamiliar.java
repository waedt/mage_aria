package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ReturnToHandSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;

public class OsrektsFamiliar extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public OsrektsFamiliar(UUID ownerId) {
        super(ownerId, 62, "Osrekt's Familiar", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlue(true);

        // When you cast an instant or sorcery, you may return Osrekt's Familiar to its owner's hand. If you do, copy that spell. You may choose new targets for the copy.
        Effect effect = new OsrektsFamiliarDoIfCostPaid(new CopyTargetSpellEffect(), new ReturnToHandSourceCost());
        effect.setText("you may return Osrekt's Familiar to its owner's hand. If you do, copy that spell. You may choose new targets for the copy.");
        this.addAbility(new SpellCastControllerTriggeredAbility(effect, filter, false, true));

        /*
        Card Text:
        ----------
        http://i.imgur.com/Xq1GVhR.jpg
        Flying
        When you cast an instant or sorcery, you may return Osrekt's Familiar to its owner's hand. If you do, copy that spell. You may choose new targets for the copy.
        */
    }

    public OsrektsFamiliar(final OsrektsFamiliar card) {
        super(card);
    }

    @Override
    public OsrektsFamiliar copy() {
        return new OsrektsFamiliar(this);
    }

}

// A subclass of DoIfCostPaid so the usage message can be nicer/more helpful.
class OsrektsFamiliarDoIfCostPaid extends DoIfCostPaid {

    public OsrektsFamiliarDoIfCostPaid(Effect effect, Cost cost) {
        super(effect, cost, "Return {this} to its owner's hand to copy the spell jsut cast?");
    }

    public OsrektsFamiliarDoIfCostPaid(final OsrektsFamiliarDoIfCostPaid other) {
        super(other);
    }

    @Override
    public OsrektsFamiliarDoIfCostPaid copy() {
        return new OsrektsFamiliarDoIfCostPaid(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));

        this.chooseUseText = "Return {this} to its owner's hand to copy ";
        if(spell != null) {
            this.chooseUseText += spell.getName() + "?";
        } else {
            this.chooseUseText += "the spell just cast?";
        }

        return super.apply(game, source);
    }
}
