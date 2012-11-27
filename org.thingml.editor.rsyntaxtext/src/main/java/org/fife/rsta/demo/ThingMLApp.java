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
package org.fife.rsta.demo;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * Stand-alone version of the demo.
 * 
 * @author Robert Futrell
 * @version 1.0
 */
public class ThingMLApp extends JFrame {

	private static final long serialVersionUID = -3237525402614050486L;
	private ThingMLRootPane rootPane;

	public ThingMLApp() throws IOException {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("RSTA Language Support ThingML Demo Application");
		setRootPane(new ThingMLRootPane());
		rootPane = (ThingMLRootPane) getRootPane();
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {
				JFrame frame = (JFrame) we.getSource();
				if (!rootPane.isTabsSaved()) {
					int result = JOptionPane
							.showConfirmDialog(
									frame,
									"Do you want to save before you exit the application?",
									"Exit Application",
									JOptionPane.YES_NO_CANCEL_OPTION);

					if (result == JOptionPane.YES_OPTION) {

						rootPane.saveTabs();
						while (rootPane.isThereAliveThreads())
							; // Not sure if this is a good while-loop...
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					} else if (result == JOptionPane.NO_OPTION)
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else {
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
		});
		pack();
	}

	public void pullThePlug() {
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}

	/**
	 * Called when we are made visible. Here we request that the
	 * {@link RSyntaxTextArea} is given focus.
	 * 
	 * @param visible
	 *            Whether this frame should be visible.
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			rootPane.focusCurrentTextArea();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
					e.printStackTrace(); // Never happens
				}
				Toolkit.getDefaultToolkit().setDynamicLayout(true);
				try {
					new ThingMLApp().setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
