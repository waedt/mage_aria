package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

public class AriVengeanceofAcco extends CardImpl {

    public AriVengeanceofAcco(UUID ownerId) {
        super(ownerId, 206, "Ari, Vengeance of Acco", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{G}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Ari");
        this.color.setWhite(true);
        this.color.setGreen(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(2)), false));


        // 0: Until end of turn, Ari becomes a 2/2 Spirit creature that has indestructible and "whenever this creature deals damage, put that many loyalty counters on it." Prevent all damage that would be dealt to it this turn. It's still a planeswalker.
        Ability ability1 = new LoyaltyAbility(new BecomesCreatureSourceEffect(new AriVengeanceofAccoToken(), "planeswalker", Duration.EndOfTurn), 0);
        ability1.addEffect(new PreventAllDamageToSourceEffect(Duration.EndOfTurn));
        this.addAbility(ability1);

        // -X: Target creature gets +X/+X and gains lifelink and indestructible until end of turn.
        Effects effects = new Effects();
        Effect effect = new BoostTargetEffect(new AriVengeanceofAccoXCost(), new AriVengeanceofAccoXCost(), Duration.EndOfTurn);
        effect.setText("Target creature gets +X/+X");
        effects.add(effect);

        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains lifelink");
        effects.add(effect);

        effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and indestructible until end of turn");
        effects.add(effect);

        LoyaltyAbility ability = new LoyaltyAbility(effects);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -8: You get an emblem with "attacking creatures you control get +1/+1 for each other attacking creature."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new AriVengeanceofAccoEmblem()), -8));

        /*
        Card Text:
        ----------
        http://i.imgur.com/owJ1m8m.jpg
        
        0: Until end of turn, Ari becomes a 2/2 Spirit creature that has indestructible and "whenever this creature deals damage, put that many loyalty counters on it." Prevent all damage that would be dealt to it this turn. It's still a planeswalker.
        -X: Target creature gets +X/+X and gains lifelink and indestructible until end of turn.
        -8: You get an emblem with "attacking creatures you control get +1/+1 for each other attacking creature."
        
        */
    }

    public AriVengeanceofAcco(final AriVengeanceofAcco card) {
        super(card);
    }

    @Override
    public AriVengeanceofAcco copy() {
        return new AriVengeanceofAcco(this);
    }

}

class AriVengeanceofAccoToken extends Token {

    public AriVengeanceofAccoToken() {
        super("", "2/2 Spirit creature that has indestructible and \"whenever this creature deals damage, put that many loyalty counters on it\"");
        cardType.add(CardType.CREATURE);
        subtype.add("Spirit");
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new DealsDamageGainLoyaltySourceTriggeredAbility());
        this.addAbility(IndestructibleAbility.getInstance());
    }
}

class DealsDamageGainLoyaltySourceTriggeredAbility extends TriggeredAbilityImpl {

    public DealsDamageGainLoyaltySourceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PutThatManyLoyaltyCountersEffect());
    }

    public DealsDamageGainLoyaltySourceTriggeredAbility(final DealsDamageGainLoyaltySourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageGainLoyaltySourceTriggeredAbility copy() {
        return new DealsDamageGainLoyaltySourceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGED_CREATURE)
                || event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGED_PLANESWALKER)) {
            if (event.getSourceId().equals(this.getSourceId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", event.getAmount());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage, " + super.getRule();
    }
}

class PutThatManyLoyaltyCountersEffect extends OneShotEffect {

    public PutThatManyLoyaltyCountersEffect() {
        super(Outcome.Benefit);
        this.staticText = "put that many loyalty counters on it";
    }

    public PutThatManyLoyaltyCountersEffect(final PutThatManyLoyaltyCountersEffect effect) {
        super(effect);
    }

    @Override
    public PutThatManyLoyaltyCountersEffect copy() {
        return new PutThatManyLoyaltyCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int amount = (Integer) getValue("damage");
        Permanent p = game.getPermanent(source.getSourceId());
        if (amount > 0 && p != null) {
            p.addCounters(CounterType.LOYALTY.createInstance(amount), game);
        }
        return true;
    }
}

// TODO: This is bascially copied from Chandra Nalaar. It maybe deserves to be
//       a common DynamicValue?
class AriVengeanceofAccoXCost implements DynamicValue {

    private static final AriVengeanceofAccoXCost defaultValue = new AriVengeanceofAccoXCost();

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int loyaltyPayed = 0;
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                loyaltyPayed = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }
        return loyaltyPayed;
    }

    @Override
    public DynamicValue copy() {
        return defaultValue;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static AriVengeanceofAccoXCost getDefault() {
        return defaultValue;
    }

}

// Attacking creatures you control get +1/+1 for each other attacking creature.
class AriVengeanceofAccoEmblem extends Emblem {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creatures");

    static {
        filter.add(new AttackingPredicate());
    }

    public AriVengeanceofAccoEmblem() {
        this.setName("EMBLEM: Ari, Vengeance of Acco");

        // -1 so that the creature attacking isn't included
        DynamicValue amount = new IntPlusDynamicValue(-1, new AttackingCreatureCount("attacking creature"));

        Effect effect = new BoostControlledEffect(amount, amount, Duration.WhileOnBattlefield, filter, false);
        effect.setText("Attacking creatures you control get +1/+1 for each other attacking creature.");

        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.getAbilities().add(ability);
    }

}
