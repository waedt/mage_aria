package mage.sets.aria;

import java.util.ArrayList;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

public class ParanoiatheMirrorDevil extends CardImpl {

    public ParanoiatheMirrorDevil(UUID ownerId) {
        super(ownerId, 64, "Paranoia, the Mirror Devil", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.expansionSetCode = "ARI";

        this.supertype.add("Lengendary");
        this.color.setBlue(true);

        // When Paranoia, the Mirror Devil enters the battlefield and at the beginning of your upkeep, you may put a token onto the battlefield that's a copy of target creature, except its name is Paranoia's Image, it's legendary in addition to its other types and it's a Devil Illusion in addition to its other types.
        Ability ability = new ParanoiaTheMirrorDevilTriggeredAbility(new CreateParanoiaTokenEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/fZzERR1.jpg
        When Paranoia, the Mirror Devil enters the battlefield and at the beginning of your upkeep, you may put a token onto the battlefield that's a copy of target creature, except its name is Paranoia's Image, it's legendary in addition to its other types and it's a Devil Illusion in addition to its other types.
        */
    }

    public ParanoiatheMirrorDevil(final ParanoiatheMirrorDevil card) {
        super(card);
    }

    @Override
    public ParanoiatheMirrorDevil copy() {
        return new ParanoiatheMirrorDevil(this);
    }

}

class ParanoiaTheMirrorDevilTriggeredAbility extends TriggeredAbilityImpl {

    public ParanoiaTheMirrorDevilTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public ParanoiaTheMirrorDevilTriggeredAbility(final ParanoiaTheMirrorDevilTriggeredAbility other) {
        super(other);
    }

    @Override
    public ParanoiaTheMirrorDevilTriggeredAbility copy() {
        return new ParanoiaTheMirrorDevilTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(getSourceId())) {
            return true;
        }

        if(event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            return event.getPlayerId().equals(this.controllerId);
        }

        return false;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield and at the beginning of your upkeep," + super.getRule();
    }
}

class CreateParanoiaTokenEffect extends OneShotEffect {

    public CreateParanoiaTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put a token onto the battlefield that's a copy of target creature, except its name is Paranoia's Image, it's legendary in addition to its other types and it's a Devil Illusion in addition to its other types";
    }

    public CreateParanoiaTokenEffect(final CreateParanoiaTokenEffect other) {
        super(other);
    }

    @Override
    public CreateParanoiaTokenEffect copy() {
        return new CreateParanoiaTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent original = game.getPermanent(targetPointer.getFirst(game, source));
        if(original == null) {
            return false;
        }

        Token token = new ParanoiaToken(original);
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), false, false);
        return true;
    }
}

class ParanoiaToken extends Token {

    public ParanoiaToken(Permanent original) {
        super("Paranoia's Image", "");
        this.color = original.getColor().copy();
        this.subtype = new ArrayList<String>(original.getSubtype());
        this.supertype = new ArrayList<String>(original.getSupertype());
        this.cardType = new ArrayList<CardType>(original.getCardType());

        this.power = original.getPower().copy();
        this.toughness = original.getToughness().copy();


        if(original.getAbilities() != null) {
            this.abilities = original.getAbilities().copy();
            this.abilities.newId();
        }

        if(!this.supertype.contains("Legendary")) {
            this.supertype.add("Legendary");
        }
        if(!this.subtype.contains("Devil")) {
            this.subtype.add("Devil");
        }
        if(!this.subtype.contains("Illusion")) {
            this.subtype.add("Illusion");
        }
    }
}
