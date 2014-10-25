package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

public class LoyalDrudge extends CardImpl {

    private static final FilterCard filter = new FilterCard("White spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public LoyalDrudge(UUID ownerId) {
        super(ownerId, 23, "Loyal Drudge", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Knight");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // White spells you cast cost W less to cast. This effect reduces only the amount of colored mana you pay.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, new ManaCostsImpl("{W}"))));

        /*
        Card Text:
        ----------
        http://i.imgur.com/QqlWcir.jpg
        Lifelink
        White spells you cast cost W less to cast. This effect reduces only the amount of colored mana you pay.
        */
    }

    public LoyalDrudge(final LoyalDrudge card) {
        super(card);
    }

    @Override
    public LoyalDrudge copy() {
        return new LoyalDrudge(this);
    }

}

