// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class QueeningNutkin extends CardImpl {

    public QueeningNutkin(UUID ownerId) {
        super(ownerId, 190, "Queening Nutkin", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Squirrel");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/RA59VMj.jpg
        When Queening Nutkin enters the battlefield, put a 1/1 green Squirrel creature token onto the battlefield.
        */
    }

    public QueeningNutkin(final QueeningNutkin card) {
        super(card);
    }

    @Override
    public QueeningNutkin copy() {
        return new QueeningNutkin(this);
    }

}

