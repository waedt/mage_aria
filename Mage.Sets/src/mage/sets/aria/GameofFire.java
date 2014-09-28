// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GameofFire extends CardImpl {

    public GameofFire(UUID ownerId) {
        super(ownerId, 135, "Game of Fire", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/GtgKsYv.jpg
        Reveal cards from the top of your library until you reveal three instants and/or sorceries. Exile them. Target opponent chooses one. That player may cast that card without paying its mana cost. You may cast the other exiled cards without paying their mana costs. Put the revealed cards on the bottom of your library in a random order.
        */
    }

    public GameofFire(final GameofFire card) {
        super(card);
    }

    @Override
    public GameofFire copy() {
        return new GameofFire(this);
    }

}

