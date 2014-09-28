// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SpellwindingGnome extends CardImpl {

    public SpellwindingGnome(UUID ownerId) {
        super(ownerId, 216, "Spellwinding Gnome", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Gnome");
        this.subtype.add("Construct");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        /*
        Card Text:
        ----------
        http://i.imgur.com/BYgO4DV.jpg
        Whenever you cast an instant or sorcery, put a +1/+1 counter on Spellwinding Gnome.
        */
    }

    public SpellwindingGnome(final SpellwindingGnome card) {
        super(card);
    }

    @Override
    public SpellwindingGnome copy() {
        return new SpellwindingGnome(this);
    }

}

