// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PontiffoftheVoid extends CardImpl {

    public PontiffoftheVoid(UUID ownerId) {
        super(ownerId, 107, "Pontiff of the Void", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/xmbvn19.jpg
        Whenever a player casts a spell, that player sacrifices a permanent.
        */
    }

    public PontiffoftheVoid(final PontiffoftheVoid card) {
        super(card);
    }

    @Override
    public PontiffoftheVoid copy() {
        return new PontiffoftheVoid(this);
    }

}

