package mage.sets.aria;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

public class BasilisksBreath extends CardImpl {

    public BasilisksBreath(UUID ownerId) {
        super(ownerId, 84, "Basilisk's Breath", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        // Buyback 2
        this.addAbility(new BuybackAbility("{2}"));

        // Target creature gains deathtouch and lifelink until end of turn.
        Effect dt = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
        dt.setText("Target creature gains deathtouch");
        Effect ll = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        ll.setText("and lifelink until end of turn.");
        this.getSpellAbility().addEffect(dt);
        this.getSpellAbility().addEffect(ll);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        /*
        Card Text:
        ----------
        http://i.imgur.com/dIJ0Lbg.jpg
        Buyback 2 (You may pay an additional 2 as you cast this spell. If you do, put this card into your hand as it resolves.)
        Target creature gains deathtouch and lifelink until end of turn.
        */
    }

    public BasilisksBreath(final BasilisksBreath card) {
        super(card);
    }

    @Override
    public BasilisksBreath copy() {
        return new BasilisksBreath(this);
    }

}

