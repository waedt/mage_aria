// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class FetteredRoc extends CardImpl {

    public FetteredRoc(UUID ownerId) {
        super(ownerId, 14, "Fettered Roc", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/DqKYGtS.jpg
        Fettered Roc has flying as long as it is tapped.
        */
    }

    public FetteredRoc(final FetteredRoc card) {
        super(card);
    }

    @Override
    public FetteredRoc copy() {
        return new FetteredRoc(this);
    }

}

