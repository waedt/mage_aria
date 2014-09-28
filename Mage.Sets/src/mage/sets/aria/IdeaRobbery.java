// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class IdeaRobbery extends CardImpl {

    public IdeaRobbery(UUID ownerId) {
        super(ownerId, 1, "Idea Robbery", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{X}{X}");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/DRJ8mTR.jpg
        Spend only blue mana on X (both).
        Gain control of target spell with converted mana cost X. You may choose new targets for it. (If it is a permanent spell, the permanent it becomes enters the battlefield under your control.)
        */
    }

    public IdeaRobbery(final IdeaRobbery card) {
        super(card);
    }

    @Override
    public IdeaRobbery copy() {
        return new IdeaRobbery(this);
    }

}

