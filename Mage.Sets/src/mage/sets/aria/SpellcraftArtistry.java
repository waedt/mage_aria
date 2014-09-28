// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SpellcraftArtistry extends CardImpl {

    public SpellcraftArtistry(UUID ownerId) {
        super(ownerId, 153, "Spellcraft Artistry", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/cQ8wS0G.jpg
        You may spend red mana this turn as if it were mana of any color.
        Draw a card.
        */
    }

    public SpellcraftArtistry(final SpellcraftArtistry card) {
        super(card);
    }

    @Override
    public SpellcraftArtistry copy() {
        return new SpellcraftArtistry(this);
    }

}

