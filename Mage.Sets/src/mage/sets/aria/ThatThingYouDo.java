// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ThatThingYouDo extends CardImpl {

    public ThatThingYouDo(UUID ownerId) {
        super(ownerId, 157, "That Thing You Do", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{5}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/iWSAs79.jpg
        Gain control of target creature until end of turn. Untap it. It gains first strike and haste until end of turn.
        */
    }

    public ThatThingYouDo(final ThatThingYouDo card) {
        super(card);
    }

    @Override
    public ThatThingYouDo copy() {
        return new ThatThingYouDo(this);
    }

}

