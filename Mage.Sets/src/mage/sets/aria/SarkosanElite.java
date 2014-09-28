// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SarkosanElite extends CardImpl {

    public SarkosanElite(UUID ownerId) {
        super(ownerId, 111, "Sarkosan Elite", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Vampire");
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/oyBoaXx.jpg
        Lifelink
        */
    }

    public SarkosanElite(final SarkosanElite card) {
        super(card);
    }

    @Override
    public SarkosanElite copy() {
        return new SarkosanElite(this);
    }

}

