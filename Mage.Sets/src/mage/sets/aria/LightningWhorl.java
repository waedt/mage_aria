package mage.sets.aria;

import java.util.Set;
import java.util.UUID;

import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;

public class LightningWhorl extends CardImpl {

    public LightningWhorl(UUID ownerId) {
        super(ownerId, 139, "Lightning Whorl", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        // Whenever you cast a spell that targets an opponent or a creature an opponent controls, Lightning Whorl deals 1 damage to each opponent.
        this.addAbility(new LightningWhorlAbility());

        /*
        Card Text:
        ----------
        http://i.imgur.com/F9S1fzO.jpg
        Whenever you cast a spell that targets an opponent or a creature an opponent controls, Lightning Whorl deals 1 damage to each opponent.
        */
    }

    public LightningWhorl(final LightningWhorl card) {
        super(card);
    }

    @Override
    public LightningWhorl copy() {
        return new LightningWhorl(this);
    }

}

class LightningWhorlAbility extends TriggeredAbilityImpl {

    public LightningWhorlAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), false);
    }

    public LightningWhorlAbility(final LightningWhorlAbility other) {
        super(other);
    }

    @Override
    public TriggeredAbility copy() {
        return new LightningWhorlAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(event.getType() != EventType.SPELL_CAST || !event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }

        Spell spell = game.getStack().getSpell(event.getTargetId());
        if(spell == null) {
            return false;
        }

        SpellAbility sa = spell.getSpellAbility();
        if(sa == null) {
            return false;
        }

        for(Target target : sa.getTargets()) {
            if(target.isNotTarget()) {
                continue;
            }
            for(UUID targetId : target.getTargets()) {
                if(checkTarget(targetId, game)) {
                    return true;
                }
            }
        }

        for(Effect effect : sa.getEffects()) {
            for(UUID targetId : effect.getTargetPointer().getTargets(game, sa)) {
                if(checkTarget(targetId, game)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkTarget(UUID targetId, Game game) {
        Set<UUID> opponents = game.getOpponents(this.getControllerId());
        if(opponents.contains(targetId)) {
            return true;
        }

        Permanent permanent = game.getPermanent(targetId);
        if(permanent != null && permanent.getCardType().contains(CardType.CREATURE) && opponents.contains(permanent.getControllerId())) {
            return true;
        }

        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell that targets an opponent or a creature an opponent controls, Lightning Whorl deals 1 damage to each opponent.";
    }
}
