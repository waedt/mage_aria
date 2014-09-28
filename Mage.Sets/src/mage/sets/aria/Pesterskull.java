// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Pesterskull extends CardImpl {

    public Pesterskull(UUID ownerId) {
        super(ownerId, 105, "Pesterskull", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Skeleton");
        this.subtype.add("Imp");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/bnk6S06.jpg
        Flying
        Whenever Pesterskull attacks, target creature blocks this turn if able.
        3B: Return Pesterskull from your graveyard to the battlefield tapped.
        */
    }

    public Pesterskull(final Pesterskull card) {
        super(card);
    }

    @Override
    public Pesterskull copy() {
        return new Pesterskull(this);
    }

}

