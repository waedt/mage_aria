package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SpellsCastThisTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

public class BogtonFootpad extends CardImpl {

    static private final FilterSpell filter = new FilterSpell();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public BogtonFootpad(UUID ownerId) {
        super(ownerId, 87, "Bogton Footpad", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Goblin");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        String text = "When Bogton Footpad enters the battlefield, if you cast an instant or sorcery this turn, it gains haste until end of turn.";
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(new ConditionalTriggeredAbility(ability, new SpellsCastThisTurnCondition(filter), text));

        /*
        Card Text:
        ----------
        http://i.imgur.com/wc09QP0.jpg
        When Bogton Footpad enters the battlefield, if you cast an instant or sorcery this turn, it gains haste until end of turn.
        */
    }

    public BogtonFootpad(final BogtonFootpad card) {
        super(card);
    }

    @Override
    public BogtonFootpad copy() {
        return new BogtonFootpad(this);
    }

}

