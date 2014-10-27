package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInYourGraveyard;

public class NostalgicNoble extends CardImpl {

    private static final FilterCard filter = new FilterCard("white instant or sorcery card from your graveyard");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public NostalgicNoble(UUID ownerId) {
        super(ownerId, 27, "Nostalgic Noble", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);

        // Sacrifice Nostalgic Noble: Return target white instant or sorcery card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/OGgnQBe.jpg
        Sacrifice Nostalgic Noble: Return target white instant or sorcery card from your graveyard to your hand.
        */
    }

    public NostalgicNoble(final NostalgicNoble card) {
        super(card);
    }

    @Override
    public NostalgicNoble copy() {
        return new NostalgicNoble(this);
    }

}

