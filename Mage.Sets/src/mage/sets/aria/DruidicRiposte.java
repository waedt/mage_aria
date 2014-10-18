package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;

public class DruidicRiposte extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature, nonland permanent");

    static {
        filter.add(Predicates.not(Predicates.or(new CardTypePredicate(CardType.LAND),
                                                new CardTypePredicate(CardType.CREATURE)
        )));
    }

    public DruidicRiposte(UUID ownerId) {
        super(ownerId, 169, "Druidic Riposte", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{4}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        // Choose one or both
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Destroy target noncreature, nonland permanent;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // or return target creature card from your graveyard to your hand.
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnFromGraveyardToHandTargetEffect());
        mode.getTargets().add(new TargetCardInGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.getSpellAbility().addMode(mode);

        /*
        Card Text:
        ----------
        http://i.imgur.com/fp2pcCA.jpg
        Choose one or both - Destroy target noncreature, nonland permanent; or return target creature card from your graveyard to your hand.
        */
    }

    public DruidicRiposte(final DruidicRiposte card) {
        super(card);
    }

    @Override
    public DruidicRiposte copy() {
        return new DruidicRiposte(this);
    }

}

