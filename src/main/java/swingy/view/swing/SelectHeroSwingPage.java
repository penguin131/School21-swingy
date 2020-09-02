package swingy.view.swing;

import swingy.controller.SelectHeroController;
import swingy.model.Hero;
import swingy.model.HeroClass;
import swingy.view.SelectHeroView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectHeroSwingPage  extends JFrame implements SelectHeroView {
	private SelectHeroController controller;

	@Override
	public void welcome() {
		JLabel welcome = new JLabel("Hello! Welcome to swingy game!");
		setLayout(null);
		welcome.setSize(300, 30);
		welcome.setLocation(10, 10);
		super.add(welcome);
		super.setVisible(true);
	}

	@Override
	public void showAllHeroes(List<Hero> heroes) {
	}

	@Override
	public boolean booleanQuestion(String text, String errorText) {
		return JOptionPane.showConfirmDialog(this,
				text,
				"Choice",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	@Override
	public Hero selectHero(List<Hero> heroes) throws InterruptedException {
		int position = 50;
		final Hero[] result = new Hero[1];
		final boolean[] isWait = {true};
		for (final Hero hero : heroes) {
			JButton heroButton = new JButton(hero.getName());
			heroButton.setSize(300, 50);
			heroButton.setLocation(10, position);
			position += 50;
			heroButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isWait[0] = false;
					result[0] = hero;
				}
			});
			super.add(heroButton);
		}
		super.validate();
		super.repaint();
		while (isWait[0]) {
			Thread.sleep(100);
		}
		this.setVisible(false);
		return result[0];
	}

	@Override
	public Hero createHero() throws InterruptedException {
		final Hero[] result = new Hero[1];
		result[0] = new Hero();
		final Hero.HeroBuilder builder = new Hero.HeroBuilder(result[0]);

		ButtonGroup group = new ButtonGroup();
		final JRadioButton man = new JRadioButton();
		man.setText("man");
		man.setBounds(10, 50, 90, 30);
		final JRadioButton woman = new JRadioButton();
		woman.setText("woman");
		woman.setBounds(100, 50, 90, 30);
		group.add(man);
		group.add(woman);
		final JTextField name = new JTextField("Hero name");
		name.setBounds(10, 100, 250, 30);
		this.add(man);
		this.add(woman);
		this.add(name);


		final boolean[] isWait = {true};
		JButton heroButton = new JButton("Save");
		heroButton.setSize(100, 100);
		heroButton.setLocation(10, 150);
		heroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//заполняю поля героя
				if (man.isSelected() && !woman.isSelected()) {
					builder.setHeroClass(HeroClass.MAN);
				} else if (!man.isSelected() && woman.isSelected()) {
					builder.setHeroClass(HeroClass.WOMAN);
				}
				builder.setName(name.getText());
				List<String> errors = controller.validateHero(builder);
				if (errors == null || errors.size() == 0) {
					isWait[0] = false;
				} else {
					StringBuilder sb = new StringBuilder();
					for (String error : errors) {
						sb.append(error).append("\n");
					}
					warning(sb.toString());
				}
			}
		});
		this.add(heroButton);
		super.validate();
		super.repaint();
		while (isWait[0]) {
			Thread.sleep(100);
		}
		this.setVisible(false);
		return result[0];
	}

	private void warning(String error) {
		JOptionPane.showConfirmDialog(this,
				error,
				"Choice",
				JOptionPane.OK_CANCEL_OPTION);
	}

	@Override
	public void destroy() {
		super.setVisible(false);
	}

	public SelectHeroSwingPage(SelectHeroController controller) {
		super("Set hero page");
		this.controller = controller;
		this.setBounds(100, 100, 500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
