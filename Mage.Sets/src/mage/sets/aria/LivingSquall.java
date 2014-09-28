// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LivingSquall extends CardImpl {

    public LivingSquall(UUID ownerId) {
        super(ownerId, 57, "Living Squall", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Elemental");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);

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

