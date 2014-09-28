// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MadcapJuggler extends CardImpl {

    public MadcapJuggler(UUID ownerId) {
        super(ownerId, 140, "Madcap Juggler", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Smr7cmD.jpg
        Madcap Juggler can't be blocked except by two or more creatures.
        */
    }

    public MadcapJuggler(final MadcapJuggler card) {
        super(card);
    }

    @Override
    public MadcapJuggler copy() {
        return new MadcapJuggler(this);
    }

}

