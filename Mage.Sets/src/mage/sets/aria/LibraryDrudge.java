// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LibraryDrudge extends CardImpl {

    public LibraryDrudge(UUID ownerId) {
        super(ownerId, 56, "Library Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Homunculus");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/wBSZu2g.jpg
        1U, Sacrifice Library Drudge: Return target instant or sorcery card from your graveyard to your hand.
        */
    }

    public LibraryDrudge(final LibraryDrudge card) {
        super(card);
    }

    @Override
    public LibraryDrudge copy() {
        return new LibraryDrudge(this);
    }

}

