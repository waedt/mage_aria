// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GravePile extends CardImpl {

    public GravePile(UUID ownerId) {
        super(ownerId, 96, "Grave Pile", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/TjVJT0D.jpg
        Destroy target creature. Exile all noncreature cards from that creature's controller's graveyard.
        */
    }

    public GravePile(final GravePile card) {
        super(card);
    }

    @Override
    public GravePile copy() {
        return new GravePile(this);
    }

}

