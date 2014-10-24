package mage.sets.aria;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

public class KnightedMagister extends CardImpl {

    public KnightedMagister(UUID ownerId) {
        super(ownerId, 17, "Knighted Magister", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        // W: Knighted Magister gains your choice of flying, first strike, or vigilance until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new KnightedMagisterEffect(), new ManaCostsImpl("{W}")));

        /*
        Card Text:
        ----------
        http://i.imgur.com/kKk4XVI.jpg
        W: Knighted Magister gains your choice of flying, first strike, or vigilance until end of turn.
        */
    }

    public KnightedMagister(final KnightedMagister card) {
        super(card);
    }

    @Override
    public KnightedMagister copy() {
        return new KnightedMagister(this);
    }

}

class KnightedMagisterEffect extends OneShotEffect {
    public KnightedMagisterEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of flying, first strike, or vigilance until end of turn";
    }

    public KnightedMagisterEffect(final KnightedMagisterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player playerControls = game.getPlayer(source.getControllerId());
        if (playerControls != null) {
            Choice abilityChoice = new ChoiceImpl();
            abilityChoice.setMessage("Choose an ability to add");

            Set<String> abilities = new HashSet<>();
            abilities.add(FlyingAbility.getInstance().getRule());
            abilities.add(FirstStrikeAbility.getInstance().getRule());
            abilities.add(VigilanceAbility.getInstance().getRule());
            abilityChoice.setChoices(abilities);
            while (!abilityChoice.isChosen()) {
                playerControls.choose(Outcome.AddAbility, abilityChoice, game);
                if (!playerControls.isInGame()) {
                    return false;
                }
            }

            String chosen = abilityChoice.getChoice();
            Ability ability = null;
            if (FlyingAbility.getInstance().getRule().equals(chosen)) {
                ability = FlyingAbility.getInstance();
            } else if (FirstStrikeAbility.getInstance().getRule().equals(chosen)) {
                ability = FirstStrikeAbility.getInstance();
            } else if (VigilanceAbility.getInstance().getRule().equals(chosen)) {
                ability = VigilanceAbility.getInstance();
            }

            if (ability != null) {
                ContinuousEffect effect = new GainAbilitySourceEffect(ability, Duration.EndOfTurn);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public KnightedMagisterEffect copy() {
        return new KnightedMagisterEffect(this);
    }

}
