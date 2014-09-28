// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CleaningDrudge extends CardImpl {

    public CleaningDrudge(UUID ownerId) {
        super(ownerId, 9, "Cleaning Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/udBRvKp.jpg
        When Cleaning Drudge enters the battlefield, you may destroy target enchantment.
        */
    }

    public CleaningDrudge(final CleaningDrudge card) {
        super(card);
    }

    @Override
    public CleaningDrudge copy() {
        return new CleaningDrudge(this);
    }

}

