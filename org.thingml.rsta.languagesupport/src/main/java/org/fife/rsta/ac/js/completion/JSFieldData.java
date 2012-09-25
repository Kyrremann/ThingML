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

import org.fife.rsta.ac.java.JarManager;
import org.fife.rsta.ac.java.buildpath.SourceLocation;
import org.fife.rsta.ac.java.classreader.ClassFile;
import org.fife.rsta.ac.java.classreader.FieldInfo;
import org.fife.rsta.ac.java.rjc.ast.CompilationUnit;
import org.fife.rsta.ac.java.rjc.ast.Field;
import org.fife.rsta.ac.java.rjc.ast.Member;
import org.fife.rsta.ac.java.rjc.ast.Method;
import org.fife.rsta.ac.java.rjc.ast.TypeDeclaration;


public class JSFieldData {
	
	private FieldInfo info;
	private JarManager jarManager;
	
	public JSFieldData(FieldInfo info, JarManager jarManager)
	{
		this.info = info;
		this.jarManager = jarManager;
	}
	
	
	public Field getField()
	{
		ClassFile cf = info.getClassFile();
		SourceLocation loc = jarManager.getSourceLocForClass(
				cf.getClassName(true));
		return getFieldFromSourceLoc(loc, cf);
	}
	
	/**
	 * Scours the source in a location (zip file, directory), looking for a
	 * particular class's source.  If it is found, it is parsed, and the
	 * {@link Method} for this method (if any) is returned.
	 *
	 * @param loc The zip file, jar file, or directory to look in.
	 * @param cf The {@link ClassFile} representing the class of this method.
	 * @return The method, or <code>null</code> if it cannot be found, or an
	 *         IO error occurred.
	 */
	private Field getFieldFromSourceLoc(SourceLocation loc, ClassFile cf) {

		CompilationUnit cu = org.fife.rsta.ac.java.Util.
									getCompilationUnitFromDisk(loc, cf);

		// If the class's source was found and successfully parsed, look for
		// this method.
		if (cu!=null) {

			for (Iterator i=cu.getTypeDeclarationIterator(); i.hasNext(); ) {

				TypeDeclaration td = (TypeDeclaration)i.next();
				String typeName = td.getName();

				// Avoid inner classes, etc.
				if (typeName.equals(cf.getClassName(false))) {

					// Get all overloads of this method with the number of
					// parameters we're looking for.  99% of the time, there
					// will only be 1, the method we're looking for.
					for (Iterator j=td.getMemberIterator(); j.hasNext(); ) {
						Member member = (Member)j.next();
						if (member instanceof Field &&
								member.getName().equals(info.getName())) {
							return (Field) member;
						}
					}

				} // if (typeName.equals(cf.getClassName(false)))

			} // for (Iterator i=cu.getTypeDeclarationIterator(); i.hasNext(); )

		} // if (cu!=null)

		return null;

	}
	
	public String getType(boolean qualified)
	{
		return info.getTypeString(qualified);
	}
	
	public boolean isStatic()
	{
		return info.isStatic();
	}
	
	public boolean isPublic()
	{
		int access = info.getAccessFlags();
		return org.fife.rsta.ac.java.classreader.Util.isPublic(access);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getEnclosingClassName(boolean fullyQualified) {
		return info.getClassFile().getClassName(fullyQualified);
	}
}
