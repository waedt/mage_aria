// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SportMuskox extends CardImpl {

    public SportMuskox(UUID ownerId) {
        super(ownerId, 199, "Sport Muskox", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Ox");
        this.subtype.add("Beast");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/KJxhp1A.jpg
        
        */
    }

    public SportMuskox(final SportMuskox card) {
        super(card);
    }

    @Override
    public SportMuskox copy() {
        return new SportMuskox(this);
    }

}

