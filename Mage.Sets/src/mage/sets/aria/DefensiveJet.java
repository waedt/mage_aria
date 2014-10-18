package mage.sets.aria;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

public class DefensiveJet extends CardImpl {

    public DefensiveJet(UUID ownerId) {
        super(ownerId, 50, "Defensive Jet", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        Effect effect = new BoostTargetEffect(-1, 1, Duration.EndOfTurn);
        effect.setText("Target creature gets -1/+1");
        this.getSpellAbility().addEffect(effect);

        effect = new BoostTargetEffect(-1, 1, Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn.");
        this.getSpellAbility().addEffect(effect);

        /*
        Card Text:
        ----------
        http://i.imgur.com/YaeVpvH.jpg
        Target creature gets -1/+1 and gains flying until end of turn.
        */
    }

    public DefensiveJet(final DefensiveJet card) {
        super(card);
    }

    @Override
    public DefensiveJet copy() {
        return new DefensiveJet(this);
    }

}

