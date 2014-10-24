package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class KrewthiMenace extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public KrewthiMenace(UUID ownerId) {
        super(ownerId, 20, "Krewthi Menace", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);

        // Whenever you cast an instant or sorcery, Krewthi Menace gains intimidate until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn), filter, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/eJvYaF1.jpg
        Whenever you cast an instant or sorcery, Krewthi Menace gains intimidate until end of turn.
        */
    }

    public KrewthiMenace(final KrewthiMenace card) {
        super(card);
    }

    @Override
    public KrewthiMenace copy() {
        return new KrewthiMenace(this);
    }

}

