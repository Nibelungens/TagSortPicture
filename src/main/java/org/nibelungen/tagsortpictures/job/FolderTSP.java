package org.nibelungen.tagsortpictures.job;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.nibelungen.tagsortpictures.job.interfaces.FolderTSPInterface;
import org.nibelungen.tagsortpictures.utils.FileUtils;

public class FolderTSP extends File implements FolderTSPInterface {
	private static final long serialVersionUID = -8945413116782304137L;
	private List<String> listFiles = null;
	private String error = null;
	
	public FolderTSP(String path) {
		super(path);
	}

	public File checkSubFolderAndCreateIfNotExist(String nameSubFolder) {
		File subFolder = new File(this.getPath() + "/" + nameSubFolder);
		
		if(!subFolder.exists()) {
			if (!subFolder.mkdir()) {
				throw new RuntimeException();
			}
		}
		
		return subFolder;
	}
	
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

	public List<String> getFiles() {
		FilenameFilter fileNameFilter = (dir, name) -> {
			File fic = new File(dir.getPath() + "\\" + name);
			return fic.isFile();
		};
		
		
		String[] tabFiles = this.list(fileNameFilter);
		String file;
		this.listFiles = new ArrayList<>();
		
		if(tabFiles == null) {
			this.error = FolderTSP.ERR_1x1;
		} else if(tabFiles.length == 0) {
			this.error = FolderTSP.ERR_1x2;
		} else {
			for (String tabFile : tabFiles) {
				file = this.isThisExtension(tabFile, FolderTSP.FILTER_IMG);

				if (file != null) {
					listFiles.add(file);
				}
			}
		}

		return this.listFiles;
	}

	public String isThisExtension(String file, String[]... extensions) {
		String filterFile = null;
		String extensionFile;

		for (String[] ext : extensions) {

			for (String s : ext) {
				extensionFile = file.substring(file.lastIndexOf(FolderTSP.EXTENSION_SEPARATOR));

				if (extensionFile.toLowerCase().equals(FolderTSP.EXTENSION_SEPARATOR + s.toLowerCase())) {
					filterFile = file;
				}
			}
		}

		return filterFile;
	}

	public Boolean isError() {
		boolean error = true;
		
		if(this.error == null) {
			error = false;
		}
		
		return error;
	}

	public Integer getNumberFileInFolder() {
		Integer number = null;

		List<String> files = this.getFiles();

		if(Objects.nonNull(files) && Objects.nonNull(this.listFiles)) {
			number  = this.listFiles.size();
		}
		
		return number;
	}

	public List<String> getListFiles() {
		return listFiles;
	}

	public String getError() {
		return error;
	}

	public void setListFiles(List<String> listFiles) {
		this.listFiles = listFiles;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public Boolean existTSP() {
		boolean isExist;
		
		if(this.exists()) {
			isExist = true;
		} else {
			isExist = false;
			this.setError(FolderTSP.ERR_1x1);
		}
		
		return isExist;
	}
}
