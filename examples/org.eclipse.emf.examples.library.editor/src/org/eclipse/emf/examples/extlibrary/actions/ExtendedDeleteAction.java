/**
 * <copyright>
 *
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: ExtendedDeleteAction.java,v 1.1 2005/11/10 18:57:56 marcelop Exp $
 */
package org.eclipse.emf.examples.extlibrary.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.DeleteAction;

import org.eclipse.emf.examples.extlibrary.Library;

/**
 * The extended library example supports multi-rooted resources
 * and therefore this action allows the user to delete a library
 * root from the resource.
 */
public class ExtendedDeleteAction
	extends DeleteAction {

	/**
	 * Constructor
	 * 
	 * @param domain
	 *            the editing domain
	 */
	public ExtendedDeleteAction(EditingDomain domain) {
		super(domain);
	}

	/**
	 * Constructor
	 */
	public ExtendedDeleteAction() {
		super();
	}

	/*
	 * @see org.eclipse.emf.edit.ui.action.CommandActionHandler#createCommand(java.util.Collection)
	 */
	public Command createCommand(Collection selection) {
		Command cmd = super.createCommand(selection);
		if (cmd != null) {
			ArrayList list = new ArrayList();
			if (selection != null && !selection.isEmpty()) {
				Iterator iter = selection.iterator();
				while (iter.hasNext()) {
					Object next = iter.next();
					if (next instanceof Library) {
						list.add(next);
					}
				}

				if (!list.isEmpty()) {
					Command removeLibraries = new RemoveLibraryCommand(domain,
						((Library) list.get(0)).eResource().getContents(), list);
					if (cmd instanceof UnexecutableCommand) {
						return removeLibraries;
					} else {
						cmd.chain(removeLibraries);
					}
				}
			}
		}

		return cmd;
	}
}