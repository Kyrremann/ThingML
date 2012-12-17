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

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.JTextComponent;

import org.fife.rsta.demo.AboutDialog;
import org.fife.rsta.demo.ThingMLRootPane;
import org.fife.rsta.demo.ExtensionFileFilter;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResource;
import org.sintef.thingml.resource.thingml.ui.ThingmlCodeCompletionHelper;
import org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal;
import org.sintef.thingml.resource.thingml.ui.ThingmlProposalPostProcessor;

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

		private ThingMLRootPane rootPane;

		public ExitAction(ThingMLRootPane rootPane) {
			this.rootPane = rootPane;
			putValue(NAME, "Exit");
			putValue(MNEMONIC_KEY, new Integer('x'));
		}

		public void actionPerformed(ActionEvent e) {
			WindowEvent wev = new WindowEvent((Window) rootPane.getParent(),
					WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
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
				putValue(NAME, "Open File...");
			putValue(MNEMONIC_KEY, new Integer('O'));
			int mods = demo.getToolkit().getMenuShortcutKeyMask();
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_O, mods);
			putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent e) {
			if (chooser == null) {
				chooser = new JFileChooser(
						"./src/main/resources/samples/samples/BigRobot");
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

	static class BrowseFileAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private JFileChooser chooser;
		private ProjectDialog projectDialog;
		private JTextField textField;

		public BrowseFileAction(ProjectDialog projectDialog,
				JTextField textField) {
			super(null);
			this.projectDialog = projectDialog;
			this.textField = textField;
			putValue("NAME", "Browse for file...");
		}

		public void actionPerformed(ActionEvent e) {
			if (chooser == null) {
				chooser = new JFileChooser();
				chooser.setFileFilter(new ExtensionFileFilter(
						"ThingML Source Files", "thingml"));
			}
			int rc = chooser.showOpenDialog(projectDialog);
			if (rc == JFileChooser.APPROVE_OPTION) {
				try {
					textField.setText(chooser.getSelectedFile()
							.getCanonicalPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	static class BrowseDirAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private JFileChooser chooser;
		private ProjectDialog projectDialog;
		private JTextField textField;

		public BrowseDirAction(ProjectDialog projectDialog, JTextField textField) {
			super(null);
			this.projectDialog = projectDialog;
			this.textField = textField;
			putValue("NAME", "Browse for directory...");
		}

		public void actionPerformed(ActionEvent e) {
			if (chooser == null) {
				chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			}

			int rc = chooser.showOpenDialog(projectDialog);
			if (rc == JFileChooser.APPROVE_OPTION) {
				try {
					textField.setText(chooser.getSelectedFile()
							.getCanonicalPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

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

	static class SaveAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane rootPane;

		public SaveAction(ThingMLRootPane demo, ImageIcon icon) {
			super(null, icon);
			this.rootPane = demo;
			if (icon == null)
				putValue(NAME, "Save");
			putValue(MNEMONIC_KEY, new Integer('S'));
			int mods = demo.getToolkit().getMenuShortcutKeyMask();
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_S, mods);
			putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent event) {
			rootPane.saveFile(rootPane.getSelectedTabIndex());
		}
	}

	static class SaveAsAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane rootPane;
		private JFileChooser chooser;

		public SaveAsAction(ThingMLRootPane demo, ImageIcon icon) {
			super(null, icon);
			this.rootPane = demo;
			if (icon == null)
				putValue(NAME, "Save As...");
		}

		public void actionPerformed(ActionEvent event) {
			if (chooser == null) {
				chooser = new JFileChooser();
				chooser.setFileFilter(new ExtensionFileFilter(
						"ThingML Source Files", "thingml"));
				chooser.setDialogTitle("Save as...");
			}

			int rc = chooser.showSaveDialog(rootPane);
			if (rc == JFileChooser.APPROVE_OPTION) {
				try {
					String name = chooser.getSelectedFile().getName();
					// if (!name.matches("\\..+"))
					if (!name.endsWith(".thingml"))
						rootPane.saveAsFile(chooser.getSelectedFile()
								.getCanonicalPath() + ".thingml", name
								+ ".thingml");

					rootPane.saveAsFile(chooser.getSelectedFile()
							.getCanonicalPath(), name);
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			} else if (rc == JFileChooser.CANCEL_OPTION)
				rootPane.setSafeToClose(false);
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

	static class FormatAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane demo;

		public FormatAction(ThingMLRootPane demo, ImageIcon icon) {
			super(null, icon);
			this.demo = demo;
			if (icon == null)
				putValue(NAME, "Format code");
			putValue(MNEMONIC_KEY, new Integer('F'));
			int mods = demo.getToolkit().getMenuShortcutKeyMask();
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F, mods);
			putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent e) {
			((ThingMLRootPane) demo).formatCurrentTextarea();
		}

	}

	static class SampleAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private ThingMLRootPane rootPane;
		private String title;

		public SampleAction(ThingMLRootPane rootPane, String title) {
			super(title);
			this.rootPane = rootPane;
			this.title = title;
		}

		public void actionPerformed(ActionEvent e) {			
			if (title.equals("Blink")) {
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/blink.thingml"));
				loadPropertiesAndSetfiles("src/main/resources/samples/samples/blink.properties");
			} else if (title.equals("Blink 2 leds")) {
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/blink2leds.thingml"));
				loadPropertiesAndSetfiles("src/main/resources/samples/samples/blink2leds.properties");
			} else if (title.equals("Blink 4 leds")) {
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/blink4leds.thingml"));
				loadPropertiesAndSetfiles("src/main/resources/samples/samples/blink4leds.properties");
			} else if (title.equals("Blink frequency")) {
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/blink_freq.thingml"));
				loadPropertiesAndSetfiles("src/main/resources/samples/samples/blink_freq.properties");
			} else if (title.equals("Big robot")) {
				rootPane.openFile(new File(
						"src/main/resources/samples/samples/BigRobot/BigRobot.thingml"));
				loadPropertiesAndSetfiles("src/main/resources/samples/samples/BigRobot/BigRobot.properties");
			}
		}

		private void loadPropertiesAndSetfiles(String name) {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(name));
				rootPane.setPropertiesPath(name);
				rootPane.setConfigFilePath(properties.getProperty("config", ""));
				rootPane.setArduinoPath(properties.getProperty("arduino", ""));
				rootPane.putProperties(properties);
			} catch (IOException e) {
				// TODO: Inform user that file is missing?
				e.getStackTrace();
			}
		}
	}

	public class ProjectAction extends AbstractAction {

		private static final long serialVersionUID = 6015770944269644342L;
		private ThingMLRootPane rootPane;

		public ProjectAction(ThingMLRootPane rootPane) {
			this.rootPane = rootPane;
			putValue(NAME, "Properties");
		}

		public void actionPerformed(ActionEvent arg0) {
			ProjectDialog dialog = new ProjectDialog(
					(ThingMLApp) SwingUtilities.getWindowAncestor(rootPane),
					rootPane.getCurrentProperties());
			dialog.setLocationRelativeTo(rootPane);
			dialog.setVisible(true);
		}
	}

	public class CloseTab extends AbstractAction {

		private static final long serialVersionUID = -7124045554769969122L;
		ThingMLRootPane rootPane;

		public CloseTab(ThingMLRootPane rootPane) {
			this.rootPane = rootPane;
			putValue(NAME, "Close tab");
			putValue(MNEMONIC_KEY, new Integer('W'));
			int mods = rootPane.getToolkit().getMenuShortcutKeyMask();
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_W, mods);
			putValue(ACCELERATOR_KEY, ks);
		}

		public void actionPerformed(ActionEvent arg0) {
			if (rootPane.getTabbedPane().getComponentCount() > 1) {
				if (rootPane.getTabTitle().endsWith("*")) {
					int result = JOptionPane.showConfirmDialog(rootPane,
							"Do you want to save before you close this tab?",
							"Close current tab",
							JOptionPane.YES_NO_CANCEL_OPTION);

					if (result == JOptionPane.YES_OPTION)
						rootPane.saveFile();
					else if (result == JOptionPane.CANCEL_OPTION)
						return; // User don't want to close the tab
				}
				// TODO: When you save a tab, after another tab with the exact
				// same name, it will override the old file :\
				// while (rootPane.isThereAliveThreads())
				// ;

				if (rootPane.getTabTitle().endsWith("*"))
					return; // User pressed cancel somewhere :\

				int selected = rootPane.getSelectedTabIndex();
				rootPane.removeTextArea(selected);
				rootPane.removeProperties(selected);
				rootPane.removeTabbedPane(selected);

			}
		}
	}

	static class ContentAssistAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		private AutoCompletion autoCompletion;
		private ThingMLRootPane rootPane;

		public ContentAssistAction(ThingMLRootPane rootPane,
				AutoCompletion autoCompletion) {
			this.autoCompletion = autoCompletion;
			this.rootPane = rootPane;
		}

		public void actionPerformed(ActionEvent e) {
			ThingmlResource resource = rootPane.getThingmlResource();
			if (resource == null) {// No resources available
				rootPane.setStatusLineMessage("Save file to get better content assist");
				autoCompletion.doCompletion();
				return;
			}
			
			LanguageAwareCompletionProvider provider = (LanguageAwareCompletionProvider) autoCompletion
					.getCompletionProvider();
			DefaultCompletionProvider dcp = (DefaultCompletionProvider) provider
					.getDefaultCompletionProvider();

			ThingmlCodeCompletionHelper helper = new ThingmlCodeCompletionHelper();
			ThingmlCompletionProposal[] proposals = helper
					.computeCompletionProposals(resource,
							rootPane.getCurrentTextArea().getText(),
							rootPane.getCaretPosition());
			ThingmlProposalPostProcessor postProcessor = new ThingmlProposalPostProcessor();
			List<ThingmlCompletionProposal> postProposals = postProcessor
					.process(Arrays.asList(proposals));
			if (postProposals == null)
				postProposals = java.util.Collections.emptyList();

			List<ThingmlCompletionProposal> finalProposals = new ArrayList<ThingmlCompletionProposal>();
			for (ThingmlCompletionProposal proposal : postProposals) {
				if (proposal.getMatchesPrefix())
					finalProposals.add(proposal);
			}

			dcp.clear();
			for (ThingmlCompletionProposal proposal : finalProposals) {
				dcp.addCompletion(new BasicCompletion(dcp, proposal
						.getInsertString()));
			}

			autoCompletion.doCompletion();
		}
	}
	
}