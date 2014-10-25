package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.SquirrelToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

public class LuckStone extends CardImpl {

    public LuckStone(UUID ownerId) {
        super(ownerId, 212, "Luck Stone", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LuckStoneEffect(), new ManaCostsImpl("{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/cGoZOoG.jpg
        G, Sacrifice Luck Stone: Reveal the top card of your library. If it is a creature card, put a 1/1 green Squirrel creature token onto the battlefield and you gain 3 life.
        */
    }

    public LuckStone(final LuckStone card) {
        super(card);
    }

    @Override
    public LuckStone copy() {
        return new LuckStone(this);
    }

}

class LuckStoneEffect extends OneShotEffect {

    public LuckStoneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top card of your library. If it is a creature card, put a 1/1 green Squirrel creature token onto the battlefield and you gain 3 life.";
    }

    public LuckStoneEffect(final LuckStoneEffect other) {
        super(other);
    }

    @Override
    public LuckStoneEffect copy() {
        return new LuckStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player == null || player.getLibrary().size() == 0) {
            return false;
        }

        Card card = player.getLibrary().getTopCards(game, 1).get(0);
        player.revealCards("Luck Stone", new CardsImpl(card), game, true);
        if(card.getCardType().contains(CardType.CREATURE)) {

            Token token = new SquirrelToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());

            player.gainLife(3, game);
        }

        return true;
    }
}
