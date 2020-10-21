package org.nibelungen.tagsortpictures.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nibelungen.tagsortpictures.core.interfaces.TagSortPicturesCoreInterface;
import org.nibelungen.tagsortpictures.core.interfaces.TemplateGuiInterface;
import org.nibelungen.tagsortpictures.job.FolderTSP;
import org.nibelungen.tagsortpictures.job.MessageRowTSP;
import org.nibelungen.tagsortpictures.job.MessageRowTSPEnum;
import org.nibelungen.tagsortpictures.job.PictureTSP;
import org.nibelungen.tagsortpictures.job.PropertiesTSP;

public class TagSortPicturesCore extends PropertiesTSP implements TagSortPicturesCoreInterface {
	private static final long serialVersionUID = 3299711943730806791L;
	private FolderTSP destinationFolder = null;
	private List<String> listExclude = null;
	private TemplateGuiInterface gui = null;
	private FolderTSP sourceFolder = null;

	public TagSortPicturesCore(String sourceFolder, String destinationFolder, List<String> listExclude, TemplateGuiInterface gui) {
		super();
		if(sourceFolder!=null) this.sourceFolder = new FolderTSP(sourceFolder);
		if(destinationFolder!=null) this.destinationFolder = new FolderTSP(destinationFolder);
		if(listExclude!=null) this.listExclude = listExclude;
		if(gui!=null) this.gui = gui;
		
		if(this.sourceFolder!=null) this.addLogMessageDEBUG(TagSortPicturesCore.MSG_PRE_SOURCE_FOLDER, this.sourceFolder.toString());
		if(this.destinationFolder!=null) this.addLogMessageDEBUG(TagSortPicturesCore.MSG_PRE_DESTINATION_FOLDER, this.destinationFolder.toString());
		if(this.listExclude!=null) this.addLogMessageDEBUG(TagSortPicturesCore.MSG_PRE_EXCLUSION_LIST, this.listExclude.toString());
	}
	
	public void run() {
		if (this.destinationFolder.existTSP()) {
			List<String> listFile = this.sourceFolder.getFiles();
			this.addLogMessageINFO(TagSortPicturesCore.MSG_START);

			if (!this.sourceFolder.isError() && !this.destinationFolder.isError()) {

				this.gui.setProgressBarTotal(this.sourceFolder.getNumberFileInFolder());
				this.addLogMessageINFO(TagSortPicturesCore.MSG_NB_FILE, this.sourceFolder.getNumberFileInFolder().toString());

				for (String file : listFile) {
					this.triPicture(this.sourceFolder.getPath() + TagSortPicturesCore.FOLDER_SEPARATOR + file);
				}

			} else if(this.sourceFolder.isError()) {
				this.addLogMessageERROR(TagSortPicturesCore.MSG_ERROR_FOLDER_SOURCE, this.sourceFolder.getName());
				this.folderErrorManagement(this.sourceFolder);

			} else if(this.destinationFolder.isError()) {
				this.addLogMessageERROR(TagSortPicturesCore.MSG_ERROR_FOLDER_DESTINATION, this.destinationFolder.getName());
				this.folderErrorManagement(this.destinationFolder);
			}

			this.addLogMessageINFO(TagSortPicturesCore.MSG_END);
		}
	}
	
	public Map<String, Integer> getExistTagsInSourceFolder() {
		List<String> listFile = this.sourceFolder.getFiles();
		Map<String, Integer> listTag = new HashMap<>();
		
		this.addLogMessageINFO(TagSortPicturesCore.MSG_START_SCAN);
		
		if (!this.sourceFolder.isError()) {

			this.gui.setProgressBarTotal(this.sourceFolder.getNumberFileInFolder());			
			this.addLogMessageINFO(TagSortPicturesCore.MSG_NB_FILE, this.sourceFolder.getNumberFileInFolder().toString());
				
			for (String file : listFile) {
				
				PictureTSP picture = new PictureTSP(this.sourceFolder.getPath() + TagSortPicturesCore.FOLDER_SEPARATOR + file);
				
				if(!picture.isError()) {	
					this.addLogMessageINFO(TagSortPicturesCore.MSG_JOB_FILE, picture.getName());
					this.addLogMessageDEBUG(TagSortPicturesCore.MSG_PATH_FULL_PICTURE, picture.getAbsoluteFile().toString());
					
					for (String tag : picture.getTags()) {
						this.addLogMessageDEBUG(TagSortPicturesCore.MSG_LOAD_TAG, tag);
						
						if(!listTag.containsKey(tag)) {
							listTag.put(tag, 1);
						} else {
							this.addLogMessageDEBUG(TagSortPicturesCore.MSG_TAG_EXIST, tag);							
							listTag.put(tag, listTag.get(tag)+1);		
						}

					}
				} else {
					this.pictureErrorManagement(picture);
				}
			}
			
		} else if(this.sourceFolder.isError()) {
			this.addLogMessageERROR(TagSortPicturesCore.MSG_ERROR_FOLDER_SOURCE, this.sourceFolder.getName());			
			this.folderErrorManagement(this.sourceFolder);
			
		}
		
		this.addLogMessageINFO(TagSortPicturesCore.MSG_END_SCAN);
		
		return listTag;
	}
	
	public Integer getNbFiles() {
		Integer nbFiles = null;
		
		this.addLogMessageINFO(TagSortPicturesCore.MSG_START_SCAN);
		
		if (!this.sourceFolder.isError()) {
			nbFiles = this.sourceFolder.getNumberFileInFolder();
			
			this.gui.setProgressBarTotal(nbFiles);
			this.addLogMessageINFO(TagSortPicturesCore.MSG_NB_FILE, nbFiles.toString());
		} else if(this.sourceFolder.isError()) {
			this.addLogMessageERROR(TagSortPicturesCore.MSG_ERROR_FOLDER_SOURCE, this.sourceFolder.getName());			
			this.folderErrorManagement(this.sourceFolder);
		}
		
		this.addLogMessageINFO(TagSortPicturesCore.MSG_END_SCAN);
		
		return nbFiles;
	}

	private void triPicture(String file) {
		PictureTSP picture = new PictureTSP(file);
		String noTagFolder;
		
		if(!picture.isError()) {	
			this.addLogMessageINFO(TagSortPicturesCore.MSG_JOB_FILE, picture.getName());
			this.addLogMessageDEBUG(TagSortPicturesCore.MSG_PATH_FULL_PICTURE, picture.getAbsoluteFile().toString());
			
			if (!picture.getTags().isEmpty()) {
				for (String tag : picture.getTags()) {
					this.addLogMessageDEBUG(TagSortPicturesCore.MSG_LOAD_TAG, tag);
					
					if(!this.isExcludeList(tag)) {

						this.addLogMessageDEBUG(TagSortPicturesCore.MSG_JOB_COPY, picture.getAbsolutePath(), this.destinationFolder.getAbsolutePath() + "\\" + tag + "\\" + picture.getName());
						this.destinationFolder.movePictureTSP(picture, tag);
						
						if(this.sourceFolder.getError()!=null) {
							this.folderErrorManagement(this.sourceFolder);
						}
					} else {
						this.addLogMessageWARNING(TagSortPicturesCore.MSG_TAG_EXCLUDE, tag);
					}

				}	
			} else {
				noTagFolder = this.getProperty(TagSortPicturesCore.PARAM_FOLDER_NAME_NO_TAG);
				
				this.addLogMessageDEBUG(TagSortPicturesCore.MSG_NO_TAG, picture.getName());
				this.addLogMessageDEBUG(TagSortPicturesCore.MSG_JOB_COPY, picture.getAbsolutePath(), this.destinationFolder.getAbsolutePath() + "\\"+ noTagFolder + "\\" + picture.getName());
				this.destinationFolder.movePictureTSP(picture, noTagFolder);
				
				if(this.sourceFolder.getError()!=null) {
					this.folderErrorManagement(this.sourceFolder);
				}
			}
			
			this.gui.addIncrementProgressBar();
		} else {
			this.pictureErrorManagement(picture);
		}
	}

	private Boolean isExcludeList(String tag) {
		boolean exclude = false;
		
		if(this.listExclude!=null) {
			for (String str : this.listExclude) {
				if (tag != null && tag.equals(str)) {
					exclude = true;
					break;
				}
			}
		}
			
		return exclude;
	}
	
	private void folderErrorManagement(FolderTSP sourceFolder) {
		this.addLogMessageERROR(TagSortPicturesCore.MSG_ERROR);

		switch (sourceFolder.getError()) {
			case FolderTSP.ERR_1x1 -> this.addLogMessageERROR(TagSortPicturesCore.ERROR_1x1);
			case FolderTSP.ERR_1x2 -> this.addLogMessageERROR(TagSortPicturesCore.ERROR_1x2);
			case FolderTSP.ERR_1x3 -> this.addLogMessageERROR(TagSortPicturesCore.ERROR_1x3);
			default -> this.addLogMessageERROR(TagSortPicturesCore.ERROR_UNKNOWN);
		}
	}
	
	private void pictureErrorManagement(PictureTSP picture) {
		this.addLogMessageERROR(TagSortPicturesCore.MSG_ERROR);

		switch (picture.getError()) {
			case PictureTSP.ERR_0x1 -> this.addLogMessageERROR(TagSortPicturesCore.ERROR_0x1);
			case PictureTSP.ERR_0x2 -> this.addLogMessageERROR(TagSortPicturesCore.ERROR_0x2);
			default -> this.addLogMessageERROR(TagSortPicturesCore.ERROR_UNKNOWN);
		}
	}

	private void addLogMessage(String propertyMsg, MessageRowTSPEnum typeMsg, String... parameters) {
		String msgWithOutParams = this.getProperty(propertyMsg);
		
		if(msgWithOutParams.contains(TagSortPicturesCore.POST_PARAMETER)) {
			for (int i = 0; i < parameters.length; i++) {
				msgWithOutParams = msgWithOutParams.replace(TagSortPicturesCore.POST_PARAMETER + (i+1), parameters[i]);			
			}
		}

		this.gui.addMessage(new MessageRowTSP(msgWithOutParams, typeMsg));
	}

	private void addLogMessageINFO(String propertyMsg, String... parameters) {
		this.addLogMessage(propertyMsg, MessageRowTSPEnum.INFO, parameters);
	}
	
	private void addLogMessageDEBUG(String propertyMsg, String... parameters) {
		this.addLogMessage(propertyMsg, MessageRowTSPEnum.DEBUG, parameters);
	}
	
	private void addLogMessageERROR(String propertyMsg, String... parameters) {
		this.addLogMessage(propertyMsg, MessageRowTSPEnum.ERROR, parameters);
	}

	private void addLogMessageWARNING(String propertyMsg, String... parameters) {
		this.addLogMessage(propertyMsg, MessageRowTSPEnum.WARNING, parameters);
	}
}
