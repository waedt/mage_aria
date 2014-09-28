// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WebsackSummons extends CardImpl {

    public WebsackSummons(UUID ownerId) {
        super(ownerId, 205, "Websack Summons", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/l1UPaa7.jpg
        If an opponent cast a spell this turn, you may pay G rather than pay Websack Summons's mana cost.
        Put three 1/2 green Spider creature tokens with reach onto the battlefield.
        */
    }

    public WebsackSummons(final WebsackSummons card) {
        super(card);
    }

    @Override
    public WebsackSummons copy() {
        return new WebsackSummons(this);
    }

}

