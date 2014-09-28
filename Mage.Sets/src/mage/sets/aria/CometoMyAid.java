// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CometoMyAid extends CardImpl {

    public CometoMyAid(UUID ownerId) {
        super(ownerId, 10, "Come to My Aid!", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/zVpR05n.jpg
        If an opponent controls more creatures than you, you may pay W rather than pay Come to My Aid!'s mana cost.
        Put three 1/1 white Soldier creature tokens onto the battlefield.
        */
    }

    public CometoMyAid(final CometoMyAid card) {
        super(card);
    }

    @Override
    public CometoMyAid copy() {
        return new CometoMyAid(this);
    }

}

