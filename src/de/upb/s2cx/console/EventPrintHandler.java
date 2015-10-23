package de.upb.s2cx.console;

import de.upb.s2cx.event.IEventHandler;
import de.upb.s2cx.query.QueryTree;

public class EventPrintHandler implements IEventHandler {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onElement(String name, boolean start) {
		if(start)
			System.out.println("ELEMENT_START(" + name + ")");
		else
			System.out.println("ELEMENT_END(" + name + ")");
	}

	@Override
	public void onText(String string, String context) {
		System.out.println("TEXT('" + string + "', " + context + ")");
	}

	@Override
	public void onOptionalOmit() {
		System.out.println("OPTIONAL_OMIT");
	}

	@Override
	public void onOptionalStart() {		
		System.out.println("OPTIONAL_START");
	}

	@Override
	public void onOptionalEnd() {
		System.out.println("OPTIONAL_END");
	}

	@Override
	public void onKleeneStart(QueryTree query) {
		System.out.println("KLEENE_START(" + query.getId() + ")");
	}

	@Override
	public void onKleeneNext(QueryTree query) {
		System.out.println("KLEENE_NEXT(" + query.getId() + ")");
	}

	@Override
	public void onKleeneEnd(QueryTree query) {
		System.out.println("KLEENE_END(" + query.getId() + ")");

	}

}
