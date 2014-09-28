// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ArtisinalFireball extends CardImpl {

    public ArtisinalFireball(UUID ownerId) {
        super(ownerId, 124, "Artisinal Fireball", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/SgNG0Uk.jpg
        Artisinal Fireball deals X damage to target creature or player.
        Add X red mana to your mana pool.
        */
    }

    public ArtisinalFireball(final ArtisinalFireball card) {
        super(card);
    }

    @Override
    public ArtisinalFireball copy() {
        return new ArtisinalFireball(this);
    }

}

