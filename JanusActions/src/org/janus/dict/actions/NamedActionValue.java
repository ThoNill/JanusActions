package org.janus.dict.actions;

import org.apache.log4j.Logger;
import org.janus.actions.Action;
import org.janus.dict.helper.ID;

/**
 * 
 * @author Thomas Nill
 * 
 *         Default Implementierung einer {@link Action}
 * 
 */
public class NamedActionValue extends PageValue {
	Logger log = Logger.getLogger(NamedActionValue.class);

	private static final long serialVersionUID = -1153454103549307177L;

	/** Aussagekräftiger Name */
	private String name;
	/** eindeutige ID in der VM */
	private int id;

	private ActionDictionary model;
	
	public int getId() {
		return id;
	}

	

	public NamedActionValue(String name,Object kern) {
		super(kern);
		id = ID.getId();
		if (name == null) {
			name = "anonym" + id;
		}
		this.name = name;
		log.debug("Erzeuge " + name + " v" + id);

	}
	

	public ActionDictionary getModel() {
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see janus.tech.model.Action1#setModel(janus.tech.model.ActionDictionary)
	 */
	
	public void setModel(ActionDictionary model) {
		this.model = model;
	}



	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Action: " + name + " id= " + getId() + " in " + model.getName();
	}


}
