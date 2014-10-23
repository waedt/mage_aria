package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;

public class FetteredRoc extends CardImpl {

    public FetteredRoc(UUID ownerId) {
        super(ownerId, 14, "Fettered Roc", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setWhite(true);

        // Fettered Roc has flying as long as it is tapped.
        Effect effect = new ConditionalContinousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()), new SourceTappedCondition(), "{this} has flying as long as it is tapped.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        /*
        Card Text:
        ----------
        http://i.imgur.com/DqKYGtS.jpg
        Fettered Roc has flying as long as it is tapped.
        */
    }

    public FetteredRoc(final FetteredRoc card) {
        super(card);
    }

    @Override
    public FetteredRoc copy() {
        return new FetteredRoc(this);
    }

}

