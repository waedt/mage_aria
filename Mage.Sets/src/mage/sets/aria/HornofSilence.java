package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousRuleModifiyingEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

public class HornofSilence extends CardImpl {

    public HornofSilence(UUID ownerId) {
        super(ownerId, 210, "Horn of Silence", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Equipment");

        // Whenever equipped creature attacks, defending player can't cast spells this turn.
        this.addAbility(new AttacksAttachedTriggeredAbility(new HornOfSilenceEffect()));

        // Equip 0
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(0)));

        /*
        Card Text:
        ----------
        http://i.imgur.com/8qXvwvs.jpg
        Whenever equipped creature attacks, defending player can't cast spells this turn.
        Equip 0
        */
    }

    public HornofSilence(final HornofSilence card) {
        super(card);
    }

    @Override
    public HornofSilence copy() {
        return new HornofSilence(this);
    }

}

class HornOfSilenceEffect extends ContinuousRuleModifiyingEffectImpl {

    public HornOfSilenceEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "defending player can't cast spells this turn";
    }

    public HornOfSilenceEffect(final HornOfSilenceEffect effect) {
        super(effect);
    }

    @Override
    public HornOfSilenceEffect copy() {
        return new HornOfSilenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL ) {
            Permanent equipment = game.getPermanent(source.getSourceId());
            UUID playerId = game.getCombat().getDefendingPlayerId(equipment.getAttachedTo(), game);
            Player player = game.getPlayer(playerId);
            if (player != null && player.getId().equals(event.getPlayerId())) {
                return true;
            }
        }
        return false;
    }
}
