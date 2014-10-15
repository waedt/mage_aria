/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */
package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.common.EmptyEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX
 */

public class KondasBanner extends CardImpl {

    private static final FilterControlledCreaturePermanent legendaryFilter = new FilterControlledCreaturePermanent("Legendary creatures");

    static {
        legendaryFilter.add(new SupertypePredicate("Legendary"));
    }

    public KondasBanner(UUID ownerId) {
        super(ownerId, 259, "Konda's Banner", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        // Konda's Banner can be attached only to a legendary creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EmptyEffect("{this} can be attached only to a legendary creature")));

        // Creatures that share a color with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KondasBannerColorBoostEffect()));

        // Creatures that share a creature type with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KondasBannerTypeBoostEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(
                Outcome.AddAbility,
                new GenericManaCost(2), 
                new TargetControlledCreaturePermanent(1,1, legendaryFilter, false)));

    }

    public KondasBanner(final KondasBanner card) {
        super(card);
    }

    @Override
    public KondasBanner copy() {
        return new KondasBanner(this);
    }        
}

class KondasBannerTypeBoostEffect extends BoostAllEffect  {

    private static final String effectText = "Creatures that share a creature type with equipped creature get +1/+1";

    KondasBannerTypeBoostEffect() {
        super(1,1, Duration.WhileOnBattlefield, new FilterCreaturePermanent(), false);
        staticText = effectText;
    }

    KondasBannerTypeBoostEffect(KondasBannerTypeBoostEffect effect) {
        super(effect);
    }

    @Override
        public boolean apply(Game game, Ability source) {
            // Check if the equipment is attached 
            Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null)
            {
                Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
                if (equipedCreature != null) {
                    for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                            if (CardUtil.shareSubtypes(perm, equipedCreature) || perm.getAbilities().contains(ChangelingAbility.getInstance())) {
                                    if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
                                            perm.addPower(power.calculate(game, source, this));
                                            perm.addToughness(toughness.calculate(game, source, this));
                                    }

                            }
                    }
                    return true;
                }
            }
            return false;
        }

    @Override
    public KondasBannerTypeBoostEffect copy() {
        return new KondasBannerTypeBoostEffect(this);
    }

}


class KondasBannerColorBoostEffect extends BoostAllEffect  {

    private static final String effectText = "Creatures that share a color with equipped creature get +1/+1.";

    KondasBannerColorBoostEffect() {
        super(1,1, Duration.WhileOnBattlefield, new FilterCreaturePermanent(), false);
        staticText = effectText;
    }

    KondasBannerColorBoostEffect(KondasBannerColorBoostEffect effect) {
        super(effect);
    }

    @Override
        public boolean apply(Game game, Ability source) {
            // Check if the equipment is attached 
            Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null)
            {
                Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
                for (Permanent perm: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                        if (equipedCreature.getColor().shares(perm.getColor())) {
                        if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
                    perm.addPower(power.calculate(game, source, this));
                    perm.addToughness(toughness.calculate(game, source, this));
                                }

                        }
        }
        return true;
            }
            return false;
        }

    @Override
    public KondasBannerColorBoostEffect copy() {
        return new KondasBannerColorBoostEffect(this);
    }

}
