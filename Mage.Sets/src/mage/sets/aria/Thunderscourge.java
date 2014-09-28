// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Thunderscourge extends CardImpl {

    public Thunderscourge(UUID ownerId) {
        super(ownerId, 158, "Thunderscourge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dragon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/v2T4fJz.jpg
        Flying
        Whenever Thunderscourge attacks, you may reveal a card with X in its mana cost from your hand. If you do, you may cast it with X as 5 without paying its mana cost.
        */
    }

    public Thunderscourge(final Thunderscourge card) {
        super(card);
    }

    @Override
    public Thunderscourge copy() {
        return new Thunderscourge(this);
    }

}

