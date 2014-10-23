// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;

public class DeathbondWitch extends CardImpl {

    public DeathbondWitch(UUID ownerId) {
        super(ownerId, 94, "Deathbond Witch", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        // TODO: How do I implement the "can't be exlied" ability?
        //       One way might be as a replacement effect that simple cancels
        //       any attempt to the card into the exile zone (excepting perhaps
        //       effects that put cards facedown into the exile zone.)
        //       Okay, so what are the questions about this effect?
        //        - What happens when X cards are exiled from the top of the
        //          library and DbW is in the middle of the X?
        //        - What about FallowShade?
        //        - Does the card have to be revealed when it would be exiled
        //          from a hidden information zone?
        //
        //       The first and the last are probably addressable in a
        //       replacement effect; I would just have to be really clever
        //       about it. The FallowShade one is hard though. I don't think
        //       I can actually prevent the ability from being activated. I
        //       might be able to prevent the pump from going off, but that
        //       seems like a poor compromise.

        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.ALL, new DamagePlayersEffect(1, TargetController.OPPONENT), TargetController.YOU, true),
                SourceOnBattelfieldOrGraveyardCondition.getInstance(), 
                "At the beginning of your upkeep, if Deathbond Witch is in your graveyard or on the battlefield, each opponent loses 1 life.");
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/WcZHPkT.jpg
        Deathtouch
        Deathbond Witch can't be exiled from anywhere.
        At the beginning of your upkeep, if Deathbond Witch is in your graveyard or on the battlefield, each opponent loses 1 life.
        */
    }

    public DeathbondWitch(final DeathbondWitch card) {
        super(card);
    }

    @Override
    public DeathbondWitch copy() {
        return new DeathbondWitch(this);
    }

}

// TODO Stolen from FiremaneAngel. Maybe there should be more generalized way
//      to handle this? If Zone could be a C# style enum (with | syntax) this
//      would be trivial and wouldn't require the ConditionalTriggeredAbility
//      at all...
class SourceOnBattelfieldOrGraveyardCondition implements Condition {

    private static final SourceOnBattelfieldOrGraveyardCondition fInstance = new SourceOnBattelfieldOrGraveyardCondition();

    public static SourceOnBattelfieldOrGraveyardCondition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return (game.getState().getZone(source.getSourceId()).equals(Zone.GRAVEYARD) ||
                game.getState().getZone(source.getSourceId()).equals(Zone.BATTLEFIELD));
    }

    @Override
    public String toString() {
        return "if {this} is in your graveyard or on the battlefield";
    }


}
