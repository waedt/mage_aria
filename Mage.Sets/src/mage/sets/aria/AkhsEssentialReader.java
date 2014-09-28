package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.MageObject;
import mage.players.Player;

public class AkhsEssentialReader extends CardImpl {

    public AkhsEssentialReader(UUID ownerId) {
        super(ownerId, 123, "Akh's Essential Reader", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        // Reveal the top three cards of your library. Put all instant and sorcery cards from among them into your hand. Put the rest of the revealed cards into your graveyard.
        this.getSpellAbility().addEffect(new AkhsEssentialReaderEffect());

        /*
        Card Text:
        ----------
        http://i.imgur.com/GmUbjTr.jpg
        Reveal the top three cards of your library. Put all instant and sorcery cards from among them into your hand. Put the rest of the revealed cards into your graveyard.
        */
    }

    public AkhsEssentialReader(final AkhsEssentialReader card) {
        super(card);
    }

    @Override
    public AkhsEssentialReader copy() {
        return new AkhsEssentialReader(this);
    }

}

class AkhsEssentialReaderEffect extends OneShotEffect {

    public AkhsEssentialReaderEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top three cards of your library. Put all instant and sorcery cards from among them into your hand. Put the rest of the revealed cards into your graveyard.";
    }

    public AkhsEssentialReaderEffect(final AkhsEssentialReaderEffect effect) {
        super(effect);
    }

    @Override
    public AkhsEssentialReaderEffect copy() {
        return new AkhsEssentialReaderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player == null || sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        int count = Math.min(player.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card == null) {
                return false;
            }
            cards.add(card);

            if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
                player.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
            } else {
                player.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
            }
        }

        if (!cards.isEmpty()) {
            player.revealCards(sourceObject.getLogName(), cards, game);
        }

        return true;
    }

}
