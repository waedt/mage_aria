// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ElectedJudge extends CardImpl {

    public ElectedJudge(UUID ownerId) {
        super(ownerId, 12, "Elected Judge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.subtype.add("Knight");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/LVKHIfD.jpg
        First strike
        Whenever you cast an instant or sorcery, put a +1/+1 counter on each creature you control.
        */
    }

    public ElectedJudge(final ElectedJudge card) {
        super(card);
    }

    @Override
    public ElectedJudge copy() {
        return new ElectedJudge(this);
    }

}

