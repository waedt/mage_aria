// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DelightedApprentice extends CardImpl {

    public DelightedApprentice(UUID ownerId) {
        super(ownerId, 127, "Delighted Apprentice", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Goblin");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/hSjQpMU.jpg
        Haste
        Whenever Delighted Apprentice attacks, it deals 1 damage to target creature or player.
        */
    }

    public DelightedApprentice(final DelightedApprentice card) {
        super(card);
    }

    @Override
    public DelightedApprentice copy() {
        return new DelightedApprentice(this);
    }

}

