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
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

public class PromiseofFlight extends CardImpl {

    public PromiseofFlight(UUID ownerId) {
        super(ownerId, 67, "Promise of Flight", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlue(true);

        // Enchant creature
        Target target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(target.getTargetName()));

        // Aurafaction
        this.addAbility(new AurafactionAbility());

        // Enchanted creature gets +1/+1 and has flying.
        Effect effect = new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);

        effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, "and has flying.");
        ability.addEffect(effect);
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/CsnEZAy.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature gets +1/+1 and gains flying.
        */
    }

    public PromiseofFlight(final PromiseofFlight card) {
        super(card);
    }

    @Override
    public PromiseofFlight copy() {
        return new PromiseofFlight(this);
    }

}

