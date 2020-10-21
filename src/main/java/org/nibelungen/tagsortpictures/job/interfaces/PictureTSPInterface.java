package org.nibelungen.tagsortpictures.job.interfaces;

import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.iptc.IptcDirectory;

public interface PictureTSPInterface {
	String ERR_0x1 = "0x1";
	String ERR_0x2 = "0x2";
	int[] TYPE_TAG_GET = {ExifIFD0Directory.TAG_WIN_KEYWORDS, IptcDirectory.TAG_KEYWORDS};
}
