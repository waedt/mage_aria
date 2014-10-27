package mage.sets.aria;

import java.util.UUID;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

public class NoblesEstate extends CardImpl {

    public NoblesEstate(UUID ownerId) {
        super(ownerId, 222, "Noble's Estate", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ARI";

        // As Noble's Estate enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // T: Add one mana of the chosen color to your mana pool.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new NoblesEstateManaEffect(), new TapSourceCost()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/VTMUz2W.jpg
        As Noble's Estate enters the battlefield, choose a color.
        T: Add one mana of the chosen color to your mana pool.
        */
    }

    public NoblesEstate(final NoblesEstate card) {
        super(card);
    }

    @Override
    public NoblesEstate copy() {
        return new NoblesEstate(this);
    }

}

class NoblesEstateManaEffect extends ManaEffect {

    public NoblesEstateManaEffect() {
        super();
        staticText = "Add one mana of the chosen color to your mana pool";
    }

    public NoblesEstateManaEffect(final NoblesEstateManaEffect effect) {
        super(effect);
    }

    @Override
    public NoblesEstateManaEffect copy() {
        return new NoblesEstateManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            switch(color.toString()) {
                case "W":
                    player.getManaPool().addMana(Mana.WhiteMana, game, source);
                    break;
                case "B":
                    player.getManaPool().addMana(Mana.BlackMana, game, source);
                    break;
                case "U":
                    player.getManaPool().addMana(Mana.BlueMana, game, source);
                    break;
                case "G":
                    player.getManaPool().addMana(Mana.GreenMana, game, source);
                    break;
                case "R":
                    player.getManaPool().addMana(Mana.RedMana, game, source);
                    break;
            }
            return true;
        }
        return false;
    }

}
