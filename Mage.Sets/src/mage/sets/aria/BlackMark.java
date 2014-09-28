// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BlackMark extends CardImpl {

    public BlackMark(UUID ownerId) {
        super(ownerId, 86, "Black Mark", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/owZ1jA0.jpg
        Enchant creature
        Enchanted creature gets -4/-4
        */
    }

    public BlackMark(final BlackMark card) {
        super(card);
    }

    @Override
    public BlackMark copy() {
        return new BlackMark(this);
    }

}

