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

package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class TurnFaceUpAbility extends ActivatedAbilityImpl {

    public TurnFaceUpAbility(Costs<Cost> costs) {
        super(Zone.BATTLEFIELD, new TurnFaceUpEffect(), costs);
        this.usesStack = false;
        this.abilityType = AbilityType.SPECIAL_ACTION;
        this.setRuleVisible(false); // will be made visible only to controller in CardView
    }

    public TurnFaceUpAbility(final TurnFaceUpAbility ability) {
        super(ability);
    }

    @Override
    public TurnFaceUpAbility copy() {
        return new TurnFaceUpAbility(this);
    }
}

class TurnFaceUpEffect extends OneShotEffect {

    public TurnFaceUpEffect() {
        super(Outcome.Benefit);
        this.staticText = "Turn this face-down permanent face up";
    }

    public TurnFaceUpEffect(final TurnFaceUpEffect effect) {
        super(effect);
    }

    @Override
    public TurnFaceUpEffect copy() {
        return new TurnFaceUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller != null && card !=null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                if (sourcePermanent.turnFaceUp(game, source.getControllerId())) {
                    game.informPlayers(controller.getName() + " pays the costs of " + card.getLogName() + " to turn it face up");

                    return true;
                }
            }
        }
        return false;
    }
}
