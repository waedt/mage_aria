// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BereaveSpellcraft extends CardImpl {

    public BereaveSpellcraft(UUID ownerId) {
        super(ownerId, 85, "Bereave Spellcraft", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/LbNgQOe.jpg
        If an opponent cast an instant spell this turn, you may pay B rather than pay Bereave Spellcraft's mana cost.
        Target player reveals his or her hand and discards all instant and sorcery cards.
        */
    }

    public BereaveSpellcraft(final BereaveSpellcraft card) {
        super(card);
    }

    @Override
    public BereaveSpellcraft copy() {
        return new BereaveSpellcraft(this);
    }

}

