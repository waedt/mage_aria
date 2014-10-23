package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.condition.common.SpellsCastThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.target.common.TargetCreaturePermanent;

public class Gleamshaper extends CardImpl {

    public Gleamshaper(UUID ownerId) {
        super(ownerId, 15, "Gleamshaper", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        // T: Tap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever the second spell of a turn is cast, untap Gleamshaper.
        // XXX The 1 below is not a typo. The trigger goes off before the count
        //     for the condition is incremented.
        this.addAbility(new ConditionalTriggeredAbility(new SpellCastAllTriggeredAbility(new UntapSourceEffect(), false), new SpellsCastThisTurnCondition(new FilterSpell(), 1), "Whenever the second spell of a turn is cast, untap Gleamshaper."));

        /*
        Card Text:
        ----------
        http://i.imgur.com/0743LlY.jpg
        T: Tap target creature.
        Whenever the second spell of a turn is cast, untap Gleamshaper.
        */
    }

    public Gleamshaper(final Gleamshaper card) {
        super(card);
    }

    @Override
    public Gleamshaper copy() {
        return new Gleamshaper(this);
    }

}

