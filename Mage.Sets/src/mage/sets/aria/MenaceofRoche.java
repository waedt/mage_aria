// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MenaceofRoche extends CardImpl {

    public MenaceofRoche(UUID ownerId) {
        super(ownerId, 186, "Menace of Roche", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Wurm");
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/aPH9Ovm.jpg
        Creatures with less power than Menace of Roche can't block it.
        At the beginning of your end step, put a +1/+1 counter on Menace of Roche.
        */
    }

    public MenaceofRoche(final MenaceofRoche card) {
        super(card);
    }

    @Override
    public MenaceofRoche copy() {
        return new MenaceofRoche(this);
    }

}

