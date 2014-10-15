/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.player.human;

import mage.MageObject;
import mage.abilities.*;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreatureForCombat;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetDefender;
import mage.util.ManaUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HumanPlayer extends PlayerImpl {

    private final transient PlayerResponse response = new PlayerResponse();

    protected static FilterCreatureForCombatBlock filterCreatureForCombatBlock = new FilterCreatureForCombatBlock();
    protected static FilterCreatureForCombat filterCreatureForCombat = new FilterCreatureForCombat();
    protected static FilterAttackingCreature filterAttack = new FilterAttackingCreature();
    protected static FilterBlockingCreature filterBlock = new FilterBlockingCreature();
    protected static final Choice replacementEffectChoice = new ChoiceImpl(true);
    private static final Map<String, Serializable> staticOptions = new HashMap<>();

    private static final Logger log = Logger.getLogger(HumanPlayer.class);

    static {
        replacementEffectChoice.setMessage("Choose replacement effect to resolve first");
        staticOptions.put("UI.right.btn.text", "Done");
    }

    public HumanPlayer(String name, RangeOfInfluence range, int skill) {
        super(name, range);
        human = true;
    }

    public HumanPlayer(final HumanPlayer player) {
        super(player);
    }

    protected void waitForResponse(Game game) {
        response.clear();
        log.debug("Waiting response from player: " + getId());
        game.resumeTimer(playerId);
        synchronized(response) {
            try {
                response.wait();
                log.debug("Got response from player: " + getId());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                game.pauseTimer(playerId);
            }
        }
    }

    protected void waitForBooleanResponse(Game game) {
        do {
            waitForResponse(game);
        } while (response.getBoolean() == null && !abort);
    }

    protected void waitForUUIDResponse(Game game) {
        do {
            waitForResponse(game);
        } while (response.getUUID() == null && !abort);
    }

    protected void waitForStringResponse(Game game) {
        do {
            waitForResponse(game);
        } while (response.getString() == null && !abort);
    }

    protected void waitForIntegerResponse(Game game) {
        do {
            waitForResponse(game);
        } while (response.getInteger() == null && !abort);
    }

    @Override
    public boolean chooseMulligan(Game game) {
        updateGameStatePriority("chooseMulligan", game);
        int nextHandSize = game.mulliganDownTo(playerId);
        game.fireAskPlayerEvent(playerId, new StringBuilder("Mulligan ")
                .append(getHand().size() > nextHandSize?"down to ":"for free, draw ")
                .append(nextHandSize)
                .append(nextHandSize == 1?" card?":" cards?").toString());
        waitForBooleanResponse(game);
        if (!abort) {
            return response.getBoolean();
        }
        return false;
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Game game) {
        updateGameStatePriority("chooseUse", game);
        game.fireAskPlayerEvent(playerId, message);
        waitForBooleanResponse(game);
        if (!abort) {
            return response.getBoolean();
        }
        return false;
    }

    @Override
    public int chooseEffect(List<String> rEffects, Game game) {
        updateGameStatePriority("chooseEffect", game);
        replacementEffectChoice.getChoices().clear();
        int count = 1;
        for (String effectText: rEffects) {
            replacementEffectChoice.getChoices().add(count + ". " + effectText);
            count++;
        }
        if (replacementEffectChoice.getChoices().size() == 1) {
            return 0;
        }
        while (!abort) {
            game.fireChooseEvent(playerId, replacementEffectChoice);
            waitForResponse(game);
            log.debug("Choose effect: " + response.getString());
            if (response.getString() != null) {
                replacementEffectChoice.setChoice(response.getString());
                count = 1;
                for (int i = 0; i < rEffects.size(); i++) {
                    if (replacementEffectChoice.getChoice().equals(count + ". " + rEffects.get(i))) {
                        return i;
                    }
                    count++;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        updateGameStatePriority("choose(3)", game);
        while (!abort) {
            game.fireChooseEvent(playerId, choice);
            waitForResponse(game);
            if (response.getString() != null) {
                choice.setChoice(response.getString());
                return true;
            } else if (!choice.isRequired()) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
         return choose(outcome, target, sourceId, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        updateGameStatePriority("choose(5)", game);
        while (!abort) {
            Set<UUID> targetIds = target.possibleTargets(sourceId, playerId, game);
            if (targetIds == null || targetIds.isEmpty()) {
                return false;
            }
            boolean required = target.isRequired(sourceId, game);
            if (target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }
            game.fireSelectTargetEvent(playerId, target.getMessage(), targetIds, required, options);
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (!targetIds.contains(response.getUUID())) {
                    continue;
                }
                if (target instanceof TargetPermanent) {
                    if (((TargetPermanent)target).canTarget(playerId, response.getUUID(), sourceId, game, false)) {
                        target.add(response.getUUID(), game);
                        if(target.doneChosing()){
                            return true;
                        }
                    }
                } else {
                    MageObject object = game.getObject(sourceId);
                    if (object instanceof Ability) {
                        if (target.canTarget(response.getUUID(), (Ability) object, game)) {
                            target.add(response.getUUID(), game);
                            if (target.doneChosing()) {
                                return true;
                            }
                        }
                    } else {
                        if (target.canTarget(response.getUUID(), game)) {
                            target.add(response.getUUID(), game);
                            if (target.doneChosing()) {
                                return true;
                            }
                        }
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!target.isRequired(sourceId, game)) {
                    return false;
                }

            }
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        updateGameStatePriority("chooseTarget", game);
        while (!abort) {
            Set<UUID> possibleTargets = target.possibleTargets(source==null?null:source.getSourceId(), playerId, game);
            boolean required = target.isRequired(source);
            if (possibleTargets.isEmpty() || target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }
            game.fireSelectTargetEvent(playerId, target.getMessage(), possibleTargets, required, getOptions(target));
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (possibleTargets.contains(response.getUUID())) {
                    if (target instanceof TargetPermanent) {
                        if (((TargetPermanent)target).canTarget(playerId, response.getUUID(), source, game)) {
                            if (target.getTargets().contains(response.getUUID())) { // if already included remove it with
                                target.remove(response.getUUID());
                            } else {
                                target.addTarget(response.getUUID(), source, game);
                                if(target.doneChosing()){
                                    return true;
                                }
                            }
                        }
                    } else if (target.canTarget(playerId, response.getUUID(), source, game)) {
                        if (target.getTargets().contains(response.getUUID())) { // if already included remove it with 
                            target.remove(response.getUUID());
                        } else {
                            target.addTarget(response.getUUID(), source, game);
                        }
                        if(target.doneChosing()){
                            return true;
                        }
                    }
                } // else do nothing - allow to pick another target
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    private Map<String, Serializable> getOptions(Target target) {
        return target.getTargets().size() >= target.getNumberOfTargets() ? staticOptions : null;
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
        updateGameStatePriority("choose(4)", game);
        while (!abort) {
            boolean required = target.isRequired();
            // if there is no cards to select from, then add possibility to cancel choosing action
            if (cards == null) {
                required = false;
            } else {
                int count = cards.count(target.getFilter(), game);
                if (count == 0) {
                    required = false;
                }
            }
            if (target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }
            Map<String, Serializable> options = getOptions(target);
            if (options == null) {
                options = new HashMap<>(1);
            }

            List<UUID> chosen = target.getTargets();
            options.put("chosen", (Serializable)chosen);
            List<UUID> choosable = new ArrayList<>();
            for (UUID cardId : cards) {
                if (target.canTarget(cardId, cards, game)) {
                    choosable.add(cardId);
                }
            }
            if (!choosable.isEmpty()) {
                options.put("choosable", (Serializable) choosable);
            }
            game.fireSelectTargetEvent(playerId, target.getMessage(), cards, required, options);
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (target.canTarget(response.getUUID(), cards, game)) {
                    if (target.getTargets().contains(response.getUUID())) { // if already included remove it with 
                        target.remove(response.getUUID());
                    } else {
                        target.add(response.getUUID(), game);
                        if (target.doneChosing()) {
                            return true;
                        }
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        updateGameStatePriority("chooseTarget(5)", game);
        while (!abort) {
            boolean required = target.isRequired(source);
            // if there is no cards to select from, then add possibility to cancel choosing action
            if (cards == null) {
                required = false;
            } else {
                int count = cards.count(target.getFilter(), game);
                if (count == 0) {
                    required = false;
                }
            }
            if (target.getTargets().size() >= target.getNumberOfTargets()) {
                required = false;
            }
            game.fireSelectTargetEvent(playerId, target.getMessage(), cards, required, null);
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (target.canTarget(response.getUUID(), cards, game)) {
                    target.addTarget(response.getUUID(), source, game);
                    if(target.doneChosing()){
                        return true;
                    }
                }
            } else {
                if (target.getTargets().size() >= target.getNumberOfTargets()) {
                    return true;
                }
                if (!required) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
        updateGameStatePriority("chooseTargetAmount", game);
        while (!abort) {
            game.fireSelectTargetEvent(playerId, target.getMessage() + "\n Amount remaining:" + target.getAmountRemaining(), target.possibleTargets(source==null?null:source.getSourceId(), playerId, game), target.isRequired(source), null);
            waitForResponse(game);
            if (response.getUUID() != null) {
                if (target.canTarget(response.getUUID(), source, game)) {
                    UUID targetId = response.getUUID();
                    int amountSelected = getAmount(1, target.getAmountRemaining(), "Select amount", game);
                    target.addTarget(targetId, amountSelected, source, game);
                    return true;
                }
            } else if (!target.isRequired(source)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean priority(Game game) {
        passed = false;
        if (!abort) {
            if (passedAllTurns) {
                pass(game);
                return false;
            }
            if (game.getStack().isEmpty()) {
                boolean dontCheckPassStep = false;
                if (passedTurn) {
                    pass(game);
                    return false;
                }
                if (passedUntilNextMain) {
                    if (game.getTurn().getStepType().equals(PhaseStep.POSTCOMBAT_MAIN) || game.getTurn().getStepType().equals(PhaseStep.PRECOMBAT_MAIN)) {
                        // it's a main phase
                        if (!skippedAtLeastOnce || (!playerId.equals(game.getActivePlayerId()) && !this.getUserData().getUserSkipPrioritySteps().isStopOnAllMainPhases())) {
                            skippedAtLeastOnce = true;
                            pass(game);
                            return false;
                        } else {
                            dontCheckPassStep = true;
                            passedUntilNextMain = false; // reset skip action
                        }
                    } else {
                        skippedAtLeastOnce = true;
                        pass(game);
                        return false;
                    }
                }
                if (passedUntilEndOfTurn) {
                    if (game.getTurn().getStepType().equals(PhaseStep.END_TURN)) {
                        // It's end of turn phase
                        if (!skippedAtLeastOnce || (playerId.equals(game.getActivePlayerId()) && !this.getUserData().getUserSkipPrioritySteps().isStopOnAllEndPhases())) {
                            skippedAtLeastOnce = true;
                            pass(game);
                            return false;
                        } else {
                            dontCheckPassStep = true;
                            passedUntilEndOfTurn = false;
                        }
                    } else {
                        skippedAtLeastOnce = true;
                        pass(game);
                        return false;
                    }
                }
                if (!dontCheckPassStep && checkPassStep(game)) {
                    pass(game);
                    return false;
                }                
            }
            updateGameStatePriority("priority", game);
            game.firePriorityEvent(playerId);
            waitForResponse(game);
            if (response.getBoolean() != null) {
                pass(game);
                return false;
            } else if (response.getInteger() != null) {
                /*if (response.getInteger() == -9999) {
                    passedAllTurns = true;
                }*/
                pass(game);
                //passedTurn = true;
                return false;
            } else if (response.getString() != null && response.getString().equals("special")) {
                specialAction(game);
            } else if (response.getUUID() != null) {
                boolean result = false;
                MageObject object = game.getObject(response.getUUID());
                if (object != null) {
                    Zone zone = game.getState().getZone(object.getId());
                    if (zone != null) {
                        if (object instanceof Card && ((Card) object).isFaceDown()) {
                            revealFaceDownCard((Card) object, game);
                            result = true;
                        }
                        LinkedHashMap<UUID, ActivatedAbility> useableAbilities = getUseableActivatedAbilities(object, zone, game);
                        if (useableAbilities != null && useableAbilities.size() > 0) {
                            activateAbility(useableAbilities, object, game);
                            result = true;
                        }
                    }
                }
                return result;
            } else if (response.getManaType() != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkPassStep(Game game) {
        if (game.getActivePlayerId().equals(playerId)) {
            return !this.getUserData().getUserSkipPrioritySteps().getYourTurn().isPhaseStepSet(game.getStep().getType());
        } else {
            return !this.getUserData().getUserSkipPrioritySteps().getOpponentTurn().isPhaseStepSet(game.getStep().getType());
        }
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        updateGameStatePriority("chooseTriggeredAbility", game);
        while (!abort) {
            game.fireSelectTargetEvent(playerId, "Pick triggered ability (goes to the stack first)", abilities);
            waitForResponse(game);
            if (response.getUUID() != null) {
                for (TriggeredAbility ability: abilities) {
                    if (ability.getId().equals(response.getUUID())) {
                        return ability;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean playMana(ManaCost unpaid, Game game) {
        updateGameStatePriority("playMana", game);
        game.firePlayManaEvent(playerId, "Pay " + unpaid.getText());
        waitForResponse(game);
        if (!this.isInGame()) {
            return false;
        }
        if (response.getBoolean() != null) {
            return false;
        } else if (response.getUUID() != null) {
            playManaAbilities(unpaid, game);
        } else if (response.getString() != null && response.getString().equals("special")) {
            if (unpaid instanceof ManaCostsImpl) {
                ManaCostsImpl<ManaCost> costs = (ManaCostsImpl<ManaCost>) unpaid;
                for (ManaCost cost : costs.getUnpaid()) {
                    if (cost instanceof PhyrexianManaCost) {
                        PhyrexianManaCost ph = (PhyrexianManaCost)cost;
                        if (ph.canPay(null, null, playerId, game)) {
                            ((PhyrexianManaCost)cost).pay(null, game, null, playerId, false);
                        }
                        break;
                    }
                }
            }
        } else if (response.getManaType() != null) {
            // this mana type can be paid once from pool
            if (response.getResponseManaTypePlayerId().equals(this.getId())) {
                this.getManaPool().unlockManaType(response.getManaType());
            }
            // TODO: Handle if mana pool 
        }
        return true;
    }

    /**
     * Gets the amount of mana the player want to spent for a x spell
     * @param min
     * @param max
     * @param message
     * @param game
     * @param ability
     * @return
     */
    @Override
    public int announceXMana(int min, int max, String message, Game game, Ability ability) {
        int xValue = 0;
        updateGameStatePriority("announceXMana", game);
        game.fireGetAmountEvent(playerId, message, min, max);
        waitForIntegerResponse(game);
        if (response != null && response.getInteger() != null) {
            xValue =  response.getInteger();
        }
        return xValue;
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost) {
        int xValue = 0;
        updateGameStatePriority("announceXCost", game);
        game.fireGetAmountEvent(playerId, message, min, max);
        waitForIntegerResponse(game);
        if (response != null && response.getInteger() != null) {
            xValue =  response.getInteger();
        }
        return xValue;
    }

    protected void playManaAbilities(ManaCost unpaid, Game game) {
        updateGameStatePriority("playManaAbilities", game);
        MageObject object = game.getObject(response.getUUID());
        if (object == null) {
            return;
        }        
        Zone zone = game.getState().getZone(object.getId());
        if (zone != null) {
            LinkedHashMap<UUID, ManaAbility> useableAbilities = getUseableManaAbilities(object, zone, game);
            if (useableAbilities != null && useableAbilities.size() > 0) {
                useableAbilities = ManaUtil.tryToAutoPay(unpaid, useableAbilities);
                activateAbility(useableAbilities, object, game);
            }
        }
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        updateGameStatePriority("selectAttackers", game);
        FilterCreatureForCombat filter = filterCreatureForCombat.copy();
        filter.add(new ControllerIdPredicate(attackingPlayerId));
        while (!abort) {
            if (passedAllTurns || 
               (!getUserData().getUserSkipPrioritySteps().isStopOnDeclareAttackersDuringSkipAction() && (passedTurn || passedUntilEndOfTurn || passedUntilNextMain) )) {
                return;
            }
            Map<String, Serializable> options = new HashMap<>();

            List<UUID> possibleAttackers = new ArrayList<>();
            for (Permanent possibleAttacker : game.getBattlefield().getActivePermanents(filter, attackingPlayerId, game)) {
                if (possibleAttacker.canAttack(game)) {
                    possibleAttackers.add(possibleAttacker.getId());
                }
            }
            options.put(Constants.Option.POSSIBLE_ATTACKERS, (Serializable)possibleAttackers);

            game.fireSelectEvent(playerId, "Select attackers", options);
            waitForResponse(game);
            if (response.getBoolean() != null) {
                // check if enough attackers are declared
                if (!game.getCombat().getCreaturesForcedToAttack().isEmpty()) {
                    if (!game.getCombat().getAttackers().containsAll(game.getCombat().getCreaturesForcedToAttack().keySet())) {
                        int forcedAttackers = 0;
                        StringBuilder sb = new StringBuilder();
                        for (UUID creatureId :game.getCombat().getCreaturesForcedToAttack().keySet()) {
                            boolean validForcedAttacker = false;
                            if (game.getCombat().getAttackers().contains(creatureId)) {
                                Set<UUID> possibleDefender = game.getCombat().getCreaturesForcedToAttack().get(creatureId);
                                if (possibleDefender.isEmpty() || possibleDefender.contains(game.getCombat().getDefenderId(creatureId))) {
                                    validForcedAttacker = true;
                                }
                            }
                            if (validForcedAttacker) {
                                forcedAttackers++;
                            } else {
                                Permanent creature = game.getPermanent(creatureId);
                                if (creature != null) {
                                    sb.append(creature.getName()).append(" ");
                                }
                            }
                            
                        }
                        if (game.getCombat().getMaxAttackers() > forcedAttackers) {
                            game.informPlayer(this, sb.insert(0," more attacker(s) that are forced to attack.\nCreatures forced to attack: ")
                                    .insert(0, Math.min(game.getCombat().getMaxAttackers() - forcedAttackers, game.getCombat().getCreaturesForcedToAttack().size() - forcedAttackers))
                                    .insert(0, "You have to attack with ").toString());
                            continue;
                        }
                    }
                }
                return;
            } else if (response.getInteger() != null) {
                //if (response.getInteger() == -9999) {
                //    passedAllTurns = true;
                //}
                //passedTurn = true;
                return;
            } else if (response.getUUID() != null) {
                Permanent attacker = game.getPermanent(response.getUUID());
                if (attacker != null) {
                    if (filterCreatureForCombat.match(attacker, null, playerId, game)) {
                        selectDefender(game.getCombat().getDefenders(), attacker.getId(), game);
                    }
                    else if (filterAttack.match(attacker, null, playerId, game) && game.getStack().isEmpty()) {
                        removeAttackerIfPossible(game, attacker);
                    }
                }
             }
        }
    }

    private void removeAttackerIfPossible(Game game, Permanent attacker) {
        for (Map.Entry entry : game.getContinuousEffects().getApplicableRequirementEffects(attacker, game).entrySet()) {
            RequirementEffect effect = (RequirementEffect)entry.getKey();
            if (effect.mustAttack(game)) {
                if (game.getCombat().getMaxAttackers() >= game.getCombat().getCreaturesForcedToAttack().size() && game.getCombat().getDefenders().size() == 1) {
                    return; // we can't change creatures forced to attack if only one possible defender exists and all forced creatures can attack
                }
            }
        }
        game.getCombat().removeAttacker(attacker.getId(), game);
    }

    /**
     * Selects a defender for an attacker and adds the attacker to combat
     *
     * @param defenders - list of possible defender
     * @param attackerId - UUID of attacker
     * @param game
     * @return
     */
    protected boolean selectDefender(Set<UUID> defenders, UUID attackerId, Game game) {
        boolean forcedToAttack = false;
        Set<UUID> possibleDefender = game.getCombat().getCreaturesForcedToAttack().get(attackerId);
        if (possibleDefender != null) {
            forcedToAttack = true;
        }
        if (possibleDefender == null || possibleDefender.isEmpty()) {
            possibleDefender = defenders;
        }
        if (possibleDefender.size() == 1) {
            declareAttacker(attackerId, defenders.iterator().next(), game, true);
            return true;
        }
        else {
            TargetDefender target = new TargetDefender(possibleDefender, attackerId);
            target.setNotTarget(true); // player or planswalker hexproof does not prevent attacking a player
            if (forcedToAttack) {
                StringBuilder sb = new StringBuilder(target.getTargetName());
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    sb.append(" (").append(attacker.getName()).append(")");
                    target.setTargetName(sb.toString());
                }
            }
            if (chooseTarget(Outcome.Damage, target, null, game)) {
                declareAttacker(attackerId, response.getUUID(), game, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
        updateGameStatePriority("selectBlockers", game);
        FilterCreatureForCombatBlock filter = filterCreatureForCombatBlock.copy();
        filter.add(new ControllerIdPredicate(defendingPlayerId));
        if (game.getBattlefield().count(filter, null, playerId, game) == 0 && !getUserData().getUserSkipPrioritySteps().isStopOnDeclareBlockerIfNoneAvailable()) {
            return;
        }
        while (!abort) {
            game.fireSelectEvent(playerId, "Select blockers");
            waitForResponse(game);
            if (response.getBoolean() != null) {
                return;
            } else if (response.getInteger() != null) {
                return;
            } else if (response.getUUID() != null) {
                Permanent blocker = game.getPermanent(response.getUUID());
                if (blocker != null) {
                    boolean removeBlocker = false;
                    // does not block yet and can block or can block more attackers
                    if (filter.match(blocker, null, playerId, game)) {                        
                        selectCombatGroup(defendingPlayerId, blocker.getId(), game);
                    } else {
                        if (filterBlock.match(blocker, null, playerId, game) && game.getStack().isEmpty()) {
                            removeBlocker = true;
                        }                            
                    }
                    
                    if (removeBlocker) {
                        game.getCombat().removeBlocker(blocker.getId(), game);
                    }
                }
            }
        }
    }

    @Override
    public UUID chooseAttackerOrder(List<Permanent> attackers, Game game) {
        updateGameStatePriority("chooseAttackerOrder", game);
        while (!abort) {
            game.fireSelectTargetEvent(playerId, "Pick attacker", attackers, true);
            waitForResponse(game);
            if (response.getUUID() != null) {
                for (Permanent perm: attackers) {
                    if (perm.getId().equals(response.getUUID())) {
                        return perm.getId();
                    }
                }
            }
        }
        return null;
    }


    @Override
    public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game) {
        updateGameStatePriority("chooseBlockerOrder", game);
        while (!abort) {
            game.fireSelectTargetEvent(playerId, "Pick blocker", blockers, true);
            waitForResponse(game);
            if (response.getUUID() != null) {
                for (Permanent perm: blockers) {
                    if (perm.getId().equals(response.getUUID())) {
                        return perm.getId();
                    }
                }
            }
        }
        return null;
    }

    protected void selectCombatGroup(UUID defenderId, UUID blockerId, Game game) {
        updateGameStatePriority("selectCombatGroup", game);
        TargetAttackingCreature target = new TargetAttackingCreature();
        game.fireSelectTargetEvent(playerId, "Select attacker to block", target.possibleTargets(null, playerId, game), false, null);
        waitForResponse(game);
        if (response.getBoolean() != null) {
            // do nothing
        } else if (response.getUUID() != null) {
            CombatGroup group = game.getCombat().findGroup(response.getUUID());
            if (group != null) {
                // check if already blocked, if not add
                if (!group.getBlockers().contains(blockerId)) {                    
                    declareBlocker(defenderId, blockerId, response.getUUID(), game);
                } else { // else remove from block
                    game.getCombat().removeBlockerGromGroup(blockerId, group, game);
                }
            }
        }
    }

    @Override
    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
        updateGameStatePriority("assignDamage", game);
        int remainingDamage = damage;
        while (remainingDamage > 0) {
            Target target = new TargetCreatureOrPlayer();
            if (singleTargetName != null) {
                target.setTargetName(singleTargetName);
            }
            choose(Outcome.Damage, target, sourceId, game);
            if (targets.isEmpty() || targets.contains(target.getFirstTarget())) {
                int damageAmount = getAmount(0, remainingDamage, "Select amount", game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanent.damage(damageAmount, sourceId, game, false, true);
                    remainingDamage -= damageAmount;
                }
                else {
                    Player player = game.getPlayer(target.getFirstTarget());
                    if (player != null) {
                        player.damage(damageAmount, sourceId, game, false, true);
                        remainingDamage -= damageAmount;
                    }
                }
            }
        }
    }

    @Override
    public int getAmount(int min, int max, String message, Game game) {
        updateGameStatePriority("getAmount", game);
        game.fireGetAmountEvent(playerId, message, min, max);
        waitForIntegerResponse(game);
        if (response != null && response.getInteger() != null) {
            return response.getInteger().intValue();
        } else {
            return 0;
        }
    }

    @Override
    public void sideboard(Match match, Deck deck) {
        match.fireSideboardEvent(playerId, deck);
    }

    @Override
    public void construct(Tournament tournament, Deck deck) {
        tournament.fireConstructEvent(playerId);
    }

    @Override
    public void pickCard(List<Card> cards, Deck deck, Draft draft) {
        draft.firePickCardEvent(playerId);
    }

    protected void specialAction(Game game) {
        updateGameStatePriority("specialAction", game);
        LinkedHashMap<UUID, SpecialAction> specialActions = game.getState().getSpecialActions().getControlledBy(playerId);
        game.fireGetChoiceEvent(playerId, name, null, new ArrayList<>(specialActions.values()));
        waitForResponse(game);
        if (response.getUUID() != null) {
            if (specialActions.containsKey(response.getUUID())) {
                activateAbility(specialActions.get(response.getUUID()), game);
            }
        }
    }

    protected void activateAbility(LinkedHashMap<UUID, ? extends ActivatedAbility> abilities, MageObject object, Game game) {
        updateGameStatePriority("activateAbility", game);
        if (abilities.size() == 1 && suppressAbilityPicker(abilities.values().iterator().next())) {
            ActivatedAbility ability = abilities.values().iterator().next();
            if (ability.getTargets().size() != 0 || !(ability.getCosts().size() == 1 && ability.getCosts().get(0) instanceof SacrificeSourceCost)) {
                activateAbility(ability, game);
                return;
            }
        }
        game.fireGetChoiceEvent(playerId, name, object, new ArrayList<>(abilities.values()));
        waitForResponse(game);
        if (response.getUUID() != null) {
            if (abilities.containsKey(response.getUUID())) {
                activateAbility(abilities.get(response.getUUID()), game);
            }
        }
    }

    private boolean suppressAbilityPicker(ActivatedAbility ability) {
        if (this.getUserData().isShowAbilityPickerForced()) {
            if (ability instanceof PlayLandAbility) {
                return true;
            }      
            if (!ability.getSourceId().equals(getCastSourceIdWithoutMana()) && ability.getManaCostsToPay().convertedManaCost() > 0) {
                return true;
            }
            if (ability instanceof ManaAbility) {
                return true;
            }
            // if ability has no mana costs you have to pick it from ability picker
            return false;
        }
        return true;
    }
    
    @Override
    public SpellAbility chooseSpellAbilityForCast(SpellAbility ability, Game game, boolean noMana) {
        switch(ability.getSpellAbilityType()) {
            case SPLIT:
            case SPLIT_FUSED:
                MageObject object = game.getObject(ability.getSourceId());
                if (object != null) {
                    LinkedHashMap<UUID, ActivatedAbility> useableAbilities = getSpellAbilities(object, game.getState().getZone(object.getId()), game);
                    if (useableAbilities != null && useableAbilities.size() == 1) {
                        return (SpellAbility) useableAbilities.values().iterator().next();
                    } else if (useableAbilities != null && useableAbilities.size() > 0) {
                        game.fireGetChoiceEvent(playerId, name, object, new ArrayList<>(useableAbilities.values()));
                        waitForResponse(game);
                        if (response.getUUID() != null) {
                            if (useableAbilities.containsKey(response.getUUID())) {
                                return (SpellAbility) useableAbilities.get(response.getUUID());
                            }
                        }
                    }
                }
                return null;
            default:
                return ability;
        }
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        updateGameStatePriority("chooseMode", game);
        if (modes.size() > 1) {
            MageObject obj = game.getObject(source.getSourceId());
            Map<UUID, String> modeMap = new LinkedHashMap<>();
            for (Mode mode: modes.values()) {
                if (!modes.getSelectedModes().contains(mode.getId()) // show only modes not already selected
                        && mode.getTargets().canChoose(source.getSourceId(), source.getControllerId(), game)) { // and where targets are available
                    String modeText = mode.getEffects().getText(mode);
                    if (obj != null) {
                        modeText = modeText.replace("{source}", obj.getName());
                    }
                    modeMap.put(mode.getId(), modeText);
                }
            }
            if (modeMap.size() > 0) {
                game.fireGetModeEvent(playerId, "Choose Mode", modeMap);
                waitForResponse(game);
                if (response.getUUID() != null) {
                    for (Mode mode: modes.values()) {
                        if (mode.getId().equals(response.getUUID())) {
                            return mode;
                        }
                    }
                }
            }
            return null;
        }
        return modes.getMode();
    }

    @Override
    public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
        updateGameStatePriority("choosePile", game);
        game.fireChoosePileEvent(playerId, message, pile1, pile2);
        waitForBooleanResponse(game);
        if (!abort) {
            return response.getBoolean();
        }
        return false;
    }

    @Override
    public void setResponseString(String responseString) {
        synchronized(response) {
            response.setString(responseString);
            response.notify();
            log.debug("Got response string from player: " + getId());
        }
    }

    @Override
    public void setResponseManaType(UUID manaTypePlayerId, ManaType manaType) {
        synchronized(response) {
            response.setManaType(manaType);
            response.setResponseManaTypePlayerId(manaTypePlayerId);
            response.notify();
            log.debug("Got response mana type from player: " + getId());
        }
    }

    @Override
    public void setResponseUUID(UUID responseUUID) {
        synchronized(response) {
            response.setUUID(responseUUID);
            response.notify();
            log.debug("Got response UUID from player: " + getId());
        }
    }

    @Override
    public void setResponseBoolean(Boolean responseBoolean) {
        synchronized(response) {
            response.setBoolean(responseBoolean);
            response.notify();
            log.debug("Got response boolean from player: " + getId());
        }
    }

    @Override
    public void setResponseInteger(Integer responseInteger) {
        synchronized(response) {
            response.setInteger(responseInteger);
            response.notify();
            log.debug("Got response integer from player: " + getId());
        }
    }

    @Override
    public void abort() {
        abort = true;
        synchronized(response) {
            response.notify();
            log.debug("Got cancel action from player: " + getId());
        }
    }

    @Override
    public void skip() {
        synchronized(response) {
            response.setInteger(0);
            response.notify();
            log.debug("Got skip action from player: " + getId());
        }
    }

    @Override
    public HumanPlayer copy() {
        return new HumanPlayer(this);
    }

    protected void updateGameStatePriority(String methodName, Game game) {
        log.debug("Setting game priority to " + getId() + " [" + methodName + "]");
        game.getState().setPriorityPlayerId(getId());
    }


}
