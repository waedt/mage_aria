// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AutarchofSol extends CardImpl {

    public AutarchofSol(UUID ownerId) {
        super(ownerId, 4, "Autarch of Sol", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/VwxPPFz.jpg
        Vigilance
        Autarch of Sol has power and toughness equal to the number of Wizards you control.
        Tap an untapped Wizard you control: Tap target non-Wizard creature.
        */
    }

    public AutarchofSol(final AutarchofSol card) {
        super(card);
    }

    @Override
    public AutarchofSol copy() {
        return new AutarchofSol(this);
    }

}

