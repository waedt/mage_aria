// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ItWasMadeofBees extends CardImpl {

    public ItWasMadeofBees(UUID ownerId) {
        super(ownerId, 183, "It Was Made of Bees!", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/sgv1knQ.jpg
        As an additional cost to cast It Was Made of Bees! sacrifice a creature.
        Put X 1/1 green Bee Insect creature tokens with flying onto the battlefield, where X is equal to the sacrificed creature's toughness.
        */
    }

    public ItWasMadeofBees(final ItWasMadeofBees card) {
        super(card);
    }

    @Override
    public ItWasMadeofBees copy() {
        return new ItWasMadeofBees(this);
    }

}

