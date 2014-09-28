// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SirLionelWraithwaite extends CardImpl {

    public SirLionelWraithwaite(UUID ownerId) {
        super(ownerId, 113, "Sir Lionel Wraithwaite", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "ARI";

        this.supertype.add("Lengendary");
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/ef0xiNN.jpg
        Whenever Sir Lionel Wraithwaite enters the battlefield or attacks, you may search your graveyard and library for a Trap card, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        Protection from creatures.
        */
    }

    public SirLionelWraithwaite(final SirLionelWraithwaite card) {
        super(card);
    }

    @Override
    public SirLionelWraithwaite copy() {
        return new SirLionelWraithwaite(this);
    }

}

