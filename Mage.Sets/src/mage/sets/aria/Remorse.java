// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Remorse extends CardImpl {

    public Remorse(UUID ownerId) {
        super(ownerId, 31, "Remorse", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}{W}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Avatar");
        this.subtype.add("Incarnation");
        this.power = new MageInt(2);
        this.toughness = new MageInt(7);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/FiJYXo7.jpg
        While choosing targets as part of casting a spell or activating an ability, your opponents must choose themselves or permanents they control if able.
        When Remorse is put into a graveyard from anywhere, shuffle it into its owner's library.
        */
    }

    public Remorse(final Remorse card) {
        super(card);
    }

    @Override
    public Remorse copy() {
        return new Remorse(this);
    }

}

