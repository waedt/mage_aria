// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ScrapfedBoar extends CardImpl {

    public ScrapfedBoar(UUID ownerId) {
        super(ownerId, 192, "Scrapfed Boar", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Boar");
        this.subtype.add("Beast");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/REtT5eB.jpg
        When Scrapfed Boar enters the battlefield, exile target creature card from a graveyard.
        When Scrapfed Boar leaves the battlefield, return the exiled card to its owner's hand.
        */
    }

    public ScrapfedBoar(final ScrapfedBoar card) {
        super(card);
    }

    @Override
    public ScrapfedBoar copy() {
        return new ScrapfedBoar(this);
    }

}

