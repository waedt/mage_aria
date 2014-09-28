package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.game.permanent.token.SquirrelToken;

public class AcornLure extends CardImpl {

    public AcornLure(UUID ownerId) {
        super(ownerId, 165, "Acorn Lure", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        // Buyback {4}
        this.addAbility(new BuybackAbility("{4}"));

        // Put a 1/1 green Squirrel creature token onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SquirrelToken()));
        /*
        Card Text:
        ----------
        http://i.imgur.com/Vyupqmj.jpg
        Buyback 4 (You may pay an additional 4 as you cast this spell. If you do, put this card into your hand as it resolves.)
        Put a 1/1 green Squirrel creature token onto the battlefield.
        */
    }

    public AcornLure(final AcornLure card) {
        super(card);
    }

    @Override
    public AcornLure copy() {
        return new AcornLure(this);
    }

}

