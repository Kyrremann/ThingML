/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fife.rsta.ac.java;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;



/**
 * An icon that can have an optional "decorations" icon beside of it.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class DecoratableIcon implements Icon {

	/**
	 * The width of this icon.
	 */
	private int width;

	/**
	 * The "main" icon (the icon that is decorated).
	 */
	private Icon mainIcon;

	/**
	 * The "decoration" icons.
	 */
	private List decorations;

	/**
	 * Whether this icon is for a "deprecated" item.
	 */
	private boolean deprecated;

	/**
	 * The width of a decoratable icon (16 + 8 + 8, - 8 for overlap).
	 */
	private static final int DEFAULT_WIDTH		= 24;


	/**
	 * Constructor.
	 *
	 * @param mainIcon The "main" icon.  This cannot be <code>null</code>.
	 */
	public DecoratableIcon(Icon mainIcon) {
		this(DEFAULT_WIDTH, mainIcon);
	}


	/**
	 * Constructor.
	 *
	 * @param width The width for this icon.
	 * @param mainIcon The "main" icon.  This cannot be <code>null</code>.
	 */
	public DecoratableIcon(int width, Icon mainIcon) {
		setMainIcon(mainIcon);
		this.width = width;
	}


	/**
	 * Adds a decoration icon.
	 *
	 * @param decoration A new decoration icon.  This cannot be
	 *        <code>null</code>.
	 * @see #setMainIcon(Icon)
	 */
	public void addDecorationIcon(Icon decoration) {
		if (decoration==null) {
			throw new IllegalArgumentException("decoration cannot be null");
		}
		if (decorations==null) {
			decorations = new ArrayList(1); // Usually just 1
		}
		decorations.add(decoration);
	}


	/**
	 * {@inheritDoc}
	 */
	public int getIconHeight() {
		return mainIcon.getIconHeight();
	}


	/**
	 * {@inheritDoc}
	 */
	public int getIconWidth() {
		return width;
	}


	/**
	 * {@inheritDoc}
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (deprecated) {
			IconFactory.get().getIcon(IconFactory.DEPRECATED_ICON).
										paintIcon(c, g, x, y);
		}
		mainIcon.paintIcon(c, g, x, y);
		if (decorations!=null) {
			x = x + getIconWidth() - 8;
			for (int i=decorations.size()-1; i>=0; i--) {
				Icon icon = (Icon)decorations.get(i);
				icon.paintIcon(c, g, x, y);
				x -= 8;
			}
		}
	}


	/**
	 * Sets whether this icon is for a deprecated item.
	 *
	 * @param deprecated Whether this icon is for a deprecated item.
	 */
	public void setDeprecated(boolean deprecated) {
		this.deprecated = deprecated;
	}


	/**
	 * Sets the main icon.
	 *
	 * @param icon The "main" icon.  This cannot be <code>null</code>.
	 * @see #addDecorationIcon(Icon)
	 */
	public void setMainIcon(Icon icon) {
		if (icon==null) {
			throw new IllegalArgumentException("icon cannot be null");
		}
		this.mainIcon = icon;
	}


}