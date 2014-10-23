package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

public class GravePile extends CardImpl {

    public GravePile(UUID ownerId) {
        super(ownerId, 96, "Grave Pile", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        // Destroy target creature. Exile all noncreature cards from that creature's controller's graveyard.
        this.getSpellAbility().addEffect(new GravePileEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        /*
        Card Text:
        ----------
        http://i.imgur.com/TjVJT0D.jpg
        Destroy target creature. Exile all noncreature cards from that creature's controller's graveyard.
        */
    }

    public GravePile(final GravePile card) {
        super(card);
    }

    @Override
    public GravePile copy() {
        return new GravePile(this);
    }

}

class GravePileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("noncreature cards");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public GravePileEffect() {
        super(Outcome.Exile);
        this.staticText = "Destroy target creature. Exile all noncreature cards from that creature's controller's graveyard";
    }

    public GravePileEffect(final GravePileEffect other) {
        super(other);
    }

    @Override
    public GravePileEffect copy() {
        return new GravePileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if(target == null) {
            return false;
        }

        Player controller = game.getPlayer(target.getControllerId());
        target.destroy(source.getSourceId(), game, false);

        if(controller == null) {
            return false;
        }

        for(UUID cardId : controller.getGraveyard().copy()) {
            Card card = game.getCard(cardId);
            if(filter.match(card, game)) {
                controller.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.GRAVEYARD);
            }
        }

        return true;

    }

}
