package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedByOneAllEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

public class MuseumCenterpiece extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MuseumCenterpiece(UUID ownerId) {
        super(ownerId, 213, "Museum Centerpiece", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "ARI";

        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

        // 2, Sacrifice Museum Centerpiece: Creatures you control gain haste until end of turn and can't be blocked this turn except by two or more creatures.
        Effect effect = new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter);
        effect.setText("Creatures you control gain haste until end of turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));;
        ability.addCost(new SacrificeSourceCost());

        effect = new CantBeBlockedByOneAllEffect(1, filter, Duration.EndOfTurn);
        effect.setText("and can't be blocked this turn except by two or more creatures.");
        ability.addEffect(effect);

        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/urR9Nfp.jpg
        Creatures you control have haste.
        2, Sacrifice Museum Centerpiece: Creatures you control gain haste until end of turn and can't be blocked this turn except by two or more creatures.
        */
    }

    public MuseumCenterpiece(final MuseumCenterpiece card) {
        super(card);
    }

    @Override
    public MuseumCenterpiece copy() {
        return new MuseumCenterpiece(this);
    }

}

