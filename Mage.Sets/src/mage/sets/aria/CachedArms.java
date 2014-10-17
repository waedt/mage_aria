package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.target.common.TargetControlledCreaturePermanent;

public class CachedArms extends CardImpl {

    public CachedArms(UUID ownerId) {
        super(ownerId, 8, "Cached Arms", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Trap");
        this.color.setWhite(true);

        // If a creature with two or less toughness is attacking you, you may pay W rather than pay Cached Arms's mana cost.
        this.getSpellAbility().addAlternativeCost(new CachedArmsAlternativeCost());

        // Target creature you control gets +2/+0 and gains first strike and lifelink until end of turn.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        Effect e = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        e.setText("Target creature you control get +2/+0");
        this.getSpellAbility().addEffect(e);

        e = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        e.setText("and gains first strike");
        this.getSpellAbility().addEffect(e);

        e = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        e.setText("and lifelink until end of turn");
        this.getSpellAbility().addEffect(e);

        /*
        Card Text:
        ----------
        http://i.imgur.com/jrS5SSH.jpg
        If a creature with two or less toughness is attacking you, you may pay W rather than pay Cached Arms's mana cost.
        Target creature you control gets +2/+0 and gains first strike and lifelink until end of turn.
        */
    }

    public CachedArms(final CachedArms card) {
        super(card);
    }

    @Override
    public CachedArms copy() {
        return new CachedArms(this);
    }

}

class CachedArmsAlternativeCost extends AlternativeCostImpl {

    public CachedArmsAlternativeCost() {
        super("you may pay {W} rather than pay Cached Arms's mana cost.");
        this.add(new ManaCostsImpl("{W}"));
    }

    public CachedArmsAlternativeCost(final CachedArmsAlternativeCost other) {
        super(other);
    }

    @Override
    public CachedArmsAlternativeCost copy() {
        return new CachedArmsAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        // Find the group(s) attacking the controller
        for(CombatGroup group : game.getCombat().getGroups()) {
            if(group.getDefenderId().equals(source.getControllerId())) {

                // Check if any have toughness 2 or less
                for(UUID attackerId : group.getAttackers()) {
                    if(game.getPermanent(attackerId).getToughness().getValue() <= 2) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String getText() {
        return "If a creature with two or less toughness is attacking you, you may pay {W} rather than pay Cached Arms's mana cost.";
    }

}
