// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WingedMenace extends CardImpl {

    public WingedMenace(UUID ownerId) {
        super(ownerId, 121, "Winged Menace", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bat");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/WE6VaI9.jpg
        Flying
        */
    }

    public WingedMenace(final WingedMenace card) {
        super(card);
    }

    @Override
    public WingedMenace copy() {
        return new WingedMenace(this);
    }

}

