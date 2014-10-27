// TODO: The handling of sac-ing a creature as an additional cost for casting
//       spell sucks. You get no warning and there doesn't seem to be a way to
//       back out once you're prompted. Is there a way to improve this?

package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetControlledCreaturePermanent;

public class OathExtractor extends CardImpl {

    public OathExtractor(UUID ownerId) {
        super(ownerId, 103, "Oath Extractor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Demon");
        this.subtype.add("Wizard");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Noncreature spells cost an additional "sacrifice a creature" to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OathExtractorEffect()));

        // At the beginning of your upkeep, each player sacrifices a creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeAllEffect(1, new FilterControlledCreaturePermanent("a creature")), TargetController.YOU, false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/REeqKpT.jpg
        Flying
        Noncreature spells cost an additional "sacrifice a creature" to cast.
        At the beginning of your upkeep, each player sacrifices a creature.
        */
    }

    public OathExtractor(final OathExtractor card) {
        super(card);
    }

    @Override
    public OathExtractor copy() {
        return new OathExtractor(this);
    }

}

class OathExtractorEffect extends CostModificationEffectImpl {

    public OathExtractorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.INCREASE_COST);
        this.staticText = "Noncreature spells cost an additional \"sacrifice a creature\" to cast";
    }

    public OathExtractorEffect(final OathExtractorEffect other) {
        super(other);
    }

    @Override
    public OathExtractorEffect copy() {
        return new OathExtractorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        abilityToModify.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if(abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility || abilityToModify instanceof RetraceAbility) {
            Spell spell = game.getStack().getSpell(abilityToModify.getId());
            if(spell == null || spell.getCardType().contains(CardType.CREATURE)) {
                return false;
            }

            return true;
        }

        return false;
    }

}
