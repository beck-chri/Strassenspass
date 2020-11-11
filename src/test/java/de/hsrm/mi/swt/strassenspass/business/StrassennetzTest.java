package de.hsrm.mi.swt.strassenspass.business;

import de.hsrm.mi.swt.strassenspass.business.components.*;
import de.hsrm.mi.swt.strassenspass.business.exceptions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StrassennetzTest {
    @BeforeAll
    public static void init(){
    }

    @Test
    public void testPlatziere(){
        Strassennetz s = new Strassennetz();
        assertTrue(s.isEmpty());
        Strassenstueck strassenstueck1 = new Strassenstueck(Strassenform.GERADE);
        s.platziere(strassenstueck1, 10.0, 10.0);
        assertFalse(s.isEmpty());
        assertSame(strassenstueck1, s.getStrassenstueck(10.0, 10.0));
    }

    @Test
    public void testStrassenstueckHinzufuegen(){
        Strassennetz s = new Strassennetz();
        assertTrue(s.isEmpty());
        Strassenstueck strassenstueck1 = new Strassenstueck(Strassenform.GERADE);
        s.strassenstueckHinzufuegen(strassenstueck1);
        assertFalse(s.isEmpty());
        assertFalse(s.isFahrbar());
        Strassenstueck strassenstueck2 = new Strassenstueck(Strassenform.KURVE);
        s.strassenstueckHinzufuegen(strassenstueck2);
        assertFalse(s.isFahrbar());
        Strassenstueck strassenstueck3 = new Strassenstueck(Strassenform.ABZWEIGUNG);
        s.strassenstueckHinzufuegen(strassenstueck3);
        assertTrue(s.isFahrbar());
    }

    @Test
    public void testStrassenstueckEntfernen() {
        Strassennetz s = new Strassennetz();
        Strassenstueck strassenstueck1 = new Strassenstueck(Strassenform.GERADE);
        Strassenstueck strassenstueck2 = new Strassenstueck(Strassenform.KURVE);
        Strassenstueck strassenstueck3 = new Strassenstueck(Strassenform.ABZWEIGUNG);
        Strassenstueck strassenstueck4 = new Strassenstueck(Strassenform.ABZWEIGUNG);
        s.strassenstueckHinzufuegen(strassenstueck1);
        s.strassenstueckHinzufuegen(strassenstueck2);
        s.strassenstueckHinzufuegen(strassenstueck3);
        s.strassenstueckHinzufuegen(strassenstueck4);

        try {
            s.strassenstueckEntfernen(strassenstueck4);
            assertTrue(strassenstueck4.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGetStrassenstueck(){
        Strassennetz s = new Strassennetz();
        Strassenstueck strassenstueck1 = new Strassenstueck(Strassenform.KREUZUNG);
        Strassenstueck strassenstueck2 = new Strassenstueck(strassenstueck1, 5.0, 5.0);
        s.strassenstueckHinzufuegen(strassenstueck2);
        assertNotNull(s.getStrassenstueck(10.0, 10.0));
    }

    @Test
    public void testExpectedFunktionalitaetException(){
        Strassennetz s = new Strassennetz();
        Strassenstueck strassenstueck1 = new Strassenstueck(Strassenform.GERADE);
        Strassenstueck strassenstueck2 = new Strassenstueck(Strassenform.KURVE);
        Strassenstueck strassenstueck3 = new Strassenstueck(Strassenform.ABZWEIGUNG);
        s.strassenstueckHinzufuegen(strassenstueck1);
        s.strassenstueckHinzufuegen(strassenstueck2);
        s.strassenstueckHinzufuegen(strassenstueck3);
        try{
            s.strassenstueckEntfernen(strassenstueck1);
            fail();
        } catch (Exception e) { }
    }

    @Test
    public void testExpectedEntfernenException(){
        Strassennetz s = new Strassennetz();
        Strassenstueck strassenstueck1 = new Strassenstueck(Strassenform.GERADE);
        Auto auto = new Auto(Auto.Autofarbe.BLAU);
        strassenstueck1.autoHinzufuegen(auto);
        s.strassenstueckHinzufuegen(strassenstueck1);
        try {
            s.strassenstueckEntfernen(strassenstueck1);
            fail();
        }catch (Exception e){

        }
    }

    @Test
    public void testExpectedFahrException(){
        Strassennetz s = new Strassennetz();
        try{
            s.starten();
            fail();
        }catch(FahrException e){ }
    }

    @Test
    public void testExpectedSpeicherException(){
        Strassennetz s = new Strassennetz();
        try{
            s.speichern();
            fail();
        }catch (SpeicherException e){ }
    }
}
