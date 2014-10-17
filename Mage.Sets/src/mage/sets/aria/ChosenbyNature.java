package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.AurafactionAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

public class ChosenbyNature extends CardImpl {

    public ChosenbyNature(UUID ownerId) {
        super(ownerId, 166, "Chosen by Nature", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Aura");
        this.color.setGreen(true);


        // Enchant permanent
        Target target = new TargetPermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(target.getTargetName()));

        // Aurafaction
        this.addAbility(new AurafactionAbility());

        String text = "As long as enchanted permanent is a creature, it gets +2/+2.";
        Condition condition = new EnchantedPermanentCardTypeCondition(CardType.CREATURE);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new BoostEnchantedEffect(2, 2), condition, text)));

        text = "As long as enchanted permanent is a land, it has \"at the beginning of your upkeep, you gain 2 life.\"";
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(2), TargetController.YOU, false);
        ContinuousEffect effect = new GainAbilityAttachedEffect(ability, AttachmentType.AURA);
        condition = new EnchantedPermanentCardTypeCondition(CardType.LAND);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect( effect, condition, text)));

        /*
        Card Text:
        ----------
        http://i.imgur.com/78RAoKg.jpg
        Enchant permanent
        Aurafaction
        Enchanted permanent has indestructible.
        As long as enchanted permanent is a creature, it gets +2/+2.
        As long as enchanted permanent is a land, it has "at the beginning of your upkeep, you gain 2 life"
        */
    }

    public ChosenbyNature(final ChosenbyNature card) {
        super(card);
    }

    @Override
    public ChosenbyNature copy() {
        return new ChosenbyNature(this);
    }

}


class EnchantedPermanentCardTypeCondition implements Condition {

    private final FilterPermanent filter = new FilterPermanent();

    public EnchantedPermanentCardTypeCondition(CardType type) {
        filter.add(new CardTypePredicate(type));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                if(filter.match(permanent, source.getSourceId(), enchantment.getControllerId(), game)){
                    return true;
                }
            }
        }
        return false;
    }
}
