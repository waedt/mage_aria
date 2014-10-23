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
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

public class GreatPromise extends CardImpl {

    public GreatPromise(UUID ownerId) {
        super(ownerId, 181, "Great Promise", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setGreen(true);

        // Enchant creature
        Target target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(target.getTargetName()));

        // Aurafaction
        this.addAbility(new AurafactionAbility());

        // Enchanted creature gets +2/+2 and has trample.
        Effect effect = new BoostEnchantedEffect(2, 2);
        effect.setText("Enchanted creature gets +2/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);

        effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has trample");
        ability.addEffect(effect);

        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/8UL3JJG.jpg
        Enchant creatre
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        Enchanted creature gets +2/+2 and has trample.
        */
    }

    public GreatPromise(final GreatPromise card) {
        super(card);
    }

    @Override
    public GreatPromise copy() {
        return new GreatPromise(this);
    }

}

