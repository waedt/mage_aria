// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AloofNoble extends CardImpl {

    public AloofNoble(UUID ownerId) {
        super(ownerId, 45, "Aloof Noble", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/GmNvJhz.jpg
        Hexproof (This creature can't be the target of spells or abilities your opponents control.)
        */
    }

    public AloofNoble(final AloofNoble card) {
        super(card);
    }

    @Override
    public AloofNoble copy() {
        return new AloofNoble(this);
    }

}

