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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.tree.TreeNode;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.fife.rsta.AbstractSourceTree;
import org.fife.rsta.LanguageSupportFactory;
import org.fife.rsta.demo.Actions;
import org.fife.rsta.demo.ExtensionFileFilter;
import org.fife.rsta.thingml.ThingMLCellRenderer;
import org.fife.rsta.thingml.ThingMLParser;
import org.fife.rsta.thingml.tree.ThingMLOutlineTree;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.ThingmlPackage;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResource;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResourceFactory;
import org.sintef.thingml.resource.thingml.ui.ThingmlCodeCompletionHelper;
import org.sintef.thingml.resource.thingml.ui.ThingmlCompletionProposal;
import org.thingml.cgenerator.CGenerator;

public class ThingMLRootPane extends JRootPane implements HyperlinkListener,
		SyntaxConstants, Actions {

	private static final long serialVersionUID = -1417936399179936282L;
	private JScrollPane treeSP;
	private AbstractSourceTree tree;
	private AutoCompletion autoCodeCompletion;
	private JLabel statusLabel, cursorLabel;
	private JTabbedPane tabbedPane;

	private JCheckBoxMenuItem cellRenderingItem;
	private JCheckBoxMenuItem showDescWindowItem;
	private JCheckBoxMenuItem paramAssistanceItem;

	private List<RSyntaxTextArea> textAreas;
	private List<Properties> properties;
	private List<String> filePaths;
	private List<Thread> threadList;
	private String arduinoPath;
	private String configFileName;
	private String propertiesPath;
	private String statusLineMessage;
	private boolean safeToClose;

	public ThingMLRootPane() {
		this(null);
	}

	// TODO: Move all Actions to action file
	// TODO: You can override files without question :\
	// TODO: What to do when your're opening the same file in multiple tabs?

	public ThingMLRootPane(CompletionProvider provider) {

		AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory
				.getDefaultInstance();
		atmf.putMapping("text/thingml",
				"org.fife.ui.rsyntaxtextarea.modes.ThingMLTokenMaker");
		TokenMakerFactory.setDefaultInstance(atmf);
		setSafeToClose(true);
		textAreas = new ArrayList<RSyntaxTextArea>();
		properties = new ArrayList<Properties>();
		filePaths = new ArrayList<String>();
		threadList = new ArrayList<Thread>();
		setStatusLineMessage("");

		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				setFocusedTextArea(tabbedPane.getSelectedIndex());
				loadProperties(tabbedPane.getSelectedIndex());
				refreshSourceTree();
			}
		});

		JTree dummy = new JTree((TreeNode) null);
		treeSP = new JScrollPane(dummy);
		final JSplitPane codeSplitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, treeSP, tabbedPane);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				codeSplitPane.setDividerLocation(150);
			}
		});
		codeSplitPane.setContinuousLayout(true);

		contentPane.setLayout(new BorderLayout());
		contentPane.add(codeSplitPane, BorderLayout.CENTER);

		contentPane.add(createToolBar(), BorderLayout.NORTH);
		contentPane.add(createStatusPanel(), BorderLayout.SOUTH);
		setJMenuBar(createMenuBar());

		newFile();
		setFocusedTextArea(0);
		refreshSourceTree();
	}

	/**
	 * Focuses the text area.
	 */
	public void focusCurrentTextArea() {
		getCurrentTextArea().requestFocusInWindow();
	}

	private void refreshSourceTree() {
		refreshSourceTree(getCurrentTextArea());
	}

	/**
	 * Displays a tree view of the current source code, if available for the
	 * current programming language.
	 */
	private void refreshSourceTree(RSyntaxTextArea textArea) {

		if (tree != null) {
			tree.uninstall();
			treeSP.remove(tree);
		}

		String language = textArea.getSyntaxEditingStyle();
		if (SyntaxConstants.SYNTAX_STYLE_THINGML.equals(language)) {
			tree = new ThingMLOutlineTree();
		} else {
			tree = null;
		}

		if (tree != null) {
			tree.listenTo(textArea);
			treeSP.setViewportView(tree);
			treeSP.revalidate();
		}
	}

	private RSyntaxTextArea createAndAddTextArea(String title,
			CompletionProvider provider) {
		RSyntaxTextArea rSyntaxTextArea = createTextArea();
		addTextArea(title, rSyntaxTextArea, false);

		// Install auto-completion onto our text area
		if (provider == null)
			provider = createCompletionProvider();

		autoCodeCompletion = new AutoCompletion(provider);
		autoCodeCompletion.setListCellRenderer(new ThingMLCellRenderer());
		autoCodeCompletion.setShowDescWindow(true);
		autoCodeCompletion.setParameterAssistanceEnabled(true);
		autoCodeCompletion.install(rSyntaxTextArea);

		rSyntaxTextArea.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Event.CTRL_MASK),
				new ContentAssistAction(this, autoCodeCompletion));

		return rSyntaxTextArea;
	}

	/**
	 * Returns the provider to use when editing code.
	 * 
	 * @return The provider.
	 * @see #createCommentCompletionProvider()
	 * @see #createStringCompletionProvider()
	 */
	private CompletionProvider createCodeCompletionProvider() {

		DefaultCompletionProvider defaultCompletionProvider = new DefaultCompletionProvider();
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader
				.getResourceAsStream("data/thingml.xml");
		try {
			if (inputStream != null) {
				defaultCompletionProvider.loadFromXML(inputStream);
				inputStream.close();
			} else {
				defaultCompletionProvider.loadFromXML(new File(
						"data/thingml.xml"));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return defaultCompletionProvider;
	}

	/**
	 * Returns the provider to use when in a comment.
	 * 
	 * @return The provider.
	 * @see #createCodeCompletionProvider()
	 * @see #createStringCompletionProvider()
	 */
	private CompletionProvider createCommentCompletionProvider() {
		DefaultCompletionProvider cp = new DefaultCompletionProvider();
		cp.addCompletion(new BasicCompletion(cp, "TODO:", "A to-do reminder"));
		cp.addCompletion(new BasicCompletion(cp, "FIXME:",
				"A bug that needs to be fixed"));
		return cp;
	}

	/**
	 * Returns the completion provider to use when the caret is in a string.
	 * 
	 * @return The provider.
	 * @see #createCodeCompletionProvider()
	 * @see #createCommentCompletionProvider()
	 */
	private CompletionProvider createStringCompletionProvider() {
		DefaultCompletionProvider cp = new DefaultCompletionProvider();
		cp.addCompletion(new BasicCompletion(cp, "\\n", "Newline",
				"Prints a newline"));
		return cp;
	}

	/**
	 * Creates the completion provider for a C editor. This provider can be
	 * shared among multiple editors.
	 * 
	 * @return The provider.
	 */
	private CompletionProvider createCompletionProvider() {

		// Create the provider used when typing code.
		CompletionProvider codeCP = createCodeCompletionProvider();

		// The provider used when typing a string.
		CompletionProvider stringCP = createStringCompletionProvider();

		// The provider used when typing a comment.
		CompletionProvider commentCP = createCommentCompletionProvider();

		// Create the "parent" completion provider.
		LanguageAwareCompletionProvider provider = new LanguageAwareCompletionProvider(
				codeCP);
		provider.setStringCompletionProvider(stringCP);
		provider.setCommentCompletionProvider(commentCP);

		return provider;

	}

	private JPanel createStatusPanel() {
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

		cursorLabel = new JLabel("Char: 0");
		cursorLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(cursorLabel);
		statusLabel = new JLabel("");
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusPanel.add(statusLabel);

		return statusPanel;
	}

	private JMenuBar createMenuBar() {

		JMenuBar mb = new JMenuBar();
		ButtonGroup bg = new ButtonGroup();

		JMenu menu = new JMenu("File");
		menu.add(new JMenuItem(new NewFileAction(this, null)));
		menu.add(new JMenuItem(new OpenAction(this, null)));
		menu.add(new JMenuItem(new SaveAction(this, null)));
		menu.add(new JMenuItem(new SaveAsAction(this, null)));
		menu.add(new JMenuItem(new ProjectAction(this)));

		menu.addSeparator();
		JMenu examples = new JMenu("Samples");
		examples.add(new JMenuItem(new SampleAction(this, "Blink")));
		examples.add(new JMenuItem(new SampleAction(this, "Blink 2 leds")));
		examples.add(new JMenuItem(new SampleAction(this, "Blink 4 leds")));
		examples.add(new JMenuItem(new SampleAction(this, "Blink frequency")));
		examples.add(new JMenuItem(new SampleAction(this, "Big robot")));
		menu.add(examples);

		menu.addSeparator();

		menu.add(new JMenuItem(new ExitAction(this)));
		mb.add(menu);

		menu = new JMenu("Edit");
		menu.add(new JMenuItem(new CloseTab(this)));
		mb.add(menu);

		menu = new JMenu("View");
		Action renderAction = new FancyCellRenderingAction();
		cellRenderingItem = new JCheckBoxMenuItem(renderAction);
		cellRenderingItem.setSelected(true);
		menu.add(cellRenderingItem);
		Action descWindowAction = new ShowDescWindowAction();
		showDescWindowItem = new JCheckBoxMenuItem(descWindowAction);
		showDescWindowItem.setSelected(true);
		menu.add(showDescWindowItem);
		Action paramAssistanceAction = new ParameterAssistanceAction();
		paramAssistanceItem = new JCheckBoxMenuItem(paramAssistanceAction);
		paramAssistanceItem.setSelected(true);
		menu.add(paramAssistanceItem);
		mb.add(menu);

		menu = new JMenu("LookAndFeel");
		bg = new ButtonGroup();
		LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < infos.length; i++) {
			addItem(new LookAndFeelAction(this, infos[i]), bg, menu);
		}
		mb.add(menu);

		menu = new JMenu("Help");
		menu.add(new JMenuItem(new AboutAction(this)));
		mb.add(menu);

		return mb;

	}

	private JToolBar createToolBar() {
		JToolBar toolbar = new JToolBar("Toolbar", JToolBar.HORIZONTAL);

		JButton button = new JButton();
		button.setAction(new NewFileAction(this, new ImageIcon(getClass()
				.getClassLoader().getResource("images/new.png"))));
		button.setToolTipText("New");
		toolbar.add(button);
		button = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource("images/open.png")));
		button.setAction(new OpenAction(this, new ImageIcon(getClass()
				.getClassLoader().getResource("images/open.png"))));
		button.setToolTipText("Open");
		toolbar.add(button);
		button = new JButton();
		button.setAction(new SaveAction(this, new ImageIcon(getClass()
				.getClassLoader().getResource("images/save.png"))));
		button.setToolTipText("Save");
		toolbar.add(button);

		toolbar.addSeparator();

		button = new JButton();
		button.setAction(new DefaultEditorKit.CutAction());
		button.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
				"images/cut.png")));
		button.setText("");
		button.setToolTipText("Cut");
		toolbar.add(button);
		button = new JButton();
		button.setAction(new DefaultEditorKit.CopyAction());
		button.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
				"images/copy.png")));
		button.setText("");
		button.setToolTipText("Copy");
		toolbar.add(button);
		button = new JButton();
		button.setAction(new DefaultEditorKit.PasteAction());
		button.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
				"images/paste.png")));
		button.setText("");
		button.setToolTipText("Paste");
		toolbar.add(button);

		toolbar.addSeparator();

		button = new JButton("To Arduino");
		button.setAction(new ArduinoAction(this, null));
		toolbar.add(button);

		button = new JButton("Format");
		button.setAction(new FormatAction(this, null));
		toolbar.add(button);

		return toolbar;
	}

	/**
	 * Creates the text area for this application.
	 * 
	 * @return The text area.
	 */
	private RSyntaxTextArea createTextArea() {
		RSyntaxTextArea textArea = new RSyntaxTextArea(30, 100);
		LanguageSupportFactory.get().register(textArea);
		textArea.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent event) {
				setStatusline(0, event.getDot());
			}
		});

		textArea.getDocument().addDocumentListener(new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {
			}

			int saveCount = 0;

			public void changedUpdate(DocumentEvent e) {
				if (getTabTitle().equals("Untitle")
						|| getTabTitle().equals("Untitle*")) {
					// TODO: User has probably just started a file, no need to
					// save, so just
					// return a null. Should be switched out with a temporarily
					// save file.
					setStatusLineMessage("Save file to enable auto-save.");
					return;
				} else if (saveCount == 30) {
					saveFile();
					saveCount = 0;
				} else
					saveCount++;
				if (!isTabSaved())
					addSaveStateToTitle(getSelectedTabIndex());
			}

		});

		textArea.setCaretPosition(0);
		textArea.addHyperlinkListener(this);
		textArea.requestFocusInWindow();
		textArea.setMarkOccurrences(true);
		textArea.setCodeFoldingEnabled(true);
		ToolTipManager.sharedInstance().registerComponent(textArea);
		return textArea;
	}

	/**
	 * Toggles whether the completion window uses "fancy" rendering.
	 */
	private class FancyCellRenderingAction extends AbstractAction {

		private static final long serialVersionUID = 4095659185154995286L;

		public FancyCellRenderingAction() {
			putValue(NAME, "Fancy Cell Rendering");
		}

		public void actionPerformed(ActionEvent e) {
			boolean fancy = cellRenderingItem.isSelected();
			autoCodeCompletion
					.setListCellRenderer(fancy ? new ThingMLCellRenderer()
							: null);
		}

	}

	/**
	 * Toggles whether parameter assistance is enabled.
	 */
	private class ParameterAssistanceAction extends AbstractAction {

		private static final long serialVersionUID = 8083581458377031473L;

		public ParameterAssistanceAction() {
			putValue(NAME, "Function Parameter Assistance");
		}

		public void actionPerformed(ActionEvent e) {
			boolean enabled = paramAssistanceItem.isSelected();
			autoCodeCompletion.setParameterAssistanceEnabled(enabled);
		}

	}

	/**
	 * Toggles whether the description window is visible.
	 */
	private class ShowDescWindowAction extends AbstractAction {
		private static final long serialVersionUID = -9093098557558432695L;

		public ShowDescWindowAction() {
			putValue(NAME, "Show Description Window");
		}

		public void actionPerformed(ActionEvent e) {
			boolean show = showDescWindowItem.isSelected();
			autoCodeCompletion.setShowDescWindow(show);
		}

	}

	private void showNoConfigError() {
		// TODO
		JOptionPane.showMessageDialog(this,
				"Can\'t find your config file. Have you added it in your properties?", "No config-file error",
				JOptionPane.WARNING_MESSAGE);
		// ProjectDialog dialog = new ProjectDialog(
		// (ThingMLApp) SwingUtilities.getWindowAncestor(this),
		// getCurrentProperties());
		// dialog.setLocationRelativeTo(this);
		// dialog.setVisible(true);
	}

	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			URL url = e.getURL();
			if (url == null) {
				UIManager.getLookAndFeel().provideErrorFeedback(null);
			} else {
				JOptionPane.showMessageDialog(this,
						"URL clicked:\n" + url.toString());
			}
		}
	}

	public void compileAndRunArduino() {
		if (getTabTitle().equals("Untitle") || getTabTitle().equals("Untitle*")) {
			setStatusLineMessage("Missing config file. Save and add config file in properties.");
			showNoConfigError();
			return;
		}
		saveFile();

		ThingmlResource resource = getThingmlResource();
		if (resource == null) {
			showNoConfigError();
			return;
		}

		// TODO: Save when compiling, and report when there are errors and ask
		// if the want to compile or fix errors
		// I also need to check for missing config

		ThingMLModel model = (ThingMLModel) resource.getContents().get(0);

		CGenerator.compileAndRunArduino(model, getArduinoPath(),
				getArduinoPath() + "/lib");
	}

	public void uninstallSourceTree() {
		tree.uninstall();
		tree = null;
	}

	public void purgeStatusLine() {
		if (cursorLabel == null)
			return;
		int result = cursorLabel.getText().indexOf("--");
		if (result == -1)
			cursorLabel.setText(cursorLabel.getText() + "-- "
					+ getStatusLineMessage());
		else
			cursorLabel.setText(cursorLabel.getText().substring(0, result)
					+ "-- " + getStatusLineMessage());
	}

	public ThingmlResource getThingmlResource() {

		// Register the generated package and the XMI Factory
		EPackage.Registry.INSTANCE.put(ThingmlPackage.eNS_URI,
				ThingmlPackage.eINSTANCE);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				"thingml", new ThingmlResourceFactory());

		// Load the model
		ResourceSet rs = new ResourceSetImpl();
		if (getTabTitle().equals("Untitle") || getTabTitle().equals("Untitle*")) {
			// TODO: User has probably just started a file, no need to save, so
			// just
			// return a null. Should be switched out with a temporarily save
			// file.
			return null;
		} else if (getConfigFilePath() == null) {
			JFileChooser chooser = new JFileChooser(
					"./src/main/resources/samples");
			chooser.setFileFilter(new ExtensionFileFilter(
					"ThingML Source Files", "thingml"));

			int rc = chooser.showOpenDialog(this);
			if (rc == JFileChooser.APPROVE_OPTION) {
				try {
					setConfigFilePath(chooser.getSelectedFile()
							.getCanonicalPath());
					// TODO Check in advance if it's the correct config file, or
					// let the user know that something went wrong when they are
					// trying to compile, plus there is one config file for each
					// compile. Maybe there is a pattern or a rule we can make
					// that the config file should be in _* folder, and the
					// config should have the same name?
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.err
						.println("User pressed cancel. Ready for an exception?");
				// TODO Handle user not wanting to compile after all
			}
		}

		URI xmiuri = URI.createFileURI(getConfigFilePath());
		Resource resource = rs.createResource(xmiuri);
		try {
			resource.load(null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		assert (resource.getContents().size() > 0);
		assert (resource.getContents().get(0) instanceof ThingMLModel);

		return (ThingmlResource) resource;
	}

	public String getFilePath(int index) {
		return filePaths.get(index);
	}

	public String getConfigFilePath() {
		return configFileName;
	}

	public String getArduinoPath() {
		return arduinoPath;
	}

	public int getSelectedTabIndex() {
		return tabbedPane.getSelectedIndex();
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public Properties getCurrentProperties() {
		return properties.get(tabbedPane.getSelectedIndex());
	}

	public String getPropertiesPath() {
		return propertiesPath;
	}

	public int getPropertiesSize() {
		return properties.size();
	}

	public String getTabTitle() {
		return getTabbedPane().getTitleAt(getSelectedTabIndex());
	}

	public String getTabTitle(int index) {
		return getTabbedPane().getTitleAt(index);
	}

	public Thread getLastThread() {
		return threadList.get(threadList.size() - 1);
	}

	public int getCaretPosition() {
		return getCurrentTextArea().getCaretPosition();
	}

	public String getStatusLineMessage() {
		return statusLineMessage;
	}

	public RSyntaxTextArea getCurrentTextArea() {
		return textAreas.get(getSelectedTabIndex());
	}

	public void setCurrentTextArea(RSyntaxTextArea rSyntaxTextArea) {
		// currentTextArea = null;// rSyntaxTextArea;
	}

	private void setStatusline(int line, int offset) {
		if (cursorLabel != null)
			cursorLabel.setText("Char: " + offset + " -- "
					+ getStatusLineMessage());
	}

	public void setStatusLineMessage(String message) {
		statusLineMessage = message;
		purgeStatusLine();
	}

	public void setPropertiesPath(String propertiesPath) {
		this.propertiesPath = propertiesPath;
	}

	public void setConfigFilePath(String configFileName) {
		this.configFileName = configFileName;
	}

	public void setFilePath(int index, String path) {
		((ThingMLParser) getCurrentTextArea().getParser(1)).setFilePath(path);
		filePaths.set(index, path);
	}

	public void setArduinoPath(String arudino) {
		this.arduinoPath = arudino;
	}

	public void setTabTitle(String title) {
		tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), title);
	}

	public void setFocusedTextArea(int i) {
		tabbedPane.setSelectedIndex(i);
		focusCurrentTextArea();
	}

	public void setSafeToClose(boolean safe) {
		this.safeToClose = safe;
	}

	public void addPane(RTextArea rTextArea) {
		addPane("", rTextArea);
	}

	private void addItem(Action a, ButtonGroup bg, JMenu menu) {
		JRadioButtonMenuItem item = new JRadioButtonMenuItem(a);
		bg.add(item);
		menu.add(item);
	}

	public void addPane(String title, RTextArea rTextArea) {
		RTextScrollPane scrollPane = new RTextScrollPane(rTextArea, true);
		scrollPane.setIconRowHeaderEnabled(true);
		// scrollPane.getGutter().setBookmarkingEnabled(true);

		tabbedPane.add(title, scrollPane);
	}

	public boolean addThread(Thread thread) {
		return threadList.add(thread);
	}

	public void addSaveStateToTitle(int index) {
		String title = tabbedPane.getTitleAt(index);
		if (!title.endsWith("*"))
			tabbedPane.setTitleAt(index, title + "*");
	}

	public boolean addFilePath(String path) {
		((ThingMLParser) getCurrentTextArea().getParser(1)).setFilePath(path);
		return filePaths.add(path);
	}

	public void addTabTitle(int index, String title) {
		tabbedPane.setTitleAt(index, title);
	}

	public int addTextArea(String title, RSyntaxTextArea rSyntaxTextArea,
			boolean background) {
		textAreas.add(rSyntaxTextArea);
		addPane(title, rSyntaxTextArea);
		if (!background)
			setFocusedTextArea(textAreas.size() - 1);
		return textAreas.size() - 1;
	}

	public void removeSaveStateFromTitle(int index) {
		String title = tabbedPane.getTitleAt(index);
		if (title.endsWith("*"))
			tabbedPane
					.setTitleAt(index, title.substring(0, title.length() - 1));
	}

	public RTextArea removeTextArea(int index) {
		return textAreas.remove(index);
	}

	public Properties removeProperties(int index) {
		return properties.remove(index);
	}

	public void removeTabbedPane(int index) {
		tabbedPane.removeTabAt(index);
	}

	public Thread removeThread(int index) {
		return threadList.remove(index);
	}

	public boolean isSafeToClose() {
		return safeToClose;
	}

	public boolean isTabSaved() {
		return getTabTitle().endsWith("*");
	}

	public boolean isTabsSaved() {
		for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
			if (getTabTitle(i).endsWith("*")) {
				setSafeToClose(false);
				return false;
			}
		}

		setSafeToClose(true);
		return true;
	}

	public boolean isThereAliveThreads() {
		for (Thread thread : threadList)
			if (thread.isAlive())
				return true;

		return false;
	}

	private void loadProperties(int index) {
		try {
			Properties prop = this.properties.get(index);
			setConfigFilePath(prop.getProperty("config", ""));
			setArduinoPath(prop.getProperty("arduino", ""));
		} catch (IndexOutOfBoundsException e) {

		}
	}

	public void putProperties(Properties properties) {
		this.properties.add(properties);
	}

	public void formatCurrentTextarea() {
		RTextArea rTextArea = getCurrentTextArea();
		rTextArea.setText(simpleFormatting(rTextArea.getText()));
	}

	public void saveTabs() {
		for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
			if (getTabTitle(i).endsWith("*")) {
				setFocusedTextArea(i);
				saveFile(i);
			}
		}
		setSafeToClose(true);
	}

	public String simpleFormatting(String input) {
		String output = "";
		int indent = 0;
		String[] array = input.split("\n");
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();

			if (array[i].contains("}") || array[i].equals("end"))
				indent--;

			for (int x = 0; x < indent; x++)
				if (!array[i].contains("\t"))
					output += "\t";

			// Stupid java and stupid end of line
			if (array[i].contains("{") || array[i].matches("^.*do$"))
				indent++;

			output += array[i];
			if ((i + 1) < array.length)
				output += "\n";
		}

		return output;
	}

	public void openFile(File file) {

		RSyntaxTextArea rSyntaxTextArea = createAndAddTextArea(file.getName(),
				null);

		try {
			StringBuilder fileContents = new StringBuilder((int) file.length());
			Scanner scanner = new Scanner(file);
			String lineSeparator = System.getProperty("line.separator");

			try {
				while (scanner.hasNextLine()) {
					fileContents.append(scanner.nextLine() + lineSeparator);
				}
			} finally {
				scanner.close();
			}

			rSyntaxTextArea.setText(fileContents.toString());
			rSyntaxTextArea.setSyntaxEditingStyle(SYNTAX_STYLE_THINGML);
			rSyntaxTextArea.setCaretPosition(0);
			addFilePath(file.getCanonicalPath());
			if (treeSP != null)
				refreshSourceTree();
			removeSaveStateFromTitle(getSelectedTabIndex());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			UIManager.getLookAndFeel().provideErrorFeedback(this);
			return;
		}
	}

	public void newFile() {
		RSyntaxTextArea rSyntaxTextArea = createAndAddTextArea("Untitle", null);
		rSyntaxTextArea.setSyntaxEditingStyle(SYNTAX_STYLE_THINGML);
		rSyntaxTextArea.setCaretPosition(0);
		addFilePath(null);
		putProperties(new Properties());
		removeSaveStateFromTitle(getSelectedTabIndex());
		if (treeSP != null)
			refreshSourceTree();
	}

	public void saveFile() {
		saveFile(getSelectedTabIndex());
	}

	public void saveFile(int index) {
		final String path = getFilePath(index);
		if (path == null) {
			new SaveAsAction(this, null).actionPerformed(null);
			return;
		}
		addThread(new Thread(new Runnable() {

			public void run() {
				try {
					FileWriter fstream = new FileWriter(path);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(getCurrentTextArea().getText());
					out.close();
					removeSaveStateFromTitle(getSelectedTabIndex());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));
		getLastThread().start();
		try {
			getLastThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void saveAsFile(final String filePath, final String fileName) {
		addThread(new Thread(new Runnable() {

			public void run() {
				try {
					FileWriter fstream = new FileWriter(filePath);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(getCurrentTextArea().getText());
					out.close();
					// TODO: addCurrentFilePath(tab, filePath);
					int tab = tabbedPane.getSelectedIndex();
					addTabTitle(tab, fileName);
					setFilePath(tab, filePath);
					removeSaveStateFromTitle(getSelectedTabIndex());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));
		getLastThread().start();
		try {
			getLastThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
