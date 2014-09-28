// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class TimeoftheMighty extends CardImpl {

    public TimeoftheMighty(UUID ownerId) {
        super(ownerId, 202, "Time of the Mighty", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{G}{G}{G}{G}{G}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/InuZYxA.jpg
        Destroy all noncreature permanents.
        */
    }

    public TimeoftheMighty(final TimeoftheMighty card) {
        super(card);
    }

    @Override
    public TimeoftheMighty copy() {
        return new TimeoftheMighty(this);
    }

}

