// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class KrewthiAerialist extends CardImpl {

    public KrewthiAerialist(UUID ownerId) {
        super(ownerId, 19, "Krewthi Aerialist", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/SJDsohf.jpg
        Flying
        When Krewthi Aerialist dies, you may tap an untapped creature you control. If you do, return Krewthi Aerialist from your graveyard to your hand.
        */
    }

    public KrewthiAerialist(final KrewthiAerialist card) {
        super(card);
    }

    @Override
    public KrewthiAerialist copy() {
        return new KrewthiAerialist(this);
    }

}

