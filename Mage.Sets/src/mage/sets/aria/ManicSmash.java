package mage.sets.aria;

import java.util.UUID;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.common.TargetArtifactPermanent;

public class ManicSmash extends CardImpl {

    public ManicSmash(UUID ownerId) {
        super(ownerId, 141, "Manic Smash", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        /*
        Card Text:
        ----------
        http://i.imgur.com/sIVxiID.jpg
        Destroy target artifact.
        Draw a card.
        */
    }

    public ManicSmash(final ManicSmash card) {
        super(card);
    }

    @Override
    public ManicSmash copy() {
        return new ManicSmash(this);
    }

}

