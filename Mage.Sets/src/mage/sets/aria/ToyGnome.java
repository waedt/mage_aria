// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ToyGnome extends CardImpl {

    public ToyGnome(UUID ownerId) {
        super(ownerId, 218, "Toy Gnome", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Gnome");
        this.subtype.add("Constuct");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        /*
        Card Text:
        ----------
        http://i.imgur.com/sozhErK.jpg
        Sacrifice Toy Gnome: You gain 2 life.
        */
    }

    public ToyGnome(final ToyGnome card) {
        super(card);
    }

    @Override
    public ToyGnome copy() {
        return new ToyGnome(this);
    }

}

