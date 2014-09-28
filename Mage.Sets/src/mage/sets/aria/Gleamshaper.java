// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Gleamshaper extends CardImpl {

    public Gleamshaper(UUID ownerId) {
        super(ownerId, 15, "Gleamshaper", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/0743LlY.jpg
        T: Tap target creature.
        Whenever the second spell of a turn is cast, untap Gleamshaper.
        */
    }

    public Gleamshaper(final Gleamshaper card) {
        super(card);
    }

    @Override
    public Gleamshaper copy() {
        return new Gleamshaper(this);
    }

}

