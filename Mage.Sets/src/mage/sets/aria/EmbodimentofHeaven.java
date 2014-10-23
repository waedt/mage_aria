package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.AurafactionAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

public class EmbodimentofHeaven extends CardImpl {

    public EmbodimentofHeaven(UUID ownerId) {
        super(ownerId, 13, "Embodiment of Heaven", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setWhite(true);

        // Enchant creature
        Target target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(target.getTargetName()));

        // Aurafaction
        this.addAbility(new AurafactionAbility());

        // Enchanted creature gets +4/+4 and has flying, lifelink and vigilance.
        Effect effect = new BoostEnchantedEffect(4, 4);
        effect.setText("Enchanted creature gets +4/+4");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);

        ability.addEffect(new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, "and has flying,"));
        ability.addEffect(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, "lifelink"));
        ability.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, "and vigilance."));

        /*
        Card Text:
        ----------
        http://i.imgur.com/HUpAAZm.jpg
        Enchant creature
        Aurafaction
        Enchanted creature gets +4/+4 and has flying, lifelink and vigilance.
        */
    }

    public EmbodimentofHeaven(final EmbodimentofHeaven card) {
        super(card);
    }

    @Override
    public EmbodimentofHeaven copy() {
        return new EmbodimentofHeaven(this);
    }

}

