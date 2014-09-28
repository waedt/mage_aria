// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class HaplessResearcher extends CardImpl {

    public HaplessResearcher(UUID ownerId) {
        super(ownerId, 54, "Hapless Researcher", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/eYymiEj.jpg
        Sacrifice Hapless Researcher: Draw a card, then discard a card.
        */
    }

    public HaplessResearcher(final HaplessResearcher card) {
        super(card);
    }

    @Override
    public HaplessResearcher copy() {
        return new HaplessResearcher(this);
    }

}

