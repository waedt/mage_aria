package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

public class EmpathicDrudge extends CardImpl {

    private static final FilterCard filter = new FilterCard("Green spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public EmpathicDrudge(UUID ownerId) {
        super(ownerId, 171, "Empathic Drudge", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Troll");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        // G: Regenerate Empathic Drudge.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{G}")));

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, new ManaCostsImpl("{G}"))));

        /*
        Card Text:
        ----------
        http://i.imgur.com/dMnkHlO.jpg
        G: Regenerate Empathic Drudge.
        Green spells you cast cost G less to cast. This effect reduces only the amount of colored mana you pay.
        */
    }

    public EmpathicDrudge(final EmpathicDrudge card) {
        super(card);
    }

    @Override
    public EmpathicDrudge copy() {
        return new EmpathicDrudge(this);
    }

}

