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

import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.ThingMLModel;
import org.thingml.cgenerator.CGenerator;
// import org.thingml.graphexport.test.StandaloneParserTestLoadFile;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Stand-alone version of the demo.
 * 
 * @author Robert Futrell
 * @version 1.0
 */
public class DemoApp extends JFrame {

	private static final long serialVersionUID = -3237525402614050486L;

	public DemoApp() throws IOException {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("RSTA Language Support ThingML Demo Application");
		setRootPane(new DemoRootPane());

		//		StandaloneParserTestLoadFile file = new StandaloneParserTestLoadFile("/home/kyrremann/workspace/ThingML-Editor/ThingMLDemo/res/examples/thingmltest.thingml");
		//		file.runTest();
		// System.out.println(file);
		
		// GraphicsEnvironment env = GraphicsEnvironment
		// .getLocalGraphicsEnvironment();
		// GraphicsDevice dev = env.getDefaultScreenDevice();
		// setResizable(true);
		// setUndecorated(true);
		// dev.setFullScreenWindow(this);
		
		pack();
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
			((DemoRootPane) getRootPane()).focusTextArea();
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
					new DemoApp().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
