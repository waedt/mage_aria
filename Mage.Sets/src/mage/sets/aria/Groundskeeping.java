package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInLibrary;

public class Groundskeeping extends CardImpl {

    private static final FilterCard graveyardFilter = new FilterCard("land card from your graveyard");
    private static final FilterCard libraryFilter = new FilterCard("land card");

    static {
        graveyardFilter.add(new CardTypePredicate(CardType.LAND));
        libraryFilter.add(new CardTypePredicate(CardType.LAND));
    }

    public Groundskeeping(UUID ownerId) {
        super(ownerId, 182, "Groundskeeping", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "ARI";

        this.color.setGreen(true);

        // Choose one or both
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMinModes(2);

        // Return target land card from your graveyard to the battlefield tapped;
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(graveyardFilter));

        // or search your library for a land card, reveal it, and put it into your hand, then shuffle your library.
        Mode mode = new Mode();
        mode.getEffects().add(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(libraryFilter), true));
        this.getSpellAbility().addMode(mode);

        /*
        Card Text:
        ----------
        http://i.imgur.com/LRLCbo6.jpg
        Choose one or both - Return target land card from your graveyard to the battlefield tapped; or search your library for a land card, reveal it, and put it into your hand, then shuffle your library.
        */
    }

    public Groundskeeping(final Groundskeeping card) {
        super(card);
    }

    @Override
    public Groundskeeping copy() {
        return new Groundskeeping(this);
    }

}

