package mage.sets.aria;

import java.util.UUID;

import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

public class ImpressiveVista extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with toughness less than the number of Mountains you control");

    static {
        filter.add(new ThoughnessMountainsPredicate());
    }

    public ImpressiveVista(UUID ownerId) {
        super(ownerId, 138, "Impressive Vista", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        // Gain control of target creature with toughness less than the number of Mountains you control.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfGame));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature with toughness less than the number of Mountains you control")));

        /*
        Card Text:
        ----------
        http://i.imgur.com/IZ30X83.jpg
        Gain control of target creature with toughness less than the number of Mountains you control.
        */
    }

    public ImpressiveVista(final ImpressiveVista card) {
        super(card);
    }

    @Override
    public ImpressiveVista copy() {
        return new ImpressiveVista(this);
    }

}

class ThoughnessMountainsPredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {

    public static final FilterLandPermanent filter = new FilterLandPermanent("Mountain");
    static {
        filter.add(new SubtypePredicate("Mountain"));
    }

    @Override
    public boolean apply(ObjectPlayer<Permanent> input, Game game) {
        Permanent permanent = input.getObject();
        if (permanent != null) {
            int mountains = game.getBattlefield().countAll(filter, input.getPlayerId(), game);
            if (permanent.getPower().getValue() < mountains) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "creature with thoughness less than the number of mountains you control";
    }
}
