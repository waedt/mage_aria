// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ImpressiveGlamer extends CardImpl {

    public ImpressiveGlamer(UUID ownerId) {
        super(ownerId, 55, "Impressive Glamer", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/ZoVbFj9.jpg
        Enchant creature
        Whenever a player casts an instant or sorcery, that player may gain control of enchanted creature.
        */
    }

    public ImpressiveGlamer(final ImpressiveGlamer card) {
        super(card);
    }

    @Override
    public ImpressiveGlamer copy() {
        return new ImpressiveGlamer(this);
    }

}

