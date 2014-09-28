// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PosableStatue extends CardImpl {

    public PosableStatue(UUID ownerId) {
        super(ownerId, 66, "Posable Statue", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/B8FdT9j.jpg
        1U: Posable Statue gets +2/-2 until end of turn.
        */
    }

    public PosableStatue(final PosableStatue card) {
        super(card);
    }

    @Override
    public PosableStatue copy() {
        return new PosableStatue(this);
    }

}

