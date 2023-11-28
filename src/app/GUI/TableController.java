package app.GUI;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import app.facade.DataEngineInterface;
import app.facade.UIData;

public class TableController implements ListSelectionListener {
	DefaultTableModel tableModel;
	JTable table;
	int selectedIndex = -1;
	DataEngineInterface dataMgr;

	@SuppressWarnings("serial")
	void init() {
		dataMgr = StockFrame.engine;
		tableModel = new DefaultTableModel(dataMgr.getColumnNames(), 0) { // table GUI
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		loadData("");

		table = new JTable(tableModel);
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(this);
		table.setPreferredScrollableViewportSize(new Dimension(500, 220));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	// load all data from mList and show
	void loadData(String kwd) {
		List<?> result = dataMgr.search(kwd);
		tableModel.setRowCount(0);
		for (Object m : result)
			tableModel.addRow(((UIData) m).getUiTexts());
	}

	void addRow(String[] editTexts) {
		try {
			dataMgr.addNewItem(editTexts);
			tableModel.addRow(editTexts);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "fail to add data");
		}

	}

	void removeRow() {
		if (selectedIndex < 0)
			return;
		String key = (String) tableModel.getValueAt(selectedIndex, 0);
		dataMgr.remove(key);
		tableModel.removeRow(selectedIndex);
	}

	void updateRow(String[] editTexts) {
		if (selectedIndex < 0)
			return;
		try {
			dataMgr.update(editTexts);
			for (int i = 0; i < editTexts.length; i++) {
				tableModel.setValueAt(editTexts[i], selectedIndex, i);
			}
			return;
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "fail to update");
			return;
		}

	}

	public void valueChanged(ListSelectionEvent e) {
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		if (!lsm.isSelectionEmpty()) {
			selectedIndex = lsm.getMinSelectionIndex();
			String[] rowTexts = new String[tableModel.getColumnCount()];
			for (int i = 0; i < rowTexts.length; i++)
				rowTexts[i] = (String) tableModel.getValueAt(selectedIndex, i);
			ProductPanel.bottom.moveSelectedToEdits(rowTexts);
		}
	}

	public void sortData(String order) {
		dataMgr.sort(order);
		loadData("");
	}

	public void categorizeData(String kwd) {
		List<?> result = dataMgr.categorize(kwd);
		tableModel.setRowCount(0);
		for (Object m : result)
			tableModel.addRow(((UIData) m).getUiTexts());
	}
}