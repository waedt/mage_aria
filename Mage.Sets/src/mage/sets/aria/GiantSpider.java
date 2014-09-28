// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GiantSpider extends CardImpl {

    public GiantSpider(UUID ownerId) {
        super(ownerId, 178, "Giant Spider", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Spider");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/172cMdC.jpg
        Reach
        */
    }

    public GiantSpider(final GiantSpider card) {
        super(card);
    }

    @Override
    public GiantSpider copy() {
        return new GiantSpider(this);
    }

}

