package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class OathscoffKnight extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public OathscoffKnight(UUID ownerId) {
        super(ownerId, 104, "Oathscoff Knight", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Vampire");
        this.subtype.add("Knight");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        // Whenever you cast an instant or sorcery, Oathscoff Knight gains first strike until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), filter, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/NpGuA8o.jpg
        Whenever you cast an instant or sorcery, Oathscoff Knight gains first strike until end of turn.
        */
    }

    public OathscoffKnight(final OathscoffKnight card) {
        super(card);
    }

    @Override
    public OathscoffKnight copy() {
        return new OathscoffKnight(this);
    }

}

