// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class EmbodimentofHeaven extends CardImpl {

    public EmbodimentofHeaven(UUID ownerId) {
        super(ownerId, 13, "Embodiment of Heaven", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/HUpAAZm.jpg
        Enchant creature
        Aurafaction
        Enchanted creature gets +4/+4 and has flying, lifelink and vigilance.
        */
    }

    public EmbodimentofHeaven(final EmbodimentofHeaven card) {
        super(card);
    }

    @Override
    public EmbodimentofHeaven copy() {
        return new EmbodimentofHeaven(this);
    }

}

