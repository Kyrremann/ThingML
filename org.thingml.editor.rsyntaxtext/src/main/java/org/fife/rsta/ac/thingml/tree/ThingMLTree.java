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
package org.fife.rsta.ac.thingml.tree;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.fife.rsta.ac.thingml.syntaxtree.Program;
import org.sintef.thingml.Event;
import org.sintef.thingml.Expression;
import org.sintef.thingml.Message;
import org.sintef.thingml.Port;
import org.sintef.thingml.State;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.Transition;
import org.sintef.thingml.impl.IntegerLiteralImpl;
import org.sintef.thingml.impl.MessageImpl;
import org.sintef.thingml.impl.PropertyAssignImpl;
import org.sintef.thingml.impl.ReceiveMessageImpl;
import org.sintef.thingml.impl.RequiredPortImpl;
import org.sintef.thingml.impl.SendActionImpl;
import org.sintef.thingml.impl.StateImpl;
import org.sintef.thingml.impl.StateMachineImpl;
import org.sintef.thingml.impl.StringLiteralImpl;
import org.sintef.thingml.impl.ThingImpl;
import org.sintef.thingml.impl.ThingMLModelImpl;
import org.sintef.thingml.impl.TransitionImpl;
import org.sintef.thingml.resource.thingml.IThingmlLocationMap;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResource;

public class ThingMLTree {

	private ThingMLTreeNode root;
	private TreeIterator<EObject> treeIterator;
	private IThingmlLocationMap locationMap;
	private Resource resource;

	public ThingMLTree(ThingmlResource resource) {
		this.locationMap = resource.getLocationMap();
		this.treeIterator = resource.getAllContents();
		this.resource = resource;
		createAST();
	}

	private void createAST() {
		EObject node = treeIterator.next();
		findTypeAndAddNode(root, node);
	}

	private void findTypeAndAddNode(ThingMLTreeNode root, EObject object) {
		findTypeAndAddNode(root, object, "");
	}

	private void findTypeAndAddNode(ThingMLTreeNode root, EObject object,
			String annotation) {
		ThingMLTreeNode node;
		if (object instanceof ThingMLModelImpl) {
			ThingMLModelImpl impl = (ThingMLModelImpl) object;
			// System.out.printf("Configs: %s\nImports: %s\nTypes: %s\n",
			// impl.getConfigs(), impl.getImports(), impl.getTypes());
			this.root = new ThingMLTreeNode("ROOT");
			// TODO: Find out where import URL are hidden
			for (EObject type : impl.getTypes()) {
				findTypeAndAddNode(this.root, type);
			}

		} else if (object instanceof ThingImpl) {
			ThingImpl impl = (ThingImpl) object;
			node = new ThingMLTreeNode(impl.getName());
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			for (Port port : impl.getPorts()) {
				findTypeAndAddNode(node, port);
			}
			for (StateMachine stateMachine : impl.getBehaviour()) {
				findTypeAndAddNode(node, stateMachine);
			}
			root.add(node);

		} else if (object instanceof RequiredPortImpl) {
			RequiredPortImpl impl = (RequiredPortImpl) object;
			node = new ThingMLTreeNode("Port: " + impl.getName());
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			// System.out.printf("Owner: %s\nSends: %s\nReceives: %s\nAnnotations: %s\n",
			// impl.getOwner(), impl.getSends(), impl.getReceives(),
			// impl.getAnnotations());
			for (Message message : impl.getSends()) {
				findTypeAndAddNode(node, message, "!");
			}
			for (Message message : impl.getReceives()) {
				findTypeAndAddNode(node, message, "?");
			}
			root.add(node);

		} else if (object instanceof StateMachineImpl) {
			StateMachineImpl impl = (StateMachineImpl) object;
			node = new ThingMLTreeNode(impl.getName() + ": init "
					+ impl.getInitial().getName());
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			// node.add(new ThingMLTreeNode("init " +
			// impl.getInitial().getName()));
			for (State state : impl.getSubstate()) {
				findTypeAndAddNode(node, state);
			}
			root.add(node);

		} else if (object instanceof StateImpl) {
			StateImpl impl = (StateImpl) object;
			node = new ThingMLTreeNode(impl.getName());
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			// System.out.printf("Entry: %s\nExit: %s\nIncoming: %s\nOutgoing: %s\nProperties: %s\nInternal: %s\nAnnotation: %s\n",
			// impl.getEntry(), impl.getExit(), impl.getIncoming(),
			// impl.getOutgoing(), impl.getProperties() , impl.getInternal(),
			// impl.getAnnotations());
			// TODO: Entry and Exit points, not giving proper information
			if (impl.getEntry() != null)
				findTypeAndAddNode(node, impl.getEntry(), "on entry ");
			if (impl.getExit() != null)
				findTypeAndAddNode(node, impl.getExit(), "on exit ");
			for (Transition transition : impl.getOutgoing()) {
				findTypeAndAddNode(node, transition);
			}
			root.add(node);

		} else if (object instanceof TransitionImpl) {
			TransitionImpl impl = (TransitionImpl) object;
			// node = new ThingMLTreeNode("transitions");
			// System.out.printf("Name: %s\nTarget: %s\nSource: %s\nGuard: %s\nEvent: %s\nBefore: %s\nAnnotations: %s\nAfter: %s\nAction: %s\n",
			// impl.getName(), impl.getTarget(), impl.getSource(),
			// impl.getGuard(), impl.getEvent(), impl.getBefore(),
			// impl.getAnnotations(), impl.getAfter(), impl.getAction());
			// node.add(new ThingMLTreeNode("-> " +
			// impl.getTarget().getName()));
			node = new ThingMLTreeNode("-> " + impl.getTarget().getName());
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			for (Event event : impl.getEvent()) {
				findTypeAndAddNode(node, event);
			}
			findTypeAndAddNode(node, impl.getAction());
			root.add(node);

		} else if (object instanceof ReceiveMessageImpl) {
			ReceiveMessageImpl impl = (ReceiveMessageImpl) object;
			// System.out.printf("Name: %s\nPort: %s\nMessage: %s\n",
			// impl.getName(), impl.getPort(), impl.getMessage());
			node = new ThingMLTreeNode("Receive message");
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			root.add(node);

		} else if (object instanceof MessageImpl) {
			MessageImpl impl = (MessageImpl) object;
			node = new ThingMLTreeNode(annotation
					+ impl.eProxyURI().toString().substring(51));
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			root.add(node);

		} else if (object instanceof SendActionImpl) {
			SendActionImpl impl = (SendActionImpl) object;
			// System.out.printf("Message: %s\nParameters: %s\nPort: %s\n",
			// impl.getMessage(), impl.getParameters(), impl.getPort());
			if (annotation.length() == 0)
				node = new ThingMLTreeNode("Send action");
			else {
				node = new ThingMLTreeNode(annotation + "send action");
				// System.out.printf("Message: %s\nParameters: %s\nPort: %s\n",
				// impl.getMessage(), impl.getParameters(), impl.getPort());
			}
			for (Expression expression : impl.getParameters()) {
				findTypeAndAddNode(node, expression);
			}
			
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			root.add(node);

		} else if (object instanceof IntegerLiteralImpl) {
			IntegerLiteralImpl impl = (IntegerLiteralImpl) object;
			node = new ThingMLTreeNode(impl.getIntValue() + ": integer");
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			root.add(node);

		} else if (object instanceof StringLiteralImpl) {
			StringLiteralImpl impl = (StringLiteralImpl) object;
			node = new ThingMLTreeNode(impl.getStringValue() + ": string");
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			root.add(node);
			
		} else if (object instanceof PropertyAssignImpl) {
			PropertyAssignImpl impl = (PropertyAssignImpl) object;
			node = new ThingMLTreeNode(impl.getName());
			node.setLine(locationMap.getLine(impl));
			node.setColumn(locationMap.getColumn(impl));
			node.setCharStart(locationMap.getCharStart(impl));
			node.setCharEnd(locationMap.getCharEnd(impl));
			
			root.add(node);
			
		} else {
			System.out.println("Missing node! Node was of type " + object
					+ ". Please add.");
			root.add(new ThingMLTreeNode(annotation + object));
		}
	}

	public ThingMLTreeNode getRoot() {
		return root;
	}

}
