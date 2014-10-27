package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

public class MasterReanimator extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature card from your graveyard");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public MasterReanimator(UUID ownerId) {
        super(ownerId, 99, "Master Reanimator", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        // When Master Reanimator enters the battlefield, search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MasterReanimatorEffect()));

        // 1B, T: Return target creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/0rf42c1.jpg
        When Master Reanimator enters the battlefield, search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        1B, T: Return target creature card from your graveyard to your hand.
        */
    }

    public MasterReanimator(final MasterReanimator card) {
        super(card);
    }

    @Override
    public MasterReanimator copy() {
        return new MasterReanimator(this);
    }

}

class MasterReanimatorEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature cards");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    private TargetCardInLibrary target = new TargetCardInLibrary(0, 3, filter);

    public MasterReanimatorEffect() {
        super(Outcome.Discard);
        this.staticText = "search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.";
    }


    public MasterReanimatorEffect(final MasterReanimatorEffect other) {
        super(other);
    }

    @Override
    public MasterReanimatorEffect copy() {
        return new MasterReanimatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.searchLibrary(target, game)) {
            for (UUID cardId: target.getTargets()) {
                Card card = player.getLibrary().getCard(cardId, game);
                if (card != null) {
                    player.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                }
            }
            player.shuffleLibrary(game);
            return true;
        }

        player.shuffleLibrary(game);
        return false;
    }
}
