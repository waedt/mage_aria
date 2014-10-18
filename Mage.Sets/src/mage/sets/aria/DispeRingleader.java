package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

public class DispeRingleader extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell you control");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public DispeRingleader(UUID ownerId) {
        super(ownerId, 128, "Dispe Ringleader", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        // Whenever an instant or sorcery spell you control would deal damage, if it is a copy, it deals twice that much damage instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DispeRingleaderEffect()));

        // RR, T: Copy target instant or sorcery you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl("{R}{R}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/9fNkgtx.jpg
        Whenever an instant or sorcery spell you control would deal damage, if it is a copy, it deals twice that much damage instead.
        RR, T: Copy target instant or sorcery you control. You may choose new targets for the copy.
        */
    }

    public DispeRingleader(final DispeRingleader card) {
        super(card);
    }

    @Override
    public DispeRingleader copy() {
        return new DispeRingleader(this);
    }

}

class DispeRingleaderEffect extends ReplacementEffectImpl {

    public DispeRingleaderEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "Whenever an instant or sorcery spell you control would deal damage, if it is a copy, it deals twice that much damage instead.";
    }

    public DispeRingleaderEffect(final DispeRingleaderEffect effect) {
        super(effect);
    }

    @Override
    public DispeRingleaderEffect copy() {
        return new DispeRingleaderEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        StackObject spell = game.getStack().getStackObject(event.getSourceId());
        if (spell == null || !spell.getControllerId().equals(source.getControllerId()) || !spell.isCopy() || !(spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY))) {
            return false;
        }

        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                event.setAmount(event.getAmount() * 2);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

}
