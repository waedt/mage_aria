// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class BirthdayGnome extends CardImpl {

    public BirthdayGnome(UUID ownerId) {
        super(ownerId, 207, "Birthday Gnome", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{0}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Gnome");
        this.subtype.add("Construct");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        /*
        Card Text:
        ----------
        http://i.imgur.com/wULsPvR.jpg
        Whenever you cast an instant or sorcery, sacrifice Birthday Gnome and flip a coin. If it comes up heads, Birthday Gnome deals 4 damage to target creature or player.
        */
    }

    public BirthdayGnome(final BirthdayGnome card) {
        super(card);
    }

    @Override
    public BirthdayGnome copy() {
        return new BirthdayGnome(this);
    }

}

