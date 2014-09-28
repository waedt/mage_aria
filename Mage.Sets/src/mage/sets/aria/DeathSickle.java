// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DeathSickle extends CardImpl {

    public DeathSickle(UUID ownerId) {
        super(ownerId, 209, "Death Sickle", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Equipment");

        /*
        Card Text:
        ----------
        http://i.imgur.com/iz1CoIA.jpg
        Equipped creature and each creature blocking or blocked by equipped creature has deathtouch.
        Equip 0
        */
    }

    public DeathSickle(final DeathSickle card) {
        super(card);
    }

    @Override
    public DeathSickle copy() {
        return new DeathSickle(this);
    }

}

