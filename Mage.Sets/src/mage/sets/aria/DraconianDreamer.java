package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class DraconianDreamer extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public DraconianDreamer(UUID ownerId) {
        super(ownerId, 51, "Draconian Dreamer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery, Draconian Dreamer gets +2/+2 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), filter, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/57vZ7BI.jpg
        Flying
        Whenever you cast an instant or sorcery, Draconian Dreamer gets +2/+2 until end of turn.
        */
    }

    public DraconianDreamer(final DraconianDreamer card) {
        super(card);
    }

    @Override
    public DraconianDreamer copy() {
        return new DraconianDreamer(this);
    }

}

