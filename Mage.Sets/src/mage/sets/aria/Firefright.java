package mage.sets.aria;

import java.util.UUID;

import mage.Mana;
import mage.abilities.effects.common.AddManaToManaPoolEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class Firefright extends CardImpl {

    public Firefright(UUID ownerId) {
        super(ownerId, 134, "Firefright", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        // Add R to your mana pool.
        this.getSpellAbility().addEffect(new AddManaToManaPoolEffect(Mana.RedMana, "your"));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        /*
        Card Text:
        ----------
        http://i.imgur.com/A9dvyaE.jpg
        Add R to your mana pool.
        Draw a card.
        */
    }

    public Firefright(final Firefright card) {
        super(card);
    }

    @Override
    public Firefright copy() {
        return new Firefright(this);
    }

}

