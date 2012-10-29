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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.fife.rsta.demo.AboutDialog;
import org.fife.rsta.demo.ThingMLRootPane;
import org.fife.rsta.demo.ExtensionFileFilter;


/**
 * Container for all actions used by the demo.
 * 
 * @author Robert Futrell and Kyrre Havik Eriksen
 * @version 1.0
 */
interface Actions {

	/**
	 * Displays an "About" dialog.
	 */
	static class AboutAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		private ThingMLRootPane demo;

		public AboutAction(ThingMLRootPane demo) {
			this.demo = demo;
			putValue(NAME, "About RSTALanguageSupport...");
		}

		public void actionPerformed(ActionEvent e) {
			AboutDialog ad = new AboutDialog(
					(ThingMLApp) SwingUtilities.getWindowAncestor(demo));
			ad.setLocationRelativeTo(demo);
			ad.setVisible(true);
		}

	}

	/**
	 * Exits the application.
	 */
	static class ExitAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public ExitAction() {
			putValue(NAME, "Exit");
			putValue(MNEMONIC_KEY, new Integer('x'));
		}

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}

	}

	/**
	 * Lets the user open a file.
	 */
	static class OpenAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane rootPane;
		private JFileChooser chooser;

		public OpenAction(ThingMLRootPane demo, ImageIcon icon) {
			super(null, icon);
			this.rootPane = demo;
			if (icon == null)
				putValue(NAME, "Open...");
			putValue(MNEMONIC_KEY, new Integer('O'));
			int mods = demo.getToolkit().getMenuShortcutKeyMask();
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_O, mods);
			putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent e) {
			if (chooser == null) {
				chooser = new JFileChooser();
				chooser.setFileFilter(new ExtensionFileFilter(
						"ThingML Source Files", "thingml"));
			}
			int rc = chooser.showOpenDialog(rootPane);
			if (rc == JFileChooser.APPROVE_OPTION) {
				// demo.openFile(chooser.getSelectedFile());
				rootPane.openFile(chooser.getSelectedFile());
			}
		}

	}

	/**
	 * Lets the user open a file.
	 */
	static class NewFileAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane rootPane;

		public NewFileAction(ThingMLRootPane rootPane, ImageIcon icon) {
			super(null, icon);
			this.rootPane = rootPane;
			if (icon == null)
				putValue(NAME, "New...");
			putValue(MNEMONIC_KEY, new Integer('N'));
			int mods = rootPane.getToolkit().getMenuShortcutKeyMask();
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_N, mods);
			putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent e) {
			rootPane.newFile();
		}

	}

	/**
	 * Lets the user open a file.
	 */
	static class SaveAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane rootPane;
		private JFileChooser chooser;

		public SaveAction(ThingMLRootPane demo, ImageIcon icon) {
			super(null, icon);
			this.rootPane = demo;
			if (icon == null)
				putValue(NAME, "Save...");
			putValue(MNEMONIC_KEY, new Integer('S'));
			int mods = demo.getToolkit().getMenuShortcutKeyMask();
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_S, mods);
			putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent event) {
			if (chooser == null) {
				chooser = new JFileChooser();
				chooser.setFileFilter(new ExtensionFileFilter(
						"ThingML Source Files", "thingml"));
			}

			int rc = chooser.showSaveDialog(rootPane);
			if (rc == JFileChooser.APPROVE_OPTION) {
				try {
					// Create file
					String filename = chooser.getSelectedFile()
							.getCanonicalPath();
					rootPane.setCurrentFilePath(filename);
					FileWriter fstream = new FileWriter(filename);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(rootPane.getCurrentTextArea().getText());
					// Close the output stream
					out.close();
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			}
		}

	}

	/**
	 * Changes the look and feel of the demo application.
	 */
	static class LookAndFeelAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private LookAndFeelInfo info;
		private ThingMLRootPane demo;

		public LookAndFeelAction(ThingMLRootPane demo, LookAndFeelInfo info) {
			putValue(NAME, info.getName());
			this.demo = demo;
			this.info = info;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				UIManager.setLookAndFeel(info.getClassName());
				SwingUtilities.updateComponentTreeUI(demo);
			} catch (RuntimeException re) {
				throw re; // FindBugs
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Changes the language being edited and installs appropriate language
	 * support.
	 */
	static class StyleAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ThingMLRootPane rootPane;
		private String res;
		private String style;

		public StyleAction(ThingMLRootPane rootPane, String name, String res,
				String style) {
			putValue(NAME, name);
			this.rootPane = rootPane;
			this.res = res;
			this.style = style;
		}

		public void actionPerformed(ActionEvent e) {
			// rootPane.setText(res, style);
		}

	}

	/**
	 * Lets the user open a file.
	 */
	static class ArduinoAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane demo;

		public ArduinoAction(ThingMLRootPane demo, ImageIcon icon) {
			super(null, icon);
			this.demo = demo;
			if (icon == null)
				putValue(NAME, "Compile to Arduino");
			putValue(MNEMONIC_KEY, new Integer('A'));
			int mods = demo.getToolkit().getMenuShortcutKeyMask();
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_A, mods);
			putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent e) {
			((ThingMLRootPane) demo).compileAndRunArduino();
		}

	}

	/**
	 * Lets the user open a file.
	 */
	static class SampleAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane rootPane;
		private String title;

		public SampleAction(ThingMLRootPane rootPane, String title) {
			super(title);
			this.rootPane = rootPane;
			this.title = title;
			// putValue(NAME, "Compile to Arduino");
			// putValue(MNEMONIC_KEY, new Integer('A'));
			// int mods = demo.getToolkit().getMenuShortcutKeyMask();
			// KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_A, mods);
			// putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent e) {
			if (title.equals("Blink"))
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/blink.thingml"));
			else if (title.equals("Blink 2 leds"))
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/blink2leds.thingml"));
			else if (title.equals("Blink 4 leds"))
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/blink4leds.thingml"));
			else if (title.equals("Blink frequency"))
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/blink_freq.thingml"));
		}

	}

}