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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SaveAndExitDialog extends JDialog {

	private static final long serialVersionUID = 652860639508564528L;

	public SaveAndExitDialog(final ThingMLApp parent, final ThingMLRootPane rootPane) {
		super(parent);
		setTitle("Save and close");

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		// constraints.fill = GridBagConstraints.HORIZONTAL;
		JLabel label = new JLabel(
				"Are sure you want to close, there are some unsaved files. Maybe you want to save them first?");
		panel.add(label);

		// constraints.fill = GridBagConstraints.VERTICAL;
		constraints.gridy = 1;
		JButton button = new JButton("Save and exit");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				rootPane.saveTabs();
				dispose();
			}
		});
		panel.add(button);

		constraints.gridx = 1;
		button = new JButton("Just exit");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				rootPane.setSafeToClose(true);
				dispose();
				parent.pullThePlug();
			}
		});
		panel.add(button);

		constraints.gridx = 2;
		button = new JButton("Abort");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel.add(button);

		setContentPane(panel);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();
	}
}
