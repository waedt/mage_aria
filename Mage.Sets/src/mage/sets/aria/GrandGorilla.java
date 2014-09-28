// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GrandGorilla extends CardImpl {

    public GrandGorilla(UUID ownerId) {
        super(ownerId, 180, "Grand Gorilla", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Ape");
        this.subtype.add("Giant");
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/2i5H1bD.jpg
        Trample
        Whenever another green creature enters the battlefield under your control, it fights target creature an opponent controls.
        Whenever Grand Gorilla deals combat damage to a player, you may return target green creature card from your graveyard to the battlefield.
        */
    }

    public GrandGorilla(final GrandGorilla card) {
        super(card);
    }

    @Override
    public GrandGorilla copy() {
        return new GrandGorilla(this);
    }

}

