// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DraconianDreamer extends CardImpl {

    public DraconianDreamer(UUID ownerId) {
        super(ownerId, 51, "Draconian Dreamer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/57vZ7BI.jpg
        Flying
        Whenever you cast an instant or sorcery, Draconian Dreamer gets +2/+2 until end of turn.
        */
    }

    public DraconianDreamer(final DraconianDreamer card) {
        super(card);
    }

    @Override
    public DraconianDreamer copy() {
        return new DraconianDreamer(this);
    }

}

