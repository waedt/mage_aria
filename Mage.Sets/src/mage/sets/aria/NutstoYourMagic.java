package mage.sets.aria;

import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SquirrelToken;
import mage.target.common.TargetEnchantmentPermanent;

public class NutstoYourMagic extends CardImpl {

    public NutstoYourMagic(UUID ownerId) {
        super(ownerId, 187, "Nuts to Your Magic", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setGreen(true);

        // If an aura an opponent controls is attached to a creature you control, you may pay G rather than pay Nuts to Your Magic's mana cost.
        this.getSpellAbility().addAlternativeCost(new NutsToYourMagicAlternativeCost());

        // Destroy target enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());

        // Put a 1/1 green Squirrel creature token onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SquirrelToken()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/E4nzKv1.jpg
        If an aura an opponent controls is attached to a creature you control, you may pay G rather than pay Nuts to Your Magic's mana cost.
        Destroy target enchantment. Put a 1/1 green Squirrel creature token onto the battlefield.
        */
    }

    public NutstoYourMagic(final NutstoYourMagic card) {
        super(card);
    }

    @Override
    public NutstoYourMagic copy() {
        return new NutstoYourMagic(this);
    }

}

class NutsToYourMagicAlternativeCost extends AlternativeCostImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public NutsToYourMagicAlternativeCost() {
        super("If an aura an opponent controls is attached to a creature you control, you may pay {G} rather than pay Nuts to Your Magic's mana cost.", new ManaCostsImpl("{G}"));
    }

    public NutsToYourMagicAlternativeCost(final NutsToYourMagicAlternativeCost other) {
        super(other);
    }

    @Override
    public NutsToYourMagicAlternativeCost copy() {
        return new NutsToYourMagicAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());

        for(Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {

            for(UUID attachedId : permanent.getAttachments()) {
                Permanent attached = game.getPermanent(attachedId);
                if(attached != null && opponents.contains(attached.getControllerId())) {
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public String getText() {
        return "If an aura an opponent controls is attached to a creature you control, you may pay {G} rather than pay Nuts to Your Magic's mana cost.";
    }
}
