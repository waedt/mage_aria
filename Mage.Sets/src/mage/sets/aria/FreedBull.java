// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class FreedBull extends CardImpl {

    public FreedBull(UUID ownerId) {
        super(ownerId, 173, "Freed Bull", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bull");
        this.subtype.add("Beast");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);

        this.addAbility(TrampleAbility.getInstance());

        // TODO: There is currently no way to detect damage done by fighting
        //       specifically. So, to handle Freed Bull's second ability, a way
        //       to do that needs to be added first.

        /*
        Card Text:
        ----------
        http://i.imgur.com/bjituTJ.jpg
        Trample
        Prevent all damage that would be dealt to Freed Bull by creatures fighting it.
        */
    }

    public FreedBull(final FreedBull card) {
        super(card);
    }

    @Override
    public FreedBull copy() {
        return new FreedBull(this);
    }

}

