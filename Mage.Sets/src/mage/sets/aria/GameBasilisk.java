// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GameBasilisk extends CardImpl {

    public GameBasilisk(UUID ownerId) {
        super(ownerId, 176, "Game Basilisk", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Basilisk");
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/WhwUaE6.jpg
        Deathtouch
        Whenever Game Basilisk attacks, target creature must block it this turn if able.
        */
    }

    public GameBasilisk(final GameBasilisk card) {
        super(card);
    }

    @Override
    public GameBasilisk copy() {
        return new GameBasilisk(this);
    }

}

