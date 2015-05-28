package org.janus.dict.actions;

import org.apache.log4j.Logger;
import org.janus.dict.interfaces.ActionListener;

public class ActionConnector {
	Logger log = Logger.getLogger(ActionConnector.class);


	public ActionConnector() {
		
	}

	
	public void connect(ActionDictionary dict,ActionListener l,String listento) {
		if (listento != null) {
			String actionNames[] = listento.split(" *, *");
			for (String name : actionNames) {
				listenToAction(dict,l,name);
			}
		}

	}

	
	private void listenToAction(ActionDictionary dict,ActionListener l,String name) {
		PageAction a = dict.getAction(name);
		if (a != null) {
			a.addActionListener(l);
		} else {
			log.error("Die Action {" + name + "] existiert nicht in "
					+ dict.getName());
		}
	}

	

}
