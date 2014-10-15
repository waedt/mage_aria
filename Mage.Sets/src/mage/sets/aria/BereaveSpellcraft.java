package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.common.SpellsCastThisTurnCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

public class BereaveSpellcraft extends CardImpl {

    private static FilterSpell filter = new FilterSpell("instant spells an opponent");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public BereaveSpellcraft(UUID ownerId) {
        super(ownerId, 85, "Bereave Spellcraft", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setBlack(true);

        // If an opponent cast an instant spell this turn, you may pay B rather than pay Bereave Spellcraft's mana cost.
        SpellsCastThisTurnCondition cond = new SpellsCastThisTurnCondition(filter);
        cond.setText("If an opponent cast an instant spell this turn");
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{B}"), cond));

        // Target player reveals his or her hand and discards all instant and sorcery cards.
        Effect effect = new RevealHandTargetEffect();
        effect.setText("Target player reveals his or her hand ");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new DiscardInstantsAndSorceriesTargetEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        /*
        Card Text:
        ----------
        http://i.imgur.com/LbNgQOe.jpg
        If an opponent cast an instant spell this turn, you may pay B rather than pay Bereave Spellcraft's mana cost.
        Target player reveals his or her hand and discards all instant and sorcery cards.
        */
    }

    public BereaveSpellcraft(final BereaveSpellcraft card) {
        super(card);
    }

    @Override
    public BereaveSpellcraft copy() {
        return new BereaveSpellcraft(this);
    }

}

/*
class OpponentCastInstantThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        OpponentCastInstantThisTurnWatcher watcher = (OpponentCastInstantThisTurnWatcher) game.getState().getWatchers().get("OpponentCastInstantThisTurnWatcher", source.getSourceId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent cast an instant spell this turn";
    }
}

class OpponentCastInstantThisTurnWatcher extends Watcher {

    private static final FilterSpell filter = new FilterSpell();
    static {
        filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public OpponentCastInstantThisTurnWatcher() {
        super("OpponentCastInstantThisTurnWatcher", WatcherScope.CARD);
    }

    public OpponentCastInstantThisTurnWatcher(final OpponentCastInstantThisTurnWatcher watcher) {
        super(watcher);
    }

    @Override
    public OpponentCastInstantThisTurnWatcher copy() {
        return new OpponentCastInstantThisTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { //no need to check - condition has already occured
            return;
        }
        Player player = game.getPlayer(controllerId);

        if (event.getType() == EventType.SPELL_CAST && player.hasOpponent(event.getPlayerId(), game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, game)) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}
*/

class DiscardInstantsAndSorceriesTargetEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();
    static {
        filter.add(Predicates.or(
            new CardTypePredicate(CardType.INSTANT),
            new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public DiscardInstantsAndSorceriesTargetEffect() {
        super(Outcome.Discard);
        this.staticText = "and discards all instant and sorcery cards.";
    }

    public DiscardInstantsAndSorceriesTargetEffect(final DiscardInstantsAndSorceriesTargetEffect effect) {
        super(effect);
    }

    public DiscardInstantsAndSorceriesTargetEffect copy() {
        return new DiscardInstantsAndSorceriesTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) { 
            return false;
        }

        // Make a copy of the players hand that can be safely iterated over
        // while cards are discard.
        Cards hand = targetPlayer.getHand().copy();
        for (UUID carduuid : hand) {
            Card card = game.getCard(carduuid);
            if (filter.match(card, game)) {
                targetPlayer.discard(card, source, game);
            }
        }

        return true;
    }

}
