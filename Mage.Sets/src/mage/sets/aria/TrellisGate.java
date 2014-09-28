// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class TrellisGate extends CardImpl {

    public TrellisGate(UUID ownerId) {
        super(ownerId, 204, "Trellis Gate", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Wall");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/efIZ8No.jpg
        Defender
        T: Add one mana of any color to your mana pool. You gain 1 life.
        */
    }

    public TrellisGate(final TrellisGate card) {
        super(card);
    }

    @Override
    public TrellisGate copy() {
        return new TrellisGate(this);
    }

}

