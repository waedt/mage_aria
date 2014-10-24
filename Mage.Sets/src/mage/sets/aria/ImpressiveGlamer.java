package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

public class ImpressiveGlamer extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public ImpressiveGlamer(UUID ownerId) {
        super(ownerId, 55, "Impressive Glamer", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlue(true);

        // Enchant permanent
        Target target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(target.getTargetName()));

        // Whenever a player casts an instant or sorcery, that player may gain control of enchanted creature.
        this.addAbility(new SpellCastAllTriggeredAbility(new ImpressiveGlamerEffect(), filter, false, true));

        /*
        Card Text:
        ----------
        http://i.imgur.com/ZoVbFj9.jpg
        Enchant creature
        Whenever a player casts an instant or sorcery, that player may gain control of enchanted creature.
        */
    }

    public ImpressiveGlamer(final ImpressiveGlamer card) {
        super(card);
    }

    @Override
    public ImpressiveGlamer copy() {
        return new ImpressiveGlamer(this);
    }

}

class ImpressiveGlamerEffect extends OneShotEffect {

    public ImpressiveGlamerEffect() {
        super(Outcome.GainControl);
        this.staticText = "that player may gain control of enchanted creature.";
    }

    public ImpressiveGlamerEffect(final ImpressiveGlamerEffect other) {
        super(other);
    }

    @Override
    public ImpressiveGlamerEffect copy() {
        return new ImpressiveGlamerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell triggeringSpell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        Player player = game.getPlayer(triggeringSpell.getControllerId());

        Permanent aura = game.getPermanent(source.getSourceId());
        if(aura == null) {
            return false;
        }
        Permanent creature = game.getPermanent(aura.getAttachedTo());
        if(creature == null) {
            return false;
        }

        String msg = "Gain control of " + creature.getName() + "?";
        if(player != null && player.chooseUse(Outcome.GainControl, msg, game)) {
            creature.changeControllerId(player.getId(), game);
            return true;
        }
        return false;
    }
}
