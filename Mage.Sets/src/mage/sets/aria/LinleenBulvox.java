// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class LinleenBulvox extends CardImpl {

    public LinleenBulvox(UUID ownerId) {
        super(ownerId, 184, "Linleen Bulvox", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Beast");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/TAOMWSC.jpg
        When Linleen Bulvox attacks, target creature blocks it this turn if able.
        */
    }

    public LinleenBulvox(final LinleenBulvox card) {
        super(card);
    }

    @Override
    public LinleenBulvox copy() {
        return new LinleenBulvox(this);
    }

}

