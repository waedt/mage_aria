// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MentalBackflip extends CardImpl {

    public MentalBackflip(UUID ownerId) {
        super(ownerId, 58, "Mental Backflip", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/pkNcKZn.jpg
        Look at the top five cards of your library and separate them into two face down piles, then name a card other than a basic land. Target opponent chooses a pile and reveals it. If the named card is in the chosen pile, put that pile into your hand. Put the rest of the cards on the bottom of your library in any order.
        */
    }

    public MentalBackflip(final MentalBackflip card) {
        super(card);
    }

    @Override
    public MentalBackflip copy() {
        return new MentalBackflip(this);
    }

}

