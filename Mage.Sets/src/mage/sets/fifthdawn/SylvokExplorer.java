/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.fifthdawn;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class SylvokExplorer extends CardImpl {

    public SylvokExplorer(UUID ownerId) {
        super(ownerId, 93, "Sylvok Explorer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "5DN";
        this.subtype.add("Human");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add to your mana pool one mana of any color that a land an opponent controls could produce.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new SylvokExplorerEffect(), new TapSourceCost()));
    }

    public SylvokExplorer(final SylvokExplorer card) {
        super(card);
    }

    @Override
    public SylvokExplorer copy() {
        return new SylvokExplorer(this);
    }
}

class SylvokExplorerEffect extends ManaEffect {

    private static final FilterPermanent filter = new FilterLandPermanent();
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public SylvokExplorerEffect() {
        super();
        staticText = "Add to your mana pool one mana of any color that a land an opponent controls could produce";
    }

    public SylvokExplorerEffect(final SylvokExplorerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        Mana types = new Mana();
        for (Permanent land : lands) {
            Abilities<ManaAbility> mana = land.getAbilities().getManaAbilities(Zone.BATTLEFIELD);
            for (ManaAbility ability : mana) {
                types.add(ability.getNetMana(game));
            }
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Pick a mana color");
        if (types.getBlack() > 0) {
            choice.getChoices().add("Black");
        }
        if (types.getRed() > 0) {
            choice.getChoices().add("Red");
        }
        if (types.getBlue() > 0) {
            choice.getChoices().add("Blue");
        }
        if (types.getGreen() > 0) {
            choice.getChoices().add("Green");
        }
        if (types.getWhite() > 0) {
            choice.getChoices().add("White");
        }
        if (types.getAny() > 0) {
            choice.getChoices().add("Black");
            choice.getChoices().add("Red");
            choice.getChoices().add("Blue");
            choice.getChoices().add("Green");
            choice.getChoices().add("White");
        }
        if (choice.getChoices().size() > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                player.choose(outcome, choice, game);
            }
            if (choice.getChoice().equals("Black")) {
                player.getManaPool().addMana(Mana.BlackMana, game, source);
            } else if (choice.getChoice().equals("Blue")) {
                player.getManaPool().addMana(Mana.BlueMana, game, source);
            } else if (choice.getChoice().equals("Red")) {
                player.getManaPool().addMana(Mana.RedMana, game, source);
            } else if (choice.getChoice().equals("Green")) {
                player.getManaPool().addMana(Mana.GreenMana, game, source);
            } else if (choice.getChoice().equals("White")) {
                player.getManaPool().addMana(Mana.WhiteMana, game, source);
            }
        }
        return true;
    }

    @Override
    public SylvokExplorerEffect copy() {
        return new SylvokExplorerEffect(this);
    }
}