// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AkhsEssentialReader extends CardImpl {

    public AkhsEssentialReader(UUID ownerId) {
        super(ownerId, 123, "Akh's Essential Reader", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/GmUbjTr.jpg
        Reveal the top three cards of your library. Put all instant and sorcery cards from among them into your hand. Put the rest of the revealed cards into your graveyard.
        */
    }

    public AkhsEssentialReader(final AkhsEssentialReader card) {
        super(card);
    }

    @Override
    public AkhsEssentialReader copy() {
        return new AkhsEssentialReader(this);
    }

}

