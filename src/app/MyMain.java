package app;

import app.GUI.GUIMain;

public class MyMain {
	public static void main(String[] args) {
		// data engine
		Authorizer authorizer = new Authorizer();

		// GUI
		GUIMain.startGUI(authorizer);
	}
}
