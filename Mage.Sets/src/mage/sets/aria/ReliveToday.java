// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ReliveToday extends CardImpl {

    public ReliveToday(UUID ownerId) {
        super(ownerId, 71, "Relive Today", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{5}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/1lKClcU.jpg
        Untap target permanent.
        Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        */
    }

    public ReliveToday(final ReliveToday card) {
        super(card);
    }

    @Override
    public ReliveToday copy() {
        return new ReliveToday(this);
    }

}

