// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class DeathbondWitch extends CardImpl {

    public DeathbondWitch(UUID ownerId) {
        super(ownerId, 94, "Deathbond Witch", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/WcZHPkT.jpg
        Deathtouch
        Deathbond Witch can't be exiled from anywhere.
        At the beginning of your upkeep, if Deathbond Witch is in your graveyard or on the battlefield, each opponent loses 1 life.
        */
    }

    public DeathbondWitch(final DeathbondWitch card) {
        super(card);
    }

    @Override
    public DeathbondWitch copy() {
        return new DeathbondWitch(this);
    }

}

