// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class OrchardTrellis extends CardImpl {

    public OrchardTrellis(UUID ownerId) {
        super(ownerId, 28, "Orchard Trellis", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Plant");
        this.subtype.add("Wall");
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/utmoIe2.jpg
        Defender
        When Orchard Trellis enters the battlefield or dies, you gain 3 life.
        */
    }

    public OrchardTrellis(final OrchardTrellis card) {
        super(card);
    }

    @Override
    public OrchardTrellis copy() {
        return new OrchardTrellis(this);
    }

}

