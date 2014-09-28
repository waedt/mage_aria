// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class StockDogs extends CardImpl {

    public StockDogs(UUID ownerId) {
        super(ownerId, 38, "Stock Dogs", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Hound");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Imk7ut6.jpg
        
        */
    }

    public StockDogs(final StockDogs card) {
        super(card);
    }

    @Override
    public StockDogs copy() {
        return new StockDogs(this);
    }

}

