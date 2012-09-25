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
package org.fife.rsta.ac.demo;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;


/**
 * An applet version of the autocompletion demo.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public class DemoApplet extends JApplet {

	private static final long serialVersionUID = 93765764419671649L;


	/**
	 * Initializes this applet.
	 */
	public void init() {
		super.init();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String laf = UIManager.getSystemLookAndFeelClassName();
				try {
					UIManager.setLookAndFeel(laf);
				} catch (Exception e) {
					e.printStackTrace();
				}
				setRootPane(new DemoRootPane());
			}
		});
	}


	/**
	 * Called when this applet is made visible.  Here we request that the
	 * {@link RSyntaxTextArea} is given focus.  I tried putting this code in
	 * {@link #start()} (wrapped in SwingUtilities.invokeLater()), but it
	 * didn't seem to work there.
	 *
	 * @param visible Whether this applet should be visible.
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			((DemoRootPane)getRootPane()).focusTextArea();
		}
	}


}