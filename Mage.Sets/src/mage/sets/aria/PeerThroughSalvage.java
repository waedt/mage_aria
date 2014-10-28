package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

public class PeerThroughSalvage extends CardImpl {

    public PeerThroughSalvage(UUID ownerId) {
        super(ownerId, 65, "Peer Through Salvage", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        // Reveal the top five cards of your library. You may put an instant or sorcery card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new PeerThroughSalvageEffect());

        /*
        Card Text:
        ----------
        http://i.imgur.com/p2QMeCq.jpg
        Reveal the top five cards of your library. You may put an instant or sorcery card from among them into your hand. Put the rest into your graveyard.
        */
    }

    public PeerThroughSalvage(final PeerThroughSalvage card) {
        super(card);
    }

    @Override
    public PeerThroughSalvage copy() {
        return new PeerThroughSalvage(this);
    }

}

class PeerThroughSalvageEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("instant or sorcery card to put into your hand");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public PeerThroughSalvageEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. You may put an instant or sorcery card from among them into your hand. Put the rest into your graveyard.";
    }

    public PeerThroughSalvageEffect(final PeerThroughSalvageEffect effect) {
        super(effect);
    }

    @Override
    public PeerThroughSalvageEffect copy() {
        return new PeerThroughSalvageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl(Zone.PICK);
        int amount = Math.min(5, player.getLibrary().size());
        if(amount < 1) {
            return true;
        }
        for(int i = 0; i < amount; i++) {
            cards.add(player.getLibrary().removeFromTop(game));
        }
        player.revealCards("Peer Through Salvage", cards, game);

        TargetCard target = new TargetCard(Zone.PICK, filter);
        target.setRequired(false);
        if(player.choose(Outcome.Benefit, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            cards.remove(card);
        }

        while (player.isInGame() && cards.size() > 1) {
            Card card;

            target = new TargetCard(Zone.PICK, new FilterCard("card to put into your graveyard"));
            player.choose(Outcome.Neutral, cards, target, game);
            card = cards.get(target.getFirstTarget(), game);

            if (card != null) {
                cards.remove(card);
                player.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.PICK);
            }
        }

        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            player.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.PICK);
        }

        return true;
    }

}
