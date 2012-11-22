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
package org.fife.rsta.thingml.tree;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.fife.rsta.thingml.tree.ThingMLTreeNode;
import org.sintef.thingml.Action;
import org.sintef.thingml.ActionBlock;
import org.sintef.thingml.ConditionalAction;
import org.sintef.thingml.ConfigInclude;
import org.sintef.thingml.ConfigPropertyAssign;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Connector;
import org.sintef.thingml.EnumLiteralRef;
import org.sintef.thingml.Enumeration;
import org.sintef.thingml.EnumerationLiteral;
import org.sintef.thingml.EqualsExpression;
import org.sintef.thingml.Event;
import org.sintef.thingml.EventReference;
import org.sintef.thingml.Expression;
import org.sintef.thingml.GreaterExpression;
import org.sintef.thingml.Instance;
import org.sintef.thingml.InstanceRef;
import org.sintef.thingml.IntegerLiteral;
import org.sintef.thingml.InternalTransition;
import org.sintef.thingml.Message;
import org.sintef.thingml.OrExpression;
import org.sintef.thingml.Port;
import org.sintef.thingml.Property;
import org.sintef.thingml.PropertyAssign;
import org.sintef.thingml.ProvidedPort;
import org.sintef.thingml.ReceiveMessage;
import org.sintef.thingml.RequiredPort;
import org.sintef.thingml.SendAction;
import org.sintef.thingml.State;
import org.sintef.thingml.StateMachine;
import org.sintef.thingml.StringLiteral;
import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingMLModel;
import org.sintef.thingml.Transition;
import org.sintef.thingml.VariableAssignment;
import org.sintef.thingml.impl.ThingMLModelImpl;
import org.sintef.thingml.resource.thingml.IThingmlLocationMap;
import org.sintef.thingml.resource.thingml.mopp.ThingmlResource;

public class ThingMLTree {

	private ThingMLTreeNode root;
	private TreeIterator<EObject> treeIterator;
	private IThingmlLocationMap locationMap;

	public ThingMLTree(ThingmlResource resource) {
		this.locationMap = resource.getLocationMap();
		this.treeIterator = resource.getAllContents();
		createAST();
	}

	private void createAST() {
		EObject node;
		while (treeIterator.hasNext()) {
			node = treeIterator.next();

			if (node instanceof ThingMLModel)
				findTypeAndAddNode(root, node);
		}
	}

	private void findTypeAndAddNode(ThingMLTreeNode root, EObject object) {
		findTypeAndAddNode(root, object, "");
	}

	private void findTypeAndAddNode(ThingMLTreeNode root, EObject object,
			String annotation) {
		ThingMLTreeNode node;
		if (object instanceof ThingMLModel) {
			ThingMLModel impl = (ThingMLModel) object;
			if (root == null)
				this.root = new ThingMLTreeNode("ROOT");

			for (ThingMLModel model : impl.getImports()) {
				if (((ThingMLModelImpl) model).eProxyURI() != null)
					this.root.add(new ThingMLTreeNode(
							((ThingMLModelImpl) model).eProxyURI().fragment()
									.replaceFirst("^[A-Z_]*_\\d_", "")));
			}

			for (EObject config : impl.getConfigs())
				findTypeAndAddNode(this.root, config);
			for (EObject type : impl.getTypes())
				findTypeAndAddNode(this.root, type);

		} else if (object instanceof Thing) {
			Thing impl = (Thing) object;
			node = new ThingMLTreeNode(impl.getName() + ": Thing");
			addLocationToNode(node, impl);

			for (Port port : impl.getPorts())
				findTypeAndAddNode(node, port);
			for (StateMachine stateMachine : impl.getBehaviour())
				findTypeAndAddNode(node, stateMachine);
			for (Message message : impl.getMessages())
				findTypeAndAddNode(node, message);

			root.add(node);

		} else if (object instanceof RequiredPort) {
			RequiredPort impl = (RequiredPort) object;
			node = new ThingMLTreeNode(impl.getName() + ": port");
			addLocationToNode(node, impl);

			// System.out.printf("Owner: %s\nSends: %s\nReceives: %s\nAnnotations: %s\n",
			// impl.getOwner(), impl.getSends(), impl.getReceives(),
			// impl.getAnnotations());
			for (Message message : impl.getSends())
				findTypeAndAddNode(node, message, "!");
			for (Message message : impl.getReceives())
				findTypeAndAddNode(node, message, "?");

			root.add(node);

		} else if (object instanceof StateMachine) {
			StateMachine impl = (StateMachine) object;
			node = new ThingMLTreeNode(impl.getName() + ": init "
					+ impl.getInitial().getName());
			addLocationToNode(node, impl);

			// node.add(new ThingMLTreeNode("init " +
			// impl.getInitial().getName()));
			for (State state : impl.getSubstate())
				findTypeAndAddNode(node, state);

			root.add(node);

		} else if (object instanceof State) {
			State impl = (State) object;
			node = new ThingMLTreeNode(impl.getName() + ": State");
			addLocationToNode(node, impl);

			// System.out
			// .printf("Entry: %s\nExit: %s\nIncoming: %s\nOutgoing: %s\nProperties: %s\nInternal: %s\nAnnotation: %s\n",
			// impl.getEntry(), impl.getExit(),
			// impl.getIncoming(), impl.getOutgoing(),
			// impl.getProperties(), impl.getInternal(),
			// impl.getAnnotations());
			// TODO: Entry and Exit points, not giving proper information
			if (impl.getEntry() != null)
				findTypeAndAddNode(node, impl.getEntry(), "on entry ");
			if (impl.getExit() != null)
				findTypeAndAddNode(node, impl.getExit(), "on exit ");
			for (Transition transition : impl.getOutgoing())
				findTypeAndAddNode(node, transition);
			for (InternalTransition internalTransition : impl.getInternal())
				findTypeAndAddNode(node, internalTransition);
			for (Transition transition : impl.getIncoming())
				findTypeAndAddNode(node, transition);

			root.add(node);

		} else if (object instanceof Transition) {
			Transition impl = (Transition) object;
			// node = new ThingMLTreeNode("transitions");
			// System.out.printf("Name: %s\nTarget: %s\nSource: %s\nGuard: %s\nEvent: %s\nBefore: %s\nAnnotations: %s\nAfter: %s\nAction: %s\n",
			// impl.getName(), impl.getTarget(), impl.getSource(),
			// impl.getGuard(), impl.getEvent(), impl.getBefore(),
			// impl.getAnnotations(), impl.getAfter(), impl.getAction());
			// node.add(new ThingMLTreeNode("-> " +
			// impl.getTarget().getName()));
			node = new ThingMLTreeNode("-> " + impl.getTarget().getName());
			addLocationToNode(node, impl);

			if (impl.getAction() != null)
				findTypeAndAddNode(node, impl.getAction());
			for (Event event : impl.getEvent()) {
				findTypeAndAddNode(node, event);
			}

			root.add(node);

		} else if (object instanceof ReceiveMessage) {
			ReceiveMessage impl = (ReceiveMessage) object;
			// System.out.printf("Name: %s\nPort: %s\nMessage: %s\n",
			// impl.getName(), impl.getPort(), impl.getMessage());
			node = new ThingMLTreeNode("Receive message");
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof Message) {
			Message impl = (Message) object;
			// TODO: Make this print out the correct name
			node = new ThingMLTreeNode(annotation + " message");
			// + ((Object) impl).eProxyURI().toString().substring(51));
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof SendAction) {
			SendAction impl = (SendAction) object;
			// System.out.printf("Message: %s\nParameters: %s\nPort: %s\n",
			// impl.getMessage(), impl.getParameters(), impl.getPort());
			if (annotation.length() == 0) {
				node = new ThingMLTreeNode("Send action");
			} else {
				node = new ThingMLTreeNode(annotation + "send action");
				// System.out.printf("Message: %s\nParameters: %s\nPort: %s\n",
				// impl.getMessage(), impl.getParameters(), impl.getPort());
			}
			for (Expression expression : impl.getParameters()) {
				findTypeAndAddNode(node, expression);
			}

			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof IntegerLiteral) {
			IntegerLiteral impl = (IntegerLiteral) object;
			node = new ThingMLTreeNode(impl.getIntValue() + ": integer");
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof StringLiteral) {
			StringLiteral impl = (StringLiteral) object;
			node = new ThingMLTreeNode(impl.getStringValue() + ": string");
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof PropertyAssign) {
			PropertyAssign impl = (PropertyAssign) object;
			node = new ThingMLTreeNode(impl.getName());
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof Configuration) {
			Configuration impl = (Configuration) object;
			node = new ThingMLTreeNode(impl.getName());
			addLocationToNode(node, impl);
			for (ConfigInclude config : impl.getConfigs())
				findTypeAndAddNode(node, config);
			for (Connector connector : impl.getConnectors())
				findTypeAndAddNode(node, connector);
			for (Instance instance : impl.getInstances())
				findTypeAndAddNode(node, instance);
			for (ConfigPropertyAssign configPropertyAssign : impl
					.getPropassigns())
				findTypeAndAddNode(node, configPropertyAssign);

			this.root.add(node);

		} else if (object instanceof ConfigInclude) {
			ConfigInclude impl = (ConfigInclude) object;
			node = new ThingMLTreeNode(impl.getName() + ": group");
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof Connector) {
			Connector impl = (Connector) object;
			node = new ThingMLTreeNode("Connector");
			addLocationToNode(node, impl);
			if (impl.getCli() != null)
				findTypeAndAddNode(node, impl.getCli());
			if (impl.getSrv() != null)
				findTypeAndAddNode(node, impl.getSrv());
			// TODO: Gives null
			// if (impl.getProvided() != null)
			// findTypeAndAddNode(node, impl.getProvided());
			if (impl.getRequired() != null)
				findTypeAndAddNode(node, impl.getRequired());
			// System.out.printf("Cli: %s\nProvided: %s\nRequired: %s\nSrv: %s\n",
			// impl.getCli(), impl.getProvided(), impl.getRequired(),
			// impl.getSrv());

			root.add(node);

		} else if (object instanceof InstanceRef) {
			InstanceRef impl = (InstanceRef) object;
			// TODO: Sometimes null
			// Also got more get's
			// if (impl.getInstance() != null)
			// node = new ThingMLTreeNode(impl.getInstance().getName()
			// + ": instance");
			// else
			node = new ThingMLTreeNode("It's null: instance");
			addLocationToNode(node, impl);
			// System.out.printf("Config: %s\nInstance: %s\n", impl.getConfig(),
			// impl.getInstance());

			root.add(node);

		} else if (object instanceof ProvidedPort) {
			ProvidedPort impl = (ProvidedPort) object;
			node = new ThingMLTreeNode(impl.getName());
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof RequiredPort) {
			RequiredPort impl = (RequiredPort) object;
			node = new ThingMLTreeNode(impl.getName());
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof Instance) {
			Instance impl = (Instance) object;
			node = new ThingMLTreeNode(impl.getName() + ": instance");
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof ConfigPropertyAssign) {
			ConfigPropertyAssign impl = (ConfigPropertyAssign) object;
			node = new ThingMLTreeNode(impl.getName());
			addLocationToNode(node, impl);
			findTypeAndAddNode(node, impl.getInstance());
			findTypeAndAddNode(node, impl.getInit());
			// findTypeAndAddNode(node, impl.getProperty());
			// System.out
			// .printf("Annotations: %s\nIndex: %s\nInit: %s\nInstance: %s\nProperty: %s\n",
			// impl.getAnnotations(), impl.getIndex(),
			// impl.getInit(), impl.getInstance(),
			// impl.getProperty());

			root.add(node);

		} else if (object instanceof EnumLiteralRef) {
			EnumLiteralRef impl = (EnumLiteralRef) object;
			node = new ThingMLTreeNode("Literal ref");
			addLocationToNode(node, impl);

			findTypeAndAddNode(node, impl.getEnum());
			findTypeAndAddNode(node, impl.getLiteral());
			// System.out.printf("Enum: %s\nLiteral: %s\n", impl.getEnum(),
			// impl.getLiteral());
			// node = new ThingMLTreeNode(impl.get)

			root.add(node);

		} else if (object instanceof EnumerationLiteral) {
			EnumerationLiteral impl = (EnumerationLiteral) object;
			node = new ThingMLTreeNode(impl.getName());
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof Enumeration) {
			Enumeration impl = (Enumeration) object;
			node = new ThingMLTreeNode(impl.getName());
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof Property) {
			Property impl = (Property) object;
			node = new ThingMLTreeNode(impl.getName());
			addLocationToNode(node, impl);

			root.add(node);

		} else if (object instanceof ActionBlock) {
			ActionBlock impl = (ActionBlock) object;
			node = new ThingMLTreeNode("Action block");
			addLocationToNode(node, impl);
			for (Action action : impl.getActions())
				findTypeAndAddNode(node, action);

			root.add(node);

		} else if (object instanceof InternalTransition) {
			InternalTransition impl = (InternalTransition) object;
			// System.out.printf("Name: %s\nAction: %s\nAnno: %s\nEvent: %s\nGuard: %s\n",
			// impl.getName(), impl.getAction(), impl.getAnnotations(),
			// impl.getEvent(), impl.getGuard());
			node = new ThingMLTreeNode("Internal transition");
			addLocationToNode(node, impl);
			if (impl.getAction() != null)
				findTypeAndAddNode(node, impl.getAction());
			for (Event event : impl.getEvent())
				findTypeAndAddNode(node, event);
			if (impl.getGuard() != null)
				findTypeAndAddNode(node, impl.getGuard());

			root.add(node);

		} else if (object instanceof ConditionalAction) {
			ConditionalAction impl = (ConditionalAction) object;
			node = new ThingMLTreeNode("Conditional action");
			addLocationToNode(node, impl);
			if (impl.getAction() != null)
				findTypeAndAddNode(node, impl.getAction());
			if (impl.getCondition() != null)
				findTypeAndAddNode(node, impl.getCondition());

			root.add(node);

		} else if (object instanceof VariableAssignment) {
			VariableAssignment impl = (VariableAssignment) object;
			// System.out.printf("Exp: %s\nIndex: %s\nProp: %s\n",
			// impl.getExpression(), impl.getIndex(), impl.getProperty());
			node = new ThingMLTreeNode("Variable");
			addLocationToNode(node, impl);
			if (impl.getExpression() != null)
				findTypeAndAddNode(node, impl.getExpression());
			if (impl.getProperty() != null)
				findTypeAndAddNode(node, impl.getProperty());

			root.add(node);

		} else if (object instanceof EventReference) {
			// EventReference impl = (EventReference) object;
			// TODO: Nullpointer
			// Create node and add location
			// System.out.printf("Msg: %s\nParam: %s\n", impl.getMsgRef(),
			// impl.getParamRef());

		} else {
			System.out.println("Missing node! Node was of type " + object
					+ ". Please add.");
			root.add(new ThingMLTreeNode(annotation + object));
		}
	}

	public void addLocationToNode(ThingMLTreeNode node, EObject impl) {
		node.setLine(locationMap.getLine(impl));
		node.setColumn(locationMap.getColumn(impl));
		node.setCharStart(locationMap.getCharStart(impl));
		node.setCharEnd(locationMap.getCharEnd(impl));
	}

	public ThingMLTreeNode getRoot() {
		return root;
	}

}
