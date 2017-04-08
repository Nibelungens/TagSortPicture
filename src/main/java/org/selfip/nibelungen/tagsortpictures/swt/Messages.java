package org.selfip.nibelungen.tagsortpictures.swt;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.selfip.nibelungen.tagsortpictures.swt.messages"; //$NON-NLS-1$
	public static String TagSortPictureSwt_shlTagsortpictures_text;
	public static String TagSortPictureSwt_mntmAbout_text;
	public static String TagSortPictureSwt_tbtmParams_text;
	public static String TagSortPictureSwt_tbtmLogs_text;
	public static String TagSortPictureSwt_btnSort_text;
	public static String TagSortPictureSwt_textSourceFolder;
	public static String TagSortPictureSwt_btnSearchSourceFolder;
	public static String TagSortPictureSwt_textDestinationFolder;
	public static String TagSortPictureSwt_btnSearchDestinationFolder;
	public static String TagSortPictureSwt_btnRefreshNumberFiles;
	public static String TagSortPictureSwt_lblNumberFiles;
	public static String TagSortPictureSwt_textDefaultNoNumberFiles;
	public static String TagSortPictureSwt_btnAddTag;
	public static String TagSortPictureSwt_btnDelTag;
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Class initialization
	//
	////////////////////////////////////////////////////////////////////////////
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
