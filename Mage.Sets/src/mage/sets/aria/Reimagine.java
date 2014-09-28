// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Reimagine extends CardImpl {

    public Reimagine(UUID ownerId) {
        super(ownerId, 69, "Reimagine", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/pRE2fdC.jpg
        Return target instant or sorcery card from your graveyard to your hand. Exile Reimagine.
        */
    }

    public Reimagine(final Reimagine card) {
        super(card);
    }

    @Override
    public Reimagine copy() {
        return new Reimagine(this);
    }

}

