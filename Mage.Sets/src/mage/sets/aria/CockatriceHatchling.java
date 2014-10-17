package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CockatriceHatchling extends CardImpl {

    public CockatriceHatchling(UUID ownerId) {
        super(ownerId, 89, "Cockatrice Hatchling", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Cockatrice");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());

        /*
        Card Text:
        ----------
        http://i.imgur.com/lqimkGU.jpg
        Flying, deathtouch
        */
    }

    public CockatriceHatchling(final CockatriceHatchling card) {
        super(card);
    }

    @Override
    public CockatriceHatchling copy() {
        return new CockatriceHatchling(this);
    }

}

