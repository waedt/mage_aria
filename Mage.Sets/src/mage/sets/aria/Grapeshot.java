// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Grapeshot extends CardImpl {

    public Grapeshot(UUID ownerId) {
        super(ownerId, 136, "Grapeshot", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/a97LNpt.jpg
        Grapeshot deals 1 damage to target creature or player.
        Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        */
    }

    public Grapeshot(final Grapeshot card) {
        super(card);
    }

    @Override
    public Grapeshot copy() {
        return new Grapeshot(this);
    }

}

