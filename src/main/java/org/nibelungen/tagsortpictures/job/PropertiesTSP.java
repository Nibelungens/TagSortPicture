package org.nibelungen.tagsortpictures.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.nibelungen.tagsortpictures.job.interfaces.PropertiesTSPInterface;
import org.nibelungen.tagsortpictures.TagSortPicturesGui;

public class PropertiesTSP extends Properties implements PropertiesTSPInterface {
	private static final long serialVersionUID = 1509253065575171577L;

	public PropertiesTSP() {
		super();
		
		this.addProperties(APPLICATION_PROPERTIES);
		
		String language = this.getProperty("application.language");		
		String property = LANGUAGE_PROPERTIES.replace("$1", language);
		
		this.addProperties(property);
	}

	public void addProperties(String fileProp) {
		try {
			InputStream input = TagSortPicturesGui.class.getClassLoader().getResourceAsStream(fileProp);
			this.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
