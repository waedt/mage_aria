package mage.sets.aria;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

public class BlackMark extends CardImpl {

    public BlackMark(UUID ownerId) {
        super(ownerId, 86, "Black Mark", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlack(true);

        // Enchant creature
        Target target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(target.getTargetName()));
        // Enchanted creature gets -4/-4
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(-4, -4, Duration.WhileOnBattlefield)));

        /*
        Card Text:
        ----------
        http://i.imgur.com/owZ1jA0.jpg
        Enchant creature
        Enchanted creature gets -4/-4
        */
    }

    public BlackMark(final BlackMark card) {
        super(card);
    }

    @Override
    public BlackMark copy() {
        return new BlackMark(this);
    }

}

