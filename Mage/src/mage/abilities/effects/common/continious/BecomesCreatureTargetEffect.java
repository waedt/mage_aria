/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common.continious;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.Target;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BecomesCreatureTargetEffect extends ContinuousEffectImpl {

    protected Token token;
    protected String type;

    /**
     *
     * @param token
     * @param type
     * @param duration
     */
    public BecomesCreatureTargetEffect(Token token, String type, Duration duration) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.type = type;
    }

    public BecomesCreatureTargetEffect(final BecomesCreatureTargetEffect effect) {
        super(effect);
        token = effect.token.copy();
        type = effect.type;
    }

    @Override
    public BecomesCreatureTargetEffect copy() {
        return new BecomesCreatureTargetEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        boolean result = false;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            permanent.getSubtype().clear();
                            if (token.getSubtype().size() > 0) {
                                permanent.getSubtype().addAll(token.getSubtype());
                            }
                            if (type == null) {
                                permanent.getCardType().clear();
                            }
                            if (token.getCardType().size() > 0) {
                                for (CardType t : token.getCardType()) {
                                    if (!permanent.getCardType().contains(t)) {
                                        permanent.getCardType().add(t);
                                    }
                                }
                            }
                        }
                        break;
                    case ColorChangingEffects_5:
                        if (sublayer == SubLayer.NA) {
                            if (token.getColor().hasColor()) {
                                permanent.getColor().setColor(token.getColor());
                            }
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            if (token.getAbilities().size() > 0) {
                                for (Ability ability : token.getAbilities()) {
                                    permanent.addAbility(ability, game);
                                }
                            }
                        }
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getToughness().setValue(token.getToughness().getValue());
                            permanent.getPower().setValue(token.getPower().getValue());
                        }
                }
                result = true;
            }
        }
        if (!result && this.duration.equals(Duration.Custom) ) {
            this.discard();
        }
        return result;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if(target.getMaxNumberOfTargets() > 1){
            if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                sb.append("Up to ");
            }
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName()).append("  each become ");
        } else {
            sb.append("Target ").append(target.getTargetName()).append(" becomes a ");
        }
        sb.append(token.getDescription());
        sb.append(" ").append(duration.toString());
        if (type != null && type.length() > 0) {
            if (target.getMaxNumberOfTargets() > 1) {
                sb.append(". They're still ").append(type);
            } else {
                sb.append(". It's still a ").append(type);
            }
        }
        return sb.toString();
    }

}
