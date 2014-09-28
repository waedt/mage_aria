// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DauntlessBurglar extends CardImpl {

    public DauntlessBurglar(UUID ownerId) {
        super(ownerId, 92, "Dauntless Burglar", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Spirit");
        this.subtype.add("Rogue");
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/MASl7cn.jpg
        Dauntless Burglar can't block or be blocked.
        */
    }

    public DauntlessBurglar(final DauntlessBurglar card) {
        super(card);
    }

    @Override
    public DauntlessBurglar copy() {
        return new DauntlessBurglar(this);
    }

}

