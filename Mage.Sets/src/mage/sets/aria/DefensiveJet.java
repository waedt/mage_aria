// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DefensiveJet extends CardImpl {

    public DefensiveJet(UUID ownerId) {
        super(ownerId, 50, "Defensive Jet", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/YaeVpvH.jpg
        Target creature gets -1/+1 and gains flying until end of turn.
        */
    }

    public DefensiveJet(final DefensiveJet card) {
        super(card);
    }

    @Override
    public DefensiveJet copy() {
        return new DefensiveJet(this);
    }

}

