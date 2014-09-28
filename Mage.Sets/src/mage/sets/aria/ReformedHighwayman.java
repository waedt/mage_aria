// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ReformedHighwayman extends CardImpl {

    public ReformedHighwayman(UUID ownerId) {
        super(ownerId, 146, "Reformed Highwayman", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Dkf4Tsf.jpg
        When Reformed Highwayman enters the battlefield, add RR to your mana pool.
        */
    }

    public ReformedHighwayman(final ReformedHighwayman card) {
        super(card);
    }

    @Override
    public ReformedHighwayman copy() {
        return new ReformedHighwayman(this);
    }

}

