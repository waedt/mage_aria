// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SelflessCommander extends CardImpl {

    public SelflessCommander(UUID ownerId) {
        super(ownerId, 35, "Selfless Commander", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/TuCfIGe.jpg
        When Selfless Commander enters the battlefield, put a 2/2 white Knight creature token with vigilance onto the battlefield.
        1, Sacrifice Selfless Commander: Creatures you control get +2/+2 until end of turn.
        */
    }

    public SelflessCommander(final SelflessCommander card) {
        super(card);
    }

    @Override
    public SelflessCommander copy() {
        return new SelflessCommander(this);
    }

}

