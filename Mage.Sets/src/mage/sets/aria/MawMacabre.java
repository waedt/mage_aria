package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

public class MawMacabre extends CardImpl {

    public MawMacabre(UUID ownerId) {
        super(ownerId, 100, "Maw Macabre", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Zombie");
        this.subtype.add("Wurm");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);

        // Sacrifice a creature: Maw Macabre gets +1/+0 and gains intimidate until end of turn.
        Effect effect = new BoostSourceEffect(1, 0, Duration.EndOfTurn);
        effect.setText("Maw Macabre gets +1/+0");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new SacrificeTargetCost(new TargetControlledCreaturePermanent()));

        effect = new GainAbilitySourceEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains intimidate until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/0KpN3gm.jpg
        Sacrifice a creature: Maw Macabre gets +1/+0 and gains intimidate until end of turn.
        */
    }

    public MawMacabre(final MawMacabre card) {
        super(card);
    }

    @Override
    public MawMacabre copy() {
        return new MawMacabre(this);
    }

}

