package app;

import java.util.ArrayList;
import java.util.List;

public class Manager {
	public ArrayList<Manageable> mList = new ArrayList<>();
	public DBConnector db = new DBConnector("c:/dp2023");

	// find the first obj in the list using keyword
	public Manageable find(String kwd) {
		for (Manageable m : mList)
			if (m.matches(kwd))
				return m;
		return null;
	}

	// read all data from the database and store in our list
	// we need DBconnector for this
	public void readAll(Factory fac, String sql) {
		ArrayList<String> arr = db.querySelect(sql); /* data */
		ArrayList<Manageable> tmp = new ArrayList<>();
		Manageable m = null;
		for (String str : arr) {
			m = fac.create();
			m.read(str);
			tmp.add(m);
		}
		mList = tmp;

	}

	// print all data to the console
	public void printAll() {
		for (Manageable m : mList) {
			m.print();
		}
	}

	// find all objs in the list using kwd
	public List<Manageable> findAll(String kwd) {
		List<Manageable> result = new ArrayList<>();
		for (Manageable m : mList) {
			if (m.matches(kwd))
				result.add(m);
		}
		return result;
	}

	public List<Manageable> findSome(String kwd) {
		List<Manageable> result = new ArrayList<>();
		for (Manageable m : mList) {
			if (m.contained(kwd))
				result.add(m);
		}
		return result;
	}

	public void writeDB(String sql) {
		db.query(sql);
	}

	public ArrayList<String> readDB(String sql) {
		ArrayList<String> arr = db.querySelect(sql);
		return arr;
	}

}
