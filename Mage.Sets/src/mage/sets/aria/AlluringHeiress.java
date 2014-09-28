package mage.sets.aria;

import java.util.UUID;
import java.util.Deque;
import java.util.LinkedList;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;
import mage.MageObject;
import mage.target.common.TargetCardInHand;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Zone;

public class AlluringHeiress extends CardImpl {

    public AlluringHeiress(UUID ownerId) {
        super(ownerId, 82, "Alluring Heiress", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        // When Alluring Heiress enters the battlefield, each opponent puts a card from his or her hand into his or her library third from the top.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AlluringHeiressEffect()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/8HCmxKI.jpg
        When Alluring Heiress enters the battlefield, each opponent puts a card from his or her hand into his or her library third from the top.
        */
    }

    public AlluringHeiress(final AlluringHeiress card) {
        super(card);
    }

    @Override
    public AlluringHeiress copy() {
        return new AlluringHeiress(this);
    }

}

// TODO: Unexpectedly Absent and Quarry Colossus both have effects that put
//       cards inside the player's library. Maybe refactor that into a method
//       Library objects and push it upstream?
class AlluringHeiressEffect extends OneShotEffect {

    public AlluringHeiressEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent puts a card from his or her hand into his or her library third from the top.";
    }

    public AlluringHeiressEffect(final AlluringHeiressEffect effect) {
        super(effect);
    }

    @Override
    public AlluringHeiressEffect copy() {
        return new AlluringHeiressEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player == null || sourceObject == null) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand();
        for(UUID oppponentId : game.getOpponents(player.getId())) {
            // Have the player pick a card from their hand
            Player oppponent = game.getPlayer(oppponentId);
            target.clearChosen();
            if (!oppponent.choose(Outcome.Detriment, target, source.getSourceId(), game)) {
                // TODO: Confirm this means that there were no valid "targets"
                continue;
            }
            Card cardToBury = game.getCard(target.getFirstTarget());


            // Pop the top cards off the library
            int cardDepth = Math.min(3, oppponent.getLibrary().size());
            Cards cards = new CardsImpl();
            Deque<UUID> cardIds = new LinkedList<>();
            for (int i = 0; i < cardDepth; i++) {
                Card card = oppponent.getLibrary().removeFromTop(game);
                cards.add(card);
                cardIds.push(card.getId());
            }

            cardToBury.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            // TODO: This message sucks. What should actually be printed here?
            game.informPlayers(new StringBuilder(player.getName())
                    .append(" puts a card from ")
                    .append(oppponent.getName())
                    .append("'s hand beneath the top 3 cards of his/her library.")
                .toString()
            );

            // Return cards back to library
            while(!cardIds.isEmpty()) {
                UUID cardId = cardIds.poll();
                Card card = cards.get(cardId, game);
                if (card != null) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
            }

        }

        return true;
    }

}
