package mage.sets.aria;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPlayer;

public class CamouflagedOwl extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                                 new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public CamouflagedOwl(UUID ownerId) {
        super(ownerId, 47, "Camouflaged Owl", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "ARI";

        this.subtype.add("Bird");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        this.addAbility(FlyingAbility.getInstance());

        // TODO: Is the specific wording important here?
        // Whenever you cast an instant or sorcery, target player mills 3 (To mill 3, that player puts the top three cards of his or her library into his or her graveyard.)
        Ability ability = new SpellCastControllerTriggeredAbility(new PutLibraryIntoGraveTargetEffect(3), filter, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        /*
        Card Text:
        ----------
        http://i.imgur.com/6YIB1Vr.jpg
        Flying
        Whenever you cast an instant or sorcery, target player mills 3 (To mill 3, that player puts the top three cards of his or her library into his or her graveyard.)
        */
    }

    public CamouflagedOwl(final CamouflagedOwl card) {
        super(card);
    }

    @Override
    public CamouflagedOwl copy() {
        return new CamouflagedOwl(this);
    }

}

