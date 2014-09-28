// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SorcererofIllus extends CardImpl {

    public SorcererofIllus(UUID ownerId) {
        super(ownerId, 74, "Sorcerer of Illus", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/VU8gjkL.jpg
        1U, T: Search your library for a Trap card, reveal it, and put it into your hand, then shuffle your library.
        1U, T: Target creature gets -2/-0 until end of turn.
        1U, T: Return Sorcerer of Illus and target creature to their owners' hands.
        */
    }

    public SorcererofIllus(final SorcererofIllus card) {
        super(card);
    }

    @Override
    public SorcererofIllus copy() {
        return new SorcererofIllus(this);
    }

}

