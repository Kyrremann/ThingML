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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProjectDialog extends JDialog {

	private static final long serialVersionUID = 7979467489948854312L;
	private JPanel jPanel;

	public ProjectDialog(ThingMLApp parent, Properties properties) {
		super(parent);
		setResizable(false);
		// TODO: Enable saving and browsing for the correct files and
		// directories.

		jPanel = new JPanel(new GridBagLayout());
		GridBagConstraints bagConstraints = new GridBagConstraints();

		bagConstraints.gridx = 0;
		JLabel config = new JLabel("Configuration file: ");
		bagConstraints.gridy = 0;
		jPanel.add(config, bagConstraints);

		JLabel arduino = new JLabel("Arduino dir: ");
		bagConstraints.gridy = 1;
		jPanel.add(arduino, bagConstraints);

		JLabel thingML = new JLabel("ThingML dir: ");
		bagConstraints.gridy = 2;
		jPanel.add(thingML, bagConstraints);

		bagConstraints.gridx = 1;
		bagConstraints.fill = GridBagConstraints.HORIZONTAL;
		JTextField configPath = new JTextField(20);
		configPath.setText(properties.getProperty("config", ""));
		bagConstraints.gridy = 0;
		jPanel.add(configPath, bagConstraints);

		JTextField arduinoPath = new JTextField();
		arduinoPath.setText(properties.getProperty("arduino", ""));
		bagConstraints.gridy = 1;
		jPanel.add(arduinoPath, bagConstraints);

		JTextField thingMLPath = new JTextField();
		thingMLPath.setText(properties.getProperty("thingml", ""));
		bagConstraints.gridy = 2;
		jPanel.add(thingMLPath, bagConstraints);

		setContentPane(jPanel);
		setTitle("ThingML Project File");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pack();
	}
}
