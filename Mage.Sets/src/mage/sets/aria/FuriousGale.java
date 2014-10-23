package mage.sets.aria;

import java.util.UUID;

import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class FuriousGale extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature with flying, each planeswalker");

    static {
        filter.add(
            Predicates.or(
                Predicates.and(new CardTypePredicate(CardType.CREATURE),
                               new AbilityPredicate(FlyingAbility.class)
                ),
                new CardTypePredicate(CardType.PLANESWALKER)
            )
        );
    }

    public FuriousGale(UUID ownerId) {
        super(ownerId, 174, "Furious Gale", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        // Furious Gale deals 1 damage to each creature with flying, each planeswalker and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(1, filter));

        // Storm
        this.addAbility(new StormAbility());

        /*
        Card Text:
        ----------
        http://i.imgur.com/NgQ3cIE.jpg
        Furious Gale deals 1 damage to each creature with flying, each planeswalker and each player.
        Storm (When you cast this spell, copy it for each spell cast before it this turn.)
        */
    }

    public FuriousGale(final FuriousGale card) {
        super(card);
    }

    @Override
    public FuriousGale copy() {
        return new FuriousGale(this);
    }

}

