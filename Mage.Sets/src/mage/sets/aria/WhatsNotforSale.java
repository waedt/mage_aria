// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class WhatsNotforSale extends CardImpl {

    public WhatsNotforSale(UUID ownerId) {
        super(ownerId, 81, "What's Not for Sale?", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/McnXA19.jpg
        Enchant permanent
        Aurafaction
        You control enchanted permanent.
        */
    }

    public WhatsNotforSale(final WhatsNotforSale card) {
        super(card);
    }

    @Override
    public WhatsNotforSale copy() {
        return new WhatsNotforSale(this);
    }

}

