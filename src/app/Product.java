package app;

import app.facade.UIData;

public class Product implements Manageable, UIData {
	String id;
	String category;
	String name;
	int stock;
	int price;

	@Override
	public void read(String str) {
		String[] strParsed = str.split("/");
		id = strParsed[0];
		category = strParsed[1];
		name = strParsed[2];
		stock = Integer.parseInt(strParsed[3]);
		price = Integer.parseInt(strParsed[4]);

	}

	@Override
	public void print() {
		System.out.printf("[%s] %s %d (%d)\n", id, name, stock, price);
	}

	@Override
	public boolean matches(String n) {
		if (id.equals(n))
			return true;
		if (name.contains(n))
			return true;

		return name.equals(n);

	}

	public boolean contained(String n) {
		if (category.contains(n))
			return true;

		return category.equals(n);

	}

	@Override
	public String toString() {
		return String.format("%s(%s)", id, name);
	}

	// -----------------------------------------------------------
	// UIData
	@Override
	public void set(Object[] row) {

		id = (String) row[0];
		category = (String) row[1];
		name = (String) row[2];
		stock = Integer.parseInt((String) row[3]);
		price = Integer.parseInt((String) row[4]);
	}

	@Override
	public String[] getUiTexts() {
		return new String[] { id, category, name, Integer.toString(stock), Integer.toString(price) };
	}

	// ----------------------------------------------------------

	public String getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public int getStock() {

		return stock;
	}

	public int getPrice() {

		return price;
	}

	public String getCategory() {

		return category;
	}

}
