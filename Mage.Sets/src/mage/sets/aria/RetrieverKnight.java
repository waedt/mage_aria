// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class RetrieverKnight extends CardImpl {

    public RetrieverKnight(UUID ownerId) {
        super(ownerId, 32, "Retriever Knight", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Knight");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/DaYi5Nm.jpg
        Flash, first strike
        When Retriever Knight enters the battlefield, return a creature you control to its owner's hand.
        */
    }

    public RetrieverKnight(final RetrieverKnight card) {
        super(card);
    }

    @Override
    public RetrieverKnight copy() {
        return new RetrieverKnight(this);
    }

}

