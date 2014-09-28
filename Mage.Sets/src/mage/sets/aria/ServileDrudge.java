// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ServileDrudge extends CardImpl {

    public ServileDrudge(UUID ownerId) {
        super(ownerId, 112, "Servile Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Zombie");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/h6itDIT.jpg
        Deathtouch
        Black spells you cast cost B less to cast. This effect reduces only the amount of colored mana you pay.
        */
    }

    public ServileDrudge(final ServileDrudge card) {
        super(card);
    }

    @Override
    public ServileDrudge copy() {
        return new ServileDrudge(this);
    }

}

