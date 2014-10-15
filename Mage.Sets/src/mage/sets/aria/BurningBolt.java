package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

public class BurningBolt extends CardImpl {

    public BurningBolt(UUID ownerId) {
        super(ownerId, 126, "Burning Bolt", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        // Choose one
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        // Burning Bolt deals 2 damage to target creature;
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // or Burning Bolt deals 3 damage to target player.
        Mode mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(3));
        mode.getTargets().add(new TargetPlayer());
        this.getSpellAbility().getModes().addMode(mode);

        /*
        Card Text:
        ----------
        http://i.imgur.com/cgZyNPf.jpg
        Choose one - Burning Bolt deals 2 damage to target creature; or Burning Bolt deals 3 damage to target player.
        */
    }

    public BurningBolt(final BurningBolt card) {
        super(card);
    }

    @Override
    public BurningBolt copy() {
        return new BurningBolt(this);
    }

}

