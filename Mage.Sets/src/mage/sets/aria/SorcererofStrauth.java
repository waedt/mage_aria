// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SorcererofStrauth extends CardImpl {

    public SorcererofStrauth(UUID ownerId) {
        super(ownerId, 152, "Sorcerer of Strauth", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/LwAvqqg.jpg
        T: Sorcerer of Strauth deals 1 damage to target creature or player.
        Whenever you cast a spell, untap Sorcerer of Strauth.
        */
    }

    public SorcererofStrauth(final SorcererofStrauth card) {
        super(card);
    }

    @Override
    public SorcererofStrauth copy() {
        return new SorcererofStrauth(this);
    }

}

