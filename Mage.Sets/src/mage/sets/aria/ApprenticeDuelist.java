// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ApprenticeDuelist extends CardImpl {

    public ApprenticeDuelist(UUID ownerId) {
        super(ownerId, 2, "Apprentice Duelist", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/m0Guulw.jpg
        2: Apprentice Duelist gains double strike until end of turn.
        */
    }

    public ApprenticeDuelist(final ApprenticeDuelist card) {
        super(card);
    }

    @Override
    public ApprenticeDuelist copy() {
        return new ApprenticeDuelist(this);
    }

}

