package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

public class LinleenGyrist extends CardImpl {

    public LinleenGyrist(UUID ownerId) {
        super(ownerId, 185, "Linleen Gyrist", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setGreen(true);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // 0: Untap Linleen Gyrist. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new GenericManaCost(0)));

        // T: Target creature has haste until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/kU6oBcZ.jpg
        Haste
        0: Untap Linleen Gyrist. Activate this ability only once each turn.
        T: Target creature has haste until end of turn.
        */
    }

    public LinleenGyrist(final LinleenGyrist card) {
        super(card);
    }

    @Override
    public LinleenGyrist copy() {
        return new LinleenGyrist(this);
    }

}

