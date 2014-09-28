// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LoftyDragonlord extends CardImpl {

    public LoftyDragonlord(UUID ownerId) {
        super(ownerId, 22, "Lofty Dragonlord", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dragon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/P8oDg4E.jpg
        Flying
        Spells your opponents control that target Lofty Dragonlord cost 2 more to cast.
        Creatures can't attack you unless their controller pays 2 for each creature he or she controls that's attacking you.
        */
    }

    public LoftyDragonlord(final LoftyDragonlord card) {
        super(card);
    }

    @Override
    public LoftyDragonlord copy() {
        return new LoftyDragonlord(this);
    }

}

