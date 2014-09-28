// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class RestlessSkeleton extends CardImpl {

    public RestlessSkeleton(UUID ownerId) {
        super(ownerId, 109, "Restless Skeleton", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Skeleton");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/7eFjYLL.jpg
        If Restless Skeleton would be put into your hand from your graveyard, you may put it onto the battlefield tapped instead.
        */
    }

    public RestlessSkeleton(final RestlessSkeleton card) {
        super(card);
    }

    @Override
    public RestlessSkeleton copy() {
        return new RestlessSkeleton(this);
    }

}

