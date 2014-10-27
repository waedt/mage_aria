package mage.abilities.keyword;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;


public class AurafactionAbility extends SimpleStaticAbility {
    public AurafactionAbility() {
        super(Zone.STACK, new AurafactionEffect());
    }

    public AurafactionAbility(final AurafactionAbility other) {
        super(other);
    }

    @Override
    public AurafactionAbility copy() {
        return new AurafactionAbility(this);
    }

    @Override
    public String getRule() {
        return "Aurafaction <i>(If this spell would be countered for having no legal target, you may return it to your hand instead.)</i>";
    }

}

class AurafactionEffect extends ReplacementEffectImpl {
    public AurafactionEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
    }

    public AurafactionEffect(final AurafactionEffect other) {
        super(other);
    }

    @Override
    public AurafactionEffect copy() {
        return new AurafactionEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());

        card.moveToZone(Zone.HAND, source.getSourceId(), game, false);

        game.informPlayers(new StringBuilder(controller.getName())
                                     .append(" used Aurafaciton to return ")
                                     .append(card.getName())
                                     .append(" to his/her hand.").toString());
        return true;
    }

    @Override
    public boolean applies(GameEvent event_, Ability source, Game game) {
        // A card moving from the stack to the graveyard without any source
        // creating the effect should be a spell being countered because it has
        // no valid targets.
        if(event_.getType() != EventType.ZONE_CHANGE || event_.getSourceId() != null) {
            return false;
        }

        // Make sure the ability making the replacement is one on the card
        ZoneChangeEvent event = (ZoneChangeEvent)event_;
        if(event.getToZone() != Zone.GRAVEYARD || event.getFromZone() != Zone.STACK || event.getTargetId() != source.getSourceId()) {
            return false;
        }

        Card card = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        return controller.chooseUse(Outcome.Benefit, card.getName() + " has no valid targets. Return it to your hand?", game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

/*
public class AurafactionAbility extends StaticAbility implements MageSingleton {
    private static final AurafactionAbility fINSTANCE = new AurafactionAbility();

    private AurafactionAbility() {
        super(Zone.ALL, null);
    }

    public static AurafactionAbility getInstance() {
        return fINSTANCE;
    }

    @Override
    public AurafactionAbility copy() {
        return fINSTANCE;
    }

    @Override
    public String getRule() {
        return "Aurafaction <i>(If this spell would be countered for having no legal target, you may return it to your hand instead.)</i>";
    }

}

XXX If trying this approach to handling Aurafaction, add this code to
    mage.game.stack.Spell at around line 256

            } else if(this.getAbilities().contains(AurafactionAbility.getInstance())) {
                // The controller of an aura with Aurafaction may choose to
                // return it to their hand if it has no valid targets.
                Player controller = game.getPlayer(this.getControllerId());
                if(controller.chooseUse(Outcome.Benefit, getName() + " has no valid targets. Return it to your hand?")) {
                    card.moveToZone(Zone.HAND, this.id, game, false);
                } else }
                    this.counter(null, game);
                }
                return false;
*/
