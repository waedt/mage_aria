package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class CrocodileofRoche extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public CrocodileofRoche(UUID ownerId) {
        super(ownerId, 168, "Crocodile of Roche", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Crocodile");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        // Whenever you cast an instant or sorcery, Crocodile of Roche gains lifelink until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), filter, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/L4ZreiL.jpg
        Whenever you cast an instant or sorcery, Crocodile of Roche gains lifelink until end of turn.
        */
    }

    public CrocodileofRoche(final CrocodileofRoche card) {
        super(card);
    }

    @Override
    public CrocodileofRoche copy() {
        return new CrocodileofRoche(this);
    }

}

