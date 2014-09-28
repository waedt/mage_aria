// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ForestDrake extends CardImpl {

    public ForestDrake(UUID ownerId) {
        super(ownerId, 172, "Forest Drake", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Drake");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/jgwmq9j.jpg
        Flying
        */
    }

    public ForestDrake(final ForestDrake card) {
        super(card);
    }

    @Override
    public ForestDrake copy() {
        return new ForestDrake(this);
    }

}

