// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class EstateDefenses extends CardImpl {

    public EstateDefenses(UUID ownerId) {
        super(ownerId, 131, "Estate Defenses", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{5}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/TtWauDG.jpg
        Estate Defenses costs 1 less to cast for each attacking creature.
        Estate Defenses deals 1 damage to each attacking creature.
        Storm (When you cast this spell, copy it for each spell cast before it this turn.)
        */
    }

    public EstateDefenses(final EstateDefenses card) {
        super(card);
    }

    @Override
    public EstateDefenses copy() {
        return new EstateDefenses(this);
    }

}

