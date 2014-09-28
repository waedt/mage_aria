// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ImpressiveVista extends CardImpl {

    public ImpressiveVista(UUID ownerId) {
        super(ownerId, 138, "Impressive Vista", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/IZ30X83.jpg
        Gain control of target creature with toughness less than the number of Mountains you control.
        */
    }

    public ImpressiveVista(final ImpressiveVista card) {
        super(card);
    }

    @Override
    public ImpressiveVista copy() {
        return new ImpressiveVista(this);
    }

}

