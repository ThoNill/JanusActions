package org.janus.dict.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.janus.data.DataContext;
import org.janus.dict.interfaces.ActionListener;

public class ActionEventSender implements Serializable {
	public static final Logger LOG = Logger.getLogger(ActionEventSender.class);

	private static final long serialVersionUID = -4112308871215103613L;
	/** Liste der {@link ActionListener} */
	protected ArrayList<ActionListener> listeners = null;

	
	
	public ActionEventSender() {
		super();
	}

	/** Mitteilung an die {@link ActionListener} */
	public void fireActionIsPerformed(DataContext data){
		LOG.debug("Action " + toString() + " hat "
				+ ((listeners == null) ? 0 : listeners.size()) + " Listener ");
		if (listeners != null) {
			for (ActionListener l : listeners) {
				LOG.debug("fireActionIsPerformed " + l.toString());
				l.actionPerformed(this, data);
			}
		}
	}

	public void addActionListener(ActionListener l) {
		LOG.debug("addActionListener " + l.toString());
		if (listeners == null) {
			listeners = new ArrayList<ActionListener>();
		}
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}
	
	public void removeActionListener(ActionListener l) {
		LOG.debug("addActionListener " + l.toString());
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