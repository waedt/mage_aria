// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class NoblesEstate extends CardImpl {

    public NoblesEstate(UUID ownerId) {
        super(ownerId, 222, "Noble's Estate", Rarity.COMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/VTMUz2W.jpg
        As Noble's Estate enters the battlefield, choose a color.
        T: Add one mana of the chosen color to your mana pool.
        */
    }

    public NoblesEstate(final NoblesEstate card) {
        super(card);
    }

    @Override
    public NoblesEstate copy() {
        return new NoblesEstate(this);
    }

}

