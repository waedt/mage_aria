// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MawMacabre extends CardImpl {

    public MawMacabre(UUID ownerId) {
        super(ownerId, 100, "Maw Macabre", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Zombie");
        this.subtype.add("Wurm");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/0KpN3gm.jpg
        Sacrifice a creature: Maw Macabre gets +1/+0 and gains intimidate until end of turn.
        */
    }

    public MawMacabre(final MawMacabre card) {
        super(card);
    }

    @Override
    public MawMacabre copy() {
        return new MawMacabre(this);
    }

}

