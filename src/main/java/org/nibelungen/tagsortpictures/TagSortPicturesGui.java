package org.nibelungen.tagsortpictures;

import org.nibelungen.tagsortpictures.job.PropertiesTSP;
import org.nibelungen.tagsortpictures.swt.TagSortPictureSwt;

import java.util.Objects;

public class TagSortPicturesGui extends PropertiesTSP {
	private static final long serialVersionUID = -3259037780500588378L;
	private static final String SWING = "swing";
	private static final String SWT = "swt";
	private static final String QT = "qt";

	public static void main(String[] args) {
		String gui = System.getenv("application.gui");

		switch (Objects.isNull(gui)? SWT: gui) {
			case SWING -> TagSortPicturesGui.swing();
			case QT -> TagSortPicturesGui.qt();
			default -> TagSortPicturesGui.swt();
		}
	}

	private static void swt() {
		TagSortPictureSwt window = new TagSortPictureSwt();
		window.open();
	}

	private static void swing() {
		//TODO Do swing version
	}


	private static void qt() {
		//TODO Do Qt version
	}
}
