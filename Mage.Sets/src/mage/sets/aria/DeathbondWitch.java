package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

public class DeathbondWitch extends CardImpl {

    public DeathbondWitch(UUID ownerId) {
        super(ownerId, 94, "Deathbond Witch", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Deathbond Witch can't be exiled from anywhere.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CantBeExiledEffect()));

        // At the beginning of your upkeep, if Deathbond Witch is in your graveyard or on the battlefield, each opponent loses 1 life.
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

class CantBeExiledEffect extends ReplacementEffectImpl {

    public CantBeExiledEffect() {
        super(Duration.EndOfGame, Outcome.Neutral);
        this.staticText = "{this} can't be exiled from anywhere.";
    }

    public CantBeExiledEffect(final CantBeExiledEffect other) {
        super(other);
    }

    @Override
    public CantBeExiledEffect copy() {
        return new CantBeExiledEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (GameEvent.EventType.ZONE_CHANGE.equals(event.getType())
                && ((ZoneChangeEvent)event).getToZone() == Zone.EXILED
                && event.getTargetId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
