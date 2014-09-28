// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Firefright extends CardImpl {

    public Firefright(UUID ownerId) {
        super(ownerId, 134, "Firefright", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/A9dvyaE.jpg
        Add R to your mana pool.
        Draw a card.
        */
    }

    public Firefright(final Firefright card) {
        super(card);
    }

    @Override
    public Firefright copy() {
        return new Firefright(this);
    }

}

