package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

public class FallowShade extends CardImpl {

    public FallowShade(UUID ownerId) {
        super(ownerId, 95, "Fallow Shade", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Shade");
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        // Exile a creature card from your graveyard: Fallow Shade gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard()))));

        /*
        Card Text:
        ----------
        http://i.imgur.com/7O7BeOR.jpg
        Exile a creature card from your graveyard: Fallow Shade gets +2/+2 until end of turn.
        */
    }

    public FallowShade(final FallowShade card) {
        super(card);
    }

    @Override
    public FallowShade copy() {
        return new FallowShade(this);
    }

}

