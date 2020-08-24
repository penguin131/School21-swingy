package swingy.view.swing;

import swingy.controller.BattlegroundController;
import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.model.Hero;
import swingy.model.Villain;
import swingy.view.BattleView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Map;

import static swingy.view.utils.Button.getButton;

public class SwingBattlePage extends JFrame implements BattleView {

	private Map<Coordinate, GameCharacter> characters;
	private BattlegroundController controller;
	private int mapSize;
	private ImageIcon hero;
	private ImageIcon villain;
	private ImageIcon zero;
	private JLabel[][] map;

	public SwingBattlePage(Map<Coordinate, GameCharacter> characters, int mapSize, BattlegroundController controller) {
		super("Battleground");
		this.controller = controller;
		this.characters = characters;
		this.mapSize = mapSize;
		this.setBounds(100, 100, 50 * mapSize + 6, 50 * mapSize + 26);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hero = resizeImage(new ImageIcon("/Users/bootcamp/Desktop/swingy/target/classes/images/hero.png"));
		villain = resizeImage(new ImageIcon("/Users/bootcamp/Desktop/swingy/target/classes/images/villain.png"));
		zero = resizeImage(new ImageIcon("/Users/bootcamp/Desktop/swingy/target/classes/images/zero.png"));
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
				} catch (IOException ex) {
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
				}
				this.add(map[i][j]);
			}
		}
		this.setVisible(true);
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
}
