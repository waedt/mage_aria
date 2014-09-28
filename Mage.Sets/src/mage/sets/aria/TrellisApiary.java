// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class TrellisApiary extends CardImpl {

    public TrellisApiary(UUID ownerId) {
        super(ownerId, 203, "Trellis Apiary", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Wall");
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/5nvpcBv.jpg
        Defender, reach
        1G: Put a 1/1 green Bee Insect creature token with flying onto the battlefield.
        1G, Sacrifice a Bee: Trellis Apiary deals 1 damage to target creature or player.
        */
    }

    public TrellisApiary(final TrellisApiary card) {
        super(card);
    }

    @Override
    public TrellisApiary copy() {
        return new TrellisApiary(this);
    }

}

