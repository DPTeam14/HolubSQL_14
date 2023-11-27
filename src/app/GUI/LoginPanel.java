package app.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.StockController;
import app.facade.DataEngineInterface;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	DataEngineInterface auth = GUIMain.engine;

	public LoginPanel() {
		super(new BorderLayout());
	}

	void addComponentsToPane() {
		JPanel idPanel = new JPanel(new BorderLayout());
		idPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		JLabel idLabel = new JLabel("ID:");
		idPanel.add(idLabel, BorderLayout.LINE_START);
		JTextField idTextField = new JTextField("", 20);
		idPanel.add(idTextField, BorderLayout.LINE_END);

		JPanel passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 30, 30));
		JLabel passwordLabel = new JLabel("Password:");
		passwordPanel.add(passwordLabel, BorderLayout.LINE_START);
		JTextField passwordTextField = new JTextField("", 20);
		passwordPanel.add(passwordTextField, BorderLayout.LINE_END);

		add(idPanel, BorderLayout.NORTH);
		add(passwordPanel, BorderLayout.CENTER);

		JButton login = new JButton("Login");
		add(login, BorderLayout.SOUTH);

		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tmpId = idTextField.getText();
				String tmpPass = passwordTextField.getText();
				System.out.println(tmpId);

				if (tmpId.equals("")) {
					JOptionPane.showMessageDialog(null, "Enter your ID");
				} else if (auth.compare(tmpId, tmpPass)) {
					// login success!

					// new engine
					StockController stock = new StockController(tmpId);

					// new GUI
					StockFrame systemFrame = new StockFrame();
					systemFrame.startGUI(stock);
					systemFrame.setTitle("Family Mart Management System");

					idTextField.setText("");
					passwordTextField.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Login failed, try again!");
					passwordTextField.setText("");

				}

			}
		});

	}

}
