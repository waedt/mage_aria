package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.SquirrelToken;

public class QueeningNutkin extends CardImpl {

    public QueeningNutkin(UUID ownerId) {
        super(ownerId, 190, "Queening Nutkin", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Squirrel");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        // When Queening Nutkin enters the battlefield, put a 1/1 green Squirrel creature token onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SquirrelToken())));

        /*
        Card Text:
        ----------
        http://i.imgur.com/RA59VMj.jpg
        When Queening Nutkin enters the battlefield, put a 1/1 green Squirrel creature token onto the battlefield.
        */
    }

    public QueeningNutkin(final QueeningNutkin card) {
        super(card);
    }

    @Override
    public QueeningNutkin copy() {
        return new QueeningNutkin(this);
    }

}

