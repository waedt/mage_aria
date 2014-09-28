// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DruidicRiposte extends CardImpl {

    public DruidicRiposte(UUID ownerId) {
        super(ownerId, 169, "Druidic Riposte", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/fp2pcCA.jpg
        Choose one or both - Destroy target noncreature, nonland permanent; or return target creature card from your graveyard to your hand.
        */
    }

    public DruidicRiposte(final DruidicRiposte card) {
        super(card);
    }

    @Override
    public DruidicRiposte copy() {
        return new DruidicRiposte(this);
    }

}

