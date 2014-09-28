// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class OsrektsFamiliar extends CardImpl {

    public OsrektsFamiliar(UUID ownerId) {
        super(ownerId, 62, "Osrekt's Familiar", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Xq1GVhR.jpg
        Flying
        When you cast an instant or sorcery, you may return Osrekt's Familiar to its owner's hand. If you do, copy that spell. You may choose new targets for the copy.
        */
    }

    public OsrektsFamiliar(final OsrektsFamiliar card) {
        super(card);
    }

    @Override
    public OsrektsFamiliar copy() {
        return new OsrektsFamiliar(this);
    }

}

