// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class NostalgicNoble extends CardImpl {

    public NostalgicNoble(UUID ownerId) {
        super(ownerId, 27, "Nostalgic Noble", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/OGgnQBe.jpg
        Sacrifice Nostalgic Noble: Return target white instant or sorcery card from your graveyard to your hand.
        */
    }

    public NostalgicNoble(final NostalgicNoble card) {
        super(card);
    }

    @Override
    public NostalgicNoble copy() {
        return new NostalgicNoble(this);
    }

}

