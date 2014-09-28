// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class QuaketoOrder extends CardImpl {

    public QuaketoOrder(UUID ownerId) {
        super(ownerId, 145, "Quake to Order", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{X}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/uVwgMKg.jpg
        Choose one or all - Quake to Order deals X damage to each creature without flying; or Quake to Order deals X damage to each creature with flying; or Quake to Order deals X damage to each player; or Quake to Order deals X damage to each planeswalker.
        */
    }

    public QuaketoOrder(final QuaketoOrder card) {
        super(card);
    }

    @Override
    public QuaketoOrder copy() {
        return new QuaketoOrder(this);
    }

}

