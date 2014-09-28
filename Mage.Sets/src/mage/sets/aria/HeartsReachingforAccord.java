// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class HeartsReachingforAccord extends CardImpl {

    public HeartsReachingforAccord(UUID ownerId) {
        super(ownerId, 16, "Hearts Reaching for Accord", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/1caVK4s.jpg
        Each player with more than four cards in hand discards until he or she has four, then each player with fewer than four cards in hand draws until he or she has four. Each player's life total becomes 10.
        */
    }

    public HeartsReachingforAccord(final HeartsReachingforAccord card) {
        super(card);
    }

    @Override
    public HeartsReachingforAccord copy() {
        return new HeartsReachingforAccord(this);
    }

}

