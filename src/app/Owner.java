package app;

import app.facade.UIData;

public class Owner implements Manageable, UIData {

	String id;
	String password;
	String name;

	// Manageable
	@Override
	public void read(String str) {
		String[] strParsed = str.split("/");
		id = strParsed[0];
		password = strParsed[1];
		name = strParsed[2];

	}

	@Override
	public void print() {
		System.out.printf("id: [%s], password: [%s], %s\n", id, password, name);

	}

	@Override
	public boolean matches(String kwd) {
		if (kwd.equals(getId()))
			return true;
		return false;
	}

	@Override
	public boolean contained(String kwd) {
		return false;
	}

	// ------------------------------------------------------------------------
	// UIData
	@Override
	public void set(Object[] row) {
		id = (String) row[0];
		password = (String) row[1];
		name = (String) row[2];
	}

	@Override
	public String[] getUiTexts() {
		return new String[] { id, password, name };
	}

	// ------------------------------------------------------------------
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

}
