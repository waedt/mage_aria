package mage.sets;

import java.util.GregorianCalendar;
import mage.cards.ExpansionSet;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public class Aria extends ExpansionSet {

    private static final Aria fINSTANCE = new Aria();

    public static Aria getInstance() {
        return fINSTANCE;
    }

    private Aria() {
        super("Aria", "ARI", "mage.sets.aria", new GregorianCalendar(2014, 5, 16).getTime(), SetType.EXPANSION);
        this.blockName = "Aria";

        // TODO: Review exactly what these flags do and find out what from
        //       Kax what the desired behvaior is.

        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
    }
}
