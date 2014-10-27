package mage.sets.aria;

import java.util.List;
import java.util.UUID;

import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

public class MercuryFountain extends CardImpl {

    public MercuryFountain(UUID ownerId) {
        super(ownerId, 221, "Mercury Fountain", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ARI";

        // T: Add 1 to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // T: Add one mana to your mana pool of any color a land you control could produce.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new MercuryFountainEffect(), new TapSourceCost()));

        /*
        Card Text:
        ----------
        http://i.imgur.com/UKQACHE.jpg
        T: Add 1 to your mana pool.
        T: Add one mana to your mana pool of any color a land you control could produce.
        */
    }

    public MercuryFountain(final MercuryFountain card) {
        super(card);
    }

    @Override
    public MercuryFountain copy() {
        return new MercuryFountain(this);
    }

}

class MercuryFountainEffect extends ManaEffect {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public MercuryFountainEffect() {
        super();
        staticText = "Add to your mana pool one mana of any type that a land you control could produce";
    }

    public MercuryFountainEffect(final MercuryFountainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        Mana types = new Mana();
        for(Permanent land : lands) {
            Abilities<ManaAbility> mana = land.getAbilities().getManaAbilities(Zone.BATTLEFIELD);
            for(ManaAbility ability : mana) {
                types.add(ability.getNetMana(game));
            }
        }
        Choice choice = new ChoiceImpl(false);
        choice.setMessage("Pick a mana color");
        if(types.getBlack() > 0) {
            choice.getChoices().add("Black");
        }
        if(types.getRed() > 0) {
            choice.getChoices().add("Red");
        }
        if(types.getBlue() > 0) {
            choice.getChoices().add("Blue");
        }
        if(types.getGreen() > 0) {
            choice.getChoices().add("Green");
        }
        if(types.getWhite() > 0) {
            choice.getChoices().add("White");
        }
        if(types.getColorless() > 0) {
            choice.getChoices().add("Colorless");
        }
        if(types.getAny() > 0) {
            choice.getChoices().add("Black");
            choice.getChoices().add("Red");
            choice.getChoices().add("Blue");
            choice.getChoices().add("Green");
            choice.getChoices().add("White");
            choice.getChoices().add("Colorless");
        }
        if(choice.getChoices().size() > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if(choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                player.choose(outcome, choice, game);
            }
            if(choice.getChoice() != null) {
                switch(choice.getChoice()) {
                    case "Black":
                        player.getManaPool().addMana(Mana.BlackMana, game, source);
                        break;
                    case "Blue":
                        player.getManaPool().addMana(Mana.BlueMana, game, source);
                        break;
                    case "Red":
                        player.getManaPool().addMana(Mana.RedMana, game, source);
                        break;
                    case "Green":
                        player.getManaPool().addMana(Mana.GreenMana, game, source);
                        break;
                    case "White":
                        player.getManaPool().addMana(Mana.WhiteMana, game, source);
                        break;
                    case "Colorless":
                        player.getManaPool().addMana(Mana.ColorlessMana, game, source);
                        break;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public MercuryFountainEffect copy() {
        return new MercuryFountainEffect(this);
    }
}
