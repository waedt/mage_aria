// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class FeintofHeart extends CardImpl {

    public FeintofHeart(UUID ownerId) {
        super(ownerId, 132, "Feint of Heart", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/233CUsu.jpg
        If an opponent controls a creature with vigilance, you may pay R rather than pay Feint of Heart's mana cost.
        Tap up to three target creatures.
        */
    }

    public FeintofHeart(final FeintofHeart card) {
        super(card);
    }

    @Override
    public FeintofHeart copy() {
        return new FeintofHeart(this);
    }

}

