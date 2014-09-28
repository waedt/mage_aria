// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CamouflagedOwl extends CardImpl {

    public CamouflagedOwl(UUID ownerId) {
        super(ownerId, 47, "Camouflaged Owl", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/6YIB1Vr.jpg
        Flying
        Whenever you cast an instant or sorcery, target player mills 3 (To mill 3, that player puts the top three cards of his or her library into his or her graveyard.)
        */
    }

    public CamouflagedOwl(final CamouflagedOwl card) {
        super(card);
    }

    @Override
    public CamouflagedOwl copy() {
        return new CamouflagedOwl(this);
    }

}

