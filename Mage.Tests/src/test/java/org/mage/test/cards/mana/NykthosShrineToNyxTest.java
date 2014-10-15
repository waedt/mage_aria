/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.mana;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class NykthosShrineToNyxTest extends CardTestPlayerBase {

    @Test
    public void testNormalUse() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);
        // Kiora's Follower {G}{U}
        // Creature - Merfolk
        // {T}: Untap another target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Kiora's Follower");
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Tusker", 2);
        // Green mana doesn't empty from your mana pool as steps and phases end.
        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Mana", 1);
        
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}: Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>.");        
        setChoice(playerA, "Green");
        
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        
        Assert.assertEquals("message", 6, playerA.getManaPool().getGreen()); // 6 green mana
        assertPowerToughness(playerA, "Omnath, Locus of Mana", 7, 7);
    }

    @Test
    public void testDoubleUse() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);
        // Kiora's Follower {G}{U}
        // Creature - Merfolk
        // {T}: Untap another target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Kiora's Follower");
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Tusker", 2);
        // Green mana doesn't empty from your mana pool as steps and phases end.
        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Mana", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}: Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>.");
        setChoice(playerA, "Green");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Untap another target permanent.","Nykthos, Shrine to Nyx");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}: Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>.");
        setChoice(playerA, "Green");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals("amount of green mana", 10, playerA.getManaPool().getGreen()); // 6G - 2G = 4G + 6G = 10G
        assertPowerToughness(playerA, "Omnath, Locus of Mana", 11,11);
    }
    /*
        Use Nykthos together with Kruphix, God of Horizons to save mana as colorless mana
    */
    
    @Test
    public void testDoubleUseWithKruphix() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2); // to use Nykthos
        addCard(Zone.BATTLEFIELD, playerA, "Nykthos, Shrine to Nyx", 1);
        // Kiora's Follower {G}{U}
        // Creature - Merfolk
        // {T}: Untap another target permanent.
        addCard(Zone.BATTLEFIELD, playerA, "Kiora's Follower"); // 1 G devotion
        addCard(Zone.BATTLEFIELD, playerA, "Kalonian Tusker", 2); // 4 G devotion
        // Kruphix, God of Horizons {3}{G}{U}
        // If unused mana would empty from your mana pool, that mana becomes colorless instead.
        addCard(Zone.BATTLEFIELD, playerA, "Kruphix, God of Horizons", 1); // 1 G devotion

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}: Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>.");
        setChoice(playerA, "Green");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Untap another target permanent.","Nykthos, Shrine to Nyx");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}: Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>.");
        setChoice(playerA, "Green");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Assert.assertEquals("amount of colorless mana", 10, playerA.getManaPool().getColorless()); // 6 - 2 (2.Activation) = 4 + 6  = 10 colorless mana 
        assertPowerToughness(playerA, "Kruphix, God of Horizons", 4,7);
    }
}
