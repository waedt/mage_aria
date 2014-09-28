// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class RitualofRising extends CardImpl {

    public RitualofRising(UUID ownerId) {
        super(ownerId, 110, "Ritual of Rising", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/ChwHmfx.jpg
        This turn, you may pay 1B rather than pay the mana costs of Skeleton or Zombie spells you control.
        You may cast creature cards from your graveyard this turn.
        If a creature would die this turn, exile it instead.
        */
    }

    public RitualofRising(final RitualofRising card) {
        super(card);
    }

    @Override
    public RitualofRising copy() {
        return new RitualofRising(this);
    }

}

