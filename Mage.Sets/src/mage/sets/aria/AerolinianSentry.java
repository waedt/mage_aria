package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Duration;

public class AerolinianSentry extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public AerolinianSentry(UUID ownerId) {
        super(ownerId, 44, "Aerolinian Sentry", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Homunculus");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);

        // Whenever you cast an instant or sorcery, Aerolinian Sentry gains vigilance until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), filter, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/XOnJ4FB.jpg
        Whenever you cast an instant or sorcery, Aerolinian Sentry gains vigilance until end of turn.
        */
    }

    public AerolinianSentry(final AerolinianSentry card) {
        super(card);
    }

    @Override
    public AerolinianSentry copy() {
        return new AerolinianSentry(this);
    }

}

