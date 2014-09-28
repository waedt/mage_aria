// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class TimeTheft extends CardImpl {

    public TimeTheft(UUID ownerId) {
        super(ownerId, 80, "Time Theft", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{7}{U}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/5AiX1sk.jpg
        Take an extra turn after this one. End the turn.
        */
    }

    public TimeTheft(final TimeTheft card) {
        super(card);
    }

    @Override
    public TimeTheft copy() {
        return new TimeTheft(this);
    }

}

