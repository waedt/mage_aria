// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class OathExtractor extends CardImpl {

    public OathExtractor(UUID ownerId) {
        super(ownerId, 103, "Oath Extractor", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Demon");
        this.subtype.add("Wizard");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/REeqKpT.jpg
        Flying
        Noncreature spells cost an additional "sacrifice a creature" to cast.
        At the beginning of your upkeep, each player sacrifices a creature.
        */
    }

    public OathExtractor(final OathExtractor card) {
        super(card);
    }

    @Override
    public OathExtractor copy() {
        return new OathExtractor(this);
    }

}

