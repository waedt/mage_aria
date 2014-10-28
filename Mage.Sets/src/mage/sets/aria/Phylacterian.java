package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

public class Phylacterian extends CardImpl {

    public Phylacterian(UUID ownerId) {
        super(ownerId, 106, "Phylacterian", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Skeleton");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);

        // If Phylacterian would be put into your hand from your graveyard, you may put it onto the battlefield tapped instead.
        Effect effect = new ReturnToBattlefieldUnderOwnerControlSourceEffect(true);
        effect.setText("put it onto the battlefield tapped");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new PhylacterianReplacementEffect(effect)));

        // When Phylacterian enters the battlefield, you may sacrifice an artifact. If you do, each opponent sacrifices a creature.
        effect = new DoIfCostPaid(new SacrificeOpponentsEffect(new FilterControlledCreaturePermanent("a creature you control to sacrifice")), new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent("an artifact you control to sacrifice"))));
        effect.setText("you may sacrifice an artifact. If you do, each opponent sacrifices a creature");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        /*
        Card Text:
        ----------
        http://i.imgur.com/29Vm8En.jpg
        If Phylacterian would be put into your hand from your graveyard, you may put it onto the battlefield tapped instead.
        When Phylacterian enters the battlefield, you may sacrifice an artifact. If you do, each opponent sacrifices a creature.
        */
    }

    public Phylacterian(final Phylacterian card) {
        super(card);
    }

    @Override
    public Phylacterian copy() {
        return new Phylacterian(this);
    }

}

class PhylacterianReplacementEffect extends ReplacementEffectImpl {

    public static final String SOURCE_CAST_SPELL_ABILITY = "sourceCastSpellAbility";
    private Effect effect;

    public PhylacterianReplacementEffect(Effect effect) {
        super(Duration.EndOfGame, Outcome.Neutral, true);
        this.effect = effect;
    }

    public PhylacterianReplacementEffect(final PhylacterianReplacementEffect other) {
        super(other);
        this.effect = other.effect;
    }

    @Override
    public PhylacterianReplacementEffect copy() {
        return new PhylacterianReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject object = game.getObject(source.getSourceId());
        if(controller == null || object == null) {
            return false;
        }
        // XXX Hard coding the message here sucks, but what else could be done?
        if(!controller.chooseUse(outcome, "Return {this} to the battlefield tapped?", game)) {
            return false;
        }

        Spell spell = game.getStack().getSpell(event.getSourceId());
        if(effect instanceof ContinuousEffect) {
            game.addEffect((ContinuousEffect) effect, source);
        }
        else {
            if (spell != null) {
                effect.setValue(SOURCE_CAST_SPELL_ABILITY, spell.getSpellAbility());
            }
            effect.apply(game, source);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(GameEvent.EventType.ZONE_CHANGE.equals(event.getType())
                && ((ZoneChangeEvent)event).getToZone() == Zone.HAND
                && ((ZoneChangeEvent)event).getFromZone() == Zone.GRAVEYARD
                && event.getTargetId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "If {this} would be put into your hand from your graveyard, you may " + effect.getText(mode) + " instead";
    }
}
