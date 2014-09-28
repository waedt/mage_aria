// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ChosenbyNature extends CardImpl {

    public ChosenbyNature(UUID ownerId) {
        super(ownerId, 166, "Chosen by Nature", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/78RAoKg.jpg
        Enchant permanent
        Aurafaction
        Enchanted permanent has indestructible.
        As long as enchanted permanent is a creature, it gets +2/+2.
        As long as enchanted permanent is a land, it has "at the beginning of your upkeep, you gain 2 life"
        */
    }

    public ChosenbyNature(final ChosenbyNature card) {
        super(card);
    }

    @Override
    public ChosenbyNature copy() {
        return new ChosenbyNature(this);
    }

}

