// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class OwlChronarch extends CardImpl {

    public OwlChronarch(UUID ownerId) {
        super(ownerId, 63, "Owl Chronarch", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/ICDMnq3.jpg
        Flying
        When Owl Chronarch enters the battlefield, you may return target instant or sorcery card from your graveyard to your hand.
        */
    }

    public OwlChronarch(final OwlChronarch card) {
        super(card);
    }

    @Override
    public OwlChronarch copy() {
        return new OwlChronarch(this);
    }

}

