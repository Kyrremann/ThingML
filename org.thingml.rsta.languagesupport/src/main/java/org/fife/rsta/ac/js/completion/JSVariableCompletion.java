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
package org.fife.rsta.ac.js.completion;

import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.text.JTextComponent;

import org.fife.rsta.ac.java.buildpath.SourceLocation;
import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.rjc.ast.CompilationUnit;
import org.fife.rsta.ac.js.IconFactory;
import org.fife.rsta.ac.js.JavaScriptHelper;
import org.fife.rsta.ac.js.SourceCompletionProvider;
import org.fife.rsta.ac.js.ast.JavaScriptVariableDeclaration;
import org.fife.rsta.ac.js.ast.type.TypeDeclarationFactory;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.VariableCompletion;
import org.fife.rsta.ac.java.Util;


public class JSVariableCompletion extends VariableCompletion implements
		JSCompletionUI {

	private JavaScriptVariableDeclaration dec;
	private boolean localVariable;


	public JSVariableCompletion(CompletionProvider provider,
			JavaScriptVariableDeclaration dec) {
		this(provider, dec, true);
	}


	public JSVariableCompletion(CompletionProvider provider,
			JavaScriptVariableDeclaration dec, boolean localVariable) {
		super(provider, dec.getName(), dec.getJavaScriptTypeName());
		this.dec = dec;
		this.localVariable = localVariable;
	}


	/**
	 * @return the type name not qualified
	 */
	public String getType() {
		return getType(false);
	}


	/**
	 * @param qualified whether to return the name as qualified
	 * @return the type name based on qualified
	 */
	public String getType(boolean qualified) {
		return TypeDeclarationFactory.convertJavaScriptType(dec.getJavaScriptTypeName(),
				qualified);
	}


	public String getAlreadyEntered(JTextComponent comp) {
		String temp = getProvider().getAlreadyEnteredText(comp);
		int lastDot = JavaScriptHelper
				.findLastIndexOfJavaScriptIdentifier(temp);
		if (lastDot > -1) {
			temp = temp.substring(lastDot + 1);
		}
		return temp;
	}


	public Icon getIcon() {
		return IconFactory
				.getIcon(localVariable ? IconFactory.LOCAL_VARIABLE_ICON
						: IconFactory.GLOBAL_VARIABLE_ICON);
	}


	public int getRelevance() {
		return localVariable ? LOCAL_VARIABLE_RELEVANCE : GLOBAL_VARIABLE_RELEVANCE;
	}


	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof VariableCompletion) {
			VariableCompletion comp = (VariableCompletion) obj;
			return getName().equals(comp.getName());
		}

		return super.equals(obj);
	}


	public int hashCode() {
		return getName().hashCode();
	}
	
	public String getSummary() {

        SourceCompletionProvider scp = (SourceCompletionProvider)getProvider();
        ClassFile cf = scp.getJavaScriptTypesFactory().getClassFile(scp.getJarManager(), JavaScriptHelper.createNewTypeDeclaration(getType(true)));
        if(cf != null)
        {
            SourceLocation  loc = scp.getSourceLocForClass(cf.getClassName(true));
    
            if (loc!=null) {
    
                CompilationUnit cu = Util.getCompilationUnitFromDisk(loc, cf);
                if (cu!=null) {
                    for (Iterator i=cu.getTypeDeclarationIterator(); i.hasNext(); ) {
                        org.fife.rsta.ac.java.rjc.ast.TypeDeclaration td = (org.fife.rsta.ac.java.rjc.ast.TypeDeclaration)i.next();
                        String typeName = td.getName();
                        // Avoid inner classes, etc.
                        if (typeName.equals(cf.getClassName(false))) {
                            String summary = td.getDocComment();
                            // Be cautious - might be no doc comment (or a bug?)
                            if (summary!=null && summary.startsWith("/**")) {
                                return Util.docCommentToHtml(summary);
                            }
                        }
                    }
                }
            }
            // Default to the fully-qualified class name.
            return cf.getClassName(true);
        }
        
        return super.getSummary();
        

    }

}