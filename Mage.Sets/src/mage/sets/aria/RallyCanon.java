// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class RallyCanon extends CardImpl {

    public RallyCanon(UUID ownerId) {
        super(ownerId, 30, "Rally Canon", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/6o0wB4d.jpg
        Buyback 1W (You may pay an additional 1W as you cast this spell. If you do, put this card into your hand as it resolves.)
        Creatures you control get +1/+1 until end of turn.
        */
    }

    public RallyCanon(final RallyCanon card) {
        super(card);
    }

    @Override
    public RallyCanon copy() {
        return new RallyCanon(this);
    }

}

