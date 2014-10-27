package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

public class MastersMagnificence extends CardImpl {

    private static final FilterCreaturePermanent creatureFilter = new FilterCreaturePermanent("White creatures");
    private static final FilterPermanent enchantmentFilter = new FilterPermanent("enchantment you control");

    static {
        creatureFilter.add(new ColorPredicate(ObjectColor.WHITE));
        enchantmentFilter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        enchantmentFilter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MastersMagnificence(UUID ownerId) {
        super(ownerId, 24, "Master's Magnificence", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");
        this.expansionSetCode = "ARI";

        this.color.setWhite(true);

        // White creatures you control get +1/+1 for each enchantment you control.
        DynamicValue count = new PermanentsOnBattlefieldCount(enchantmentFilter);
        Effect effect = new BoostControlledEffect(count, count, Duration.WhileOnBattlefield, creatureFilter, false);
        effect.setText("White creatures you control get +1/+1 for each enchantment you control.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        /*
        Card Text:
        ----------
        http://i.imgur.com/PJrjxdt.jpg
        White creatures you control get +1/+1 for each enchantment you control.
        */
    }

    public MastersMagnificence(final MastersMagnificence card) {
        super(card);
    }

    @Override
    public MastersMagnificence copy() {
        return new MastersMagnificence(this);
    }

}

