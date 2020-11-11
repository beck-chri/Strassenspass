package de.hsrm.mi.swt.strassenspass.persistence;

import de.hsrm.mi.swt.strassenspass.business.Strassennetz;
import de.hsrm.mi.swt.strassenspass.persistence.exceptions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHandler {
	private Gson gson;
	private FileWriter fileWriter;
	private String aktuellerOrdner = System.getProperty("user.dir");
	private final String ORDNER = "src/main/resources/savings/";
	
	public JsonHandler() {
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}
	
	public void strassennetzSpeichern(Strassennetz s) throws JsonWritingException {
		
		String json = gson.toJson(s);
		try {
			fileWriter = new FileWriter(ORDNER + s.getName() + ".json");
			fileWriter.write(json);
			fileWriter.close();
		} catch (IOException e) {
			throw new JsonWritingException();
		}
	}
	
	public Strassennetz getStrassennetz(String name) throws JsonLoadingException {
		Strassennetz geladenesStrassennetz;
		try {
			FileSystem fs = FileSystems.getDefault();
			Path path = fs.getPath(aktuellerOrdner, ORDNER + name + ".json");
			String json = Files.readString(path, StandardCharsets.US_ASCII);
			geladenesStrassennetz = gson.fromJson(json, Strassennetz.class);
		} catch (IOException e) {
			throw new JsonLoadingException();
		}

		return geladenesStrassennetz;
	}

	public String getOrdner() {
		return ORDNER;
	}

	public ArrayList<Strassennetz> getGespeicherteStrassennetze(){
		File f = new File(aktuellerOrdner, ORDNER);
		String[] gespeicherteStrassennetze = f.list();
		ArrayList<Strassennetz> alle = new ArrayList<Strassennetz>();
		for(String filename : gespeicherteStrassennetze){
			int index = filename.lastIndexOf('.');
			if (index != -1) {
				filename = filename.substring(0, index);
			}
			try {
				alle.add(getStrassennetz(filename));
			} catch (JsonLoadingException e) {
				e.printStackTrace();
			}
		}
		return alle;
	}
}
