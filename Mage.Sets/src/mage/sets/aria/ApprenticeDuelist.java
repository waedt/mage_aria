package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.constants.Duration;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.costs.mana.ManaCostsImpl;

public class ApprenticeDuelist extends CardImpl {

    public ApprenticeDuelist(UUID ownerId) {
        super(ownerId, 2, "Apprentice Duelist", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        // 2: Apprentice Duelist gains double strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{2}")));

        /*
        Card Text:
        ----------
        http://i.imgur.com/m0Guulw.jpg
        2: Apprentice Duelist gains double strike until end of turn.
        */
    }

    public ApprenticeDuelist(final ApprenticeDuelist card) {
        super(card);
    }

    @Override
    public ApprenticeDuelist copy() {
        return new ApprenticeDuelist(this);
    }

}

