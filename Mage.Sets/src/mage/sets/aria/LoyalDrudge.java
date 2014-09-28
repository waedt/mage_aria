// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LoyalDrudge extends CardImpl {

    public LoyalDrudge(UUID ownerId) {
        super(ownerId, 23, "Loyal Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Knight");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/QqlWcir.jpg
        Lifelink
        White spells you cast cost W less to cast. This effect reduces only the amount of colored mana you pay.
        */
    }

    public LoyalDrudge(final LoyalDrudge card) {
        super(card);
    }

    @Override
    public LoyalDrudge copy() {
        return new LoyalDrudge(this);
    }

}

