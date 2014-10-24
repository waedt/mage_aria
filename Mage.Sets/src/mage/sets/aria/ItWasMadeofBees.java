package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

public class ItWasMadeofBees extends CardImpl {

    public ItWasMadeofBees(UUID ownerId) {
        super(ownerId, 183, "It Was Made of Bees!", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        // As an additional cost to cast It Was Made of Bees! sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent()));

        // Put X 1/1 green Bee Insect creature tokens with flying onto the battlefield, where X is equal to the sacrificed creature's toughness.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeeInsectToken(), new SacrificeCostCreaturesToughness()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/sgv1knQ.jpg
        As an additional cost to cast It Was Made of Bees! sacrifice a creature.
        Put X 1/1 green Bee Insect creature tokens with flying onto the battlefield, where X is equal to the sacrificed creature's toughness.
        */
    }

    public ItWasMadeofBees(final ItWasMadeofBees card) {
        super(card);
    }

    @Override
    public ItWasMadeofBees copy() {
        return new ItWasMadeofBees(this);
    }

}

class BeeInsectToken extends Token {

    public BeeInsectToken() {
        super("Bee Insect", "1/1 green Bee Insect creature with flying token");

        this.setOriginalExpansionSetCode("ARI");
        this.cardType.add(CardType.CREATURE);
        this.color.setGreen(true);
        this.subtype.add("Bee");
        this.subtype.add("Insect");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
    }
}
