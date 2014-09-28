package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.target.TargetSpell;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class AbjuretheGarish extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or planeswalker spell");

    static {
        filter.add(Predicates.or(
            new CardTypePredicate(CardType.CREATURE),
            new CardTypePredicate(CardType.PLANESWALKER)
        ));
    }

    public AbjuretheGarish(UUID ownerId) {
        super(ownerId, 42, "Abjure the Garish", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        // Counter target creature or planeswalker spell.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());

        /*
        Card Text:
        ----------
        http://i.imgur.com/g4Kf4oj.jpg
        Counter target creature or planeswalker spell.
        */
    }

    public AbjuretheGarish(final AbjuretheGarish card) {
        super(card);
    }

    @Override
    public AbjuretheGarish copy() {
        return new AbjuretheGarish(this);
    }

}

