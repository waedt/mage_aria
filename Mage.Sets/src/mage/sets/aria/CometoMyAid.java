package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.condition.common.OpponentControllsMoreCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.SoldierToken;

public class CometoMyAid extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public CometoMyAid(UUID ownerId) {
        super(ownerId, 10, "Come to My Aid!", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setWhite(true);

        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{W}"), new OpponentControllsMoreCondition(filter), "If an opponent controls more creatures than you, you may pay {W} rather than pay Come to My Aid!'s mana cost."));

        // Put three 1/1 white Soldier creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierToken(), 3));

        /*
        Card Text:
        ----------
        http://i.imgur.com/zVpR05n.jpg
        If an opponent controls more creatures than you, you may pay W rather than pay Come to My Aid!'s mana cost.
        Put three 1/1 white Soldier creature tokens onto the battlefield.
        */
    }

    public CometoMyAid(final CometoMyAid card) {
        super(card);
    }

    @Override
    public CometoMyAid copy() {
        return new CometoMyAid(this);
    }

}

