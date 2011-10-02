/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2011 dimitry
 *
 *  This file author is dimitry
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.freeplane.view.swing.ui;

import java.awt.event.MouseEvent;

import org.freeplane.core.resources.ResourceController;
import org.freeplane.core.ui.IEditHandler.FirstAction;
import org.freeplane.core.util.Compat;
import org.freeplane.features.text.mindmapmode.MTextController;

/**
 * @author Dimitry Polivaev
 * Oct 2, 2011
 */
public class MNodeMouseMotionListener extends DefaultNodeMouseMotionListener {
    public void mouseClicked(final MouseEvent e) {
    	if (wasFocused() && e.getClickCount() == 2
    			&& Compat.isPlainEvent(e) && e.getButton() == MouseEvent.BUTTON1) {
    		if(ResourceController.getResourceController().getBooleanProperty("start_editor_on_double_click")) {
    			final MTextController textController = (MTextController) MTextController.getController();
    			textController.getEventQueue().activate(e);
    			textController.edit(FirstAction.EDIT_CURRENT, e.isAltDown());
    		}
    		return;
    	}
    	super.mouseClicked(e);
    }
}