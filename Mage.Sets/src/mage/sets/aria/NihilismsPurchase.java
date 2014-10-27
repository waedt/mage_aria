package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

public class NihilismsPurchase extends CardImpl {

    public NihilismsPurchase(UUID ownerId) {
        super(ownerId, 102, "Nihilism's Purchase", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        this.addAbility(new NihilismsPurchaseTriggeredAbility(new NihilismsPurchaseEffect()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/fuh0FWH.jpg
        Whenever a card is put into a player's hand, if that player did not draw it, that player puts a card from his or her hand on top of his or her library.
        */
    }

    public NihilismsPurchase(final NihilismsPurchase card) {
        super(card);
    }

    @Override
    public NihilismsPurchase copy() {
        return new NihilismsPurchase(this);
    }

}

class NihilismsPurchaseTriggeredAbility extends TriggeredAbilityImpl {


    public NihilismsPurchaseTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public NihilismsPurchaseTriggeredAbility(final NihilismsPurchaseTriggeredAbility other) {
        super(other);
    }

    @Override
    public NihilismsPurchaseTriggeredAbility copy() {
        return new NihilismsPurchaseTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // A card moving to the players hand with a null sourceId should be a
        // draw. So, trigger for anything else.
        // XXX If this doesn't work (ie triggers when it shouldn't), go to the
        //     code implementing card draw (MageDrawAction) and create static
        //     UUID to use as the sourceId for card draw.
        if(event.getType() == EventType.ZONE_CHANGE) {
            ZoneChangeEvent zevent = (ZoneChangeEvent)event;
            if(zevent.getToZone() == Zone.HAND && zevent.getSourceId() != null) {

                for(Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(zevent.getPlayerId()));
                }
                return true;

            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a card is put into a player's hand, if that player did not draw it, " + super.getRule();
    }

}

class NihilismsPurchaseEffect extends OneShotEffect {

    public NihilismsPurchaseEffect() {
        super(Outcome.Detriment);
        this.staticText = " that player puts a card from his or her hand on top of his or her library";
    }

    public NihilismsPurchaseEffect(final NihilismsPurchaseEffect other) {
        super(other);
    }

    @Override
    public NihilismsPurchaseEffect copy() {
        return new NihilismsPurchaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));

        TargetCardInHand target = new TargetCardInHand(new FilterCard("card to put on the top of your library"));
        player.choose(Outcome.Detriment, target, source.getSourceId(), game);
        Card card = player.getHand().get(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.HAND, true, false);
        }

        return true;
    }

}
