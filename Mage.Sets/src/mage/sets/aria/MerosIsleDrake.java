// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MerosIsleDrake extends CardImpl {

    public MerosIsleDrake(UUID ownerId) {
        super(ownerId, 59, "Meros Isle Drake", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Drake");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/aILCz16.jpg
        Flying
        */
    }

    public MerosIsleDrake(final MerosIsleDrake card) {
        super(card);
    }

    @Override
    public MerosIsleDrake copy() {
        return new MerosIsleDrake(this);
    }

}

