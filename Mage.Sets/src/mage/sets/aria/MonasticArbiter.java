// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MonasticArbiter extends CardImpl {

    public MonasticArbiter(UUID ownerId) {
        super(ownerId, 26, "Monastic Arbiter", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/RFgPPA3.jpg
        Each opponent can't cast spells that share a card type with a spell that player cast this turn.
        */
    }

    public MonasticArbiter(final MonasticArbiter card) {
        super(card);
    }

    @Override
    public MonasticArbiter copy() {
        return new MonasticArbiter(this);
    }

}

