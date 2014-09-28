// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class FierceDrudge extends CardImpl {

    public FierceDrudge(UUID ownerId) {
        super(ownerId, 133, "Fierce Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/GwkSg0q.jpg
        Haste
        Red spells you cast cost R less to cast. This effect reduces only the amount of colored mana you pay.
        */
    }

    public FierceDrudge(final FierceDrudge card) {
        super(card);
    }

    @Override
    public FierceDrudge copy() {
        return new FierceDrudge(this);
    }

}

