package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BecomesCreatureTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;

public class GaeasDay extends CardImpl {

    public GaeasDay(UUID ownerId) {
        super(ownerId, 175, "Gaea's Day", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        // Target land you control becomes an X/X creature that's still a land.
        this.getSpellAbility().addEffect(new GaeasDayEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterControlledLandPermanent()));

        this.addAbility(new StormAbility());

        /*
        Card Text:
        ----------
        http://i.imgur.com/J6sz9b2.jpg
        Target land you control becomes an X/X creature that's still a land.
        Storm
        */
    }

    public GaeasDay(final GaeasDay card) {
        super(card);
    }

    @Override
    public GaeasDay copy() {
        return new GaeasDay(this);
    }

}

class GaeasDayEffect extends OneShotEffect {

    public GaeasDayEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target land you control becomes an X/X creature that's still a land.";
    }

    public GaeasDayEffect(final GaeasDayEffect effect) {
        super(effect);
    }

    @Override
    public GaeasDayEffect copy() {
        return new GaeasDayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        ContinuousEffect effect = new BecomesCreatureTargetEffect(new GaeasDayElementalToken(amount), "land", Duration.WhileOnBattlefield);
        effect.setTargetPointer(targetPointer);
        game.addEffect(effect, source);
        return false;
    }
}

class GaeasDayElementalToken extends Token {

    GaeasDayElementalToken(int amount) {
        super("", "X/X creature");
        cardType.add(CardType.CREATURE);
        power = new MageInt(amount);
        toughness = new MageInt(amount);
    }
}
