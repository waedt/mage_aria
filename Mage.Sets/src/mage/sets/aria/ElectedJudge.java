package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.BoostCounter;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class ElectedJudge extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public ElectedJudge(UUID ownerId) {
        super(ownerId, 12, "Elected Judge", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.subtype.add("Knight");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);

        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever you cast an instant or sorcery, put a +1/+1 counter on each creature you control.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersAllEffect(new BoostCounter(1, 1, 1), new FilterControlledCreaturePermanent()), filter, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/LVKHIfD.jpg
        First strike
        Whenever you cast an instant or sorcery, put a +1/+1 counter on each creature you control.
        */
    }

    public ElectedJudge(final ElectedJudge card) {
        super(card);
    }

    @Override
    public ElectedJudge copy() {
        return new ElectedJudge(this);
    }

}

