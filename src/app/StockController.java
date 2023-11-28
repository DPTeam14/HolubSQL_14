package app;

import java.util.ArrayList;
import java.util.List;

import app.facade.DataEngineInterface;

public class StockController implements DataEngineInterface {
	String ownerId;
	private Manager productManager = new Manager();

	public StockController(String id) {
		this.ownerId = id;
		readAll();
	}

	private static final String[] labels = { "id", "category", "name", "stock", "price" };

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public String[] getColumnNames() {
		return labels;
	}

	@Override
	public List<Manageable> search(String kwd) {
		if (kwd == null)
			return productManager.mList;
		return productManager.findAll(kwd);
	}

	@Override
	public List<Manageable> categorize(String kwd) {
		if (kwd == null)
			return productManager.mList;
		return productManager.findSome(kwd);
	}

	@Override
	public void readAll() {
		productManager.readAll(new Factory() {
			public Manageable create() {
				return new Product();
			}

		}, "select * from product");

	}

	@Override
	public void addNewItem(String[] editTexts) throws Exception {
		Product s = new Product();
		s.set(editTexts);
		if (productManager.find(s.getId()) != null) {
			throw new Exception();
		}
		productManager.mList.add(s);
		String values = "('" + s.getId() + "', " + "'" + s.getCategory() + "', " + "'" + s.getName() + "', "
				+ s.getStock() + ", " + s.getPrice() + ")";
		productManager.writeDB("insert into product values" + values);
	}

	@Override
	public void update(String[] editTexts) throws Exception {
		Product s = (Product) productManager.find(editTexts[0]);
		s.set(editTexts);

		String values = "('" + s.getId() + "', " + "'" + s.getCategory() + "', " + "'" + s.getName() + "', "
				+ s.getStock() + ", " + s.getPrice() + ")";
		System.out.println(values);
		productManager.writeDB("insert into product values" + values);

	}

	@Override
	public void remove(String kwd) {
		Product s = (Product) productManager.find(kwd);
		productManager.mList.remove(s);
		productManager.writeDB("delete from product where id = " + s.getId());
	}

	@Override
	public boolean compare(String kwd, String input) {
		return false;
	}

	@Override
	public void sort(String order) {
		productManager.readAll(new Factory() {
			public Manageable create() {
				return new Product();
			}

		}, "select * from product order by " + order + " asc");
	}

	@Override
	public String[] getCategory() {
		ArrayList<String> arr = productManager.readDB("select category, count(id) from product group by category");
		String tmp = arr.toString();
		tmp = tmp.substring(1, tmp.length() - 1);
		String[] result = tmp.split(",");
		for (int i = 0; i < result.length; i++) {
			result[i] = result[i].replaceFirst("/", " (");
			result[i] = result[i].replaceFirst("/", ")");
			result[i] = result[i].trim();
		}

		return result;
	}

}
