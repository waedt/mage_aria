package mage.sets.aria;

import java.util.UUID;

import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

public class BlindingJustice extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public BlindingJustice(UUID ownerId) {
        super(ownerId, 6, "Blinding Justice", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        // Buyback-tap three untapped white creatures you control
        this.addAbility(new BuybackAbility(new TapTargetCost(new TargetControlledCreaturePermanent(3, 3, filter, true))));

        // Target player gains shroud until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ShroudAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetPlayer());

        /*
        Card Text:
        ----------
        http://i.imgur.com/eEnzfyi.jpg
        Buyback-tap three untapped white creatures you control
        Target player gains shroud until end of turn.
        */
    }

    public BlindingJustice(final BlindingJustice card) {
        super(card);
    }

    @Override
    public BlindingJustice copy() {
        return new BlindingJustice(this);
    }

}

