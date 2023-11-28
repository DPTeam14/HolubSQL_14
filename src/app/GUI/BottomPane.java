package app.GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
class BottomPane extends JPanel implements ActionListener {
	JTextField edits[];
	int columnCount;

	void init(int columnCount) {
		this.columnCount = columnCount;
		edits = new JTextField[columnCount];
		setLayout(new FlowLayout());
		for (int i = 0; i < columnCount; i++) {
			edits[i] = new JTextField("", 10);
			add(edits[i]);
		}
		JButton editBtn = new JButton("edit");
		editBtn.setActionCommand("Done");
		editBtn.addActionListener(this);
		add(editBtn);

		setLayout(new FlowLayout());
		JButton buttons[] = new JButton[2];
		String btnTexts[] = { "add", "delete" };
		for (int i = 0; i < 2; i++) {
			buttons[i] = new JButton(btnTexts[i]);
			buttons[i].addActionListener(this);
			add(buttons[i]);
		}
	}

	void clearEdits() {
		for (JTextField edit : edits)
			edit.setText("");
	}

	String[] getEditTexts() {
		String[] editTexts = new String[columnCount];
		for (int i = 0; i < columnCount; i++)
			editTexts[i] = edits[i].getText();
		return editTexts;
	}

	void moveSelectedToEdits(String[] rowTexts) {
		for (int i = 0; i < rowTexts.length; i++) {
			edits[i].setText(rowTexts[i]);

		}
		edits[0].setEditable(false);
	}

	public void actionPerformed(ActionEvent e) {
		String[] editTexts = getEditTexts();
		clearEdits();
		edits[0].setEditable(true);
		TableController tableController = ProductPanel.tableController;
		switch (e.getActionCommand()) {
		case "add":
			tableController.addRow(editTexts);
			break;
		case "Done":
			tableController.updateRow(editTexts);
			break;
		case "delete":
			tableController.removeRow();
			break;
		default:
			break;
		}
		tableController.table.clearSelection();
	}
}
