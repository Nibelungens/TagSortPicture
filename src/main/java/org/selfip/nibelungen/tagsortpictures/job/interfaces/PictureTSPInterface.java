/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.job.interfaces;

import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.iptc.IptcDirectory;

/**
 * @author Mickaël
 *
 */
public interface PictureTSPInterface {
	
	/**
	 * Erreur format ficher
	 */
	public static final String ERR_0x1 = "0x1";

	/**
	 * Erreur fichier inexistant
	 */
	public static final String ERR_0x2 = "0x2";
	
	/**
	 * Type de tag resuperer
	 */
	public static final int[] TYPE_TAG_RECUP = {ExifIFD0Directory.TAG_WIN_KEYWORDS, IptcDirectory.TAG_KEYWORDS};
}
