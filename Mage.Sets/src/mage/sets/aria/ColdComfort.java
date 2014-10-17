package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SkipEnchantedUntapEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

public class ColdComfort extends CardImpl {

    public ColdComfort(UUID ownerId) {
        super(ownerId, 49, "Cold Comfort", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setBlue(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));

        // When Cold Comfort enters the battlefield, tap all creatures enchanted creature's controller controls.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ColdComfortEffect()));

        // Enchanted creature doesn't untap during its controller's untap step.
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipEnchantedUntapEffect()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/Nnk0jZ1.jpg
        Enchant creature
        When Cold Comfort enters the battlefield, tap all creatures enchanted creature's controller controls.
        Enchanted creature doesn't untap during its controller's untap step.
        */
    }

    public ColdComfort(final ColdComfort card) {
        super(card);
    }

    @Override
    public ColdComfort copy() {
        return new ColdComfort(this);
    }

}


class ColdComfortEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public ColdComfortEffect() {
        super(Outcome.Tap);
        this.staticText = "tap all creatures enchanted creature's controller controls.";
    }

    public ColdComfortEffect(final ColdComfortEffect effect) {
        super(effect);
    }

    @Override
    public ColdComfortEffect copy() {
        return new ColdComfortEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        UUID controllerId = permanent.getControllerId();

        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, controllerId, source.getSourceId(), game)) {
            creature.tap(game);
        }
        return true;
    }
}
