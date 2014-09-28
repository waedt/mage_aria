// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class OgreButler extends CardImpl {

    public OgreButler(UUID ownerId) {
        super(ownerId, 61, "Ogre Butler", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Ogre");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/kNnoHOC.jpg
        Whenever Ogre Butler deals damage to a creature, return that creature to its owner's hand.
        */
    }

    public OgreButler(final OgreButler card) {
        super(card);
    }

    @Override
    public OgreButler copy() {
        return new OgreButler(this);
    }

}

