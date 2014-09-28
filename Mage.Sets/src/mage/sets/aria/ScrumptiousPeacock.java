// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ScrumptiousPeacock extends CardImpl {

    public ScrumptiousPeacock(UUID ownerId) {
        super(ownerId, 194, "Scrumptious Peacock", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/z7wjyV5.jpg
        Flying
        Creatures must block Scrumptious Peacock if able.
        G: Scrumptious Peacock loses flying until end of turn.
        */
    }

    public ScrumptiousPeacock(final ScrumptiousPeacock card) {
        super(card);
    }

    @Override
    public ScrumptiousPeacock copy() {
        return new ScrumptiousPeacock(this);
    }

}

