// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class StrauthiApprentice extends CardImpl {

    public StrauthiApprentice(UUID ownerId) {
        super(ownerId, 156, "Strauthi Apprentice", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.subtype.add("Wizard");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.color.setRed(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/YUI50L3.jpg
        Whenever a player casts a spell, Strauthi Apprentice gets +1/+0 until end of turn. (Any nonland card is a spell when you cast it.)
        */
    }

    public StrauthiApprentice(final StrauthiApprentice card) {
        super(card);
    }

    @Override
    public StrauthiApprentice copy() {
        return new StrauthiApprentice(this);
    }

}

