package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.TapSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class AwestruckDispe extends CardImpl {

    public AwestruckDispe(UUID ownerId) {
        super(ownerId, 83, "Awestruck Dispe", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Ogre");
        this.subtype.add("Warrior");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);

        // Whenever a player casts a spell, tap Awestruck Dispe.
        this.addAbility(new SpellCastAllTriggeredAbility(new TapSourceEffect(), false));

        /*
        Card Text:
        ----------
        http://i.imgur.com/0V3hDwB.jpg
        Whenever a player casts a spell, tap Awestruck Dispe.
        */
    }

    public AwestruckDispe(final AwestruckDispe card) {
        super(card);
    }

    @Override
    public AwestruckDispe copy() {
        return new AwestruckDispe(this);
    }

}

