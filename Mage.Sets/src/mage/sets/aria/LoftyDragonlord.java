package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

public class LoftyDragonlord extends CardImpl {

    public LoftyDragonlord(UUID ownerId) {
        super(ownerId, 22, "Lofty Dragonlord", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dragon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setWhite(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells your opponents control that target Lofty Dragonlord cost 2 more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LoftyDragonlordSpellCostReductionEffect()));

        // Creatures can't attack you unless their controller pays 2 for each creature he or she controls that's attacking you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LoftyDragonlordAttackingCreatureCostEffect()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/P8oDg4E.jpg
        Flying
        Spells your opponents control that target Lofty Dragonlord cost 2 more to cast.
        Creatures can't attack you unless their controller pays 2 for each creature he or she controls that's attacking you.
        */
    }

    public LoftyDragonlord(final LoftyDragonlord card) {
        super(card);
    }

    @Override
    public LoftyDragonlord copy() {
        return new LoftyDragonlord(this);
    }

}

class LoftyDragonlordAttackingCreatureCostEffect extends ReplacementEffectImpl {

    public LoftyDragonlordAttackingCreatureCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "Creatures can't attack you unless their controller pays {2} for each creature he or she controls that's attacking you";
    }

    public LoftyDragonlordAttackingCreatureCostEffect(final LoftyDragonlordAttackingCreatureCostEffect effect) {
        super(effect);
    }

    @Override
    public LoftyDragonlordAttackingCreatureCostEffect copy() {
        return new LoftyDragonlordAttackingCreatureCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if(player != null) {
            ManaCostsImpl attackTax = new ManaCostsImpl("{2}");
            if(attackTax.canPay(source, source.getSourceId(), event.getPlayerId(), game) &&
                 player.chooseUse(Outcome.Neutral, "Pay {2} to attack player?", game))
            {
                if(attackTax.payOrRollback(source, game, source.getSourceId(), event.getPlayerId()) ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(event.getType() == GameEvent.EventType.DECLARE_ATTACKER && event.getTargetId().equals(source.getControllerId())) {
            Player attackedPlayer = game.getPlayer(event.getTargetId());
            if(attackedPlayer != null) {
                // only if a player is attacked. Attacking a planeswalker is free
                return true;
            }
        }
        return false;
    }

}


class LoftyDragonlordSpellCostReductionEffect extends CostModificationEffectImpl {

    public LoftyDragonlordSpellCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target {this} cost {2} more to cast";
    }

    public LoftyDragonlordSpellCostReductionEffect(final LoftyDragonlordSpellCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if(abilityToModify instanceof SpellAbility) {
            if(game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for(Target target :abilityToModify.getTargets()) {
                    for(UUID targetUUID :target.getTargets()) {
                        if(targetUUID.equals(source.getSourceId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public LoftyDragonlordSpellCostReductionEffect copy() {
        return new LoftyDragonlordSpellCostReductionEffect(this);
    }

}
