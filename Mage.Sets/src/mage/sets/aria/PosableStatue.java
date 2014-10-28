package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;

public class PosableStatue extends CardImpl {

    public PosableStatue(UUID ownerId) {
        super(ownerId, 66, "Posable Statue", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);
        this.color.setBlue(true);

        // 1U: Posable Statue gets +2/-2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl("{1}{U}")));

        /*
        Card Text:
        ----------
        http://i.imgur.com/B8FdT9j.jpg
        1U: Posable Statue gets +2/-2 until end of turn.
        */
    }

    public PosableStatue(final PosableStatue card) {
        super(card);
    }

    @Override
    public PosableStatue copy() {
        return new PosableStatue(this);
    }

}

