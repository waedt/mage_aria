// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SkittishSkink extends CardImpl {

    public SkittishSkink(UUID ownerId) {
        super(ownerId, 197, "Skittish Skink", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Lizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/1fzf7m2.jpg
        Hexproof
        */
    }

    public SkittishSkink(final SkittishSkink card) {
        super(card);
    }

    @Override
    public SkittishSkink copy() {
        return new SkittishSkink(this);
    }

}

