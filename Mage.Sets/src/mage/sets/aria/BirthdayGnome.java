package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

public class BirthdayGnome extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public BirthdayGnome(UUID ownerId) {
        super(ownerId, 207, "Birthday Gnome", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{0}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Gnome");
        this.subtype.add("Construct");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever you cast an instant or sorcery, sacrifice Birthday Gnome and flip a coin. If it comes up heads, Birthday Gnome deals 4 damage to target creature or player.
        Ability ability = new SpellCastControllerTriggeredAbility(new BirthdayGnomeEffect(), filter, false);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/wULsPvR.jpg
        Whenever you cast an instant or sorcery, sacrifice Birthday Gnome and flip a coin. If it comes up heads, Birthday Gnome deals 4 damage to target creature or player.
        */
    }

    public BirthdayGnome(final BirthdayGnome card) {
        super(card);
    }

    @Override
    public BirthdayGnome copy() {
        return new BirthdayGnome(this);
    }

}

class BirthdayGnomeEffect extends OneShotEffect {

    public BirthdayGnomeEffect() {
        super(Outcome.Damage);
        this.staticText = "sacrifice Birthday Gnome and flip a coin. If it comes up heads, {this} deals 4 damage to target creature or player.";
    }

    public BirthdayGnomeEffect(final BirthdayGnomeEffect effect) {
        super(effect);
    }

    @Override
    public BirthdayGnomeEffect copy() {
        return new BirthdayGnomeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Permanent gnome = game.getPermanent(source.getSourceId());
        gnome.sacrifice(source.getSourceId(), game);

        if (player.flipCoin(game)) {
            Permanent targetPerm = game.getPermanent(source.getTargets().getFirstTarget());
            if (targetPerm != null) {
                targetPerm.damage(4, source.getSourceId(), game, false, false);
            }

            Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
            if (targetPlayer != null) {
                targetPlayer.damage(4, source.getSourceId(), game, false, false);
            }
        }

        return true;
    }
}
