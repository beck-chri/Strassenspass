package de.hsrm.mi.swt.strassenspass.persistence;

import de.hsrm.mi.swt.strassenspass.persistence.exceptions.*;
import de.hsrm.mi.swt.strassenspass.business.*;
import de.hsrm.mi.swt.strassenspass.business.components.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonHandlerTest {
    JsonHandler jsonHandler = new JsonHandler();
    String filename = "Testnetz";

    @BeforeAll
    public static void init(){
    }

    @Test
    public void testStrassennetzSpeichern(){
        Strassennetz strassennetz = generiereStrassennetz();
        try {
            jsonHandler.strassennetzSpeichern(strassennetz);
        } catch (JsonWritingException e) {
            fail();
        }
        File file = new File(jsonHandler.getOrdner() + filename + ".json");
        assertTrue(file.exists());
    }

    @Test
    public void testGetStrassennetz(){
        Strassennetz geladenesStrassennetz = null;
        Strassennetz strassennetz = generiereStrassennetz();
        try {
            geladenesStrassennetz = jsonHandler.getStrassennetz(filename);
        } catch (JsonLoadingException e) {
            fail();
        }
        boolean assertion = strassennetz.equals(geladenesStrassennetz);
        assertTrue(assertion);
        File file = new File(jsonHandler.getOrdner() + filename + ".json");
        file.delete();
    }

    public Strassennetz generiereStrassennetz(){
        Strassennetz strassennetz = new Strassennetz();
        strassennetz.setName(filename);
        Strassenstueck strassenstueck1 = new Strassenstueck(Strassenform.GERADE);
        Strassenstueck strassenstueck2 = new Strassenstueck(Strassenform.KURVE);
        Strassenstueck strassenstueck3 = new Strassenstueck(Strassenform.ABZWEIGUNG);
        strassennetz.strassenstueckHinzufuegen(strassenstueck1);
        strassennetz.strassenstueckHinzufuegen(strassenstueck2);
        strassennetz.strassenstueckHinzufuegen(strassenstueck3);
        return strassennetz;
    }
}
