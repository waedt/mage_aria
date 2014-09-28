// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BasilisksBreath extends CardImpl {

    public BasilisksBreath(UUID ownerId) {
        super(ownerId, 84, "Basilisk's Breath", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/dIJ0Lbg.jpg
        Buyback 2 (You may pay an additional 2 as you cast this spell. If you do, put this card into your hand as it resolves.)
        Target creature gains deathtouch and lifelink until end of turn.
        */
    }

    public BasilisksBreath(final BasilisksBreath card) {
        super(card);
    }

    @Override
    public BasilisksBreath copy() {
        return new BasilisksBreath(this);
    }

}

