package org.janus.dict.actions;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.janus.actions.Action;
import org.janus.actions.DataFormat;
import org.janus.actions.DataValue;
import org.janus.actions.ReadValue;
import org.janus.actions.WriteValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.dict.helper.ID;
import org.janus.dict.interfaces.ActionListener;

/**
 * 
 * @author Thomas Nill
 * 
 *         Default Implementierung einer {@link Action}
 * 
 */
public class NamedActionReference implements ReadValue, WriteValue,Action {
	private static final long serialVersionUID = -8957628964218589941L;
	Logger log = Logger.getLogger(NamedActionReference.class);

	/** Aussagekräftiger Name */
	private String name;
	NamedActionValue value;
	
	

	public NamedActionReference(String name) {
		super();
	
		this.name = name;
		log.debug("Erzeuge Referenz " + name);

	}

	private NamedActionValue getValue(DataContext context) {
		if (value == null ) {
			value = getValue(context.getDataDescription());
		}
		return value;
	}

	private NamedActionValue getValue(DataDescription dict) {
		if (value == null && dict instanceof ActionDictionary) {
			value = ((ActionDictionary)dict).getAction(name);
		}
		return value;
	}


	public void perform(DataContext data) {
		getValue(data).perform(data);
	}



	public void setObject(DataContext ctx, Serializable v) {
		getValue(ctx).setObject(ctx, v);
	}



	public Serializable getObject(DataContext ctx) {
		return getValue(ctx).getObject(ctx);
	}



	public boolean isOn(DataContext data) {
		return getValue(data).isOn(data);
	}

	@Override
	public void configure(DataDescription description) {
		getValue(description).configure(description);
	}


	



}
