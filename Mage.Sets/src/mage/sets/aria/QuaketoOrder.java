package mage.sets.aria;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;

public class QuaketoOrder extends CardImpl {

    private static final FilterPermanent filterFlyer = new FilterPermanent("creature with flying");
    private static final FilterPermanent filterNonFlyer = new FilterPermanent("creature without flying");
    private static final FilterPermanent filterPlaneswalker = new FilterPermanent("planeswalker");
    private static final FilterPermanent filterAll = new FilterPermanent("everything");


    static {
        filterFlyer.add(new CardTypePredicate(CardType.CREATURE));
        filterFlyer.add(new AbilityPredicate(FlyingAbility.class));

        filterNonFlyer.add(new CardTypePredicate(CardType.CREATURE));
        filterFlyer.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));

        filterPlaneswalker.add(new CardTypePredicate(CardType.PLANESWALKER));
    }

    public QuaketoOrder(UUID ownerId) {
        super(ownerId, 145, "Quake to Order", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{R}");
        this.expansionSetCode = "ARI";

        this.color.setRed(true);

        // Quake to Order deals X damage to each creature without flying;
        this.getSpellAbility().addEffect(new DamageAllEffect(new GetXValue(), filterNonFlyer));

        // or Quake to Order deals X damage to each creature with flying;
        Mode mode = new Mode();
        mode.getEffects().add(new DamageAllEffect(new GetXValue(), filterFlyer));
        this.getSpellAbility().addMode(mode);

        // or Quake to Order deals X damage to each player;
        mode = new Mode();
        mode.getEffects().add(new DamagePlayersEffect(Outcome.Damage, new GetXValue()));
        this.getSpellAbility().addMode(mode);

        // or Quake to Order deals X damage to each planeswalker.
        mode = new Mode();
        mode.getEffects().add(new DamageAllEffect(new GetXValue(), filterPlaneswalker));
        this.getSpellAbility().addMode(mode);

        // There isn't a good way to implement 'Choose one or all' within the
        // mode architecture. (Though one could create a Modes subclass its a
        // bit a mess.) So, simply create an extra mode that's equivalent to
        // choosing 'all'.
        mode = new Mode();
        Effect effect = new DamageEverythingEffect(new GetXValue(), filterAll);
        effect.setText("all modes");
        mode.getEffects().add(effect);
        this.getSpellAbility().addMode(mode);

        /*
        Card Text:
        ----------
        http://i.imgur.com/uVwgMKg.jpg
        Choose one or all - Quake to Order deals X damage to each creature without flying; or Quake to Order deals X damage to each creature with flying; or Quake to Order deals X damage to each player; or Quake to Order deals X damage to each planeswalker.
        */
    }

    public QuaketoOrder(final QuaketoOrder card) {
        super(card);
    }

    @Override
    public QuaketoOrder copy() {
        return new QuaketoOrder(this);
    }

}

