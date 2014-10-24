package mage.sets.aria;

import java.util.UUID;

import mage.abilities.condition.Condition.ComparisonType;
import mage.abilities.condition.common.SpellsCastThisTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.permanent.ControllerPredicate;

public class IgniteYourSpark extends CardImpl {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public IgniteYourSpark(UUID ownerId) {
        super(ownerId, 137, "Ignite Your Spark", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new WinGameSourceControllerEffect(), new SpellsCastThisTurnCondition(filter, ComparisonType.Equal, 10), "If {this} is the tenth spell you've cast this turn, you win the game."));

        /*
        Card Text:
        ----------
        http://i.imgur.com/hruELrF.jpg
        If Ignite Your Spark is the tenth spell you've cast this turn, you win the game.
        */
    }

    public IgniteYourSpark(final IgniteYourSpark card) {
        super(card);
    }

    @Override
    public IgniteYourSpark copy() {
        return new IgniteYourSpark(this);
    }

}

