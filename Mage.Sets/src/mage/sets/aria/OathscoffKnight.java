// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class OathscoffKnight extends CardImpl {

    public OathscoffKnight(UUID ownerId) {
        super(ownerId, 104, "Oathscoff Knight", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Vampire");
        this.subtype.add("Knight");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/NpGuA8o.jpg
        Whenever you cast an instant or sorcery, Oathscoff Knight gains first strike until end of turn.
        */
    }

    public OathscoffKnight(final OathscoffKnight card) {
        super(card);
    }

    @Override
    public OathscoffKnight copy() {
        return new OathscoffKnight(this);
    }

}

