// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AerolinianSentry extends CardImpl {

    public AerolinianSentry(UUID ownerId) {
        super(ownerId, 44, "Aerolinian Sentry", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Homunculus");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/XOnJ4FB.jpg
        Whenever you cast an instant or sorcery, Aerolinian Sentry gains vigilance until end of turn.
        */
    }

    public AerolinianSentry(final AerolinianSentry card) {
        super(card);
    }

    @Override
    public AerolinianSentry copy() {
        return new AerolinianSentry(this);
    }

}

