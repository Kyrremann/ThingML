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
package org.sintef.thingml.resource.thingml.ui;

/**
 * An adapter from the Eclipse
 * <code>org.eclipse.jface.text.rules.ITokenScanner</code> interface to the
 * generated lexer.
 */
public class ThingmlTokenScanner implements org.eclipse.jface.text.rules.ITokenScanner {
	
	private org.sintef.thingml.resource.thingml.IThingmlTextScanner lexer;
	private org.sintef.thingml.resource.thingml.IThingmlTextToken currentToken;
	private int offset;
	private String languageId;
	private org.eclipse.jface.preference.IPreferenceStore store;
	private org.sintef.thingml.resource.thingml.ui.ThingmlColorManager colorManager;
	private org.sintef.thingml.resource.thingml.IThingmlTextResource resource;
	
	/**
	 * 
	 * @param colorManager A manager to obtain color objects
	 */
	public ThingmlTokenScanner(org.sintef.thingml.resource.thingml.IThingmlTextResource resource, org.sintef.thingml.resource.thingml.ui.ThingmlColorManager colorManager) {
		this.resource = resource;
		this.colorManager = colorManager;
		this.lexer = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().createLexer();
		this.languageId = new org.sintef.thingml.resource.thingml.mopp.ThingmlMetaInformation().getSyntaxName();
		this.store = org.sintef.thingml.resource.thingml.ui.ThingmlUIPlugin.getDefault().getPreferenceStore();
	}
	
	public int getTokenLength() {
		return currentToken.getLength();
	}
	
	public int getTokenOffset() {
		return offset + currentToken.getOffset();
	}
	
	public org.eclipse.jface.text.rules.IToken nextToken() {
		org.sintef.thingml.resource.thingml.mopp.ThingmlDynamicTokenStyler dynamicTokenStyler = new org.sintef.thingml.resource.thingml.mopp.ThingmlDynamicTokenStyler();
		currentToken = lexer.getNextToken();
		if (currentToken == null || !currentToken.canBeUsedForSyntaxHighlighting()) {
			return org.eclipse.jface.text.rules.Token.EOF;
		}
		org.eclipse.jface.text.TextAttribute ta = null;
		String tokenName = currentToken.getName();
		if (tokenName != null) {
			String enableKey = org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.ENABLE);
			boolean enabled = store.getBoolean(enableKey);
			org.sintef.thingml.resource.thingml.IThingmlTokenStyle staticStyle = null;
			if (enabled) {
				String colorKey = org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.COLOR);
				org.eclipse.swt.graphics.RGB foregroundRGB = org.eclipse.jface.preference.PreferenceConverter.getColor(store, colorKey);
				org.eclipse.swt.graphics.RGB backgroundRGB = null;
				boolean bold = store.getBoolean(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.BOLD));
				boolean italic = store.getBoolean(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.ITALIC));
				boolean strikethrough = store.getBoolean(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.STRIKETHROUGH));
				boolean underline = store.getBoolean(org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.getPreferenceKey(languageId, tokenName, org.sintef.thingml.resource.thingml.ui.ThingmlSyntaxColoringHelper.StyleProperty.UNDERLINE));
				// now call dynamic token styler to allow to apply modifications to the static
				// style
				staticStyle = new org.sintef.thingml.resource.thingml.mopp.ThingmlTokenStyle(convertToIntArray(foregroundRGB), convertToIntArray(backgroundRGB), bold, italic, strikethrough, underline);
			}
			org.sintef.thingml.resource.thingml.IThingmlTokenStyle dynamicStyle = dynamicTokenStyler.getDynamicTokenStyle(resource, currentToken, staticStyle);
			if (dynamicStyle != null) {
				int[] foregroundColorArray = dynamicStyle.getColorAsRGB();
				org.eclipse.swt.graphics.Color foregroundColor = colorManager.getColor(new org.eclipse.swt.graphics.RGB(foregroundColorArray[0], foregroundColorArray[1], foregroundColorArray[2]));
				int[] backgroundColorArray = dynamicStyle.getBackgroundColorAsRGB();
				org.eclipse.swt.graphics.Color backgroundColor = null;
				if (backgroundColorArray != null) {
					org.eclipse.swt.graphics.RGB backgroundRGB = new org.eclipse.swt.graphics.RGB(backgroundColorArray[0], backgroundColorArray[1], backgroundColorArray[2]);
					backgroundColor = colorManager.getColor(backgroundRGB);
				}
				int style = org.eclipse.swt.SWT.NORMAL;
				if (dynamicStyle.isBold()) {
					style = style | org.eclipse.swt.SWT.BOLD;
				}
				if (dynamicStyle.isItalic()) {
					style = style | org.eclipse.swt.SWT.ITALIC;
				}
				if (dynamicStyle.isStrikethrough()) {
					style = style | org.eclipse.jface.text.TextAttribute.STRIKETHROUGH;
				}
				if (dynamicStyle.isUnderline()) {
					style = style | org.eclipse.jface.text.TextAttribute.UNDERLINE;
				}
				ta = new org.eclipse.jface.text.TextAttribute(foregroundColor, backgroundColor, style);
			}
		}
		return new org.eclipse.jface.text.rules.Token(ta);
	}
	
	public void setRange(org.eclipse.jface.text.IDocument document, int offset, int length) {
		this.offset = offset;
		try {
			lexer.setText(document.get(offset, length));
		} catch (org.eclipse.jface.text.BadLocationException e) {
			// ignore this error. It might occur during editing when locations are outdated
			// quickly.
		}
	}
	
	public String getTokenText() {
		return currentToken.getText();
	}
	
	public int[] convertToIntArray(org.eclipse.swt.graphics.RGB rgb) {
		if (rgb == null) {
			return null;
		}
		return new int[] {rgb.red, rgb.green, rgb.blue};
	}
	
}
