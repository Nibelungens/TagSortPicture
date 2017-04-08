/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures;

import org.selfip.nibelungen.tagsortpictures.job.PropertiesTSP;
import org.selfip.nibelungen.tagsortpictures.swt.TagSortPictureSwt;



/**
 * @author Mickaël
 *
 */
public class TagSortPicturesGui extends PropertiesTSP{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3259037780500588378L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String language = System.getenv("application.language");
		String gui = System.getenv("application.gui");
		
		if(language==null) language="fr";
		if(gui==null) gui="swt";
		
		switch (gui) {
		case "swt":	
			TagSortPictureSwt window = new TagSortPictureSwt();
			window.open();
		break;
		default:
		break;
		}
	}

}
