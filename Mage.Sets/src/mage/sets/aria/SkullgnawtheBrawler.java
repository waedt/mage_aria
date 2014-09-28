// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class SkullgnawtheBrawler extends CardImpl {

    public SkullgnawtheBrawler(UUID ownerId) {
        super(ownerId, 198, "Skullgnaw the Brawler", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "ARI";

        this.supertype.add("Lengendary");
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/vPocJtZ.jpg
        T: Skullgnaw the Brawler fights target creature. When that creature dies this turn, untap Skullgnaw the Brawler.
        Whenever a creature you control fights a creature, you may return Skullgnaw the Brawler from your graveyard to the battlefield.
        */
    }

    public SkullgnawtheBrawler(final SkullgnawtheBrawler card) {
        super(card);
    }

    @Override
    public SkullgnawtheBrawler copy() {
        return new SkullgnawtheBrawler(this);
    }

}

