package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class PrizeHound extends CardImpl {

    public PrizeHound(UUID ownerId) {
        super(ownerId, 29, "Prize Hound", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Hound");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        // When Prize Hound dies, exile it.
        this.addAbility(new DiesTriggeredAbility(new ExileSourceEffect()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/qSmMFdD.jpg
        When Prize Hound dies, exile it.
        */
    }

    public PrizeHound(final PrizeHound card) {
        super(card);
    }

    @Override
    public PrizeHound copy() {
        return new PrizeHound(this);
    }

}

