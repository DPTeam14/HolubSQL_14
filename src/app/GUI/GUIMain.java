package app.GUI;

import javax.swing.JFrame;

import app.facade.DataEngineInterface;

public class GUIMain {
	static DataEngineInterface engine;

	public static void startGUI(DataEngineInterface en) {
		engine = en;
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		JFrame mainFrame = new JFrame("Family Mart Management System");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LoginPanel newContentPane = new LoginPanel();
		newContentPane.addComponentsToPane();
		mainFrame.getContentPane().add(newContentPane);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
