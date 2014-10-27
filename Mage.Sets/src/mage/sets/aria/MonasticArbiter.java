package mage.sets.aria;

import java.util.List;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifiyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.SpellsCastThisTurnWatcher;

public class MonasticArbiter extends CardImpl {

    public MonasticArbiter(UUID ownerId) {
        super(ownerId, 26, "Monastic Arbiter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        // Each opponent can't cast spells that share a card type with a spell that player cast this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MonasticArbiterEffect()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/RFgPPA3.jpg
        Each opponent can't cast spells that share a card type with a spell that player cast this turn.
        */
    }

    public MonasticArbiter(final MonasticArbiter card) {
        super(card);
    }

    @Override
    public MonasticArbiter copy() {
        return new MonasticArbiter(this);
    }

}

class MonasticArbiterEffect extends ContinuousRuleModifiyingEffectImpl {

    public MonasticArbiterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "Each opponent can't cast spells that share a card type with a spell that player cast this turn.";
    }

    public MonasticArbiterEffect(final MonasticArbiterEffect effect) {
        super(effect);
    }

    @Override
    public MonasticArbiterEffect copy() {
        return new MonasticArbiterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(event.getType() != GameEvent.EventType.CAST_SPELL) {
            return false;
        }

        Ability ability = game.getAbility(event.getTargetId(), event.getSourceId());
        if(ability == null || !(ability instanceof SpellAbility) || source.getControllerId().equals(ability.getControllerId())) {
            return false;
        }
        Card card = game.getCard(ability.getSourceId());
        if(card == null) {
            return false;
        }

        // Build a filter that matches any spell containing the card types of
        // spell being cast.
        FilterSpell filter = new FilterSpell();
        List<CardType> typeList = card.getCardType();
        CardTypePredicate[] predArray = new CardTypePredicate[typeList.size()];
        for(int i = 0; i < predArray.length; i++) {
            predArray[i] = new CardTypePredicate(typeList.get(i));
        }
        filter.add(Predicates.or(predArray));
        filter.add(new ControllerPredicate(TargetController.YOU));

        SpellsCastThisTurnWatcher watcher = (SpellsCastThisTurnWatcher)game.getState().getWatchers().get("SpellsCastThisTurnWatcher");
        return watcher.getSpellsCastThisTurn(filter, ability.getControllerId(), game) > 0;
    }
}
