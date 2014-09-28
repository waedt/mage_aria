// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SneakyDrudge extends CardImpl {

    public SneakyDrudge(UUID ownerId) {
        super(ownerId, 73, "Sneaky Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Homunculus");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/0m2EAU2.jpg
        Whenever Sneaky Drudge deals combat damage to a player, you may draw a card.
        Blue spells you cast cost U less to cast. This effect reduces only the amount of colored mana you pay.
        */
    }

    public SneakyDrudge(final SneakyDrudge card) {
        super(card);
    }

    @Override
    public SneakyDrudge copy() {
        return new SneakyDrudge(this);
    }

}

