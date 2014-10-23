package mage.sets.aria;

import java.util.UUID;
import mage.constants.Rarity;

public class GiantSpider extends mage.sets.tenth.GiantSpider {

    public GiantSpider(UUID ownerId) {
        super(ownerId);
        this.rarity = Rarity.COMMON;
        this.cardNumber = 178;
        this.expansionSetCode = "ARI";

        /*
        Card Text:
        ----------
        http://40.media.tumblr.com/c8db3ea87049ff6d3144f7f5cb68564b/tumblr_n60cyqd6y41tcy430o7_400.png
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

