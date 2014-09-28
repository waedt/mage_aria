// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LuckStone extends CardImpl {

    public LuckStone(UUID ownerId) {
        super(ownerId, 212, "Luck Stone", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/cGoZOoG.jpg
        G, Sacrifice Luck Stone: Reveal the top card of your library. If it is a creature card, put a 1/1 green Squirrel creature token onto the battlefield and you gain 3 life.
        */
    }

    public LuckStone(final LuckStone card) {
        super(card);
    }

    @Override
    public LuckStone copy() {
        return new LuckStone(this);
    }

}

