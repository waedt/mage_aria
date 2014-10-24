package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

public class IdeaRobbery extends CardImpl {

    private static final FilterMana filter = new FilterMana();

    static {
        filter.setBlue(true);
    }

    public IdeaRobbery(UUID ownerId) {
        super(ownerId, 1, "Idea Robbery", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{X}");
        this.expansionSetCode = "ARI";
        this.color.setBlue(true);

        // Spend only blue mana on X (both).
        VariableCost variableCost = this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filter);
        }

        // Gain control of target spell with converted mana cost X. You may choose new targets for it. (If it is a permanent spell, the permanent it becomes enters the battlefield under your control.)
        this.getSpellAbility().addEffect(new IdeaRobberyEffect());
        this.getSpellAbility().addTarget(new TargetSpell(new FilterSpell("spell with converted mana cost X")));

        /*
        Card Text:
        ----------
        http://i.imgur.com/DRJ8mTR.jpg
        Spend only blue mana on X (both).
        Gain control of target spell with converted mana cost X. You may choose new targets for it. (If it is a permanent spell, the permanent it becomes enters the battlefield under your control.)
        */
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            FilterSpell filter = new FilterSpell("spell with converted mana cost X");
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, ability.getManaCostsToPay().getX()));
            ability.addTarget(new TargetSpell(filter));
        }
    }

    public IdeaRobbery(final IdeaRobbery card) {
        super(card);
    }

    @Override
    public IdeaRobbery copy() {
        return new IdeaRobbery(this);
    }

}

class IdeaRobberyEffect extends OneShotEffect {

    public IdeaRobberyEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of target spell with converted mana cost X. You may choose new targets for it. (If it is a permanent spell, the permanent it becomes enters the battlefield under your control.)";
    }

    public IdeaRobberyEffect(final IdeaRobberyEffect other) {
        super(other);
    }

    public IdeaRobberyEffect copy() {
        return new IdeaRobberyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if(spell == null) {
            // XXX Is correct? If the spell can't be targeted or has been
            //     exiled, taking control of it does nothing?
            return false;
        }

        spell.setControllerId(source.getControllerId());
        spell.chooseNewTargets(game, source.getControllerId());

        return false;
    }
}
