package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

public class GiantFleaBeetle extends CardImpl {

    private static final FilterCreaturePermanent flyingFilter = new FilterCreaturePermanent("creature with flying");
    private static final FilterCreaturePermanent nonFlyingFilter = new FilterCreaturePermanent("creature without flying");

    static {
        flyingFilter.add(new AbilityPredicate(FlyingAbility.class));
        nonFlyingFilter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public GiantFleaBeetle(UUID ownerId) {
        super(ownerId, 177, "Giant Flea Beetle", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Insect");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Giant Flea Beetle attacks, choose one or both; Giant Flea Beetle deals 2 damage to target creature with flying;
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(2), false);
        ability.addTarget(new TargetCreaturePermanent(flyingFilter));
        ability.getModes().setMinModes(1);
        ability.getModes().setMinModes(2);

        // or Giant Flea Beetle deals 2 damage to target creature without flying.
        Mode mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(2));
        mode.getTargets().add(new TargetCreaturePermanent(nonFlyingFilter));
        ability.addMode(mode);

        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/piwtSD9.jpg
        Flying
        Whenever Giant Flea Beetle attacks, choose one or both; Giant Flea Beetle deals 2 damage to target creature with flying; or Giant Flea Beetle deals 2 damage to target creature without flying.
        */
    }

    public GiantFleaBeetle(final GiantFleaBeetle card) {
        super(card);
    }

    @Override
    public GiantFleaBeetle copy() {
        return new GiantFleaBeetle(this);
    }

}

