package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

public class PoxParoxysm extends CardImpl {

    public PoxParoxysm(UUID ownerId) {
        super(ownerId, 108, "Pox Paroxysm", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        // Buypack-Pay 1 life, discard a card, sacrifice a creature, and sacrifice a land.
        Ability ability = new BuybackAbility(new PayLifeCost(1));
        ability.addCost(new DiscardCardCost(new FilterCard("a card to discard")));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent())));
        this.addAbility(ability);

        // Target player loses 1 life and discards a card. If the buyback cost was paid, that player sacrifices a creature and sacrifices a land.
        this.getSpellAbility().addEffect(new PoxParoxysmEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());


        /*
        Card Text:
        ----------
        http://i.imgur.com/TLQ6I8O.jpg
        Buypack-Pay 1 life, discard a card, sacrifice a creature, and sacrifice a land.
        Target player loses 1 life and discards a card. If the buyback cost was paid, that player sacrifices a creature and sacrifices a land.
        */
    }

    public PoxParoxysm(final PoxParoxysm card) {
        super(card);
    }

    @Override
    public PoxParoxysm copy() {
        return new PoxParoxysm(this);
    }

}

class PoxParoxysmEffect extends OneShotEffect {

    private static final FilterControlledLandPermanent landFilter = new FilterControlledLandPermanent("land to sacrifice");
    private static final FilterControlledCreaturePermanent creatureFilter = new FilterControlledCreaturePermanent("creature to sacrifice");

    public PoxParoxysmEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player loses 1 life and discards a card. If the buyback cost was paid, that player sacrifices a creature and sacrifices a land.";
    }

    public PoxParoxysmEffect(final PoxParoxysmEffect other) {
        super(other);
    }

    @Override
    public PoxParoxysmEffect copy() {
        return new PoxParoxysmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if(player == null) {
            return false;
        }

        StackObject sobj = game.getStack().getStackObject(source.getId());
        boolean buybackPaid = false;
        if(sobj != null) {
            for(Ability ability : sobj.getAbilities()) {
                if(ability instanceof BuybackAbility && ((BuybackAbility)ability).isActivated()) {
                    buybackPaid = true;
                    break;
                }
            }
        }

        player.loseLife(1, game);
        player.discard(1, source, game);

        if(buybackPaid) {
            // Sac a creature
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, creatureFilter, true);
            if(target.canChoose(player.getId(), game) && player.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if(permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }

            // Sac a land
            target = new TargetControlledPermanent(1, 1, landFilter, true);
            if(target.canChoose(player.getId(), game) && player.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if(permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }

        }

        return true;
    }
}
