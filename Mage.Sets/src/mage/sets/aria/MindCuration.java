// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MindCuration extends CardImpl {

    public MindCuration(UUID ownerId) {
        super(ownerId, 60, "Mind Curation", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/mEgGXMt.jpg
        Buyback 2U
        Reveal the top five cards of your library. For each blue card among them, target player mills 2. Put any number of the revealed cards on the bottom of your library in any order. Then put the rest on top of your library in any order.
        */
    }

    public MindCuration(final MindCuration card) {
        super(card);
    }

    @Override
    public MindCuration copy() {
        return new MindCuration(this);
    }

}

