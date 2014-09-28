// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Repliverberate extends CardImpl {

    public Repliverberate(UUID ownerId) {
        super(ownerId, 148, "Repliverberate", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/oLnI6Hd.jpg
        Copy target instant or sorcery spell. You may choose new targets for the copy.
        Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        */
    }

    public Repliverberate(final Repliverberate card) {
        super(card);
    }

    @Override
    public Repliverberate copy() {
        return new Repliverberate(this);
    }

}

