package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

public class OwlChronarch extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcory card from your graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public OwlChronarch(UUID ownerId) {
        super(ownerId, 63, "Owl Chronarch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Owl Chronarch enters the battlefield, you may return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/ICDMnq3.jpg
        Flying
        When Owl Chronarch enters the battlefield, you may return target instant or sorcery card from your graveyard to your hand.
        */
    }

    public OwlChronarch(final OwlChronarch card) {
        super(card);
    }

    @Override
    public OwlChronarch copy() {
        return new OwlChronarch(this);
    }

}

