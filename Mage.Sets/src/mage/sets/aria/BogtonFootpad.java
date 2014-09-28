// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BogtonFootpad extends CardImpl {

    public BogtonFootpad(UUID ownerId) {
        super(ownerId, 87, "Bogton Footpad", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Goblin");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/wc09QP0.jpg
        When Bogton Footpad enters the battlefield, if you cast an instant or sorcery this turn, it gains haste until end of turn.
        */
    }

    public BogtonFootpad(final BogtonFootpad card) {
        super(card);
    }

    @Override
    public BogtonFootpad copy() {
        return new BogtonFootpad(this);
    }

}

