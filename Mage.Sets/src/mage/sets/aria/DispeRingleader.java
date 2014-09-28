// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DispeRingleader extends CardImpl {

    public DispeRingleader(UUID ownerId) {
        super(ownerId, 128, "Dispe Ringleader", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/9fNkgtx.jpg
        Whenever an instant or sorcery spell you control would deal damage, if it is a copy, it deals twice that much damage instead.
        RR, T: Copy target instant or sorcery you control. You may choose new targets for the copy.
        */
    }

    public DispeRingleader(final DispeRingleader card) {
        super(card);
    }

    @Override
    public DispeRingleader copy() {
        return new DispeRingleader(this);
    }

}

