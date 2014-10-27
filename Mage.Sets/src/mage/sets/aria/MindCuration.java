package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

public class MindCuration extends CardImpl {

    public MindCuration(UUID ownerId) {
        super(ownerId, 60, "Mind Curation", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        // Buyback 2U
        this.addAbility(new BuybackAbility("{2}{U}"));

        this.getSpellAbility().addEffect(new MindCurationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        /*
        Card Text:
        ----------
        http://i.imgur.com/mEgGXMt.jpg
        Buyback 2U
        Reveal the top five cards of your library. For each blue card among them, target player mills 2. Put any number of the revealed cards on the bottom of your library in any order. Then put the rest on top of your library in any order.
        */
    }

    public MindCuration(final MindCuration card) {
        super(card);
    }

    @Override
    public MindCuration copy() {
        return new MindCuration(this);
    }

}

class MindCurationEffect extends OneShotEffect {

    public MindCurationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top five cards of your library. For each blue card among them, target player mills 2. Put any number of the revealed cards on the bottom of your library in any order. Then put the rest on top of your library in any order.";
    }

    public MindCurationEffect(final MindCurationEffect other) {
        super(other);
    }

    @Override
    public MindCurationEffect copy() {
        return new MindCurationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if(controller == null || targetPlayer == null) {
            return false;
        }

        // Reveal the top 5 cards
        int cardsToReveal = Math.min(5, controller.getLibrary().size());
        Cards revealedCards = new CardsImpl(Zone.PICK);
        for (int i = 0; i < cardsToReveal; i++) {
            Card card = controller.getLibrary().removeFromTop(game);
            if (card != null) {
                revealedCards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }

        controller.revealCards("Mind Curation", revealedCards, game);

        // Count the number of blue cards
        int cardsToDiscard = 0;
        for(UUID cardId : revealedCards) {
            Card card = revealedCards.get(cardId, game);
            if(card.getColor().isBlue()) {
                cardsToDiscard++;
            }
        }

        // Target player mills
        cardsToDiscard = Math.min(cardsToDiscard * 2, targetPlayer.getLibrary().size());
        for (int i = 0; i < cardsToDiscard; i++) {
            Card card = targetPlayer.getLibrary().removeFromTop(game);
            if (card != null) {
                targetPlayer.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
            }
        }

        TargetCard targetCard = new TargetCard(Zone.PICK, new FilterCard("card to put on the top of your library (press cancel to select cards to put on bottom)"));
        targetCard.setRequired(false);

        // The controller puts cards onto the top of their library until they
        // cancel (or run out of cards.)
        while(controller.isInGame() && revealedCards.size() > 1) {
            if(!controller.choose(Outcome.Neutral, revealedCards, targetCard, game)) {
                break;
            }
            Card chosenCard = revealedCards.get(targetCard.getFirstTarget(), game);
            if (chosenCard != null) {
                revealedCards.remove(chosenCard);
                chosenCard.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
            targetCard.clearChosen();
        }

        // Put the rest of the cards on the bottom of the library
        controller.putCardsOnBottomOfLibrary(revealedCards, game, source, true);

        return true;
    }
}
