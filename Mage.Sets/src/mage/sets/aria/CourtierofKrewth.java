package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.KnightToken;

public class CourtierofKrewth extends CardImpl {

    public CourtierofKrewth(UUID ownerId) {
        super(ownerId, 11, "Courtier of Krewth", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Advisor");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);

        // When Courtier of Krewth enters the battlefield, put a 2/2 white Knight creature token with vigilance onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightToken())));

        /*
        Card Text:
        ----------
        http://i.imgur.com/sy9tK38.jpg
        When Courtier of Krewth enters the battlefield, put a 2/2 white Knight creature token with vigilance onto the battlefield.
        */
    }

    public CourtierofKrewth(final CourtierofKrewth card) {
        super(card);
    }

    @Override
    public CourtierofKrewth copy() {
        return new CourtierofKrewth(this);
    }

}

