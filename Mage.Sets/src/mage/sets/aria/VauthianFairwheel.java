// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class VauthianFairwheel extends CardImpl {

    public VauthianFairwheel(UUID ownerId) {
        super(ownerId, 161, "Vauthian Fairwheel", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/88HkkT8.jpg
        Each player discards his or her hand, then draws four cards.
        */
    }

    public VauthianFairwheel(final VauthianFairwheel card) {
        super(card);
    }

    @Override
    public VauthianFairwheel copy() {
        return new VauthianFairwheel(this);
    }

}

