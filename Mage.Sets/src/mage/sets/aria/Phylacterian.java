// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Phylacterian extends CardImpl {

    public Phylacterian(UUID ownerId) {
        super(ownerId, 106, "Phylacterian", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Skeleton");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/29Vm8En.jpg
        If Phylacterian would be put into your hand from your graveyard, you may put it onto the battlefield tapped instead.
        When Phylacterian enters the battlefield, you may sacrifice an artifact. If you do, each opponent sacrifices a creature.
        */
    }

    public Phylacterian(final Phylacterian card) {
        super(card);
    }

    @Override
    public Phylacterian copy() {
        return new Phylacterian(this);
    }

}

