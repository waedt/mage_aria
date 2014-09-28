// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class StuntTroll extends CardImpl {

    public StuntTroll(UUID ownerId) {
        super(ownerId, 200, "Stunt Troll", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Troll");
        this.subtype.add("Shaman");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/JjqZpL6.jpg
        When Stunt Troll dies, put it on top of its owner's library.
        When Stunt Troll is put into your library from a graveyard, target player chooses a card from his or her hand and puts it on top of his or her library.
        */
    }

    public StuntTroll(final StuntTroll card) {
        super(card);
    }

    @Override
    public StuntTroll copy() {
        return new StuntTroll(this);
    }

}

