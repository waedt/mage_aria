// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CourtierofKrewth extends CardImpl {

    public CourtierofKrewth(UUID ownerId) {
        super(ownerId, 11, "Courtier of Krewth", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Advisor");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/sy9tK38.jpg
        When Courtier of Krewth enters the battlefield, put a 2/2 white Knight creature token with vigilance onto the battlefield.
        */
    }

    public CourtierofKrewth(final CourtierofKrewth card) {
        super(card);
    }

    @Override
    public CourtierofKrewth copy() {
        return new CourtierofKrewth(this);
    }

}

