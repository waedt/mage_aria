// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class EmeraldMystic extends CardImpl {

    public EmeraldMystic(UUID ownerId) {
        super(ownerId, 170, "Emerald Mystic", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Kithkin");
        this.subtype.add("Druid");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/lAT7O2p.jpg
        T: The next time you activate an ability of a land you control this turn, copy it. You may choose new targets for the copy.
        */
    }

    public EmeraldMystic(final EmeraldMystic card) {
        super(card);
    }

    @Override
    public EmeraldMystic copy() {
        return new EmeraldMystic(this);
    }

}

