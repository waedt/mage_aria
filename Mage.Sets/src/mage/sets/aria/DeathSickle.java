// TODO: Test this. It needs to be tested both when its equipped to something
//       and its not.

package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

public class DeathSickle extends CardImpl {

    public DeathSickle(UUID ownerId) {
        super(ownerId, 209, "Death Sickle", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Equipment");

        // Equip 0
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(0)));

        Effect effect = new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);

        // Usually a single static filter is created for every instance of this
        // card. Here however, a different filter is needed for each instance
        // of this card.
        FilterPermanent filter = new FilterPermanent();
        filter.add(new BlockedByOrBlockingAttachedPredicate(this.getId()));

        effect = new GainAbilityAllEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and each creature blocking or blocked by equipped creature has deathtouch.");
        ability.addEffect(effect);

        /*
        Card Text:
        ----------
        http://i.imgur.com/iz1CoIA.jpg
        Equipped creature and each creature blocking or blocked by equipped creature has deathtouch.
        Equip 0
        */
    }

    public DeathSickle(final DeathSickle card) {
        super(card);
    }

    @Override
    public DeathSickle copy() {
        return new DeathSickle(this);
    }

}

class BlockedByOrBlockingAttachedPredicate implements Predicate<Permanent> {

    private UUID equipmentId;

    public BlockedByOrBlockingAttachedPredicate(UUID equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        Permanent equipment = game.getPermanent(equipmentId);
        if(equipment == null) {
            return false;
        }
        UUID attachedId = equipment.getAttachedTo();

        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getBlockers().contains(attachedId) && combatGroup.getAttackers().contains(input.getId())) {
                return true;
            }

            if (combatGroup.getBlockers().contains(input.getId()) && combatGroup.getAttackers().contains(attachedId)) {
                return true;
            }
        }
        return false;
    }
}
