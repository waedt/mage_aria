// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class StrauthiAmbushParty extends CardImpl {

    public StrauthiAmbushParty(UUID ownerId) {
        super(ownerId, 155, "Strauthi Ambush Party", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/BnxsBnM.jpg
        Haste
        Whenever you cast an instant or sorcery, Strauthi Ambush Party gets +2/+2 until end of turn.
        */
    }

    public StrauthiAmbushParty(final StrauthiAmbushParty card) {
        super(card);
    }

    @Override
    public StrauthiAmbushParty copy() {
        return new StrauthiAmbushParty(this);
    }

}

