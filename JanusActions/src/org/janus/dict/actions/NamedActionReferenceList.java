package org.janus.dict.actions;

import org.janus.actions.ActionList;

public class NamedActionReferenceList extends ActionList {
	
	public NamedActionReferenceList() {
		super();
	}
	
	public NamedActionReferenceList(String names) {
		this();
		for(String name : names.split(" *, *")) {
			addAction(new NamedActionReference(name));
		}
	}



}
