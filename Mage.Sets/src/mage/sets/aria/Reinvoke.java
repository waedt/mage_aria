// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Reinvoke extends CardImpl {

    public Reinvoke(UUID ownerId) {
        super(ownerId, 147, "Reinvoke", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{5}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/LvOWHKS.jpg
        If an opponent cast two or more spells this turn, you may pay 0 rather than pay Reinvoke's mana cost.
        Copy target instant or sorcery spell. You may choose new targets for the copy.
        */
    }

    public Reinvoke(final Reinvoke card) {
        super(card);
    }

    @Override
    public Reinvoke copy() {
        return new Reinvoke(this);
    }

}

