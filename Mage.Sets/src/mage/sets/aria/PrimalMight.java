package mage.sets.aria;

import java.util.UUID;

import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

public class PrimalMight extends CardImpl {

    public PrimalMight(UUID ownerId) {
        super(ownerId, 189, "Primal Might", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        // Target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Storm
        this.addAbility(new StormAbility());

        /*
        Card Text:
        ----------
        http://i.imgur.com/z0o3Urv.jpg
        Target creature gets +1/+1 until end of turn.
        Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        */
    }

    public PrimalMight(final PrimalMight card) {
        super(card);
    }

    @Override
    public PrimalMight copy() {
        return new PrimalMight(this);
    }

}

