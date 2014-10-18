package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

public class DauntlessBurglar extends CardImpl {

    public DauntlessBurglar(UUID ownerId) {
        super(ownerId, 92, "Dauntless Burglar", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Spirit");
        this.subtype.add("Rogue");
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        Effect effect = new CantBlockSourceEffect(Duration.WhileOnBattlefield);
        effect.setText("{this} can't block");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);

        effect = new CantBeBlockedByCreaturesSourceEffect(new FilterCreaturePermanent(), Duration.WhileOnBattlefield);
        effect.setText("or be blocked.");
        ability.addEffect(effect);

        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/MASl7cn.jpg
        Dauntless Burglar can't block or be blocked.
        */
    }

    public DauntlessBurglar(final DauntlessBurglar card) {
        super(card);
    }

    @Override
    public DauntlessBurglar copy() {
        return new DauntlessBurglar(this);
    }

}

