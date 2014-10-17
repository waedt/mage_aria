package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

public class CloakinOak extends CardImpl {

    public CloakinOak(UUID ownerId) {
        super(ownerId, 167, "Cloak in Oak", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{G}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+3");
        this.getSpellAbility().addEffect(effect);

        effect = new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains hexproof until end of turn");
        this.getSpellAbility().addEffect(effect);

        /*
        Card Text:
        ----------
        http://i.imgur.com/W6yK275.jpg
        Target creature gets +3/+3 and gains hexproof until end of turn.
        */
    }

    public CloakinOak(final CloakinOak card) {
        super(card);
    }

    @Override
    public CloakinOak copy() {
        return new CloakinOak(this);
    }

}

