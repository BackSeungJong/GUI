package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

class Game extends MyUtil {

	private int dir = 0;
	private Rect[][] map = new Rect[10][10];
	private ArrayList<Rect> snake = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> yx = new ArrayList<ArrayList<Integer>>();

	private ArrayList<Rect> item = new ArrayList<Rect>();
	private ArrayList<ArrayList<Integer>> itemyx = new ArrayList<ArrayList<Integer>>();

	private Color head = Color.red;
	private Color body = Color.green;

	private boolean growtail;

	public Game() {
		setLayout(null);
		setBounds(0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);
//		setBackground(Color.orange);

		Map();
		Snake();
		Item();

		setFocusable(true);
		addKeyListener(this);
	}

	private void Map() {

		int start = SnakeGame.SIZE / 2 - 50 * 10 / 2;
		int x = start;
		int y = start;

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				this.map[i][j] = new Rect(x, y, 50, 50, Color.gray);
				x += 50;
			}
			x = start;
			y += 50;
		}
	}

	private void Snake() {
		for (int i = 0; i < 4; i++) {
			// snake
			Rect tmp = map[0][i];
			Rect nemo = null;

			if (i == 0) {
				nemo = new Rect(tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight(), head);
			} else {
				nemo = new Rect(tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight(), body);
			}
			snake.add(nemo);

			// yx
			ArrayList<Integer> pair = new ArrayList<Integer>();
			pair.add(0);
			pair.add(i);
			yx.add(pair);
		}
	}

	private void Item() {
		Random ran = new Random();
		int n = ran.nextInt(10) + 5;

		for (int i = 0; i < n; i++) {
			int y = ran.nextInt(map.length);
			int x = ran.nextInt(map.length);

			// check
			boolean check = false;
			for (int j = 0; j < yx.size(); j++) {
				if (y == yx.get(j).get(0) && x == yx.get(j).get(1)) {
					check = true;
				}
			}

			if (check) {
				i--;
			} else {
				Rect tmp = map[y][x];
				Rect putitem = new Rect(tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight(), Color.cyan);
				item.add(putitem);

				// itemyx
				ArrayList<Integer> pair = new ArrayList<Integer>();
				pair.add(y);
				pair.add(x);

				itemyx.add(pair);
			}
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// snake
		for (int i = 0; i < snake.size(); i++) {
			Rect nemo = snake.get(i);
			g.setColor(nemo.getC());
			g.fillRect(nemo.getX(), nemo.getY(), nemo.getWidth(), nemo.getHeight());

		}

		// map
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Rect nemo = this.map[i][j];
				g.setColor(nemo.getC());
				g.drawRect(nemo.getX(), nemo.getY(), nemo.getWidth(), nemo.getHeight());
			}
		}

		// item
		for (int i = 0; i < item.size(); i++) {
			Rect nemo = item.get(i);
			g.setColor(nemo.getC());
			g.fillRoundRect(nemo.getX(), nemo.getY(), nemo.getWidth() - 20, nemo.getHeight() - 20, nemo.getWidth() - 20,
					nemo.getHeight() - 20);
		}

		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_LEFT) {
			dir = 1;
		} else if (e.getKeyCode() == e.VK_DOWN) {
			dir = 4;
		} else if (e.getKeyCode() == e.VK_RIGHT) {
			dir = 2;
		} else if (e.getKeyCode() == e.VK_UP) {
			dir = 3;
		}

		move();
	}

	private void move() {

		// 머리위치
		int yy = yx.get(0).get(0);
		int xx = yx.get(0).get(1);

		// 좌
		if (dir == 1 && xx > 0) {
			xx--;
		}
		// 우
		else if (dir == 2 && xx < map.length - 1) {
			xx++;
		}
		// 상
		else if (dir == 3 && yy > 0) {
			yy--;
		}
		// 하
		else if (dir == 4 && yy < map.length - 1) {
			yy++;
		}

		// check item
		for (int i = 0; i < itemyx.size(); i++) {
			if (yy == itemyx.get(i).get(0) && xx == itemyx.get(i).get(1)) {
				item.remove(i);
				itemyx.remove(i);
				growtail = true;
			}
		}
		// check body
		boolean check = false;
		for (int i = 0; i < yx.size(); i++) {
			if (yy == yx.get(i).get(0) && xx == yx.get(i).get(1)) {
				check = true;
			}
		}

		if (!check) {
			// move
			Rect tail = snake.get(snake.size() - 1);
			ArrayList<Integer> tailyx = yx.get(yx.size() - 1);

			for (int i = snake.size() - 1; i > 0; i--) {
				// body
				Rect tmp = snake.get(i - 1);
				tmp.setC(body);
				snake.set(i, tmp);

				// yx
				yx.set(i, yx.get(i - 1));
			}
			Rect tmp = map[yy][xx];
			Rect newhead = new Rect(tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight(), head);
			snake.set(0, newhead);

			ArrayList<Integer> pair = new ArrayList<>();
			pair.add(yy);
			pair.add(xx);
			yx.set(0, pair);

			if (growtail) {
				snake.add(tail);
				yx.add(tailyx);
				growtail = false;
			}

		} else {
			System.out.println("DIE");
			new Alter();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		dir = 0;
	}

}

class Alter extends JFrame {

	private JLabel text = new JLabel();

	public Alter() {
		super("Game Clear");
		setBounds(SnakeGame.WIDTH / 2 - 150, SnakeGame.HEIGHT / 2 - 100, 300, 200);

		text.setText("사망");
		text.setBounds(0, 0, 300, 200);
		text.setFont(new Font("", Font.BOLD, 20));
		text.setHorizontalAlignment(JLabel.CENTER);
		add(text);

		setVisible(true);
		revalidate();

	}
}

class SnakeGame extends JFrame {

	public static Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
	public static int WIDTH = dm.width;
	public static int HEIGHT = dm.height;

	public static final int SIZE = 700;

	public SnakeGame() {
		super("Snake Game");
		setLayout(null);
		setBounds((WIDTH - SIZE) / 2, (HEIGHT - SIZE) / 2, SIZE, SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		revalidate();
		add(new Game());
	}
}

public class Snake {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SnakeGame game = new SnakeGame();
	}
}
