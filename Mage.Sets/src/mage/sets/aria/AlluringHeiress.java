// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AlluringHeiress extends CardImpl {

    public AlluringHeiress(UUID ownerId) {
        super(ownerId, 82, "Alluring Heiress", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/8HCmxKI.jpg
        When Alluring Heiress enters the battlefield, each opponent puts a card from his or her hand into his or her library third from the top.
        */
    }

    public AlluringHeiress(final AlluringHeiress card) {
        super(card);
    }

    @Override
    public AlluringHeiress copy() {
        return new AlluringHeiress(this);
    }

}

