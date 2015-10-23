package de.upb.s2cx.console;

import de.upb.s2cx.event.IEventHandler;
import de.upb.s2cx.query.QueryTree;

public class XMLEventHandler implements IEventHandler {

	@Override
	public void init() {

	}

	@Override
	public void onElement(String name, boolean start) {
		if(start)
			System.out.println("<" + name + ">");
		else
			System.out.println("</" + name + ">");
	}

	@Override
	public void onText(String string, String context) {
		System.out.println(string);
	}

	@Override
	public void onOptionalOmit() {
	}

	@Override
	public void onOptionalStart() {		
	}

	@Override
	public void onOptionalEnd() {
	}

	@Override
	public void onKleeneStart(QueryTree query) {
	}

	@Override
	public void onKleeneNext(QueryTree query) {
	}

	@Override
	public void onKleeneEnd(QueryTree query) {

	}

}
