package org.janus.dict.actions;

import java.io.Serializable;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.janus.data.DataContext;
import org.janus.dict.interfaces.ActionListener;

public class ActionEventSender implements Serializable {
	public static Logger log = Logger.getLogger(ActionEventSender.class);

	private static final long serialVersionUID = -4112308871215103613L;
	/** Liste der {@link ActionListener} */
	protected Vector<ActionListener> listeners = null;

	
	
	public ActionEventSender() {
		super();
	}

	/** Mitteilung an die {@link ActionListener} */
	public void fireActionIsPerformed(DataContext data){
		log.debug("Action " + toString() + " hat "
				+ ((listeners == null) ? 0 : listeners.size()) + " Listener ");
		if (listeners != null) {
			for (ActionListener l : listeners) {
				log.debug("fireActionIsPerformed " + l.toString());
				l.actionPerformed(this, data);
			}
		}
	}

	public void addActionListener(ActionListener l) {
		log.debug("addActionListener " + l.toString());
		if (listeners == null) {
			listeners = new Vector<ActionListener>();
		}
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}
	
	public void removeActionListener(ActionListener l) {
		log.debug("addActionListener " + l.toString());
		if (listeners != null) {
			if (listeners.contains(l)) {
				listeners.remove(l);
			}
			if (listeners.size() ==0) {
				listeners = null;
			}
		}
	}
	
	public int getListenersCount() {
		if (listeners != null) {
			return listeners.size();
		}
		return 0;
	}

	

}