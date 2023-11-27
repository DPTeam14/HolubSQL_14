package app.facade;

import java.util.List;

import app.Manageable;

public interface DataEngineInterface {

	int getColumnCount();

	String[] getColumnNames();

	void readAll();

	List<Manageable> search(String kwd);

	void addNewItem(String[] uiTexts) throws Exception;

	void update(String[] uiTexts) throws Exception;

	void remove(String kwd);

	boolean compare(String kwd, String input);

	void sort(String order);

	String[] getCategory();

	List<Manageable> categorize(String kwd);

}
