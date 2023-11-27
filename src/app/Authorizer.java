package app;

import java.util.List;

import app.facade.DataEngineInterface;

public class Authorizer implements DataEngineInterface {
	Manager ownerManager = new Manager();

	private static final String[] columns = {};

	private String id;
	private String truePassword;
	private String name;

	public Authorizer() {
		readAll();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String[] getColumnNames() {
		return columns;
	}

	@Override
	public void readAll() {
		ownerManager.readAll(new Factory() {
			public Manageable create() {
				return new Owner();
			}

		}, "select * from owner");
		ownerManager.printAll();
	}

	@Override
	public List<Manageable> search(String kwd) {
		if (kwd == null)
			return ownerManager.mList;
		return ownerManager.findAll(kwd);
	}

	@Override
	public boolean compare(String kwd, String input) {
		boolean result = tryLogin(kwd, input);

		return result;
	}

	@Override
	public void addNewItem(String[] uiTexts) {
	}

	@Override
	public void update(String[] uiTexts) {
	}

	@Override
	public void remove(String kwd) {
	}

	@Override
	public void sort(String order) {

	}

	@Override
	public String[] getCategory() {
		return null;
	}

	@Override
	public List<Manageable> categorize(String kwd) {
		return null;
	}

	// --------------------------------------------------------------
	private boolean tryLogin(String inputId, String inputPassword) {
		boolean suceed = false;

		// retrieve owner info using input id
		Owner found = (Owner) ownerManager.find(inputId);
		this.id = found.getId();
		this.truePassword = found.getPassword();
		this.name = found.getName();

		if (inputPassword.equals(truePassword)) {
			suceed = true;
		}

		return suceed;
	}
}
