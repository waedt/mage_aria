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

package mage.abilities;

import mage.MageObject;
import mage.abilities.costs.*;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.*;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.mana.ManaAbility;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.Choices;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.costs.common.TapSourceCost;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class AbilityImpl implements Ability {

    private static final transient Logger logger = Logger.getLogger(AbilityImpl.class);

    protected UUID id;
    protected UUID originalId;
    protected AbilityType abilityType;
    protected UUID controllerId;
    protected UUID sourceId;
    protected ManaCosts<ManaCost> manaCosts;
    protected ManaCosts<ManaCost> manaCostsToPay;
    protected Costs<Cost> costs;
    protected ArrayList<AlternativeCost> alternativeCosts = new ArrayList<>();
    protected Costs<Cost> optionalCosts;
    protected Modes modes;
    protected Zone zone;
    protected String name;
    protected AbilityWord abilityWord;
    protected boolean usesStack = true;
    protected boolean ruleAtTheTop = false;
    protected boolean ruleVisible = true;
    protected boolean ruleAdditionalCostsVisible = true;
    protected boolean costModificationActive = true;
    protected boolean activated = false;
    protected boolean worksFaceDown = false;

    public AbilityImpl(AbilityType abilityType, Zone zone) {
        this.id = UUID.randomUUID();
        this.originalId = id;
        this.abilityType = abilityType;
        this.zone = zone;
        this.manaCosts = new ManaCostsImpl<>();
        this.manaCostsToPay = new ManaCostsImpl<>();
        this.costs = new CostsImpl<>();
        this.optionalCosts = new CostsImpl<>();
        this.modes = new Modes();
    }

    public AbilityImpl(final AbilityImpl ability) {
        this.id = ability.id;
        this.originalId = ability.originalId;
        this.abilityType = ability.abilityType;
        this.controllerId = ability.controllerId;
        this.sourceId = ability.sourceId;
        this.zone = ability.zone;
        this.name = ability.name;
        this.usesStack = ability.usesStack;
        this.manaCosts = ability.manaCosts;
        this.manaCostsToPay = ability.manaCostsToPay.copy();
        this.costs = ability.costs.copy();
        this.optionalCosts = ability.optionalCosts.copy();
        for (AlternativeCost cost: ability.alternativeCosts) {
            this.alternativeCosts.add((AlternativeCost)cost.copy());
        }
        this.modes = ability.modes.copy();
        this.ruleAtTheTop = ability.ruleAtTheTop;
        this.ruleVisible = ability.ruleVisible;
        this.ruleAdditionalCostsVisible = ability.ruleAdditionalCostsVisible;
        this.costModificationActive = ability.costModificationActive;
        this.worksFaceDown = ability.worksFaceDown;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void newId() {
        if (!(this instanceof MageSingleton)) {
            this.id = UUID.randomUUID();
        }
        getEffects().newId();
    }

    @Override
    public void newOriginalId() {
        this.id = UUID.randomUUID();
        this.originalId = id;
        getEffects().newId();
    }

    @Override
    public AbilityType getAbilityType() {
        return this.abilityType;
    }

    @Override
    public boolean resolve(Game game) {
        boolean result = true;
        //20100716 - 117.12
        if (checkIfClause(game)) {
            for (Effect effect: getEffects()) {
                if (effect instanceof OneShotEffect) {
                    if (!(effect instanceof PostResolveEffect)) {
                        boolean effectResult = effect.apply(game, this);
                        result &= effectResult;
                        if (logger.isDebugEnabled()) {
                            if (!this.getAbilityType().equals(AbilityType.MANA)) {
                                if (!effectResult) {
                                    if (this.getSourceId() != null) {
                                        MageObject mageObject = game.getObject(this.getSourceId());
                                        if (mageObject != null) {
                                            logger.debug("AbilityImpl.resolve: object: " + mageObject.getName());
                                        }
                                    }
                                    logger.debug("AbilityImpl.resolve: effect returned false -" + effect.getText(this.getModes().getMode()));
                                }
                            }
                        }
                    }
                }
                else {
                    game.addEffect((ContinuousEffect) effect, this);
                }
                // some effects must be applied before next effect is resolved, because effect is dependend.
                if (effect.applyEffectsAfter()) {
                    game.applyEffects();
                }
            }
        }
        return result;
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller == null) {
            return false;
        }

        /* 20130201 - 601.2b
         * If the spell is modal the player announces the mode choice (see rule 700.2).
         */
        if (!modes.choose(game, this)) {
            return false;
        }

        /* 20130201 - 601.2b
         * If the player wishes to splice any cards onto the spell (see rule 702.45), he
         * or she reveals those cards in his or her hand.
         */
        if (this.abilityType.equals(AbilityType.SPELL)) {
            game.getContinuousEffects().applySpliceEffects(this, game);
        }

        
        MageObject sourceObject = game.getObject(sourceId);
        if (sourceObject != null) {
            sourceObject.adjustChoices(this, game);
        }
        for (UUID modeId :this.getModes().getSelectedModes()) {
            this.getModes().setMode(this.getModes().get(modeId));
            if (getChoices().size() > 0 && getChoices().choose(game, this) == false) {
                logger.debug("activate failed - choice");
                return false;
            }
        }

        // if ability can be cast for no mana, clear the mana costs now, because additional mana costs must be paid.
        // For Flashback ability can be set X before, so the X costs have to be restored for the flashbacked ability
        if (noMana) {
            if (this.getManaCostsToPay().getVariableCosts().size() > 0) {
                int xValue = this.getManaCostsToPay().getX();
                this.getManaCostsToPay().clear();
                VariableManaCost xCosts = new VariableManaCost();
                xCosts.setAmount(xValue);
                this.getManaCostsToPay().add(xCosts);
            } else {
                this.getManaCostsToPay().clear();
            }
        }
        // 20130201 - 601.2b
        // If the spell has alternative or additional costs that will be paid as it's being cast such
        // as buyback, kicker, or convoke costs (see rules 117.8 and 117.9), the player announces his
        // or her intentions to pay any or all of those costs (see rule 601.2e).
        // A player can't apply two alternative methods of casting or two alternative costs to a single spell.
        if (sourceObject != null && !(this instanceof FlashbackAbility)) {
            boolean alternativeCostisUsed = false;
            for (Ability ability : sourceObject.getAbilities()) {
                // if cast for noMana no Alternative costs are allowed
                if (!noMana && ability instanceof AlternativeSourceCosts) {
                    AlternativeSourceCosts alternativeSpellCosts = (AlternativeSourceCosts) ability;
                    if (alternativeSpellCosts.isAvailable(this, game)) {
                        if (alternativeSpellCosts.askToActivateAlternativeCosts(this, game)) {
                            // only one alternative costs may be activated
                            alternativeCostisUsed = true;
                            break;
                        }
                    }
                }                
                if (ability instanceof OptionalAdditionalSourceCosts) {
                    ((OptionalAdditionalSourceCosts)ability).addOptionalAdditionalCosts(this, game);
                }
            }
            // controller specific alternate spell costs
            if (!noMana && !alternativeCostisUsed) {
                if (this.getAbilityType().equals(AbilityType.SPELL)) {
                    for (AlternativeSourceCosts alternativeSourceCosts: controller.getAlternativeSourceCosts()) {
                         if (alternativeSourceCosts.isAvailable(this, game)) {
                            if (alternativeSourceCosts.askToActivateAlternativeCosts(this, game)) {
                                // only one alternative costs may be activated
                                break;
                            }
                        }
                    }
                }
            }
        }

        // 20121001 - 601.2b
        // If the spell has a variable cost that will be paid as it's being cast (such as an {X} in
        // its mana cost; see rule 107.3), the player announces the value of that variable.
        VariableManaCost variableManaCost = handleManaXCosts(game, noMana, controller);
        String announceString = handleOtherXCosts(game, controller);

        for (UUID modeId :this.getModes().getSelectedModes()) {
            this.getModes().setMode(this.getModes().get(modeId));
            //20121001 - 601.2c
            // 601.2c The player announces his or her choice of an appropriate player, object, or zone for
            // each target the spell requires. A spell may require some targets only if an alternative or
            // additional cost (such as a buyback or kicker cost), or a particular mode, was chosen for it;
            // otherwise, the spell is cast as though it did not require those targets. If the spell has a
            // variable number of targets, the player announces how many targets he or she will choose before
            // he or she announces those targets. The same target can't be chosen multiple times for any one
            // instance of the word "target" on the spell. However, if the spell uses the word "target" in
            // multiple places, the same object, player, or zone can be chosen once for each instance of the
            // word "target" (as long as it fits the targeting criteria). If any effects say that an object
            // or player must be chosen as a target, the player chooses targets so that he or she obeys the
            // maximum possible number of such effects without violating any rules or effects that say that
            // an object or player can't be chosen as a target. The chosen players, objects, and/or zones
            // each become a target of that spell. (Any abilities that trigger when those players, objects,
            // and/or zones become the target of a spell trigger at this point; they'll wait to be put on
            // the stack until the spell has finished being cast.)

            if (sourceObject != null) {
                sourceObject.adjustTargets(this, game);
            }
            if (getTargets().size() > 0 && getTargets().chooseTargets(getEffects().get(0).getOutcome(), this.controllerId, this, game) == false) {
                if (variableManaCost != null || announceString != null) {
                    game.informPlayer(controller, new StringBuilder(sourceObject != null ? sourceObject.getLogName(): "").append(": no valid targets with this value of X").toString());
                }
                return false; // when activation of ability is canceled during target selection 
            }
        } // end modes

        // TODO: Handle optionalCosts at the same time as already OptionalAdditionalSourceCosts are handled.
        for (Cost cost : optionalCosts) {
              if (cost instanceof ManaCost) {
                cost.clearPaid();
                if (controller.chooseUse(Outcome.Benefit, "Pay optional cost " + cost.getText() + "?", game)) {
                    manaCostsToPay.add((ManaCost) cost);
                }
            }
        }
        //20100716 - 601.2e
        if (sourceObject != null) {
            sourceObject.adjustCosts(this, game);
            for (Ability ability : sourceObject.getAbilities()) {
                if (ability instanceof AdjustingSourceCosts) {
                    ((AdjustingSourceCosts)ability).adjustCosts(this, game);
                }
            }
        }

        // this is a hack to prevent mana abilities with mana costs from causing endless loops - pay other costs first
        if (this instanceof ManaAbility && !costs.pay(this, game, sourceId, controllerId, noMana)) {
            logger.debug("activate mana ability failed - non mana costs");
            return false;
        }

        //20101001 - 601.2e
        if (costModificationActive) {
            game.getContinuousEffects().costModification(this, game);
        } else {
            costModificationActive = true;
        }
        
        UUID activatorId = controllerId;
        if ((this instanceof ActivatedAbilityImpl) && ((ActivatedAbilityImpl)this).getActivatorId()!= null) {
             activatorId = ((ActivatedAbilityImpl)this).getActivatorId();
        }
        
        if (!useAlternativeCost(game)) { // old way still used?

            //20100716 - 601.2f  (noMana is not used here, because mana costs were cleared for this ability before adding additional costs and applying cost modification effects)
            if (!manaCostsToPay.pay(this, game, sourceId, activatorId, false)) {
                return false; // cancel during mana payment
            }
        }

        //20100716 - 601.2g

        if (!costs.pay(this, game, sourceId, activatorId, noMana)) {
            logger.debug("activate failed - non mana costs");
            return false;
        }
        // inform about x costs now, so canceled announcements are not shown in the log
        if (announceString != null) {
            game.informPlayers(announceString);
        }
        if (variableManaCost != null) {
            int xValue = getManaCostsToPay().getX();
            game.informPlayers(new StringBuilder(controller.getName()).append(" announces a value of ").append(xValue).append(" for ").append(variableManaCost.getText()).toString());
        }
        activated = true;
        // fire if tapped for mana (may only fires now because else costs of ability itself can be payed with mana of abilities that trigger for that event
        if (this.getAbilityType().equals(AbilityType.MANA)) {
            for (Cost cost: costs) {
                if (cost instanceof TapSourceCost) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.TAPPED_FOR_MANA, sourceId, sourceId, controllerId));
                    break;
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean isActivated() {
        return activated;
    }

    /**
     * Handles the setting of non mana X costs
     * @param controller    *
     * @param game
     * @return announce message
     *
     */
    protected String handleOtherXCosts(Game game, Player controller) {
        String announceString = null;
        for (VariableCost variableCost : this.costs.getVariableCosts()) {
            if (!(variableCost instanceof VariableManaCost)) {
                int xValue = variableCost.announceXValue(this, game);
                costs.add(variableCost.getFixedCostsFromAnnouncedValue(xValue));
                // set the xcosts to paid
                variableCost.setAmount(xValue);
                ((Cost) variableCost).setPaid();
                String message = new StringBuilder(controller.getName())
                        .append(" announces a value of ").append(xValue).append(" (").append(variableCost.getActionText()).append(")").toString();
                if (announceString == null) {
                    announceString = message;
                } else {
                    announceString = new StringBuilder(announceString).append(" ").append(message).toString();
                }
            }
        }
        return announceString;
    }

    /**
     * Handles X mana costs and sets manaCostsToPay.
     * 
     * @param game
     * @param noMana
     * @param controller
     * @return variableManaCost for posting to log later
     */
    protected VariableManaCost handleManaXCosts(Game game, boolean noMana, Player controller) {
        // 20121001 - 601.2b
        // If the spell has a variable cost that will be paid as it's being cast (such as an {X} in
        // its mana cost; see rule 107.3), the player announces the value of that variable.
        // TODO: Handle announcing other variable costs here like: RemoveVariableCountersSourceCost
        VariableManaCost variableManaCost = null;
        for (ManaCost cost: manaCostsToPay) {
            if (cost instanceof VariableManaCost) {
                variableManaCost = (VariableManaCost) cost;
                break; // only one VariableManCost per spell (or is it possible to have more?)
            }
        }
        if (variableManaCost != null) {
            int xValue;
            if (!variableManaCost.isPaid()) { // should only happen for human players
                if (!noMana) {
                    xValue = controller.announceXMana(variableManaCost.getMinX(), variableManaCost.getMaxX(), "Announce the value for " + variableManaCost.getText(), game, this);
                    int amountMana = xValue * variableManaCost.getMultiplier();
                    StringBuilder manaString = new StringBuilder();
                    if (variableManaCost.getFilter() == null || variableManaCost.getFilter().isColorless()) {
                        manaString.append("{").append(amountMana).append("}");
                    } else {
                        String manaSymbol = null;
                        if (variableManaCost.getFilter().isBlack()) {
                            manaSymbol = "B";
                        } else if (variableManaCost.getFilter().isRed()) {
                            manaSymbol = "R";
                        } else if (variableManaCost.getFilter().isBlue()) {
                            manaSymbol = "U";
                        } else if (variableManaCost.getFilter().isGreen()) {
                            manaSymbol = "G";
                        } else if (variableManaCost.getFilter().isWhite()) {
                            manaSymbol = "W";
                        }
                        if (manaSymbol == null) {
                            throw new UnsupportedOperationException("ManaFilter is not supported: " +this.toString() );
                        }
                        for (int i = 0; i < amountMana; i++) {
                            manaString.append("{").append(manaSymbol).append("}");
                        }
                    }
                    manaCostsToPay.add(new ManaCostsImpl(manaString.toString()));
                    manaCostsToPay.setX(amountMana);
                }
                variableManaCost.setPaid();
            }
        }

        return variableManaCost;
    }

    @Override
    public void reset(Game game) {}

    // Is this still needed?
    protected boolean useAlternativeCost(Game game) {
        for (AlternativeCost cost: alternativeCosts) {
            if (cost.isAvailable(game, this)) {
                if (game.getPlayer(this.controllerId).chooseUse(Outcome.Neutral, "Use alternative cost " + cost.getName(), game)) {
                    return cost.pay(this, game, sourceId, controllerId, false);
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkIfClause(Game game) {
        return true;
    }

    @Override
    public UUID getControllerId() {
        return controllerId;
    }

    @Override
    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }


    @Override
    public UUID getSourceId() {
        return sourceId;
    }

    @Override
    public void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public Costs<Cost> getCosts() {
        return costs;
    }

    @Override
    public ManaCosts<ManaCost> getManaCosts() {
        return manaCosts;
    }

    /**
     * Should be used by {@link mage.abilities.effects.CostModificationEffect cost modification effects}
     * to manipulate what is actually paid before resolution.
     * 
     * @return
     */
    @Override
    public ManaCosts<ManaCost> getManaCostsToPay ( ) {
        return manaCostsToPay;
    }

    @Override
    public List<AlternativeCost> getAlternativeCosts() {
        return alternativeCosts;
    }

    @Override
    public Costs<Cost> getOptionalCosts() {
        return optionalCosts;
    }

    @Override
    public Effects getEffects() {
        return modes.getMode().getEffects();
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        Effects typedEffects = new Effects();
        for (Effect effect: getEffects()) {
            if (effect.getEffectType() == effectType) {
                typedEffects.add(effect);
            }
        }
        return typedEffects;
    }

    @Override
    public Choices getChoices() {
        return modes.getMode().getChoices();
    }

    @Override
    public Zone getZone() {
        return zone;
    }

    @Override
    public boolean isUsesStack() {
        return usesStack;
    }

    @Override
    public String getRule() {
        return getRule(false);
    }

    @Override
    public String getRule(boolean all) {
        StringBuilder sbRule = new StringBuilder();       
        if (all || this.abilityType != AbilityType.SPELL) {
            if (manaCosts.size() > 0) {
                sbRule.append(manaCosts.getText());
            }
            if (costs.size() > 0) {
                if (sbRule.length() > 0) {
                    sbRule.append(",");
                }
                sbRule.append(costs.getText());
            }
            if (sbRule.length() > 0) {
                sbRule.append(": ");
            }
        }
        if (abilityWord != null) {
            sbRule.insert(0, new StringBuilder("<i>").append(abilityWord.toString()).append("</i> - "));
        }         
        String text = modes.getText();
        if (!text.isEmpty()) {
            if (sbRule.length() > 1) {
                String end = sbRule.substring(sbRule.length()-2).trim();
                if (end.isEmpty() || end.equals(":") || end.equals(".")) {
                    sbRule.append(Character.toUpperCase(text.charAt(0))).append(text.substring(1));
                } else {
                    sbRule.append(text);
                }
            } else {
                sbRule.append(text);
            }
        }

        return sbRule.toString();
    }

    @Override
    public String getRule(String source) {
        return formatRule(getRule(), source);
    }

    protected String formatRule(String rule, String source) {
        String replace = rule;
        if (rule != null && source != null && !source.isEmpty()) {
            replace = rule.replace("{this}", source);
            replace = replace.replace("{source}", source);
        }
        return replace;
    }

    @Override
    public void addCost(Cost cost) {
        if (cost != null) {
            if (cost instanceof ManaCost) {
                this.addManaCost((ManaCost)cost);
            }
            else {
                this.costs.add(cost);
            }
        }
    }

    @Override
    public void addManaCost(ManaCost cost) {
        if (cost != null) {
            this.manaCosts.add(cost);
            this.manaCostsToPay.add(cost);
        }
    }

    @Override
    public void addAlternativeCost(AlternativeCost cost) {
        if (cost != null) {
            this.alternativeCosts.add(cost);
        }
    }

    @Override
    public void addOptionalCost(Cost cost) {
        if (cost != null) {
            this.optionalCosts.add(cost);
        }
    }

    @Override
    public void addEffect(Effect effect) {
        if (effect != null) {
            getEffects().add(effect);
        }
    }

    @Override
    public void addTarget(Target target) {
        if (target != null) {
            getTargets().add(target);
        }
    }

    @Override
    public void addChoice(Choice choice) {
        if (choice != null) {
            getChoices().add(choice);
        }
    }

    @Override
    public Targets getTargets() {
        return modes.getMode().getTargets();
    }

    @Override
    public UUID getFirstTarget() {
        return getTargets().getFirstTarget();
    }

    @Override
    public boolean isModal() {
        return this.modes.size() > 1;
    }

    @Override
    public void addMode(Mode mode) {
        this.modes.addMode(mode);
    }

    @Override
    public Modes getModes() {
        return modes;
    }

    @Override
    public boolean canChooseTarget(Game game) {
        for (Mode mode: modes.values()) {
            if (mode.getTargets().canChoose(sourceId, controllerId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, boolean checkLKI) {
        if (zone.equals(Zone.COMMAND)) {
            if (this.getSourceId() == null) { // commander effects
                return true;
            }
            MageObject object = game.getObject(this.getSourceId());
            // emblem are always actual
            if (object != null && object instanceof Emblem) {
                return true;
            }
        }

        // try LKI first
        if (checkLKI) {
            MageObject lkiTest = game.getShortLivingLKI(getSourceId(), zone);
            if (lkiTest != null) {
                return true;
            }
        }

        MageObject object;
        UUID parameterSourceId;
        // for singleton abilities like Flying we can't rely on abilities' source because it's only once in continuous effects
        // so will use the sourceId of the object itself that came as a parameter if it is not null
        if (this instanceof MageSingleton && source != null) {
            object = source;
            parameterSourceId = source.getId();
        } else {
            object = game.getObject(getSourceId());
            parameterSourceId = getSourceId();
        }

        if (object != null && !object.getAbilities().contains(this)) {
            boolean found = false;
            // unfortunately we need to handle double faced cards separately and only this way
            if (object instanceof PermanentCard && ((PermanentCard)object).canTransform()) {
                PermanentCard permanent = (PermanentCard)object;
                found = permanent.getSecondCardFace().getAbilities().contains(this) || permanent.getCard().getAbilities().contains(this);
            }
            if (!found) {
                return false;
            }
        }

        // check against current state
        Zone test = game.getState().getZone(parameterSourceId);
        return test != null && zone.match(test);
    }

    @Override
    public String toString() {
        return getRule();
    }
    
    @Override
    public boolean getRuleAtTheTop() {
        return ruleAtTheTop;
    }

    @Override
    public void setRuleAtTheTop(boolean ruleAtTheTop) {
        this.ruleAtTheTop = ruleAtTheTop;
    }

    @Override
    public boolean getRuleVisible() {
        return ruleVisible;
    }

    @Override
    public void setRuleVisible(boolean ruleVisible) {
        if (!(this instanceof MageSingleton)) { // prevent to change singletons
            this.ruleVisible = ruleVisible;
        }
    }

    @Override
    public boolean getAdditionalCostsRuleVisible() {
        return ruleAdditionalCostsVisible;
    }

    @Override
    public void setAdditionalCostsRuleVisible(boolean ruleAdditionalCostsVisible) {
        this.ruleAdditionalCostsVisible = ruleAdditionalCostsVisible;
    }
    
    @Override
    public UUID getOriginalId() {
        return this.originalId;
    }

    @Override
    public void setAbilityWord(AbilityWord abilityWord) {
       this.abilityWord = abilityWord;
    }

    @Override
    public String getGameLogMessage(Game game) {
        if (game.isSimulation()) {
            return "";
        }
        MageObject object = game.getObject(this.sourceId);
        if (object == null) { // e.g. sacrificed token
            logger.warn("Could get no object: " + this.toString());
        }
        return new StringBuilder(" activates: ")
                .append(object != null ? this.formatRule(modes.getText(), object.getLogName()) :modes.getText())
                .append(" from ")
                .append(getMessageText(game)).toString();
    }

    protected String getMessageText(Game game) {
        StringBuilder sb = new StringBuilder();
        MageObject object = game.getObject(this.sourceId);
        if (object != null) {
            if (object instanceof StackAbility) {
                Card card = game.getCard(((StackAbility) object).getSourceId());
                if (card != null) {
                    sb.append(card.getLogName());
                } else {
                    sb.append(object.getName());
                }
            } else {
                if (object instanceof Spell) {
                    Spell spell = (Spell) object;
                    String castText = spell.getSpellCastText(game);
                    sb.append((castText.startsWith("Cast ") ? castText.substring(5):castText));
                    if (spell.getFromZone() == Zone.GRAVEYARD) {
                        sb.append(" from graveyard");
                    }
                    sb.append(getOptionalTextSuffix(game, spell));
                } else {
                    sb.append(object.getLogName());
                }
            }
        } else {
            sb.append("unknown");
        }
        if (object instanceof Spell && ((Spell) object).getSpellAbilities().size() > 1) {
            if (((Spell) object).getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
                Spell spell = (Spell) object;
                int i = 0;
                for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                    i++;
                    String half;
                    if (i == 1) {
                        half = " left";
                    } else {
                        half = " right";
                    }
                    if (spellAbility.getTargets().size() > 0) {
                        sb.append(half).append(" half targeting ");
                        for (Target target: spellAbility.getTargets()) {
                            sb.append(target.getTargetedName(game));
                        }
                    }
                }
            } else {
                Spell spell = (Spell) object;
                int i = 0;
                for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                    i++;
                    if ( i > 1) {
                        sb.append(" splicing ");
                        if (spellAbility.name.length() > 5 && spellAbility.name.startsWith("Cast ")) {
                            sb.append(spellAbility.name.substring(5));
                        } else {
                            sb.append(spellAbility.name);
                        }
                    }
                    sb.append(getTargetDescriptionForLog(spellAbility.getTargets(), game));
                }
            }
        } else if (object instanceof Spell && ((Spell) object).getSpellAbility().getModes().size() > 1) {
            Modes spellModes = ((Spell) object).getSpellAbility().getModes();
            int item = 0;
            for (Mode mode : spellModes.values()) {
                item++;
                if (spellModes.getSelectedModes().contains(mode.getId())) {
                    spellModes.setMode(mode);
                    sb.append(" (mode ").append(item).append(")");
                    sb.append(getTargetDescriptionForLog(getTargets(), game));
                }
            }
        } else {
            sb.append(getTargetDescriptionForLog(getTargets(), game));
        }
        return sb.toString();
    }

    public String getTargetDescription(Targets targets, Game game) {
        return getTargetDescriptionForLog(targets, game);
    }
    
    protected String getTargetDescriptionForLog(Targets targets, Game game) {
        StringBuilder sb = new StringBuilder();
        if (targets.size() > 0) {
            String usedVerb = null;
            for (Target target : targets) {
                if (!target.getTargets().isEmpty()) {
                    if (!target.isNotTarget()) {
                        if (usedVerb == null || usedVerb.equals(" choosing ")) {
                            usedVerb = " targeting ";
                            sb.append(usedVerb);
                        }
                    } else if (target.isNotTarget() && (usedVerb == null || usedVerb.equals(" targeting "))) {
                        usedVerb = " choosing ";
                        sb.append(usedVerb);
                    }
                    sb.append(target.getTargetedName(game));
                }
            }
        }
        for (Choice choice :this.getChoices()) {
            sb.append(" - ").append(choice.getMessage()).append(": ").append(choice.getChoice());
        }        
        return sb.toString();
    }

    private String getOptionalTextSuffix(Game game, Spell spell) {
        StringBuilder sb = new StringBuilder();
        for (Ability ability : spell.getAbilities()) {
            if (ability instanceof OptionalAdditionalSourceCosts) {
                sb.append(((OptionalAdditionalSourceCosts) ability).getCastMessageSuffix());
            }
            if (ability instanceof AlternativeSourceCosts && ((AlternativeSourceCosts) ability).isActivated(this, game)) {
                sb.append(((AlternativeSourceCosts) ability).getCastMessageSuffix(game));
            }
        }
        return sb.toString();
    }

    @Override
    public void setCostModificationActive(boolean active) {
        this.costModificationActive = active;
    }

    @Override
    public boolean getWorksFaceDown() {
        return worksFaceDown;
    }

    @Override
    public void setWorksFaceDown(boolean worksFaceDown) {
        this.worksFaceDown = worksFaceDown;
    }



}

