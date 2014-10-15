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

package org.mage.test.player;

import mage.MageObject;
import mage.abilities.*;
import mage.abilities.costs.VariableCost;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.SpellAbilityType;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreatureForCombat;
import mage.filter.common.FilterCreatureForCombatBlock;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanentAmount;
import org.junit.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.mana.ManaAbility;
import mage.cards.Card;
import mage.constants.Zone;
import mage.target.TargetSource;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
@Ignore
public class TestPlayer extends ComputerPlayer {

    private final List<PlayerAction> actions = new ArrayList<>();
    private final List<String> choices = new ArrayList<>();
    private final List<String> targets = new ArrayList<>();
    private final List<String> modesSet = new ArrayList<>();

    public TestPlayer(String name, RangeOfInfluence range) {
        super(name, range);
        human = false;
    }

    public TestPlayer(final TestPlayer player) {
        super(player);
    }

    public void addAction(int turnNum, PhaseStep step, String action) {
        actions.add(new PlayerAction(turnNum, step, action));
    }

    @Override
    public int getActionCount() {
        return actions.size();
    }

    public void addChoice(String choice) {
        choices.add(choice);
    }

    public void addModeChoice(String mode) {
        modesSet.add(mode);
    }

    public void addTarget(String target) {
        targets.add(target);
    }

    @Override
    public TestPlayer copy() {
        return new TestPlayer(this);
    }

    @Override
    public boolean priority(Game game) {
        for (PlayerAction action: actions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getStep() == game.getStep().getType()) {

                if (action.getAction().startsWith("activate:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("activate:") + 9);
                    String[] groups = command.split(";");
                    if (!checkSpellOnStackCondition(groups, game) || !checkSpellOnTopOfStackCondition(groups, game)) {
                        break;
                    }
                    for (Ability ability: this.getPlayable(game, true)) {
                        if (ability.toString().startsWith(groups[0])) {
                            Ability newAbility = ability.copy();
                            if (groups.length > 1) {
                                if (!addTargets(newAbility, groups, game)) {
                                    // targets could not be set -> try next priority
                                    break;
                                }
                            }
                            this.activateAbility((ActivatedAbility)newAbility, game);
                            actions.remove(action);
                            return true;
                        }
                    }                    
                } else
                
                if (action.getAction().startsWith("manaActivate:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("manaActivate:") + 13);
                    String[] groups = command.split(";");
                    List<Permanent> manaPerms = this.getAvailableManaProducers(game);
                    for (Permanent perm: manaPerms) {
                        for (Ability manaAbility: perm.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game)) {
                            if (manaAbility.toString().startsWith(groups[0])) {                                
                                Ability newManaAbility = manaAbility.copy();                                
                                this.activateAbility((ActivatedAbility)newManaAbility, game);
                                actions.remove(action);
                                return true;
                            }                            
                        }                        
                    }
                    List<Permanent> manaPermsWithCost = this.getAvailableManaProducersWithCost(game);
                    for (Permanent perm: manaPermsWithCost) {
                        for (ManaAbility manaAbility: perm.getAbilities().getAvailableManaAbilities(Zone.BATTLEFIELD, game)) {
                            if (manaAbility.toString().startsWith(groups[0]) && manaAbility.canActivate(playerId, game)) {                                
                                Ability newManaAbility = manaAbility.copy();                                
                                this.activateAbility((ActivatedAbility)newManaAbility, game);
                                actions.remove(action);
                                return true;
                            }                            
                        }                     
                    }
                } else 
                
                if (action.getAction().startsWith("addCounters:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("addCounters:") + 12);
                    String[] groups = command.split(";");
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                        if (permanent.getName().equals(groups[0])) {
                            Counter counter = new Counter(groups[1], Integer.parseInt(groups[2]));
                            permanent.addCounters(counter, game);
                            break;
                        }
                    }
                }
            }
        }
        pass(game);
        return false;
    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        UUID defenderId = null;
        for (PlayerAction action: actions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getAction().startsWith("attack:")) {
                for (UUID uuid: game.getCombat().getDefenders()) {
                    Player defender = game.getPlayer(uuid);
                    if (defender != null) {
                        defenderId = uuid;
                    }
                }
                String command = action.getAction();
                command = command.substring(command.indexOf("attack:") + 7);
                String[] groups = command.split(";");
                for (int i = 1; i < groups.length; i++) {
                    String group = groups[i];
                    if (group.startsWith("planeswalker=")) {
                        String planeswalkerName = group.substring(group.indexOf("planeswalker=") + 13);
                        for (Permanent permanent :game.getBattlefield().getAllActivePermanents(new FilterPlaneswalkerPermanent(), game)) {
                            if (permanent.getName().equals(planeswalkerName)) {
                                defenderId = permanent.getId();                                        
                            }
                        }
                    }
                }
                FilterCreatureForCombat filter = new FilterCreatureForCombat();
                filter.add(new NamePredicate(groups[0]));
                filter.add(Predicates.not(new AttackingPredicate()));
                Permanent attacker = findPermanent(filter, playerId, game);
                if (attacker != null && attacker.canAttack(defenderId, game)) {
                    this.declareAttacker(attacker.getId(), defenderId, game, false);
                }
            }
        }
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
        UUID opponentId = game.getOpponents(playerId).iterator().next();
        for (PlayerAction action: actions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getAction().startsWith("block:")) {
                String command = action.getAction();
                command = command.substring(command.indexOf("block:") + 6);
                String[] groups = command.split(";");
                FilterCreatureForCombatBlock filterBlocker = new FilterCreatureForCombatBlock();
                filterBlocker.add(new NamePredicate(groups[0]));
                filterBlocker.add(Predicates.not(new BlockingPredicate()));
                Permanent blocker = findPermanent(filterBlocker, playerId, game);
                if (blocker != null) {
                    FilterAttackingCreature filterAttacker = new FilterAttackingCreature();
                    filterAttacker.add(new NamePredicate(groups[1]));
                    Permanent attacker = findPermanent(filterAttacker, opponentId, game);
                    if (attacker != null) {
                        this.declareBlocker(defendingPlayerId, blocker.getId(), attacker.getId(), game);
                    }
                }
            }
        }
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        if (!modesSet.isEmpty() && modes.getMaxModes() > modes.getSelectedModes().size()) {
            int selectedMode = Integer.parseInt(modesSet.get(0));            
            int i = 1;
            for (Mode mode: modes.values()) {
                if (i == selectedMode) {
                    modesSet.remove(0);
                    return mode;
                }
                i++;
            }
        }
        return super.chooseMode(modes, source, game); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        if (!choices.isEmpty()) {
            for (String choose2: choices) {
                for (String choose1: choice.getChoices()) {
                    if (choose1.equals(choose2)) {
                        choice.setChoice(choose2);
                        choices.remove(choose2);
                        return true;
                    }
                }
            }
        }
        return super.choose(outcome, choice, game);
    }

    @Override
    public int chooseEffect(List<String> rEffects, Game game) {
        if (!choices.isEmpty()) {
            for (String choice: choices) {
                for (int index = 0; index < rEffects.size(); index++) {
                    if (choice.equals(rEffects.get(index))) {
                        choices.remove(choice);
                        return index;
                    }
                }
            }
        }
        return super.chooseEffect(rEffects, game);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        if (!choices.isEmpty()) {
            if (target instanceof TargetPermanent) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents((FilterPermanent)target.getFilter(), game)) {
                    for (String choose2: choices) {
                        if (permanent.getName().equals(choose2)) {
                            if (((TargetPermanent)target).canTarget(playerId, permanent.getId(), null, game) && !target.getTargets().contains(permanent.getId())) {
                                target.add(permanent.getId(), game);
                                choices.remove(choose2);
                                return true;
                            }
                        } else if ((permanent.getName()+"-"+permanent.getExpansionSetCode()).equals(choose2)) {
                            if (((TargetPermanent)target).canTarget(playerId, permanent.getId(), null, game) && !target.getTargets().contains(permanent.getId())) {
                                target.add(permanent.getId(), game);
                                choices.remove(choose2);
                                return true;
                            }
                        }
                    }
                }
            }
            if (target instanceof TargetPlayer) {
                for (Player player :game.getPlayers().values()) {
                    for (String choose2: choices) {
                        if (player.getName().equals(choose2)) {
                            if (((TargetPlayer)target).canTarget(playerId, player.getId(), null, game) && !target.getTargets().contains(player.getId())) {
                                target.add(player.getId(), game);
                                choices.remove(choose2);
                                return true;                                
                            }
                        }
                    }
                }
            }
            if (target instanceof TargetSource) {
                Set<UUID> possibleTargets;
                TargetSource t = ((TargetSource) target);
                possibleTargets = t.possibleTargets(sourceId, playerId, game);
                for (UUID targetId : possibleTargets) {
                    MageObject targetObject = game.getObject(targetId);
                    if (targetObject != null) {
                        for (String choose2: choices) {
                            if (targetObject.getName().equals(choose2)) {
                                List<UUID> alreadyTargetted = target.getTargets();
                                if (t.canTarget(targetObject.getId(), game)) {
                                    if (alreadyTargetted != null && !alreadyTargetted.contains(targetObject.getId())) {
                                        target.add(targetObject.getId(), game);
                                        choices.remove(choose2);
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.choose(outcome, target, sourceId, game, options);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        if (!targets.isEmpty()) {
            if (target instanceof TargetPermanent) {
                for (String targetDefinition: targets) {
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName: targetList) {
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents((FilterPermanent)target.getFilter(), game)) {
                            if (permanent.getName().equals(targetName) || (permanent.getName()+"-"+permanent.getExpansionSetCode()).equals(targetName)) {
                                if (((TargetPermanent)target).canTarget(source.getControllerId(), permanent.getId(), source, game) && !target.getTargets().contains(permanent.getId())) {
                                    target.add(permanent.getId(), game);
                                    targetFound = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        targets.remove(targetDefinition);
                        return true;
                    }
                }
            }
            if (target instanceof TargetPlayer) {
                for (String targetDefinition: targets) {
                    if (targetDefinition.startsWith("targetPlayer=")) {
                        String playerName = targetDefinition.substring(targetDefinition.indexOf("targetPlayer=") + 13);
                        for (Player player: game.getPlayers().values()) {
                            if (player.getName().equals(playerName)
                                    && ((TargetPlayer) target).canTarget(playerId, player.getId(), source, game)) {
                                target.add(player.getId(), game);
                                return true;
                            }
                        }
                    }
                }
                
            }
            if (target instanceof TargetCardInHand) {
                for (String targetDefinition: targets) {
                    String[] targetList = targetDefinition.split("\\^");
                    boolean targetFound = false;
                    for (String targetName: targetList) {
                        for (Card card: this.getHand().getCards(((TargetCardInHand)target).getFilter(), game)) {
                            if (card.getName().equals(targetName) || (card.getName()+"-"+card.getExpansionSetCode()).equals(targetName)) {
                                if (((TargetCardInHand)target).canTarget(source.getControllerId(), card.getId(), source, game) && !target.getTargets().contains(card.getId())) {
                                    target.add(card.getId(), game);
                                    targetFound = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        targets.remove(targetDefinition);
                        return true;
                    }
                }

            }

        }
        return super.chooseTarget(outcome, target, source, game);
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        if (!choices.isEmpty()) {
            for(TriggeredAbility ability :abilities) {
                if (choices.get(0).equals(ability.toString())) {
                    choices.remove(0);
                    return ability;
                }
            }
        }
        return super.chooseTriggeredAbility(abilities, game);
    }

    @Override
    public boolean chooseUse(Outcome outcome, String message, Game game) {
        if (!choices.isEmpty()) {
            if (choices.get(0).equals("No")) {
                choices.remove(0);
                return false;
            }
            if (choices.get(0).equals("Yes")) {
                choices.remove(0);
                return true;
            }
        }
        return true;
    }

    @Override
    public int announceXMana(int min, int max, String message, Game game, Ability ability) {
        if (!choices.isEmpty()) {
            if (choices.get(0).startsWith("X=")) {
                int xValue = Integer.parseInt(choices.get(0).substring(2));
                choices.remove(0);
                return xValue;
            }
        }
        return super.announceXMana(min, max, message, game, ability);
    }

    @Override
    public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variablCost) {
        if (!choices.isEmpty()) {
            if (choices.get(0).startsWith("X=")) {
                int xValue = Integer.parseInt(choices.get(0).substring(2));
                choices.remove(0);
                return xValue;
            }
        }
        return super.announceXCost(min, max, message, game, ability, null);
    }

    protected Permanent findPermanent(FilterPermanent filter, UUID controllerId, Game game) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, controllerId, game);
        if (permanents.size() > 0) {
            return permanents.get(0);
        }
        return null;
    }

    private boolean checkSpellOnStackCondition(String[] groups, Game game) {
        if (groups.length > 2 && groups[2].startsWith("spellOnStack=")) {
            String spellOnStack = groups[2].substring(13);
            for (StackObject stackObject: game.getStack()) {
                if (stackObject.getStackAbility().toString().contains(spellOnStack)) {
                    return true;
                }
            }
            return false;
        } else if (groups.length > 2 && groups[2].startsWith("!spellOnStack=")) {
            String spellNotOnStack = groups[2].substring(14);
            for (StackObject stackObject: game.getStack()) {
                if (stackObject.getStackAbility().toString().contains(spellNotOnStack)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private boolean checkSpellOnTopOfStackCondition(String[] groups, Game game) {
        if (groups.length > 2 && groups[2].startsWith("spellOnTopOfStack=")) {
            String spellOnTopOFStack = groups[2].substring(18);
            if (game.getStack().size() > 0) {
                StackObject stackObject = game.getStack().getFirst();
                if (stackObject != null && stackObject.getStackAbility().toString().contains(spellOnTopOFStack)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    private boolean addTargets(Ability ability, String[] groups, Game game) {
        boolean result = true;
        for (int i = 1; i < groups.length; i++) {
            String group = groups[i];
            if (group.startsWith("spellOnStack") || group.startsWith("spellOnTopOfStack") || group.startsWith("!spellOnStack")) {
                break;
            }
            if (ability instanceof SpellAbility && ((SpellAbility) ability).getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
                if (group.contains("FuseLeft-")) {
                    result = handleTargetString(group.substring(group.indexOf("FuseLeft-") + 9), ability, game);
                } else if(group.startsWith("FuseRight-")) {
                    result = handleTargetString(group.substring(group.indexOf("FuseRight-") + 10), ability, game);
                } else {
                    result = false;
                }
            } else {
                result = handleTargetString(group, ability, game);
            }
        }
        return result;
    }

    private boolean handleTargetString(String target, Ability ability, Game game){
        boolean result = false;
        if (target.startsWith("targetPlayer=")) {
            result = handlePlayerTarget(target.substring(target.indexOf("targetPlayer=") + 13), ability, game);
        } else if (target.startsWith("target=")) {
            result = handleNonPlayerTargetTarget(target.substring(target.indexOf("target=") + 7), ability, game);
        }
        return result;
    }
    
    private boolean handlePlayerTarget(String target, Ability ability, Game game) {
        boolean result = true;
        int targetsSet = 0;
        for (Player player: game.getPlayers().values()) {
            if (player.getName().equals(target)) {
                ability.getTargets().get(0).addTarget(player.getId(), ability, game);
                targetsSet++;
                break;
            }
        }
        if (targetsSet < 1) {
            result = false;
        }
        return result;
    }
    
    private boolean handleNonPlayerTargetTarget(String target, Ability ability, Game game) {
        boolean result = true;
        if (target.isEmpty()) {
            return true; // needed if spell has no target but waits until spell is on the stack
        }
        String[] targetList = target.split("\\^");
        int index = 0;
        int targetsSet = 0;
        for (String targetName: targetList) {
            if (targetName.startsWith("targetPlayer=")) {
                target = targetName.substring(targetName.indexOf("targetPlayer=") + 13);
                for (Player player: game.getPlayers().values()) {
                    if (player.getName().equals(target)) {
                        ability.getTargets().get(index).addTarget(player.getId(), ability, game);
                        index++;
                        targetsSet++;
                        break;
                    }
                }
            } else {
                if (ability.getTargets().size() == 0) {
                    throw new AssertionError("Ability has no targets. " + ability.toString());
                }
                for (UUID id: ability.getTargets().get(0).possibleTargets(ability.getSourceId(), ability.getControllerId(), game)) {
                    MageObject object = game.getObject(id);
                    if (object != null && object.getName().equals(targetName)) {
                        if (index >= ability.getTargets().size()) {
                            index--;
                        }
                        if (ability.getTargets().get(index).getNumberOfTargets() == 1) {
                            ability.getTargets().get(index).clearChosen();
                        }
                        if (ability.getTargets().get(index) instanceof TargetCreaturePermanentAmount) {
                            // supports only to set the complete amount to one target
                            TargetCreaturePermanentAmount targetAmount = (TargetCreaturePermanentAmount) ability.getTargets().get(index);
                            targetAmount.setAmount(ability, game);
                            int amount = targetAmount.getAmountRemaining();
                            targetAmount.addTarget(id, amount,ability, game);
                            targetsSet++;
                        } else {
                            ability.getTargets().get(index).addTarget(id, ability, game);
                            targetsSet++;
                        }
                        index++;
                        break;
                    }
                }
            }
        }
        if (targetsSet != targetList.length) {
            result = false;
        }
        return result;
    }
}

