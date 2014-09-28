// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WorkisNeverOver extends CardImpl {

    public WorkisNeverOver(UUID ownerId) {
        super(ownerId, 122, "Work is Never Over", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/rhFwWO9.jpg
        Return X target creature cards from your graveyard to your hand. (As you cast this, choose a number for X. Use that number in the cost and text of this spell.)
        */
    }

    public WorkisNeverOver(final WorkisNeverOver card) {
        super(card);
    }

    @Override
    public WorkisNeverOver copy() {
        return new WorkisNeverOver(this);
    }

}

