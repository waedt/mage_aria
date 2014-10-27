package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;

public class MenacedbySkeletons extends CardImpl {

    public MenacedbySkeletons(UUID ownerId) {
        super(ownerId, 101, "Menaced by Skeletons", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.color.setBlack(true);

        // Whenever a creature an opponent controls attacks, you may pay 1B. If you do, put a 1/1 black Skeleton creature token onto the battlefield.
        this.addAbility(new MenacedbySkeletonsTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new SkeletonToken()), new ManaCostsImpl("{1}{B}"))));

        /*
        Card Text:
        ----------
        http://i.imgur.com/fjA20fF.jpg
        Whenever a creature an opponent controls attacks, you may pay 1B. If you do, put a 1/1 black Skeleton creature token onto the battlefield.
        */
    }

    public MenacedbySkeletons(final MenacedbySkeletons card) {
        super(card);
    }

    @Override
    public MenacedbySkeletons copy() {
        return new MenacedbySkeletons(this);
    }

}

class SkeletonToken extends Token {

    public SkeletonToken() {
        super("Skeleton", "1/1 black Skeleton creature token");
        this.setOriginalExpansionSetCode("ARI");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Skeleton");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }
}

class MenacedbySkeletonsTriggeredAbility extends TriggeredAbilityImpl {

    public MenacedbySkeletonsTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public MenacedbySkeletonsTriggeredAbility(final MenacedbySkeletonsTriggeredAbility other) {
        super(other);
    }

    @Override
    public TriggeredAbility copy() {
        return new MenacedbySkeletonsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            if(game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls attacks, " + super.getRule();
    }
}
