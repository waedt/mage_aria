// TODO: Finish me!

package mage.sets.aria;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class EverythingisLies extends CardImpl {

    public EverythingisLies(UUID ownerId) {
        super(ownerId, 52, "Everything is Lies", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{U}{U}{U}");
        this.expansionSetCode = "ARI";

        this.color.setBlue(true);

        /*
        Card Text:
        ----------
        http://i.imgur.com/Wk6Tzfd.jpg
        For each permanent, its owner puts it on the bottom of his or her library, then reveals cards from the top of his or her library until that player reveals a card that shares a type with it, then puts it onto the battlefield and puts the rest on the bottom of his or her library. Then each player attaches each aura he or she controls to a permanent it can enchant.
        */
    }

    public EverythingisLies(final EverythingisLies card) {
        super(card);
    }

    @Override
    public EverythingisLies copy() {
        return new EverythingisLies(this);
    }

}

