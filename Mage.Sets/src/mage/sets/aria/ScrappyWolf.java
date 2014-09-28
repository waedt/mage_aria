// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ScrappyWolf extends CardImpl {

    public ScrappyWolf(UUID ownerId) {
        super(ownerId, 193, "Scrappy Wolf", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Wolf");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/nIDCK6L.jpg
        T: Scrappy Wolf fights target creature. (Each deals damage equal to its power to the other.)
        */
    }

    public ScrappyWolf(final ScrappyWolf card) {
        super(card);
    }

    @Override
    public ScrappyWolf copy() {
        return new ScrappyWolf(this);
    }

}

