// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class StoneCollector extends CardImpl {

    public StoneCollector(UUID ownerId) {
        super(ownerId, 75, "Stone Collector", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/4t7UElJ.jpg
        When Stone Collector enters the battlefield, you may search your library for an artifact card with converted mana cost 0, reveal it, and put it into your hand. If you do, shuffle your library.
        */
    }

    public StoneCollector(final StoneCollector card) {
        super(card);
    }

    @Override
    public StoneCollector copy() {
        return new StoneCollector(this);
    }

}

