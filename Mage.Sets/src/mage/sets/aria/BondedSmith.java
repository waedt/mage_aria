// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BondedSmith extends CardImpl {

    public BondedSmith(UUID ownerId) {
        super(ownerId, 7, "Bonded Smith", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/D8k7gAL.jpg
        When Bonded Smith dies, you may search your library for an Equipment card, reveal that card and put it into your hand. Then shuffle your library.
        */
    }

    public BondedSmith(final BondedSmith card) {
        super(card);
    }

    @Override
    public BondedSmith copy() {
        return new BondedSmith(this);
    }

}

