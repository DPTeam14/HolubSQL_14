package app.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import app.facade.DataEngineInterface;

public class StockFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	static DataEngineInterface engine;
	JPanel menuPanel = null;
	ProductPanel productPanel = null;

	public void startGUI(DataEngineInterface en) {
		engine = en;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		productPanel = new ProductPanel();
		productPanel.addComponentsToPane();
		getContentPane().add(productPanel);

		// Display the window.
		pack();
		setVisible(true);
	}

}
