package mage.sets.aria;

import java.util.UUID;

import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DeathofReason extends CardImpl {

    public DeathofReason(UUID ownerId) {
        super(ownerId, 93, "Death of Reason", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        // Target player discards a card.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));

        this.addAbility(new StormAbility());

        /*
        Card Text:
        ----------
        http://i.imgur.com/UDVR8EP.jpg
        Target player discards a card.
        Storm (When you cast this spell, copy it for each spell cast before it this turn. You may choose new targets for the copies.)
        */
    }

    public DeathofReason(final DeathofReason card) {
        super(card);
    }

    @Override
    public DeathofReason copy() {
        return new DeathofReason(this);
    }

}

