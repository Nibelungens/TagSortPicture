package org.nibelungen.tagsortpictures.job;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nibelungen.tagsortpictures.job.interfaces.PictureTSPInterface;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;

public class PictureTSP extends File implements PictureTSPInterface {
	private static final long serialVersionUID = -8930436865752451820L;
	private String error = null;
	private List<String> listTags = null;
	
	public PictureTSP(String filepath) {
		super(filepath);
	}
	
	public List<String> getTags() {
		this.listTags = new ArrayList<>();
		Metadata metadata;
		

		try {
			metadata = ImageMetadataReader.readMetadata(this);
			
	        for (Directory directory : metadata.getDirectories()) {
	        	for (int i = 0; i < PictureTSP.TYPE_TAG_GET.length; i++) {
	        		this.getTagByType(directory, PictureTSP.TYPE_TAG_GET[i]);
				}
	        	
	        }

		} catch (ImageProcessingException e) {
			this.error = "0x1";
		} catch (IOException e) {
			this.error = "0x2";
		}
		
		return listTags;
	}
	
	private void getTagByType(Directory directory, int tagType) {
		String[] allTags;
		String allDescriptions = directory.getDescription(tagType);
		
       	if(allDescriptions!=null) {
    		allTags = allDescriptions.split(";");

			Collections.addAll(this.listTags, allTags);
    	}
	}

	public Boolean isError() {
		return (this.error != null);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
