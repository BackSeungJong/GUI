package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Panel extends MyUtil {
	private boolean MainRun = true;

	private Rect[][] map;
	private ArrayList<Rect> item; // 아이탬 위치정보
	private JLabel itemCnt;
	private String text = "";

	private int dir = 3; // 방향
	private int size; // 스네이크 크기
	private Rect[] snake;

	private JButton[] key; // 방향키
	private JButton reset; // 리셋 : Enter

	private int count = 0;
	private Alter a;

	public Panel() {
		setLayout(null);
		setBounds(0, 0, 1000, 800);
		setBackground(new Color(223, 216, 202));

		Cnt();
		key();
		map();
		snake();
		setFocusable(true);
		addKeyListener(this);
	}

	private void Cnt() {
		text = "";
		item = new ArrayList<>();
		itemCnt = new JLabel();
		text = "[아이템 : " + item.size() + " 개]";
		itemCnt.setText(text);
		itemCnt.setFont(new Font("", Font.ROMAN_BASELINE, 20));
		itemCnt.setBounds(1000 - 250, 800 - 600, 200, 100);
		add(itemCnt);
	}

	private void key() {
		key = new JButton[4];
		String[] text = { "←", "↓", "→", "↑" };

		// 방향키
		int x = 1000 - 250;
		int y = 800 - 300;
		for (int i = 0; i < key.length; i++) {
			key[i] = new JButton();
			key[i].setBounds(x, y, 50, 50);
			key[i].setText(text[i]);
			add(key[i]);

			x += 50;
			if (i == key.length - 1 - 1) {
				y -= 50;
				x = 1000 - 250 + 50;
			}
		}

		// 리셋 버튼
		reset = new JButton();
		reset.setBounds(1000 - 250, 800 - 225 - 10, 150, 35);
		reset.setText("RESET");
		reset.addMouseListener(this);
		add(reset);
	}

	private void map() {
		int size = 30;
		int x = 50;
		int y = 50;

		map = new Rect[20][20];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Rect(x, y, size, size);
				x += size;
			}
			x = 50;
			y += size;
		}

	}

	private void snake() {
		size = 4;
		snake = new Rect[4];
		for (int i = 0; i < size; i++) {
			snake[i] = new Rect(map[10][i + 5].getX(), map[10][i + 5].getY(), map[10][i + 5].getWidth(),
					map[10][i + 5].getHeight());
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		if (MainRun) {
			super.paintComponent(g);
			update();
			if (item.size() < 10 && count == 3) {
				count = 0;
				item();
			}
			if (!item.isEmpty()) {
				for (int i = 0; i < item.size(); i++) {
					g.setColor(new Color(78, 159, 61));
					g.fillRect(item.get(i).getX(), item.get(i).getY(), item.get(i).getWidth(), item.get(i).getHeight());
				}
			}
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					g.setColor(Color.black);
					g.drawRect(map[i][j].getX(), map[i][j].getY(), map[i][j].getWidth(), map[i][j].getHeight());
				}
			}
			for (int i = 0; i < snake.length; i++) {
				if (i == snake.length - 1) {
					g.setColor(new Color(73, 255, 0));
					g.fillRect(snake[i].getX(), snake[i].getY(), snake[i].getWidth(), snake[i].getHeight());
				} else {
					g.setColor(new Color(62, 124, 23));
					g.fillRect(snake[i].getX(), snake[i].getY(), snake[i].getWidth(), snake[i].getHeight());
				}
			}
			repaint();

			try {
				Thread.sleep(500);
				count++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("종료");
		}

	}

	private void item() {
		// TODO Auto-generated method stub
		Random ran = new Random();

		int x = 0;
		int y = 0;
		boolean check = true;
		while (true) {
			x = ran.nextInt(map.length);
			y = ran.nextInt(map.length);

			for (int i = 0; i < snake.length; i++) {
				if (map[x][y].getX() == snake[i].getX() && map[x][y].getY() == snake[i].getY()) {
					check = false;
				}
			}
			if (check) {
				item.add(new Rect(map[x][y].getX(), map[x][y].getY(), map[x][y].getWidth(), map[x][y].getHeight()));
				text = "[아이템 : " + item.size() + " 개]";
				itemCnt.setText(text);
				break;
			}
		}
	}

	// snake의 좌표를 갱신
	private void update() {
		// 좌
		if (dir == 1) {
//			System.out.println(snake[size - 1].getX() - 30);
//			System.out.println(map[0][0].getX() - 30);
			boolean chk = true;
			for (int i = 0; i < snake.length; i++) {
				if (snake[size - 1].getX() - 30 == snake[i].getX() && snake[size - 1].getY() == snake[i].getY()) {
					chk = false;
				}
			}
			if (chk && snake[size - 1].getX() - 30 >= map[0][0].getX() - 30) {
				System.out.println("←");
				// 아이템을 만났을때
				int check = meet(snake[size - 1].getX() - 30, snake[size - 1].getY());
				if (check != -1) {
					size++;
					Rect[] tmp = snake;
					snake = new Rect[size];
					for (int i = 0; i < size - 1; i++) {
						snake[i] = tmp[i];
					}
					snake[size - 1] = item.get(check);
					item.remove(check);
					text = "[아이템 : " + item.size() + " 개]";
					itemCnt.setText(text);
				}
				// 아닐때
				else {
					for (int i = 0; i < snake.length - 1; i++) {
						snake[i].setX(snake[i + 1].getX());
						snake[i].setY(snake[i + 1].getY());
					}
					snake[size - 1].setX(snake[size - 1].getX() - 30);
				}

			} else {
				System.out.println("죽음");
				new Alter();
				MainRun = false;
			}
		}
		// 하
		else if (dir == 2) {
//			System.out.println(snake[size - 1].getY() + 30);
//			System.out.println(map[19][19].getY() + 30);
			boolean chk = true;
			for (int i = 0; i < snake.length; i++) {
				if (snake[size - 1].getX() == snake[i].getX() && snake[size - 1].getY() + 30 == snake[i].getY()) {
					chk = false;
				}
			}
			if (chk && snake[size - 1].getY() + 30 <= map[19][19].getY() + 30) {
				System.out.println("↓");
				// 아이템을 만났을때
				int check = meet(snake[size - 1].getX(), snake[size - 1].getY() + 30);
				if (check != -1) {
					size++;
					Rect[] tmp = snake;
					snake = new Rect[size];
					for (int i = 0; i < size - 1; i++) {
						snake[i] = tmp[i];
					}
					snake[size - 1] = item.get(check);
					item.remove(check);
					text = "[아이템 : " + item.size() + " 개]";
					itemCnt.setText(text);
				}
				// 아닐때
				else {
					for (int i = 0; i < snake.length - 1; i++) {
						snake[i].setX(snake[i + 1].getX());
						snake[i].setY(snake[i + 1].getY());
					}
					snake[size - 1].setY(snake[size - 1].getY() + 30);
				}
			} else {
				System.out.println("죽음");
				new Alter();
				MainRun = false;
			}
		}
		// 우
		else if (dir == 3) {
//			System.out.println(snake[size - 1].getX() + 30);
//			System.out.println(map[19][19].getX() + 30);
			boolean chk = true;
			for (int i = 0; i < snake.length; i++) {
				if (snake[size - 1].getX() + 30 == snake[i].getX() && snake[size - 1].getY() == snake[i].getY()) {
					chk = false;
				}
			}
			if (chk && snake[size - 1].getX() + 30 <= map[19][19].getX() + 30) {
				System.out.println("→");
				int check = meet(snake[size - 1].getX() + 30, snake[size - 1].getY());
				if (check != -1) {
					size++;
					Rect[] tmp = snake;
					snake = new Rect[size];
					for (int i = 0; i < size - 1; i++) {
						snake[i] = tmp[i];
					}
					snake[size - 1] = item.get(check);
					item.remove(check);
					text = "[아이템 : " + item.size() + " 개]";
					itemCnt.setText(text);
				}
				// 아닐때
				else {
					for (int i = 0; i < snake.length - 1; i++) {
						snake[i].setX(snake[i + 1].getX());
						snake[i].setY(snake[i + 1].getY());
					}
					snake[size - 1].setX(snake[size - 1].getX() + 30);
				}
			} else {
				System.out.println("죽음");
				new Alter();
				MainRun = false;

			}
		}
		// 상
		else if (dir == 4) {
//			System.out.println(snake[size - 1].getY() - 30);
//			System.out.println(map[0][0].getY());
			boolean chk = true;
			for (int i = 0; i < snake.length; i++) {
				if (snake[size - 1].getX() == snake[i].getX() && snake[size - 1].getY() - 30 == snake[i].getY()) {
					chk = false;
				}
			}
			if (chk && snake[size - 1].getY() - 30 >= map[0][0].getY() - 30) {
				System.out.println("↑");
				int check = meet(snake[size - 1].getX(), snake[size - 1].getY() - 30);
				if (check != -1) {
					size++;
					Rect[] tmp = snake;
					snake = new Rect[size];
					for (int i = 0; i < size - 1; i++) {
						snake[i] = tmp[i];
					}
					snake[size - 1] = item.get(check);
					item.remove(check);
					text = "[아이템 : " + item.size() + " 개]";
					itemCnt.setText(text);
				}
				// 아닐때
				else {
					for (int i = 0; i < snake.length - 1; i++) {
						snake[i].setX(snake[i + 1].getX());
						snake[i].setY(snake[i + 1].getY());
					}
					snake[size - 1].setY(snake[size - 1].getY() - 30);
				}
			} else {
				System.out.println("죽음");
				new Alter();
				MainRun = false;
			}
		}
//		System.out.println("move");
	}

	private int meet(int x, int y) {
		for (int i = 0; i < item.size(); i++) {
			if (item.get(i).getX() == x && item.get(i).getY() == y) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// 좌
		if (dir != 3 && e.getKeyCode() == e.VK_LEFT) {
			System.out.println("left");
			this.dir = 1;
		}
		// 하
		else if (dir != 4 && e.getKeyCode() == e.VK_DOWN) {
			System.out.println("down");
			this.dir = 2;
		}
		// 우
		else if (dir != 1 && e.getKeyCode() == e.VK_RIGHT) {
			System.out.println("right");
			this.dir = 3;
		}
		// 상
		else if (dir != 2 && e.getKeyCode() == e.VK_UP) {
			System.out.println("up");
			this.dir = 4;
		}
		if (e.getKeyCode() == e.VK_ENTER) {
			System.out.println("리셋");
			this.dir = 3;
			itemCnt.setText("");
			count = 0;
			Cnt();
			key();
			map();
			snake();
			MainRun = true;
		}
	}
}

class p extends JPanel {
	JLabel l = new JLabel();

	public p() {
		setBounds(0, 0, 300, 300);
		setLayout(null);

		l.setText("The End");
		l.setBounds(0, 0, 300, 200);
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setFont(new Font("", Font.BOLD, 30));
		add(l);
	}
}

class Alter extends JFrame {

	public Alter() {
		super("GAME OVER");
		setLayout(null);
		setBounds(200, 200, 300, 300);
		setVisible(true);
		add(new p());
	}
}

public class Game extends JFrame {
	public Game() {
		super("Snake Game");
		setLayout(null);
		setBounds(300, 100, 1000, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new Panel());

		setVisible(true);
		revalidate();
	}
}
