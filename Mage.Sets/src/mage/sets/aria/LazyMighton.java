package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition.ComparisonType;
import mage.abilities.condition.common.SpellsCastThisTurnCondition;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class LazyMighton extends CardImpl {

    public LazyMighton(UUID ownerId) {
        super(ownerId, 21, "Lazy Mighton", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Giant");
        this.subtype.add("Soldier");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);

        // Lazy Mighton can't attack or block unless you've cast an instant or sorcery this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LazyMightonEffect()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/8loRd3l.jpg
        Lazy Mighton can't attack or block unless you've cast an instant or sorcery this turn.
        */
    }

    public LazyMighton(final LazyMighton card) {
        super(card);
    }

    @Override
    public LazyMighton copy() {
        return new LazyMighton(this);
    }

}

class LazyMightonEffect extends RestrictionEffect {

    private static final FilterSpell filter = new FilterSpell();
    private static final SpellsCastThisTurnCondition condition = new SpellsCastThisTurnCondition(filter, ComparisonType.GreaterThan, 0);

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)
        ));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public LazyMightonEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless you've cast an instant or sorcery this turn";
    }

    public LazyMightonEffect(final LazyMightonEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(UUID defenderId, Ability source, Game game) {
        return condition.apply(game, source);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return condition.apply(game, source);
    }

    @Override
    public LazyMightonEffect copy() {
        return new LazyMightonEffect(this);
    }

}
