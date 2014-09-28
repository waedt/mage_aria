package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Duration;


public class AerolinianGlider extends CardImpl {

    public AerolinianGlider(UUID ownerId) {
        super(ownerId, 43, "Aerolinian Glider", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Homunculus");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        // Whenever Aerolinian Glider attacks, it gains flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/jyKOLik.jpg
        Whenever Aerolinian Glider attacks, it gains flying until end of turn.
        */
    }

    public AerolinianGlider(final AerolinianGlider card) {
        super(card);
    }

    @Override
    public AerolinianGlider copy() {
        return new AerolinianGlider(this);
    }

}

