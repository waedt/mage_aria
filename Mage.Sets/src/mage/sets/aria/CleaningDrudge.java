package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

public class CleaningDrudge extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchantment");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public CleaningDrudge(UUID ownerId) {
        super(ownerId, 9, "Cleaning Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/udBRvKp.jpg
        When Cleaning Drudge enters the battlefield, you may destroy target enchantment.
        */
    }

    public CleaningDrudge(final CleaningDrudge card) {
        super(card);
    }

    @Override
    public CleaningDrudge copy() {
        return new CleaningDrudge(this);
    }

}

