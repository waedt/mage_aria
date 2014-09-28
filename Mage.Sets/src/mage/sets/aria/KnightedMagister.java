// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class KnightedMagister extends CardImpl {

    public KnightedMagister(UUID ownerId) {
        super(ownerId, 17, "Knighted Magister", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/kKk4XVI.jpg
        W: Knighted Magister gains your choice of flying, first strike, or vigilance until end of turn.
        */
    }

    public KnightedMagister(final KnightedMagister card) {
        super(card);
    }

    @Override
    public KnightedMagister copy() {
        return new KnightedMagister(this);
    }

}

