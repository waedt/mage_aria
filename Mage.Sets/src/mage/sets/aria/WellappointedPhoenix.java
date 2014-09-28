// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WellappointedPhoenix extends CardImpl {

    public WellappointedPhoenix(UUID ownerId) {
        super(ownerId, 162, "Well-appointed Phoenix", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Phoenix");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Hu6SmMF.jpg
        Flying, haste
        Whenever Well-appointed Phoenix deals combat damage, you may exile a card from your graveyard. If you do, return target instant or sorcery card from your graveyard to your hand.
        Whenever an instant or sorcery you control deals damage, you may return Well-appointed Phoenix from your graveyard to your hand.
        */
    }

    public WellappointedPhoenix(final WellappointedPhoenix card) {
        super(card);
    }

    @Override
    public WellappointedPhoenix copy() {
        return new WellappointedPhoenix(this);
    }

}

