package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetCardInYourGraveyard;

public class ArmatheTinkerer extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("Artifact creature");
    static final private FilterCreatureCard filter2 = new FilterCreatureCard("artifact creature with converted mana cost 2 or less from your graveyard");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter2.add(new CardTypePredicate(CardType.ARTIFACT));
        filter2.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 2));
    }

    public ArmatheTinkerer(UUID ownerId) {
        super(ownerId, 3, "Arma the Tinkerer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "ARI";

        this.supertype.add("Lengendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        // When Arma the Tinkerer enters the battlefield, return target artifact creature with converted mana cost 2 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);

        // Artifact creatures you control get +1/+1
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        /*
        Card Text:
        ----------
        http://i.imgur.com/Tk0Ui9n.jpg
        When Arma the Tinkerer enters the battlefield, return target artifact creature with converted mana cost 2 or less from your graveyard to the battlefield.
        Artifact creatures you control get +1/+1
        */
    }

    public ArmatheTinkerer(final ArmatheTinkerer card) {
        super(card);
    }

    @Override
    public ArmatheTinkerer copy() {
        return new ArmatheTinkerer(this);
    }

}

