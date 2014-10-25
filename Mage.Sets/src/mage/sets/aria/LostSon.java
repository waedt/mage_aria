package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.TargetPlayer;

public class LostSon extends CardImpl {

    public LostSon(UUID ownerId) {
        super(ownerId, 98, "Lost Son", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Zombie");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/HQztsJl.jpg
        B, Discard Lost Son: Target player loses 1 life.
        */
    }

    public LostSon(final LostSon card) {
        super(card);
    }

    @Override
    public LostSon copy() {
        return new LostSon(this);
    }

}

