// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class VauthianAcrobat extends CardImpl {

    public VauthianAcrobat(UUID ownerId) {
        super(ownerId, 160, "Vauthian Acrobat", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/ncyFiqu.jpg
        First strike
        If Vauthian Acrobat is countered, put it to your hand instead of putting it into your graveyard.
        */
    }

    public VauthianAcrobat(final VauthianAcrobat card) {
        super(card);
    }

    @Override
    public VauthianAcrobat copy() {
        return new VauthianAcrobat(this);
    }

}

