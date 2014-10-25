package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition.ComparisonType;
import mage.abilities.condition.common.SpellsCastThisTurnCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.permanent.ControllerPredicate;

public class LivingSquall extends CardImpl {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public LivingSquall(UUID ownerId) {
        super(ownerId, 57, "Living Squall", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Elemental");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you've cast five or more spells this turn, you may pay U rather than pay Living Squall's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{U}"), new SpellsCastThisTurnCondition(filter, ComparisonType.GreaterThan, 4), "If you've cast five or more spells this turn, you may pay {U} rather than pay Living Squall's mana cost."));

        // U: Return Living Squall to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(), new ManaCostsImpl("{U}")));

        /*
        Card Text:
        ----------
        http://i.imgur.com/Kzms2R9.jpg
        If you've cast five or more spells this turn, you may pay U rather than pay Living Squall's mana cost.
        Flying
        U: Return Living Squall to its owner's hand.
        */
    }

    public LivingSquall(final LivingSquall card) {
        super(card);
    }

    @Override
    public LivingSquall copy() {
        return new LivingSquall(this);
    }

}

