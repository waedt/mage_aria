// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SlogArc extends CardImpl {

    public SlogArc(UUID ownerId) {
        super(ownerId, 151, "Slog Arc", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/srYczHI.jpg
        Buyback-Exile the top fifteen cards of your library
        Slog Arc deals 2 damage to target creature or player.
        */
    }

    public SlogArc(final SlogArc card) {
        super(card);
    }

    @Override
    public SlogArc copy() {
        return new SlogArc(this);
    }

}

