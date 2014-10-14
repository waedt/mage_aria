
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.IntCompareCondition;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.watchers.common.SpellsCastThisTurnWatcher;

public class SpellsCastThisTurnCondition extends IntCompareCondition {

    private final FilterSpell filter;
    private String text = null;

    public SpellsCastThisTurnCondition(FilterSpell filter) {
        super(ComparisonType.GreaterThan, 0);
        this.filter = filter;
    }

    public SpellsCastThisTurnCondition(FilterSpell filter, int value) {
        super(ComparisonType.GreaterThan, value);
        this.filter = filter;
    }

    public SpellsCastThisTurnCondition(FilterSpell filter, ComparisonType cmp, int value) {
        super(cmp, value);
        this.filter = filter;
    }

    // TODO: How do I install this watcher?
    public int getInputValue(Game game, Ability source) {
        SpellsCastThisTurnWatcher watcher = (SpellsCastThisTurnWatcher)game.getState().getWatchers().get("SpellsCastThisTurnWatcher");
        return watcher.getSpellsCastThisTurn(filter, source.getControllerId(), game);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        if(text != null)
            return text;

        return new StringBuilder("If the number of ")
                         .append(this.filter.getMessage())
                         .append(" cast this turn is ")
                         .append(super.toString())
                         .toString();
    }

}
