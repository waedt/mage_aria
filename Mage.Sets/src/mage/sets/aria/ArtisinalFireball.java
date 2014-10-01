package mage.sets.aria;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

public class ArtisinalFireball extends CardImpl {

    public ArtisinalFireball(UUID ownerId) {
        super(ownerId, 124, "Artisinal Fireball", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addEffect(new AddXRedManaToManaPoolEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());

        /*
        Card Text:
        ----------
        http://i.imgur.com/SgNG0Uk.jpg
        Artisinal Fireball deals X damage to target creature or player.
        Add X red mana to your mana pool.
        */
    }

    public ArtisinalFireball(final ArtisinalFireball card) {
        super(card);
    }

    @Override
    public ArtisinalFireball copy() {
        return new ArtisinalFireball(this);
    }

}

class AddXRedManaToManaPoolEffect extends OneShotEffect {

    public AddXRedManaToManaPoolEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Add X red mana to your mana pool.";
    }

    public AddXRedManaToManaPoolEffect(final AddXRedManaToManaPoolEffect effect) {
        super(effect);
    }

    public AddXRedManaToManaPoolEffect copy() {
        return new AddXRedManaToManaPoolEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player == null) {
            return false;
        }

        int x = source.getManaCostsToPay().getX();
        player.getManaPool().addMana(Mana.RedMana(x), game, source);

        return true;
    }
}
