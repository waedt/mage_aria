// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

public class KrewthiAerialist extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public KrewthiAerialist(UUID ownerId) {
        super(ownerId, 19, "Krewthi Aerialist", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Krewthi Aerialist dies, you may tap an untapped creature you control. If you do, return Krewthi Aerialist from your graveyard to your hand.
        this.addAbility(new DiesTriggeredAbility(new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(filter))), false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/SJDsohf.jpg
        Flying
        When Krewthi Aerialist dies, you may tap an untapped creature you control. If you do, return Krewthi Aerialist from your graveyard to your hand.
        */
    }

    public KrewthiAerialist(final KrewthiAerialist card) {
        super(card);
    }

    @Override
    public KrewthiAerialist copy() {
        return new KrewthiAerialist(this);
    }

}

