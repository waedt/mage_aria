// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PrizeDrake extends CardImpl {

    public PrizeDrake(UUID ownerId) {
        super(ownerId, 144, "Prize Drake", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Drake");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Z8hAgUk.jpg
        Flying
        */
    }

    public PrizeDrake(final PrizeDrake card) {
        super(card);
    }

    @Override
    public PrizeDrake copy() {
        return new PrizeDrake(this);
    }

}

