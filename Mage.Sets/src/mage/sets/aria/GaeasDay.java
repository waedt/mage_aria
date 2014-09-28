// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GaeasDay extends CardImpl {

    public GaeasDay(UUID ownerId) {
        super(ownerId, 175, "Gaea's Day", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{X}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/J6sz9b2.jpg
        Target land you control becomes an X/X creature that's still a land.
        Storm
        */
    }

    public GaeasDay(final GaeasDay card) {
        super(card);
    }

    @Override
    public GaeasDay copy() {
        return new GaeasDay(this);
    }

}

