// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MenacedbySkeletons extends CardImpl {

    public MenacedbySkeletons(UUID ownerId) {
        super(ownerId, 101, "Menaced by Skeletons", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/fjA20fF.jpg
        Whenever a creature an opponent controls attacks, you may pay 1B. If you do, put a 1/1 black Skeleton creature token onto the battlefield.
        */
    }

    public MenacedbySkeletons(final MenacedbySkeletons card) {
        super(card);
    }

    @Override
    public MenacedbySkeletons copy() {
        return new MenacedbySkeletons(this);
    }

}

