// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class EmpathicDrudge extends CardImpl {

    public EmpathicDrudge(UUID ownerId) {
        super(ownerId, 171, "Empathic Drudge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Troll");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/dMnkHlO.jpg
        G: Regenerate Empathic Drudge.
        Green spells you cast cost G less to cast. This effect reduces only the amount of colored mana you pay.
        */
    }

    public EmpathicDrudge(final EmpathicDrudge card) {
        super(card);
    }

    @Override
    public EmpathicDrudge copy() {
        return new EmpathicDrudge(this);
    }

}

