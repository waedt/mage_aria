package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

public class Pesterskull extends CardImpl {

    public Pesterskull(UUID ownerId) {
        super(ownerId, 105, "Pesterskull", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Skeleton");
        this.subtype.add("Imp");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setBlack(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Pesterskull attacks, target creature blocks this turn if able.
        Ability ability = new AttacksTriggeredAbility(new BlocksIfAbleTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // 3B: Return Pesterskull from your graveyard to the battlefield tapped.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true), new ManaCostsImpl("{3}{B}")));

        /*
        Card Text:
        ----------
        http://i.imgur.com/bnk6S06.jpg
        Flying
        Whenever Pesterskull attacks, target creature blocks this turn if able.
        3B: Return Pesterskull from your graveyard to the battlefield tapped.
        */
    }

    public Pesterskull(final Pesterskull card) {
        super(card);
    }

    @Override
    public Pesterskull copy() {
        return new Pesterskull(this);
    }

}

