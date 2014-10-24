package mage.sets.aria;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetCreaturePermanent;

public class HastyDeath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public HastyDeath(UUID ownerId) {
        super(ownerId, 97, "Hasty Death", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setBlack(true);

        // Hasty Death costs 4 less to cast if it targets a creature with haste.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(4, HastyDeathCondition.getInstance())));

        // Destroy target nonblack creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        /*
        Card Text:
        ----------
        http://i.imgur.com/xothMwa.jpg
        Hasty Death costs 4 less to cast if it targets a creature with haste.
        Destroy target nonblack creature.
        */
    }

    public HastyDeath(final HastyDeath card) {
        super(card);
    }

    @Override
    public HastyDeath copy() {
        return new HastyDeath(this);
    }

}

class HastyDeathCondition implements Condition {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with haste");

    static {
        filter.add(new AbilityPredicate(HasteAbility.class));
    }

    private static final HastyDeathCondition fInstance = new HastyDeathCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell != null && sourceSpell.getStackAbility().getTargets().isChosen()) {

            Permanent targetCreature = game.getPermanent(sourceSpell.getStackAbility().getTargets().getFirstTarget());
            if (targetCreature != null) {
                return filter.match(targetCreature, game);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it targets a creature with haste";
    }

}
