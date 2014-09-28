// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ReleaseSewerGas extends CardImpl {

    public ReleaseSewerGas(UUID ownerId) {
        super(ownerId, 70, "Release Sewer Gas", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/ucVQSBf.jpg
        If an opponent controls a creature with reach, you may pay U rather than pay Release Sewer Gas's mana cost.
        Creatures get -4/-0 until end of turn.
        */
    }

    public ReleaseSewerGas(final ReleaseSewerGas card) {
        super(card);
    }

    @Override
    public ReleaseSewerGas copy() {
        return new ReleaseSewerGas(this);
    }

}

