// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DweldianOaf extends CardImpl {

    public DweldianOaf(UUID ownerId) {
        super(ownerId, 130, "Dweldian Oaf", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Giant");
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/FsAgAFn.jpg
        Whenever you cast an instant or sorcery, Dweldian Oaf gains trample until end of turn.
        */
    }

    public DweldianOaf(final DweldianOaf card) {
        super(card);
    }

    @Override
    public DweldianOaf copy() {
        return new DweldianOaf(this);
    }

}

