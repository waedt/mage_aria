package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

public class AutarchofSol extends CardImpl {

    private final static FilterControlledCreaturePermanent wizardFilter = new FilterControlledCreaturePermanent("Wizards you control");
    private final static FilterControlledCreaturePermanent untappedWizardFilter = new FilterControlledCreaturePermanent("untapped Wizard");
    private final static FilterCreaturePermanent nonWizardFilter = new FilterCreaturePermanent("non-Wizard creature");

    static {
        wizardFilter.add(new SubtypePredicate("Wizard"));
        untappedWizardFilter.add(new SubtypePredicate("Wizard"));
        untappedWizardFilter.add(Predicates.not(new TappedPredicate()));
        nonWizardFilter.add(Predicates.not(new SubtypePredicate("Wizard")));
    }

    public AutarchofSol(UUID ownerId) {
        super(ownerId, 4, "Autarch of Sol", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setWhite(true);

        this.addAbility(VigilanceAbility.getInstance());

        // Autarch of Sol has power and toughness equal to the number of Wizards you control.
        Effect effect = new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(wizardFilter), Duration.EndOfGame);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));

        // Tap an untapped Wizard you control: Tap target non-Wizard creature.
        Effect effect2 = new TapTargetEffect();
        Cost cost = new TapTargetCost(new TargetControlledCreaturePermanent(untappedWizardFilter));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, cost);
        ability.addTarget(new TargetCreaturePermanent(nonWizardFilter));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/VwxPPFz.jpg
        Vigilance
        Autarch of Sol has power and toughness equal to the number of Wizards you control.
        Tap an untapped Wizard you control: Tap target non-Wizard creature.
        */
    }

    public AutarchofSol(final AutarchofSol card) {
        super(card);
    }

    @Override
    public AutarchofSol copy() {
        return new AutarchofSol(this);
    }

}

