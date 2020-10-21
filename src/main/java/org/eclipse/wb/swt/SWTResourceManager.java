package org.eclipse.wb.swt;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class SWTResourceManager {
	private static final int MISSING_IMAGE_SIZE = 10;
	private static final Map<RGB, Color> M_COLORMAP = new HashMap<>();
	private static final Map<String, Image> M_IMAGE_MAP = new HashMap<>();

	/**
	 * Returns the system {@link Color} matching the specific ID.
	 * 
	 * @param systemColorID
	 *            the ID value for the color
	 * @return the system {@link Color} matching the specific ID
	 */
	public static Color getColor(int systemColorID) {
		Display display = Display.getCurrent();
		return display.getSystemColor(systemColorID);
	}

	/**
	 * Returns a {@link Color} given its red, green and blue component values.
	 * 
	 * @param r
	 *            the red component of the color
	 * @param g
	 *            the green component of the color
	 * @param b
	 *            the blue component of the color
	 * @return the {@link Color} matching the given red, green and blue component values
	 */
	public static Color getColor(int r, int g, int b) {
		return getColor(new RGB(r, g, b));
	}

	/**
	 * Returns a {@link Color} given its RGB value.
	 * 
	 * @param rgb
	 *            the {@link RGB} value of the color
	 * @return the {@link Color} matching the RGB value
	 */
	public static Color getColor(RGB rgb) {
		Color color = M_COLORMAP.get(rgb);
		if (color == null) {
			Display display = Display.getCurrent();
			color = new Color(display, rgb);
			M_COLORMAP.put(rgb, color);
		}
		return color;
	}

	/**
	 * Returns an {@link Image} encoded by the specified {@link InputStream}.
	 * 
	 * @param stream
	 *            the {@link InputStream} encoding the image data
	 * @return the {@link Image} encoded by the specified input stream
	 */
	protected static Image getImage(InputStream stream) throws IOException {
		try (stream) {
			Display display = Display.getCurrent();
			ImageData data = new ImageData(stream);
			if (data.transparentPixel > 0) {
				return new Image(display, data, data.getTransparencyMask());
			}
			return new Image(display, data);
		}
	}

	/**
	 * Returns an {@link Image} stored in the file at the specified path relative to the specified class.
	 * 
	 * @param clazz
	 *            the {@link Class} relative to which to find the image
	 * @param path
	 *            the path to the image file, if starts with <code>'/'</code>
	 * @return the {@link Image} stored in the file at the specified path
	 */
	public static Image getImage(Class<?> clazz, String path) {
		String key = clazz.getName() + '|' + path;
		Image image = M_IMAGE_MAP.get(key);
		if (image == null) {
			try {
				image = getImage(clazz.getResourceAsStream(path));
				M_IMAGE_MAP.put(key, image);
			} catch (Exception e) {
				image = getMissingImage();
				M_IMAGE_MAP.put(key, image);
			}
		}
		return image;
	}

	/**
	 * @return the small {@link Image} that can be used as placeholder for missing image.
	 */
	private static Image getMissingImage() {
		Image image = new Image(Display.getCurrent(), MISSING_IMAGE_SIZE, MISSING_IMAGE_SIZE);

		GC gc = new GC(image);
		gc.setBackground(getColor(SWT.COLOR_RED));
		gc.fillRectangle(0, 0, MISSING_IMAGE_SIZE, MISSING_IMAGE_SIZE);
		gc.dispose();

		return image;
	}
}