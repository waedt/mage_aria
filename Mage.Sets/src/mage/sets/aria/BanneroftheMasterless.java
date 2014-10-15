package mage.sets.aria;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class BanneroftheMasterless extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant and sorcery card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public BanneroftheMasterless(UUID ownerId) {
        super(ownerId, 125, "Banner of the Masterless", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        // Creatures you control get +1/+0 for each instant and sorcery card in your graveyard.
        Effect effect = new BoostControlledEffect(new CardsInControllerGraveyardCount(), new StaticValue(0), Duration.WhileOnBattlefield);
        effect.setText("Creatures you control get +1/+0 for each instant and sorcery card in your graveyard.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        /*
        Card Text:
        ----------
        http://i.imgur.com/GEiuihl.jpg
        Creatures you control get +1/+0 for each instant and sorcery card in your graveyard.
        */
    }

    public BanneroftheMasterless(final BanneroftheMasterless card) {
        super(card);
    }

    @Override
    public BanneroftheMasterless copy() {
        return new BanneroftheMasterless(this);
    }

}

