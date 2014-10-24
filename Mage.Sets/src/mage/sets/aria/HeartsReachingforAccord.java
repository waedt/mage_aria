package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

public class HeartsReachingforAccord extends CardImpl {

    public HeartsReachingforAccord(UUID ownerId) {
        super(ownerId, 16, "Hearts Reaching for Accord", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        this.getSpellAbility().addEffect(new HeartsReachingForAccordEffect());

        /*
        Card Text:
        ----------
        http://i.imgur.com/1caVK4s.jpg
        Each player with more than four cards in hand discards until he or she has four, then each player with fewer than four cards in hand draws until he or she has four. Each player's life total becomes 10.
        */
    }

    public HeartsReachingforAccord(final HeartsReachingforAccord card) {
        super(card);
    }

    @Override
    public HeartsReachingforAccord copy() {
        return new HeartsReachingforAccord(this);
    }

}

class HeartsReachingForAccordEffect extends OneShotEffect {

    public HeartsReachingForAccordEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each player with more than four cards in hand discards until he or she has four, then each player with fewer than four cards in hand draws until he or she has four. Each player's life total becomes 10";
    }

    public HeartsReachingForAccordEffect(final HeartsReachingForAccordEffect other) {
        super(other);
    }

    @Override
    public HeartsReachingForAccordEffect copy() {
        return new HeartsReachingForAccordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        for(UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if(player == null) {
                continue;
            }

            Cards hand = player.getHand();
            if(hand.size() > 4) {
                player.discard(hand.size() - 4, source, game);
            } else if(hand.size() < 4) {
                player.drawCards(4 - hand.size(), game);
            }

            player.setLife(10, game);
        }

        return true;
    }
}
