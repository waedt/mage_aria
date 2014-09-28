// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ParanoiatheMirrorDevil extends CardImpl {

    public ParanoiatheMirrorDevil(UUID ownerId) {
        super(ownerId, 64, "Paranoia, the Mirror Devil", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.expansionSetCode = "ARI";

        this.supertype.add("Lengendary");
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/fZzERR1.jpg
        When Paranoia, the Mirror Devil enters the battlefield and at the beginning of your upkeep, you may put a token onto the battlefield that's a copy of target creature, except its name is Paranoia's Image, it's legendary in addition to its other types and it's a Devil Illusion in addition to its other types.
        */
    }

    public ParanoiatheMirrorDevil(final ParanoiatheMirrorDevil card) {
        super(card);
    }

    @Override
    public ParanoiatheMirrorDevil copy() {
        return new ParanoiatheMirrorDevil(this);
    }

}

