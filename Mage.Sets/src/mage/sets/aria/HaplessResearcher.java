package mage.sets.aria;

import java.util.UUID;

public class HaplessResearcher extends mage.sets.judgment.HaplessResearcher {

    public HaplessResearcher(UUID ownerId) {
        super(ownerId);
        this.cardNumber = 54;
        this.expansionSetCode = "ARI";

        /*
        Card Text:
        ----------
        http://i.imgur.com/eYymiEj.jpg
        Sacrifice Hapless Researcher: Draw a card, then discard a card.
        */
    }

    public HaplessResearcher(final HaplessResearcher card) {
        super(card);
    }

    @Override
    public HaplessResearcher copy() {
        return new HaplessResearcher(this);
    }

}

