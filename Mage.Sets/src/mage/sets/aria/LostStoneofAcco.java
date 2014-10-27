package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

public class LostStoneofAcco extends CardImpl {

    public LostStoneofAcco(UUID ownerId) {
        super(ownerId, 211, "Lost Stone of Acco", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        // 1, T: Put the bottom card of your library into your hand, then put Lost Stone of Acco on the bottom of its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LostStoneOfAccoEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/ruDJvNO.jpg
        1, T: Put the bottom card of your library into your hand, then put Lost Stone of Acco on the bottom of its owner's library.
        */
    }

    public LostStoneofAcco(final LostStoneofAcco card) {
        super(card);
    }

    @Override
    public LostStoneofAcco copy() {
        return new LostStoneofAcco(this);
    }

}

class LostStoneOfAccoEffect extends OneShotEffect {

    public LostStoneOfAccoEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Put the bottom card of your library into your hand, then put Lost Stone of Acco on the bottom of its owner's library.";
    }

    public LostStoneOfAccoEffect(final LostStoneOfAccoEffect other) {
        super(other);
    }

    @Override
    public LostStoneOfAccoEffect copy() {
        return new LostStoneOfAccoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null && controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().removeFromBottom(game);
            //ucontroller.putInHand(card, game);
            card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
        }

        Permanent permanent = game.getPermanent(source.getSourceId());
        if(permanent != null) {
            controller.moveCardToLibraryWithInfo(permanent, source.getSourceId(), game, Zone.BATTLEFIELD, false, true);
        }

        return true;
    }
}
