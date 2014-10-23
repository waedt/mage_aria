package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

public class GrandGorilla extends CardImpl {

    private static final FilterCard graveyardFilter = new FilterCard("green creature card from your graveyard");
    private static final FilterCreaturePermanent etbFilter = new FilterCreaturePermanent("another green creature");
    private static final FilterCreaturePermanent opponentFilter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        graveyardFilter.add(new CardTypePredicate(CardType.CREATURE));
        graveyardFilter.add(new ColorPredicate(ObjectColor.GREEN));
        etbFilter.add(new CardTypePredicate(CardType.CREATURE));
        etbFilter.add(new ColorPredicate(ObjectColor.GREEN));
        etbFilter.add(new ControllerPredicate(TargetController.YOU));
        etbFilter.add(new AnotherPredicate());
        opponentFilter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public GrandGorilla(UUID ownerId) {
        super(ownerId, 180, "Grand Gorilla", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Ape");
        this.subtype.add("Giant");
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        this.color.setGreen(true);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another green creature enters the battlefield under your control, it fights target creature an opponent controls.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GrandGorillaEffect(), etbFilter, false, true, null, true);
        ability.addTarget(new TargetCreaturePermanent(opponentFilter));
        this.addAbility(ability);

        // Whenever Grand Gorilla deals combat damage to a player, you may return target green creature card from your graveyard to the battlefield.
        ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(graveyardFilter));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/2i5H1bD.jpg
        Trample
        Whenever another green creature enters the battlefield under your control, it fights target creature an opponent controls.
        Whenever Grand Gorilla deals combat damage to a player, you may return target green creature card from your graveyard to the battlefield.
        */
    }

    public GrandGorilla(final GrandGorilla card) {
        super(card);
    }

    @Override
    public GrandGorilla copy() {
        return new GrandGorilla(this);
    }

}

class GrandGorillaEffect extends OneShotEffect {

    public GrandGorillaEffect() {
        super(Outcome.Damage);
        this.staticText = "it fights target creature an opponent controls.";
    }

    public GrandGorillaEffect(final GrandGorillaEffect other) {
        super(other);
    }

    @Override
    public GrandGorillaEffect copy() {
        return new GrandGorillaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null && target != null && triggeredCreature.getCardType().contains(CardType.CREATURE) && target.getCardType().contains(CardType.CREATURE)) {

            triggeredCreature.damage(target.getPower().getValue(), target.getId(), game, false, true);
            target.damage(triggeredCreature.getPower().getValue(), triggeredCreature.getId(), game, false, true);

            return true;
        }
        return false;
    }
}
