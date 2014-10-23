package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

public class FeintofHeart extends CardImpl {

    public FeintofHeart(UUID ownerId) {
        super(ownerId, 132, "Feint of Heart", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setRed(true);

        this.getSpellAbility().addAlternativeCost(new FeintOfHeartAlternativeCost());

        // Tap up to three target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));

        /*
        Card Text:
        ----------
        http://i.imgur.com/233CUsu.jpg
        If an opponent controls a creature with vigilance, you may pay R rather than pay Feint of Heart's mana cost.
        Tap up to three target creatures.
        */
    }

    public FeintofHeart(final FeintofHeart card) {
        super(card);
    }

    @Override
    public FeintofHeart copy() {
        return new FeintofHeart(this);
    }

}

class FeintOfHeartAlternativeCost extends AlternativeCostImpl {

    private static final FilterPermanent filter = new FilterPermanent();
    private static final Condition condition = new OpponentControlsPermanentCondition(filter);

    static {
        filter.add(new AbilityPredicate(VigilanceAbility.class));
    }

    public FeintOfHeartAlternativeCost() {
        super("you may pay {R} rather than pay {this}'s mana cost");
        this.add(new ManaCostsImpl("{R}"));
    }

    public FeintOfHeartAlternativeCost(final FeintOfHeartAlternativeCost cost) {
        super(cost);
    }

    @Override
    public FeintOfHeartAlternativeCost copy() {
        return new FeintOfHeartAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        return condition.apply(game, source);
    }

    @Override
    public String getText() {
        return "If an opponent controls a creature with vigilance, you may pay {R} rather than pay {this}'s mana cost";
    }
}
