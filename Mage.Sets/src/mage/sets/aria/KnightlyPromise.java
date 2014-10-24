package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.AurafactionAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.permanent.token.SoldierToken;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

public class KnightlyPromise extends CardImpl {

    public KnightlyPromise(UUID ownerId) {
        super(ownerId, 18, "Knightly Promise", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setWhite(true);

        // Enchant permanent
        Target target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(target.getTargetName()));

        // Aurafaction
        this.addAbility(new AurafactionAbility());

        // When Knightly Promise enters the battlefield, put a 1/1 white Soldier creature token onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierToken())));

        // Enchanted creature gets +1/+1 and has first strike.
        Effect effect = new BoostEnchantedEffect(1, 1);
        effect.setText("Enchanted creature gets +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, "and has first strike"));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/KGBc0hA.jpg
        Enchant creature
        Aurafaction (If this spell would be countered for having no legal target, you may return it to your hand instead.)
        When Knightly Promise enters the battlefield, put a 1/1 white Soldier creature token onto the battlefield.
        Enchanted creature gets +1/+1 and has first strike.
        */
    }

    public KnightlyPromise(final KnightlyPromise card) {
        super(card);
    }

    @Override
    public KnightlyPromise copy() {
        return new KnightlyPromise(this);
    }

}

