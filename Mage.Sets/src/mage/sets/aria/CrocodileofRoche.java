// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CrocodileofRoche extends CardImpl {

    public CrocodileofRoche(UUID ownerId) {
        super(ownerId, 168, "Crocodile of Roche", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Crocodile");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/L4ZreiL.jpg
        Whenever you cast an instant or sorcery, Crocodile of Roche gains lifelink until end of turn.
        */
    }

    public CrocodileofRoche(final CrocodileofRoche card) {
        super(card);
    }

    @Override
    public CrocodileofRoche copy() {
        return new CrocodileofRoche(this);
    }

}

