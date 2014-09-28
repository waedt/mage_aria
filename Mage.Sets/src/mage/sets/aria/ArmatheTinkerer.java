// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class ArmatheTinkerer extends CardImpl {

    public ArmatheTinkerer(UUID ownerId) {
        super(ownerId, 3, "Arma the Tinkerer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "ARI";

        this.supertype.add("Lengendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Tk0Ui9n.jpg
        When Arma the Tinkerer enters the battlefield, return target artifact creature with converted mana cost 2 or less from your graveyard to the battlefield.
        Artifact creatures you control get +1/+1
        */
    }

    public ArmatheTinkerer(final ArmatheTinkerer card) {
        super(card);
    }

    @Override
    public ArmatheTinkerer copy() {
        return new ArmatheTinkerer(this);
    }

}

