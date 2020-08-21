package swingy.view.swing;

import swingy.model.Coordinate;
import swingy.model.GameCharacter;
import swingy.model.Hero;
import swingy.model.Villain;
import swingy.view.BattleView;

import javax.swing.*;
import java.util.Map;

public class SwingBattlePage extends JFrame implements BattleView {

	private Map<Coordinate, GameCharacter> characters;
	private int mapSize;
	private ImageIcon hero;
	private ImageIcon villain;
	private ImageIcon zero;
	private JLabel map[][];

	public SwingBattlePage(Map<Coordinate, GameCharacter> characters, int mapSize) {
		super("Battleground");
		this.characters = characters;
		this.mapSize = mapSize;
		this.setBounds(100, 100, 50 * mapSize + 6, 50 * mapSize + 26);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hero = new ImageIcon("/Users/bootcamp/Desktop/swingy/target/classes/images/hero.jpg");
		villain = new ImageIcon("/Users/bootcamp/Desktop/swingy/target/classes/images/villain.jpg");
		zero = new ImageIcon("/Users/bootcamp/Desktop/swingy/target/classes/images/zero.png");
		map = new JLabel[mapSize][];
		this.setLayout(null);
		for (int i = 0; i < mapSize; i++) {
			map[i] = new JLabel[mapSize];
			for (int j = 0; j < mapSize; j++) {
				map[i][j] = new JLabel();
				map[i][j].setLocation(i * 50 + 3, j * 50 + 3);
				map[i][j].setSize(45, 45);
//				map[i][j].setXY(i * 50, j * 50);
			}
		}

	}

	@Override
	public void printMap() {
//		this.removeAll();
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

		try {
			Thread.sleep(10000);
		} catch (Exception ex) {

		}
	}

	@Override
	public void destroy() {
		setVisible(false);
		dispose();
	}
}
