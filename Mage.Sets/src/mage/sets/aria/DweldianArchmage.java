package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.BoostCounter;
import mage.target.common.TargetCreatureOrPlayer;

public class DweldianArchmage extends CardImpl {

    public DweldianArchmage(UUID ownerId) {
        super(ownerId, 129, "Dweldian Archmage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Elemental");
        this.subtype.add("Wizard");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setRed(true);

        this.addAbility(HasteAbility.getInstance());

        // Dweldian Archmage enters the battlefield with two +1/+1 counters on it for each other spell cast this turn.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(new BoostCounter(1, 1, 2))));

        // 1, Remove a +1/+1 counter from Dweldian Archmage: Dweldian Archmage deals 1 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(new BoostCounter(1, 1, 1)));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/uIBNh5P.jpg
        Haste
        Dweldian Archmage enters the battlefield with two +1/+1 counters on it for each other spell cast this turn.
        1, Remove a +1/+1 counter from Dweldian Archmage: Dweldian Archmage deals 1 damage to target creature or player.
        */
    }

    public DweldianArchmage(final DweldianArchmage card) {
        super(card);
    }

    @Override
    public DweldianArchmage copy() {
        return new DweldianArchmage(this);
    }

}

