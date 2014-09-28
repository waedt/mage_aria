// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SufferasI extends CardImpl {

    public SufferasI(UUID ownerId) {
        super(ownerId, 116, "Suffer as I", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/qAJaahR.jpg
        Each player who controls five or more creatures loses 5 life. Each player who controls no creatures discards his or her hand. Destroy all creatures.
        */
    }

    public SufferasI(final SufferasI card) {
        super(card);
    }

    @Override
    public SufferasI copy() {
        return new SufferasI(this);
    }

}

