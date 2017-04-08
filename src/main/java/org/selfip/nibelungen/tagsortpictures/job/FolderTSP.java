/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.job;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.selfip.nibelungen.tagsortpictures.job.interfaces.FolderTSPInterface;
import org.selfip.nibelungen.tagsortpictures.utils.FileUtils;

/**
 * @author Mickaël
 * 
 */
public class FolderTSP extends File implements FolderTSPInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8945413116782304137L;

	/**
	 * 
	 */
	private List<String> listFiles = null;

	/**
	 * 
	 */
	private String error = null;
	
	/**
	 * 
	 * @param path
	 */
	public FolderTSP(String path) {
		super(path);
	}

	/**
	 * 
	 * @param nameSubFolder
	 * @throws IOException
	 */
	public File checkSubFolderAndCreateIfNotExist(String nameSubFolder) throws IOException {
		File subFolder = new File(this.getPath() + "/" + nameSubFolder);
		
		if(!subFolder.exists()) {
			subFolder.mkdir();
		}
		
		return subFolder;
	}
	
	/**
	 * 
	 */
	public void movePictureTSP(PictureTSP picture, String nameSubFolder) {
		File fileDestination;
		
		try {
			File destination = checkSubFolderAndCreateIfNotExist(nameSubFolder);
			fileDestination = new File(destination.getPath() + "/" + picture.getName());
			FileUtils.copyFile(picture, fileDestination);
		} catch (IOException e) {
			this.error = FolderTSP.ERR_1x3;
		}	
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getFiles() {
		FilenameFilter fileNameFilter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				File fic = new File(dir.getPath() + "\\" + name);
				
				if(fic.isFile())
	            {
					return true;
	            }

	            return false;
			}
		};
		
		
		String[] tabfiles = this.list(fileNameFilter);
		String file = null;
		this.listFiles = new ArrayList<String>();
		
		if(tabfiles == null) {
			this.error = FolderTSP.ERR_1x1;
		} else if(tabfiles.length == 0) {
			this.error = FolderTSP.ERR_1x2;
		} else {
			for (int ii = 0; ii < tabfiles.length; ii++) {
				file = this.isThisExtension(tabfiles[ii], FolderTSP.FILTRE_IMG);
				
				if(file!=null) {
					listFiles.add(file);
				}
			}
		}

		return this.listFiles;
	}

	/**
	 * 
	 * @param listeFiles
	 * @param extesions
	 */
	public String isThisExtension(String file, String[]... extesions) {
		String filterFile = null;
		String extensionFile = null;

		for (String[] ext : extesions) {
				
			for (int ii = 0; ii < ext.length; ii++) {
				extensionFile = file.substring(file.lastIndexOf(FolderTSP.EXTENSION_SEPARATOR));
			
				if (extensionFile.toLowerCase().equals(FolderTSP.EXTENSION_SEPARATOR + ext[ii].toLowerCase())) {
					filterFile = file;
				}
			}
		}

		return filterFile;
	}

	/**
	 * verifie si il ya une erreur
	 * 
	 * @return
	 */
	public Boolean isError() {
		Boolean retour = true;
		
		if(this.error == null) {
			retour = false;;
		}
		
		return retour;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getNumberFileInFolder() {
		Integer number = null;
		
		this.getFiles();
		
		if(this.listFiles!=null) {
			number  = this.listFiles.size();
		}
		
		return number;
	}

	/**
	 * @return the listFiles
	 */
	public List<String> getListFiles() {
		return listFiles;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param listFiles
	 *            the listFiles to set
	 */
	public void setListFiles(List<String> listFiles) {
		this.listFiles = listFiles;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	
	public Boolean existTSP() {
		Boolean retour = null;
		
		if(this.exists()) {
			retour = true;
		} else {
			retour = false;
			this.setError(FolderTSP.ERR_1x1);
		}
		
		return retour;
	}
}
