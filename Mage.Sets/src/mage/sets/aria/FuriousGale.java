// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class FuriousGale extends CardImpl {

    public FuriousGale(UUID ownerId) {
        super(ownerId, 174, "Furious Gale", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/NgQ3cIE.jpg
        Furious Gale deals 1 damage to each creature with flying, each planeswalker and each player.
        Storm (When you cast this spell, copy it for each spell cast before it this turn.)
        */
    }

    public FuriousGale(final FuriousGale card) {
        super(card);
    }

    @Override
    public FuriousGale copy() {
        return new FuriousGale(this);
    }

}

