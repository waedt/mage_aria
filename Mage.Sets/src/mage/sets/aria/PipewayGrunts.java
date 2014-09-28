// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PipewayGrunts extends CardImpl {

    public PipewayGrunts(UUID ownerId) {
        super(ownerId, 143, "Pipeway Grunts", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/mvy8Jjt.jpg
        Sacrifice Pipeway Grunts: Add R to your mana pool.
        */
    }

    public PipewayGrunts(final PipewayGrunts card) {
        super(card);
    }

    @Override
    public PipewayGrunts copy() {
        return new PipewayGrunts(this);
    }

}

