package mage.sets.aria;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

public class MentalBackflip extends CardImpl {

    public MentalBackflip(UUID ownerId) {
        super(ownerId, 58, "Mental Backflip", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        this.getSpellAbility().addEffect(new MentalBackflipEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        /*
        Card Text:
        ----------
        http://i.imgur.com/pkNcKZn.jpg
        Look at the top five cards of your library and separate them into two face down piles, then name a card other than a basic land. Target opponent chooses a pile and reveals it. If the named card is in the chosen pile, put that pile into your hand. Put the rest of the cards on the bottom of your library in any order.
        */
    }

    public MentalBackflip(final MentalBackflip card) {
        super(card);
    }

    @Override
    public MentalBackflip copy() {
        return new MentalBackflip(this);
    }

}

class MentalBackflipEffect extends OneShotEffect {

    public MentalBackflipEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top five cards of your library and separate them into two face down piles, then name a card other than a basic land. Target opponent chooses a pile and reveals it. If the named card is in the chosen pile, put that pile into your hand. Put the rest of the cards on the bottom of your library in any order.";
    }

    public MentalBackflipEffect(final MentalBackflipEffect other) {
        super(other);
    }

    @Override
    public MentalBackflipEffect copy() {
        return new MentalBackflipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if(controller == null || opponent == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(controller.getLibrary().size(), 5);
        for (int i = 0; i < count; i++) {
            Card card = controller.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }

        // The controller selects which cards go in the piles
        TargetCard target = new TargetCard(0, cards.size(), Zone.PICK, new FilterCard("cards to put in the first pile"));
        target.setRequired(false);
        Cards pile1 = new CardsImpl(Zone.PICK);
        if (controller.choose(Outcome.Neutral, cards, target, game)) {
            List<UUID> targets = target.getTargets();
            for (UUID targetId : targets) {
                Card card = cards.get(targetId, game);
                if (card != null) {
                    pile1.add(card);
                    cards.remove(card);
                }
            }
        }
        game.informPlayers(String.format("%s placed %d cards in Pile 1 and %d cards in Pile 2.", controller.getName(), pile1.size(), cards.size()));

        // The controller names a card
        Choice nameChoice = new ChoiceImpl();
        // XXX This needs to be replaced with a way to get all of the non-basic
        //     land cards. For now, simplying excluding lands should be okay.
        nameChoice.setChoices(CardRepository.instance.getNonLandNames());
        nameChoice.setMessage("Name a non land card");
        controller.choose(Outcome.Detriment, nameChoice, game);
        String cardName = nameChoice.getChoice();
        game.informPlayers(controller.getName() + " named card [" + cardName + "]");

        // The opponent chooses a pile
        Choice pileChoice = new ChoiceImpl();
        pileChoice.setMessage("Select a pile");
        pileChoice.getChoices().add(String.format("Pile 1 (%d cards)", pile1.size()));
        pileChoice.getChoices().add(String.format("Pile 2 (%d cards)", cards.size()));

        boolean chosePile1 = true;
        if(opponent.choose(Outcome.Neutral, pileChoice, game)) {
            chosePile1 = pileChoice.getChoice().startsWith("Pile 1");
        }
        Cards cardsToHand = chosePile1 ? pile1 : cards;
        Cards cardsToLibrary = chosePile1 ? cards : pile1;

        // Reveal the chosen pile
        controller.revealCards("Mental Backflip: Chosen pile", cardsToHand, game);

        boolean successfullyNamedCard = false;
        // Check if the named card is in the chosen pile
        for(UUID cardId : cardsToHand) {
            Card card = cardsToHand.get(cardId, game);
            if(card.getName().equals(cardName)) {
                successfullyNamedCard = true;
                break;
            }
        }

        if(successfullyNamedCard) {
            // If the named card was in the chosen pile, put the chosen pile
            // in the controllers hand
            for(UUID cardId : cardsToHand) {
                Card card = cardsToHand.get(cardId, game);
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
            }
            // and bottom of deck the other pile
            controller.putCardsOnBottomOfLibrary(cardsToLibrary, game, source, true);
        } else {
            // Combine the two piles (so they controller has complete control
            // over the ordering) and then bottom of deck them
            cardsToLibrary.addAll(cardsToHand);
            cardsToHand.clear();
            controller.putCardsOnBottomOfLibrary(cardsToLibrary, game, source, true);
        }

        return true;
    }
}
