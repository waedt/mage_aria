package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

public class FierceDrudge extends CardImpl {

    private static final FilterCard filter = new FilterCard("Red spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public FierceDrudge(UUID ownerId) {
        super(ownerId, 133, "Fierce Drudge", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        this.addAbility(HasteAbility.getInstance());

        // Red spells you cast cost R less to cast. This effect reduces only the amount of colored mana you pay.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, new ManaCostsImpl("{R}"))));

        /*
        Card Text:
        ----------
        http://i.imgur.com/GwkSg0q.jpg
        Haste
        Red spells you cast cost R less to cast. This effect reduces only the amount of colored mana you pay.
        */
    }

    public FierceDrudge(final FierceDrudge card) {
        super(card);
    }

    @Override
    public FierceDrudge copy() {
        return new FierceDrudge(this);
    }

}

