// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LinleenGyrist extends CardImpl {

    public LinleenGyrist(UUID ownerId) {
        super(ownerId, 185, "Linleen Gyrist", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/kU6oBcZ.jpg
        Haste
        0: Untap Linleen Gyrist. Activate this ability only once each turn.
        T: Target creature has haste until end of turn.
        */
    }

    public LinleenGyrist(final LinleenGyrist card) {
        super(card);
    }

    @Override
    public LinleenGyrist copy() {
        return new LinleenGyrist(this);
    }

}

