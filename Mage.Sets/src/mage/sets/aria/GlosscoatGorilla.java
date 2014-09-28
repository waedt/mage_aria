// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GlosscoatGorilla extends CardImpl {

    public GlosscoatGorilla(UUID ownerId) {
        super(ownerId, 179, "Glosscoat Gorilla", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Ape");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/S3ts3F7.jpg
        Shroud
        */
    }

    public GlosscoatGorilla(final GlosscoatGorilla card) {
        super(card);
    }

    @Override
    public GlosscoatGorilla copy() {
        return new GlosscoatGorilla(this);
    }

}

