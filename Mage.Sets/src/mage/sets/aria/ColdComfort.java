// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ColdComfort extends CardImpl {

    public ColdComfort(UUID ownerId) {
        super(ownerId, 49, "Cold Comfort", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Nnk0jZ1.jpg
        Enchant creature
        When Cold Comfort enters the battlefield, tap all creatures enchanted creature's controller controls.
        Enchanted creature doesn't untap during its controller's untap step.
        */
    }

    public ColdComfort(final ColdComfort card) {
        super(card);
    }

    @Override
    public ColdComfort copy() {
        return new ColdComfort(this);
    }

}

