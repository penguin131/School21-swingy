package swingy.view.swing;

import swingy.controller.BattlegroundController;
import swingy.model.*;
import swingy.view.BattleView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static swingy.view.utils.Button.getButton;

public class SwingBattlePage extends JFrame implements BattleView {

	private Map<Coordinate, GameCharacter> characters;
	private int mapSize;
	private ImageIcon hero;
	private ImageIcon villain;
	private ImageIcon zero;
	private JLabel[][] map;

	public SwingBattlePage(Map<Coordinate, GameCharacter> characters, int mapSize, final BattlegroundController controller) {
		super("Battleground");
		this.characters = characters;
		this.mapSize = mapSize;
		this.setBounds(100, 100, 50 * mapSize + 206, 50 * mapSize + 26);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String userDir = System.getProperty("user.dir");
		hero = resizeImage(new ImageIcon(userDir + "/target/classes/images/hero.png"));
		villain = resizeImage(new ImageIcon(userDir + "/target/classes/images/villain.png"));
		zero = resizeImage(new ImageIcon(userDir + "/target/classes/images/zero.png"));
		map = new JLabel[mapSize][];
		this.setLayout(null);
		for (int i = 0; i < mapSize; i++) {
			map[i] = new JLabel[mapSize];
			for (int j = 0; j < mapSize; j++) {
				map[i][j] = new JLabel();
				map[i][j].setLocation(i * 50 + 3, j * 50 + 3);
				map[i][j].setSize(45, 45);
			}
		}
		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
					controller.pressButton(getButton(e.getKeyCode()));
				} catch (IOException | InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	@Override
	public void printMap() {
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				GameCharacter field = characters.get(new Coordinate(i, j));
				if (field == null) {
					map[i][j].setIcon(zero);
				} else if (field instanceof Villain) {
					map[i][j].setIcon(villain);
				} else if (field instanceof Hero) {
					map[i][j].setIcon(hero);
					printHeroInfo((Hero) field);
				}
				this.add(map[i][j]);
			}
		}
		this.setVisible(true);
	}

	@Override
	public boolean fightChoice() {
		if (JOptionPane.showConfirmDialog(this,
				"Do you want to fight?",
				"Choice",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			return true;
		else
			return new Random().nextBoolean();
	}

	@Override
	public boolean artifactChoice(Artifact artifact) {
		return JOptionPane.showConfirmDialog(this,
				"Take artifact " + artifact.getType().getName() + "(" + artifact.getPower() + ")?",
				"Choice",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	@Override
	public void destroy() {
		setVisible(false);
		dispose();
	}

	private ImageIcon resizeImage(ImageIcon image) {
		Image image1 = image.getImage();
		Image newImg = image1.getScaledInstance(45, 45,  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);
	}

	private void printHeroInfo(Hero hero) {
		JLabel t1,t2, t3, t4, t5;
		t1 = new JLabel("Hero: " + hero.getName());
		t2 = new JLabel("lvl: " + hero.getCurrentLvl());
		t3 = new JLabel("attack: " + hero.getAttack());
		t4 = new JLabel("defence: " + hero.getDefence());
		t5 = new JLabel("hitPoints: " + hero.getHitPoint());
		t1.setBounds(50 * mapSize + 6, 6, 200,200);
		t2.setBounds(50 * mapSize + 6, 26, 200,200);
		t3.setBounds(50 * mapSize + 6, 46, 200,200);
		t4.setBounds(50 * mapSize + 6, 66, 200,200);
		t5.setBounds(50 * mapSize + 6, 86, 200,200);
		this.add(t1);
		this.add(t2);
		this.add(t3);
		this.add(t4);
		this.add(t5);
	}
}
