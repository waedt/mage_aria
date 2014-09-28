// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class MasterReanimator extends CardImpl {

    public MasterReanimator(UUID ownerId) {
        super(ownerId, 99, "Master Reanimator", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/0rf42c1.jpg
        When Master Reanimator enters the battlefield, search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        1B, T: Return target creature card from your graveyard to your hand.
        */
    }

    public MasterReanimator(final MasterReanimator card) {
        super(card);
    }

    @Override
    public MasterReanimator copy() {
        return new MasterReanimator(this);
    }

}

