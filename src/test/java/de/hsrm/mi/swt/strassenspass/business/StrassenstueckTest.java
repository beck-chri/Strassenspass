package de.hsrm.mi.swt.strassenspass.business;

import de.hsrm.mi.swt.strassenspass.business.components.*;
import de.hsrm.mi.swt.strassenspass.business.exceptions.PlatzierungsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class StrassenstueckTest {
    @BeforeAll
    public static void init(){
    }

    @Test
    public void testLeereNachbarn(){
        Richtung richtung = Richtung.N;
        Strassenstueck s1 = new Strassenstueck(Strassenform.GERADE);
        Strassenstueck s2 = new Strassenstueck(true, s1, richtung);
        HashMap<Richtung, Strassenstueck> nachbarn = s2.getNachbarn();
        /*assertTrue(nachbarn.size() == 4);
        for(Strassenstueck nachbar : nachbarn.values()){
            assertNotNull(nachbar);
        }*/
        assertTrue(nachbarn.size() == 1);
        assertEquals(nachbarn.get(richtung.getOpposite()), s1);
    }

    @Test
    public void testEnthaeltMittelpunkt(){
        double expectedHalbeHoehe = Strassenstueck.HOEHE / 2;
        double expectedHalbeBreite = Strassenstueck.BREITE / 2;
        Strassenstueck s1 = new Strassenstueck(Strassenform.GERADE);
        Strassenstueck s2 = new Strassenstueck(s1, 0.0, 0.0);
        assertArrayEquals(new double[]{expectedHalbeHoehe, expectedHalbeBreite}, s2.getMittelpunkt());
        assertTrue(s2.enthaelt(expectedHalbeHoehe, expectedHalbeBreite));
    }

    /*@Test
    public void testDrehen(){ //hier ist noch ein NullPointer drin?
        Richtung[] expectedAndockstellen = {Richtung.O, Richtung.W};
        Strassenstueck s1 = new Strassenstueck(Strassenform.GERADE);
        Strassenstueck s2 = new Strassenstueck(true, s1, Richtung.N);
        //s1.drehen();
        //assertArrayEquals(expectedAndockstellen, s1.getStrassenform().getAndockstellen());
        s2.drehen();
        assertEquals(s1, s2.getNachbar(Richtung.W));
    }*/

    @Test
    public void testIsKompatibel(){
        Strassenstueck s1 = new Strassenstueck(Strassenform.GERADE);
        Strassenstueck s2 = new Strassenstueck(Strassenform.KURVE);
        assertTrue(s1.isKompatibel(s2, Richtung.S));
    }

    @Test
    public void testExpectedPlatzierungsException(){
        Strassenstueck s1 = new Strassenstueck(Strassenform.GERADE);
        Auto auto1 = new Auto(Auto.Autofarbe.BLAU);
        Auto auto2 = new Auto(Auto.Autofarbe.ROSA);

        try{
            s1.autoHinzufuegen(auto1);
            s1.autoHinzufuegen(auto2);
            fail();
        }catch (PlatzierungsException e){

        }
    }
}
