// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WorryStone extends CardImpl {

    public WorryStone(UUID ownerId) {
        super(ownerId, 219, "Worry Stone", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        

        /*
        Card Text:
        ----------
        http://i.imgur.com/eKUnKb0.jpg
        B, Sacrifice Worry Stone: Target player reveals a card from his or her hand at random. If it is an enchantment, instant or sorcery card, that player discards that card.
        */
    }

    public WorryStone(final WorryStone card) {
        super(card);
    }

    @Override
    public WorryStone copy() {
        return new WorryStone(this);
    }

}

