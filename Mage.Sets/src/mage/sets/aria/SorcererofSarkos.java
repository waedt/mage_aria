// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SorcererofSarkos extends CardImpl {

    public SorcererofSarkos(UUID ownerId) {
        super(ownerId, 114, "Sorcerer of Sarkos", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Vampire");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/8qgxmxf.jpg
        Flying
        Whenever a creature an opponent controls dies, you may exile a card from your graveyard and pay 1. If you do, return target instant or sorcery from your graveyard to your hand.
        */
    }

    public SorcererofSarkos(final SorcererofSarkos card) {
        super(card);
    }

    @Override
    public SorcererofSarkos copy() {
        return new SorcererofSarkos(this);
    }

}

