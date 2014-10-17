package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

public class CharmStone extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature with power 3 or less");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new PowerPredicate(Filter.ComparisonType.LessThan, 4));
    }

    public CharmStone(UUID ownerId) {
        super(ownerId, 208, "Charm Stone", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        // U, Sacrifice Charm Stone: Return target creature with power 3 or less to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl("{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/wq8BnAj.jpg
        U, Sacrifice Charm Stone: Return target creature with power 3 or less to its owner's hand.
        */
    }

    public CharmStone(final CharmStone card) {
        super(card);
    }

    @Override
    public CharmStone copy() {
        return new CharmStone(this);
    }

}

