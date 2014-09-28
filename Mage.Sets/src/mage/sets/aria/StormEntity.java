// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class StormEntity extends CardImpl {

    public StormEntity(UUID ownerId) {
        super(ownerId, 154, "Storm Entity", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Elemental");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/xbscNbP.jpg
        Haste
        Storm Entity enters the battlefield with a +1/+1 counter on it for each other spell cast this turn.
        */
    }

    public StormEntity(final StormEntity card) {
        super(card);
    }

    @Override
    public StormEntity copy() {
        return new StormEntity(this);
    }

}

