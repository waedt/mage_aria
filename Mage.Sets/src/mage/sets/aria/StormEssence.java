// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class StormEssence extends CardImpl {

    public StormEssence(UUID ownerId) {
        super(ownerId, 76, "Storm Essence", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Elemental");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/qo19DNU.jpg
        Flash, flying
        Storm Essence enters the battlefield with a +1/+1 counter on it for each other spell cast this turn.
        */
    }

    public StormEssence(final StormEssence card) {
        super(card);
    }

    @Override
    public StormEssence copy() {
        return new StormEssence(this);
    }

}

