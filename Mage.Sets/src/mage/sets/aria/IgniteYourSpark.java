// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class IgniteYourSpark extends CardImpl {

    public IgniteYourSpark(UUID ownerId) {
        super(ownerId, 137, "Ignite Your Spark", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/hruELrF.jpg
        If Ignite Your Spark is the tenth spell you've cast this turn, you win the game.
        */
    }

    public IgniteYourSpark(final IgniteYourSpark card) {
        super(card);
    }

    @Override
    public IgniteYourSpark copy() {
        return new IgniteYourSpark(this);
    }

}

