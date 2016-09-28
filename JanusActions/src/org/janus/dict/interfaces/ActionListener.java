package org.janus.dict.interfaces;

import java.io.Serializable;

import org.janus.data.DataContext;

/**
 * 
 * @author Thomas Nill
 * 
 *         Nachdem eine {@link Action} ausgeführt wurde, werden die
 *         {@link ActionListener} benachrichtigt
 * 
 */
public interface ActionListener extends Serializable{
	void actionPerformed(Object a, DataContext data);
}
