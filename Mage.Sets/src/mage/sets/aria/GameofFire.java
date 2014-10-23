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
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;

public class GameofFire extends CardImpl {

    public GameofFire(UUID ownerId) {
        super(ownerId, 135, "Game of Fire", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        this.getSpellAbility().addEffect(new GameOfFireEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        /*
        Card Text:
        ----------
        http://i.imgur.com/GtgKsYv.jpg
        Reveal cards from the top of your library until you reveal three instants and/or sorceries. Exile them. Target opponent chooses one. That player may cast that card without paying its mana cost. You may cast the other exiled cards without paying their mana costs. Put the revealed cards on the bottom of your library in a random order.
        */
    }

    public GameofFire(final GameofFire card) {
        super(card);
    }

    @Override
    public GameofFire copy() {
        return new GameofFire(this);
    }

}

class GameOfFireEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("instant or sorcery exlied by Game of Fire to cast without paying its mana cost");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public GameOfFireEffect() {
        super(Outcome.PlayForFree);
        staticText = "Reveal cards from the top of your library until you reveal three instants and/or sorceries. Exile them. Target opponent chooses one. That player may cast that card without paying its mana cost. You may cast the other exiled cards without paying their mana costs. Put the revealed cards on the bottom of your library in a random order.";
    }

    public GameOfFireEffect(final GameOfFireEffect effect) {
        super(effect);
    }

    @Override
    public GameOfFireEffect copy() {
        return new GameOfFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        CardsImpl revealedIorS = new CardsImpl();
        CardsImpl revealedOthers = new CardsImpl();
        Library library = controller.getLibrary();

        // Remove cards from the top of the library until either 3 instants or
        // sorceries are found or the library is empty.
        while(revealedIorS.size() < 3 && library.size() > 0) {
            Card card = library.removeFromTop(game);

            if(filter.match(card, game)) {
                revealedIorS.add(card);
            } else {
                revealedOthers.add(card);
            }

        }

        // Reveal all the cards removed from library
        CardsImpl union = new CardsImpl(revealedIorS);
        union.addAll(revealedOthers);
        controller.revealCards("Game of Fire", union, game);
        union = null;

        // Exile the instant or sorceries.
        for(UUID cardId : revealedIorS) {
            Card card = game.getCard(cardId);
            card.moveToExile(source.getSourceId(), "Game of Fire", source.getSourceId(), game);
        }

        Player opponent = game.getPlayer(source.getFirstTarget());

        // The opponent chooses a card from exile
        TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
        target.setRequired(false);
        if(opponent != null && opponent.isInGame() && opponent.choose(Outcome.PlayForFree, game.getExile().getExileZone(source.getSourceId()), target, game)) {

            Card card = game.getCard(target.getFirstTarget());
            game.getExile().removeCard(card, game);
            opponent.cast(card.getSpellAbility(), game, true);
        }

        target.clearChosen();

        // The controller chooses to cast the remaining exlied cards
        while(controller != null && controller.isInGame() && controller.choose(Outcome.PlayForFree, game.getExile().getExileZone(source.getSourceId()), target, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if(card != null) {
                game.getExile().removeCard(card, game);
                controller.cast(card.getSpellAbility(), game, true);
            }
            target.clearChosen();
        }

        // Place the other revealed cards on the bottom of the controller's
        // library in a random order.
        while(revealedOthers.size() > 0) {
            Card card = revealedOthers.getRandom(game);
            if(card != null) {
                revealedOthers.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
        }

        return true;
    }
}
