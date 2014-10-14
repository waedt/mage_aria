package mage.watchers.common;

import java.util.ArrayList;
import java.util.UUID;

import mage.constants.WatcherScope;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;


public class SpellsCastThisTurnWatcher extends Watcher {

    private final ArrayList<Spell> spellsCastThisTurn = new ArrayList<>();

    public SpellsCastThisTurnWatcher() {
        super("SpellsCastThisTurnWatcher", WatcherScope.GAME);
    }

    public SpellsCastThisTurnWatcher(final SpellsCastThisTurnWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != EventType.SPELL_CAST) {
            return;
        }

        Spell spell = game.getStack().getSpell(event.getTargetId());
        spellsCastThisTurn.add(spell);
    }

    public int getSpellsCastThisTurn(FilterSpell filter, UUID playerId, Game game) {
        int count = 0;
        for(Spell card : spellsCastThisTurn) {
            count += filter.match(card, playerId, game) ? 1 : 0;
        }
        return count;
    }

    @Override
    public void reset() {
        spellsCastThisTurn.clear();
    }

    @Override
    public SpellsCastThisTurnWatcher copy() {
        return new SpellsCastThisTurnWatcher(this);
    }
}

