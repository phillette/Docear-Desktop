package org.docear.plugin.bibtex;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map.Entry;

import net.sf.jabref.BibtexEntry;
import net.sf.jabref.Globals;
import net.sf.jabref.labelPattern.LabelPatternUtil;

import org.docear.plugin.pdfutilities.util.NodeUtils;
import org.freeplane.core.util.TextUtils;
import org.freeplane.features.link.LinkController;
import org.freeplane.features.link.mindmapmode.MLinkController;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.Controller;

public class JabRefAttributes {

	private HashMap<String, String> valueAttributes = new HashMap<String, String>();
	private String keyAttribute;

	public JabRefAttributes() {		
		registerAttributes();
	}
	
	public void registerAttributes() {
		this.keyAttribute = "bibtex_key";
		
		this.valueAttributes.put(TextUtils.getText("jabref_author"), "author");
		this.valueAttributes.put(TextUtils.getText("jabref_title"), "title");
		this.valueAttributes.put(TextUtils.getText("jabref_year"), "year");
		this.valueAttributes.put(TextUtils.getText("jabref_journal"), "journal");
	}
	
	public String getKeyAttribute() {
		return keyAttribute;
	}
	
	public HashMap<String, String> getValueAttributes() {
		return valueAttributes;
	}

	public void addReferenceToNode(BibtexEntry entry) {
		NodeModel currentNode = Controller.getCurrentModeController().getMapController().getSelectedNode();
		
		
		if (entry.getCiteKey()==null) {
			LabelPatternUtil.makeLabel(Globals.prefs.getKeyPattern(), ReferencesController.getController().getJabrefWrapper().getDatabase(), entry);						
		}
		
		renewAttribute(currentNode, keyAttribute, entry.getCiteKey());
		
		for (Entry<String, String> e : this.valueAttributes.entrySet()) {
			renewAttribute(currentNode, e.getKey(), entry.getField(e.getValue()));
		}
		
		String path = entry.getField("file");
		System.out.println("debug path: "+path);
		
		
		if (path != null) {
			NodeUtils.setLinkFrom(new File(path).toURI(), currentNode);
		}
		else {
			path = entry.getField("url");			
			if (path != null) {
				URI link;			
				try {
					link = LinkController.createURI(path.trim());
					final MLinkController linkController = (MLinkController) MLinkController.getController();
					linkController.setLink(currentNode, link, LinkController.LINK_ABSOLUTE);
				}
				catch (URISyntaxException e) {				
					e.printStackTrace();
				}
			}
		}

	}

	private static void renewAttribute(NodeModel node, String key, String value) {
		NodeUtils.removeAttribute(node, key, false);
		NodeUtils.setAttributeValue(node, key, value, false);
	}
}