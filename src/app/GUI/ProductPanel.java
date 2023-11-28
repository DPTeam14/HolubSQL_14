package app.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import app.facade.DataEngineInterface;

public class ProductPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	DataEngineInterface dataMgr;

	static TableController tableController;
	static BottomPane bottom;
	JPanel rightPanel;
	JPanel leftPanel;

	public ProductPanel() {
		super(new BorderLayout());
	}

	void addComponentsToPane() {
		rightPanel = new JPanel(new BorderLayout());

		// TableCotroller
		tableController = new TableController();
		tableController.init();
		JScrollPane center = new JScrollPane(tableController.table);
		rightPanel.add(center, BorderLayout.CENTER);

		// BottomPane contains edit fields
		bottom = new BottomPane();
		bottom.init(StockFrame.engine.getColumnCount());
		rightPanel.add(bottom, BorderLayout.PAGE_END);

		// TopPane contains search field
		setupTopPane();

		// LeftPanel contains sorting & category options
		setupLeftPanel();

		add(rightPanel, BorderLayout.CENTER);
		add(leftPanel, BorderLayout.WEST);
	}

	void setupTopPane() {
		JPanel topPane = new JPanel();
		JTextField kwdTextField = new JTextField("", 20);
		topPane.add(kwdTextField, BorderLayout.LINE_START);
		JButton search = new JButton("search");
		topPane.add(search, BorderLayout.LINE_END);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("search")) {
					tableController.loadData(kwdTextField.getText());
				}
			}
		});
		rightPanel.add(topPane, BorderLayout.PAGE_START);
	}

	void setupLeftPanel() {
		dataMgr = StockFrame.engine;
		leftPanel = new JPanel(new BorderLayout());

		// category selection combo box
		JPanel categoryPane = new JPanel(new BorderLayout());
		categoryPane.setBorder(BorderFactory.createEmptyBorder(50, 30, 30, 10));

		JComboBox<String> categorySelection;

		// load category from DB!!
		String categorys[] = dataMgr.getCategory();

		categorySelection = new JComboBox<String>(categorys);

		JLabel categoryLabel = new JLabel("select category:  ");

		categoryPane.add(categoryLabel, BorderLayout.NORTH);
		categoryPane.add(categorySelection, BorderLayout.SOUTH);

		leftPanel.add(categoryPane, BorderLayout.PAGE_START);

		categorySelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kwd = categorySelection.getSelectedItem().toString();
				int index = kwd.indexOf("(");
				kwd = kwd.substring(0, index);
				kwd = kwd.trim();

				tableController.categorizeData(kwd);

			}
		});

		// sorting selection radio button
		JPanel sortingPane = new JPanel();

		JLabel sortingLabel = new JLabel("select sorting option:  ");
		sortingPane.add(sortingLabel);
		sortingPane.add(new JLabel(""));

		String options[] = { "id", "stock", "price" };

		JRadioButton[] radio = new JRadioButton[3];
		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i < radio.length; i++) {
			radio[i] = new JRadioButton(options[i]);
			group.add(radio[i]);
			sortingPane.add(radio[i]);
			radio[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String s = e.getActionCommand();
					tableController.sortData(s);

				}
			});

		}
		sortingPane.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 10));
		leftPanel.add(sortingPane, BorderLayout.PAGE_END);

	}
}
