// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SavvyBaloth extends CardImpl {

    public SavvyBaloth(UUID ownerId) {
        super(ownerId, 191, "Savvy Baloth", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Beast");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/cWwBjHU.jpg
        Protection from Traps
        */
    }

    public SavvyBaloth(final SavvyBaloth card) {
        super(card);
    }

    @Override
    public SavvyBaloth copy() {
        return new SavvyBaloth(this);
    }

}

