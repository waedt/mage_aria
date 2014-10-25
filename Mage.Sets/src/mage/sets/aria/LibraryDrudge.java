package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

public class LibraryDrudge extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from your graveyard");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public LibraryDrudge(UUID ownerId) {
        super(ownerId, 56, "Library Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Homunculus");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        // 1U, Sacrifice Library Drudge: Return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{1}{U}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/wBSZu2g.jpg
        1U, Sacrifice Library Drudge: Return target instant or sorcery card from your graveyard to your hand.
        */
    }

    public LibraryDrudge(final LibraryDrudge card) {
        super(card);
    }

    @Override
    public LibraryDrudge copy() {
        return new LibraryDrudge(this);
    }

}

