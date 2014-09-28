// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BenedictionSerf extends CardImpl {

    public BenedictionSerf(UUID ownerId) {
        super(ownerId, 5, "Benediction Serf", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Cleric");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/MdAYFB7.jpg
        Whenever you cast an instant or sorcery, gain 1 life.
        */
    }

    public BenedictionSerf(final BenedictionSerf card) {
        super(card);
    }

    @Override
    public BenedictionSerf copy() {
        return new BenedictionSerf(this);
    }

}

