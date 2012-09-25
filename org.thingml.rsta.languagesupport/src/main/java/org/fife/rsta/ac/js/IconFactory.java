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
package org.fife.rsta.ac.js;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.fife.ui.autocomplete.EmptyIcon;


/**
 * Holds icons used by JavaScript auto-completion.
 * 
 * @author Robert Futrell
 * @version 1.0
 */
public class IconFactory {

	public static final String FUNCTION_ICON = "function";
	public static final String LOCAL_VARIABLE_ICON = "local_variable";
	public static final String TEMPLATE_ICON = "template";
	public static final String EMPTY_ICON = "empty";
	public static final String GLOBAL_VARIABLE_ICON = "global_variable";
	public static final String DEFAULT_FUNCTION_ICON = "default_function";
	public static final String PUBLIC_STATIC_FUNCTION_ICON = "public_static_function";
	public static final String STATIC_VAR_ICON = "static_var";
	public static final String DEFAULT_VARIABLE_ICON = "default_variable";

	private Map iconMap;

	private static final IconFactory INSTANCE = new IconFactory();


	private IconFactory() {

		iconMap = new HashMap();

		iconMap.put(FUNCTION_ICON,
				loadIcon("org/fife/rsta/ac/js/img/methpub_obj.gif"));
		iconMap.put(PUBLIC_STATIC_FUNCTION_ICON,
				loadIcon("org/fife/rsta/ac/js/img/methpub_static.gif"));
		iconMap.put(LOCAL_VARIABLE_ICON,
				loadIcon("org/fife/rsta/ac/js/img/localvariable_obj.gif"));
		iconMap.put(GLOBAL_VARIABLE_ICON,
				loadIcon("org/fife/rsta/ac/js/img/field_public_obj.gif"));
		iconMap.put(TEMPLATE_ICON,
				loadIcon("org/fife/rsta/ac/js/img/template_obj.gif"));
		iconMap.put(DEFAULT_FUNCTION_ICON,
				loadIcon("org/fife/rsta/ac/js/img/methdef_obj.gif"));
		iconMap.put(STATIC_VAR_ICON,
				loadIcon("org/fife/rsta/ac/js/img/static_co.gif"));
		iconMap.put(DEFAULT_VARIABLE_ICON,
				loadIcon("org/fife/rsta/ac/js/img/field_default_obj.gif"));
		iconMap.put(EMPTY_ICON, new EmptyIcon(16));

	}


	private Icon getIconImage(String name) {
		return (Icon) iconMap.get(name);
	}


	public static Icon getIcon(String name) {
		return INSTANCE.getIconImage(name);
	}


	public static String getEmptyIcon() {
		return EMPTY_ICON;
	}


	/**
	 * Loads an icon.
	 * 
	 * @param name The file name of the icon to load.
	 * @return The icon.
	 */
	private Icon loadIcon(String name) {
		URL res = getClass().getClassLoader().getResource(name);
		if (res == null) { // Never happens
			// IllegalArgumentException is what would be thrown if res
			// was null anyway, we're just giving the actual arg name to
			// make the message more descriptive
			throw new IllegalArgumentException("icon not found: " + name);
		}
		return new ImageIcon(res);
	}

}