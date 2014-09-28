// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DweldianArchmage extends CardImpl {

    public DweldianArchmage(UUID ownerId) {
        super(ownerId, 129, "Dweldian Archmage", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Elemental");
        this.subtype.add("Wizard");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/uIBNh5P.jpg
        Haste
        Dweldian Archmage enters the battlefield with two +1/+1 counters on it for each other spell cast this turn.
        1, Remove a +1/+1 counter from Dweldian Archmage: Dweldian Archmage deals 1 damage to target creature or player.
        */
    }

    public DweldianArchmage(final DweldianArchmage card) {
        super(card);
    }

    @Override
    public DweldianArchmage copy() {
        return new DweldianArchmage(this);
    }

}

