// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SerpentofDwyReach extends CardImpl {

    public SerpentofDwyReach(UUID ownerId) {
        super(ownerId, 72, "Serpent of Dwy Reach", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Serpent");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/kylsFfO.jpg
        Defender
        Whenever you cast an instant or sorcery, Serpent of Dwy Reach loses defender until end of turn.
        */
    }

    public SerpentofDwyReach(final SerpentofDwyReach card) {
        super(card);
    }

    @Override
    public SerpentofDwyReach copy() {
        return new SerpentofDwyReach(this);
    }

}

