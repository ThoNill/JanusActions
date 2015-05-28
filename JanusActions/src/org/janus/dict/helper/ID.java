package org.janus.dict.helper;

public class ID {
	private static int nextID = 0;

	public synchronized static int getId() {
		int ret = nextID;
		nextID++;
		return ret;
	}
}
