// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Revengeinabox extends CardImpl {

    public Revengeinabox(UUID ownerId) {
        super(ownerId, 149, "Revenge-in-a-box", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/6JtJTgN.jpg
        If an opponent blocked a creature with two or more creatures this turn, you may pay R rather than pay Revenge-in-a-box's mana cost.
        Revenge-in-a-box deals 3 damage to target creature and 3 damage to that creature's controller.
        */
    }

    public Revengeinabox(final Revengeinabox card) {
        super(card);
    }

    @Override
    public Revengeinabox copy() {
        return new Revengeinabox(this);
    }

}

