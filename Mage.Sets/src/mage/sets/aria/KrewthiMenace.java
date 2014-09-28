// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class KrewthiMenace extends CardImpl {

    public KrewthiMenace(UUID ownerId) {
        super(ownerId, 20, "Krewthi Menace", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Dwarf");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/eJvYaF1.jpg
        Whenever you cast an instant or sorcery, Krewthi Menace gains intimidate until end of turn.
        */
    }

    public KrewthiMenace(final KrewthiMenace card) {
        super(card);
    }

    @Override
    public KrewthiMenace copy() {
        return new KrewthiMenace(this);
    }

}

