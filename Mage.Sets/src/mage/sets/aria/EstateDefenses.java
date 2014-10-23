package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.util.CardUtil;

public class EstateDefenses extends CardImpl {

    public EstateDefenses(UUID ownerId) {
        super(ownerId, 131, "Estate Defenses", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{5}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setRed(true);

        // Estate Defenses costs 1 less to cast for each attacking creature.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new EstateDefensesCostReductionEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Estate Defenses deals 1 damage to each attacking creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, new FilterAttackingCreature()));

        this.addAbility(new StormAbility());

        /*
        Card Text:
        ----------
        http://i.imgur.com/TtWauDG.jpg
        Estate Defenses costs 1 less to cast for each attacking creature.
        Estate Defenses deals 1 damage to each attacking creature.
        Storm (When you cast this spell, copy it for each spell cast before it this turn.)
        */
    }

    public EstateDefenses(final EstateDefenses card) {
        super(card);
    }

    @Override
    public EstateDefenses copy() {
        return new EstateDefenses(this);
    }

}

class EstateDefensesCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AttackingPredicate());
    }

    public EstateDefensesCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {1} less to cast for each attacking creature";
    }

    protected EstateDefensesCostReductionEffect(EstateDefensesCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = game.getBattlefield().count(filter, source.getSourceId(),  source.getControllerId(),game);
        CardUtil.reduceCost(abilityToModify, reductionAmount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility || abilityToModify instanceof RetraceAbility)
                && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public EstateDefensesCostReductionEffect copy() {
        return new EstateDefensesCostReductionEffect(this);
    }
}
