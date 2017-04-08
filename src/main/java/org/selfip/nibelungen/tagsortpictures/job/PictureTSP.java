/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.job;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.selfip.nibelungen.tagsortpictures.job.interfaces.PictureTSPInterface;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;

/**
 * @author Mickaël
 *
 */
public class PictureTSP extends File implements PictureTSPInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8930436865752451820L;

	/**
	 * 
	 */
	private String error = null;
	
	/**
	 * 
	 */
	private List<String> listTags = null;
	
	/**
	 * Construit le fichier image
	 * 
	 * @param filepath String Chemin du fichier
	 */
	public PictureTSP(String filepath) {
		super(filepath);
	}
	
	/**
	 * Retourne tout les tags du fichier
	 * 	
	 * @return List<String> list des tags
	 */
	public List<String> getTags() {
		this.listTags = new ArrayList<String>();		
		Metadata metadata = null;
		

		try {
			metadata = ImageMetadataReader.readMetadata(this);
			
	        for (Directory directory : metadata.getDirectories()) {
	        	for (int i = 0; i < PictureTSP.TYPE_TAG_RECUP.length; i++) {
	        		this.recupTagByType(directory, PictureTSP.TYPE_TAG_RECUP[i]);
				}
	        	
	        }

		} catch (ImageProcessingException e) {
			this.error = "0x1";
		} catch (IOException e) {
			this.error = "0x2";
		}
		
		return listTags;
	}
	
	private void recupTagByType(Directory directory, int tagType) {
		String[] allTags = null;
		String allDescriptions = directory.getDescription(tagType);
		
       	if(allDescriptions!=null) {
    		allTags = allDescriptions.split(";");
    		
    		for (int i = 0; i < allTags.length; i++) this.listTags.add(allTags[i]);
    	}
	}

	/**
	 * verifie si il ya une erreur
	 * 
	 * @return
	 */
	public Boolean isError() {
		return (this.error != null);
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
}
