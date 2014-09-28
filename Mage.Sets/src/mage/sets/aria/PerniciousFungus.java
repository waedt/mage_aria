// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PerniciousFungus extends CardImpl {

    public PerniciousFungus(UUID ownerId) {
        super(ownerId, 188, "Pernicious Fungus", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Fungus");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/MLT480X.jpg
        When Pernicious Fungus enters the battlefield, you may search your graveyard and library for any number of cards named Pernicious Fungus, reveal them, and put them into your hand. If you search your library this way, shuffle it.
        */
    }

    public PerniciousFungus(final PerniciousFungus card) {
        super(card);
    }

    @Override
    public PerniciousFungus copy() {
        return new PerniciousFungus(this);
    }

}

