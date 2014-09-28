// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AerolinianGlider extends CardImpl {

    public AerolinianGlider(UUID ownerId) {
        super(ownerId, 43, "Aerolinian Glider", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Homunculus");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/jyKOLik.jpg
        Whenever Aerolinian Glider attacks, it gains flying until end of turn.
        */
    }

    public AerolinianGlider(final AerolinianGlider card) {
        super(card);
    }

    @Override
    public AerolinianGlider copy() {
        return new AerolinianGlider(this);
    }

}

