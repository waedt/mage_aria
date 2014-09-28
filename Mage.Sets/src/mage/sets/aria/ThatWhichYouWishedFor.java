// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ThatWhichYouWishedFor extends CardImpl {

    public ThatWhichYouWishedFor(UUID ownerId) {
        super(ownerId, 78, "That Which You Wished For", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Djinn");
        this.subtype.add("Horror");
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/cLeKtFE.jpg
        You may cast That Which You Wished For from your sideboard.
        That Which You Wished For costs 1 less to cast for each other spell cast this turn.
        Flying, hexproof
        */
    }

    public ThatWhichYouWishedFor(final ThatWhichYouWishedFor card) {
        super(card);
    }

    @Override
    public ThatWhichYouWishedFor copy() {
        return new ThatWhichYouWishedFor(this);
    }

}

