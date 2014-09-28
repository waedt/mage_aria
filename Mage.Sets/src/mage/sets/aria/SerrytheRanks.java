// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SerrytheRanks extends CardImpl {

    public SerrytheRanks(UUID ownerId) {
        super(ownerId, 36, "Serry the Ranks", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/IxQYmQy.jpg
        Each player puts three 2/2 white Knight creature tokens with vigilance onto the battlefield, then sacrifices all but three creatures.
        */
    }

    public SerrytheRanks(final SerrytheRanks card) {
        super(card);
    }

    @Override
    public SerrytheRanks copy() {
        return new SerrytheRanks(this);
    }

}

