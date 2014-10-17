package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class CorpseMiner extends CardImpl {

    public CorpseMiner(UUID ownerId) {
        super(ownerId, 90, "Corpse Miner", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Zombie");
        this.subtype.add("Giant");
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);

        // TODO: Does the exact wording matter here?
        // When Corpse Miner enters the battlefield, you mill 5. (Put the top five cards of your library into your graveyard.)
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutTopCardOfLibraryIntoGraveControllerEffect(5)));

        /*
        Card Text:
        ----------
        http://i.imgur.com/XOghMRg.jpg
        When Corpse Miner enters the battlefield, you mill 5. (Put the top five cards of your library into your graveyard.)
        */
    }

    public CorpseMiner(final CorpseMiner card) {
        super(card);
    }

    @Override
    public CorpseMiner copy() {
        return new CorpseMiner(this);
    }

}

