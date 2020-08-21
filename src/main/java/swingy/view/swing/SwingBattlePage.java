package swingy.view.swing;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.view.BattleView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class SwingBattlePage extends JFrame implements BattleView {

	private JButton button = new JButton("Press");
	private JTextField input = new JTextField("", 5);
	private JLabel label = new JLabel("Input:");
	private JRadioButton radio1 = new JRadioButton("Select this");
	private JRadioButton radio2 = new JRadioButton("Select that");
	private JCheckBox check = new JCheckBox("Check", false);

	public SwingBattlePage() {
		super("Simple Example");
		this.setBounds(100,100,250,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container container = this.getContentPane();
		container.setLayout(new GridLayout(3,2,2,2));
		container.add(label);
		container.add(input);

		ButtonGroup group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		container.add(radio1);

		radio1.setSelected(true);
		container.add(radio2);
		container.add(check);
		button.addActionListener(new ButtonEventListener());
		container.add(button);
	}


	class ButtonEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String message = "";
			message += "Button was pressed\n";
			message += "Text is " + input.getText() + "\n";
			message += (radio1.isSelected()?"Radio #1":"Radio #2")
					+ " is selected\n";
			message += "CheckBox is " + ((check.isSelected())
					?"checked":"unchecked");
			JOptionPane.showMessageDialog(null,
					message,
					"Output",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	@Override
	public void printMap(Map<Coordinate, GameCharacter> characters, int mapSize) {

	}

	@Override
	public void destroy() {

	}
}
