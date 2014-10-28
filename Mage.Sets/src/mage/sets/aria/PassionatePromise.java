package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.AurafactionAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

public class PassionatePromise extends CardImpl {

    public PassionatePromise(UUID ownerId) {
        super(ownerId, 142, "Passionate Promise", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setRed(true);

        // Enchant creature
        Target target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(target.getTargetName()));

        // Aurafaction
        this.addAbility(new AurafactionAbility());

        // Enchanted creature gets +2/+2 and has haste.
        Effect effect = new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +2/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);

        effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, "and has haste.");
        ability.addEffect(effect);
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/QzBP1nk.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature gets +2/+2 and has haste.
        */
    }

    public PassionatePromise(final PassionatePromise card) {
        super(card);
    }

    @Override
    public PassionatePromise copy() {
        return new PassionatePromise(this);
    }

}

