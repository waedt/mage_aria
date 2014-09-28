// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BanneroftheMasterless extends CardImpl {

    public BanneroftheMasterless(UUID ownerId) {
        super(ownerId, 125, "Banner of the Masterless", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/GEiuihl.jpg
        Creatures you control get +1/+0 for each instant and sorcery card in your graveyard.
        */
    }

    public BanneroftheMasterless(final BanneroftheMasterless card) {
        super(card);
    }

    @Override
    public BanneroftheMasterless copy() {
        return new BanneroftheMasterless(this);
    }

}

