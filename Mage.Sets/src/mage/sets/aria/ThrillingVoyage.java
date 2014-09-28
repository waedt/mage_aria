// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ThrillingVoyage extends CardImpl {

    public ThrillingVoyage(UUID ownerId) {
        super(ownerId, 41, "Thrilling Voyage", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/iLCmIBf.jpg
        Put a 1/1 white Soldier creature token onto the battlefield. Up to one creature you control gets +2/+2 this turn and gains flying until end of turn.
        */
    }

    public ThrillingVoyage(final ThrillingVoyage card) {
        super(card);
    }

    @Override
    public ThrillingVoyage copy() {
        return new ThrillingVoyage(this);
    }

}

