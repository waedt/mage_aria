// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class GiantFleaBeetle extends CardImpl {

    public GiantFleaBeetle(UUID ownerId) {
        super(ownerId, 177, "Giant Flea Beetle", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Insect");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/piwtSD9.jpg
        Flying
        Whenever Giant Flea Beetle attacks, choose one or both; Giant Flea Beetle deals 2 damage to target creature with flying; or Giant Flea Beetle deals 2 damage to target creature without flying.
        */
    }

    public GiantFleaBeetle(final GiantFleaBeetle card) {
        super(card);
    }

    @Override
    public GiantFleaBeetle copy() {
        return new GiantFleaBeetle(this);
    }

}

