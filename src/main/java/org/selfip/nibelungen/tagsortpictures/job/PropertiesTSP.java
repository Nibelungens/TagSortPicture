/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.job;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.selfip.nibelungen.tagsortpictures.TagSortPicturesGui;
import org.selfip.nibelungen.tagsortpictures.job.interfaces.PropertiesTSPInterface;

/**
 * @author Mickaël
 * 
 */
public class PropertiesTSP extends Properties implements PropertiesTSPInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1509253065575171577L;

	/**
	 * 
	 */
	public PropertiesTSP() {
		super();
		
		this.addProperties(APPLICATION_PROPERTIES);
		
		String language = this.getProperty("application.language");		
		String azzo = LANGUAGE_PROPERTIES.replace("$1", language);
		
		this.addProperties(azzo);
	}

	/**
	 * 
	 * @param fileProp
	 */
	public void addProperties(String fileProp) {
		try {
			InputStream input = TagSortPicturesGui.class.getClassLoader().getResourceAsStream(fileProp);
			this.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
