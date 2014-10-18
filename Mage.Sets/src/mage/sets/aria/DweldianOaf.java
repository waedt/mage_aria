package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class DweldianOaf extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public DweldianOaf(UUID ownerId) {
        super(ownerId, 130, "Dweldian Oaf", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Giant");
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        // Whenever you cast an instant or sorcery, Dweldian Oaf gains trample until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), filter, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/FsAgAFn.jpg
        Whenever you cast an instant or sorcery, Dweldian Oaf gains trample until end of turn.
        */
    }

    public DweldianOaf(final DweldianOaf card) {
        super(card);
    }

    @Override
    public DweldianOaf copy() {
        return new DweldianOaf(this);
    }

}

