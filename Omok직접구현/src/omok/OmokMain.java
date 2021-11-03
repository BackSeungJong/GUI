package omok;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Co {
	int x;
	int y;
	Color color;

	public Co(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Co(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
}

class Pop extends JFrame {
	JLabel lb = new JLabel();

	public Pop(String name) {
		super(name);
		setBounds((MainFrame.W - 100) / 2, (MainFrame.H - 100) / 2, 500, 300);
		setLayout(null);

		lb.setBounds(0, 0, 500, 250);
		lb.setFont(new Font("", Font.BOLD, 20));
		lb.setHorizontalAlignment(JLabel.CENTER);
		add(lb);

		setVisible(true);
	}
}

class GameBoard extends JPanel {

	// 오목판
	JButton map[][] = new JButton[10][10];

	public GameBoard() {
		setBounds(305, 5, 675, 750);
		setBackground(new Color(249, 207, 147));
		setLayout(null);
		setMap();
	}

	private void setMap() {
		int x = 40;
		int y = 55;

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new JButton();
				map[i][j].setBounds(x, y, 45, 45);
				map[i][j].setBorderPainted(false);
				map[i][j].setContentAreaFilled(false);

//				map[i][j].addActionListener(new mal());

				add(map[i][j]);
				x += 60;
			}
			x = 40;
			y += 60;
		}
	}

	public Co getCo(JButton btn) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (btn.equals(map[i][j])) {
					Co co = new Co(i, j);
					return co;
				}
			}
		}
		return null;
	}

	public void resetMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				remove(map[i][j]);
			}
		}
		setMap();
	}

	public Color check(Co co, Color color) {
		int x = co.x;
		int y = co.y;

		boolean reverse = false;

		// 위아래
		int count = 0;
		for (int i = 1; i <= 5; i++) {
			if (x - i >= 0 && map[x - i][y].getBackground().equals(color)) {
				count++;
			} else if (x - i < 0 || !map[x - i][y].getBackground().equals(color)) {
				break;
			}
			if (count == 4) {
				System.out.println("세로 이김");
				return color;
			}
		}
		for (int i = 1; i <= 5; i++) {
			if (x + i < map.length && map[x + i][y].getBackground().equals(color)) {
				count++;
			} else if (x + i >= map.length || !map[x + i][y].getBackground().equals(color)) {
				break;
			}
			if (count == 4) {
				System.out.println("세로 이김");
				return color;
			}
		}

		// 좌우
		count = 0;
		for (int i = 1; i <= 5; i++) {
			if (y - i >= 0 && map[x][y - i].getBackground().equals(color)) {
				count++;
			} else if (y - i < 0 || !map[x][y - i].getBackground().equals(color)) {
				break;
			}
			if (count == 4) {
				System.out.println("가로 이김");
				return color;
			}
		}
		for (int i = 1; i <= 5; i++) {
			if (y + i < map.length && map[x][y + i].getBackground().equals(color)) {
				count++;
			} else if (y + i >= map.length || !map[x][y + i].getBackground().equals(color)) {
				break;
			}
			if (count == 4) {
				System.out.println("가로 이김");
				return color;
			}
		}
		// \
		count = 0;
		for (int i = 1; i <= 5; i++) {
			if (x - i >= 0 && y - i >= 0 && map[x - i][y - i].getBackground().equals(color)) {
				count++;
			} else if (x - i < 0 || y - i < 0 || !map[x - i][y - i].getBackground().equals(color)) {
				break;
			}
			if (count == 4) {
				System.out.println("대각선 이김");
				return color;
			}
		}
		for (int i = 1; i <= 5; i++) {
			if (x + i < map.length && y + i < map.length && map[x + i][y + i].getBackground().equals(color)) {
				count++;
			} else if (x + i >= map.length || y + i >= map.length || !map[x + i][y + i].getBackground().equals(color)) {
				break;
			}
			if (count == 4) {
				System.out.println("대각선 이김");
				return color;
			}
		}
		// /
		count = 0;
		for (int i = 1; i <= 5; i++) {
			if (x - i >= 0 && y + i < map.length && map[x - i][y + i].getBackground().equals(color)) {
				count++;
			} else if (x - i < 0 || y + i >= map.length || !map[x - i][y + i].getBackground().equals(color)) {
				break;
			}
			if (count == 4) {
				System.out.println("대각선 이김");
				return color;
			}
		}
		for (int i = 1; i <= 5; i++) {
			if (x + i < map.length && y - i >= 0 && map[x + i][y - i].getBackground().equals(color)) {
				count++;
			} else if (x + i >= map.length || y + i < 0 || !map[x + i][y - i].getBackground().equals(color)) {
				break;
			}
			if (count == 4) {
				System.out.println("대각선 이김");
				return color;
			}
		}

		return null;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		// 줄 그리기
		for (int i = 1; i <= 10; i++) {
			// 가로 줄 그리기
			g.drawLine(65, (i * 60) + 15, 605, (i * 60) + 15); // 시작점
			// 세로줄 그리기
			g.drawLine((i * 60) + 5, 75, (i * 60) + 5, 615); // 시작점
		}
		drawStone(g);

	}

	private void drawStone(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j].getBackground() == Color.BLACK) {
					g.setColor(Color.BLACK);
					g.fillRoundRect(j * 60 + 50, i * 60 + 60, 30, 30, 30, 30);
				} else if (map[i][j].getBackground() == Color.WHITE) {
					g.setColor(Color.WHITE);
					g.fillRoundRect(j * 60 + 50, i * 60 + 60, 30, 30, 30, 30);
				}
			}
		}
	}

}

class InfoPanel extends JPanel {

	// 헤드
	JLabel head = new JLabel("재미있는 오목게임");

	// 차례
	JLabel line = new JLabel();
	JLabel turn = new JLabel();

	// 타이머
	Thread timer;
	int m, s, t = 0;
	JLabel mm = new JLabel("00");
	JLabel c = new JLabel(":");
	JLabel ss = new JLabel("00");

	// 버튼()
	JButton reset = new JButton("RESET");
	JButton guide = new JButton("GUIDE");

	public InfoPanel() {
		setBounds(5, 5, 295, 750);
		setBackground(new Color(249, 207, 147));
		setLayout(null);

		set_head();
		set_line();
		set_turn();
		set_timer();
		set_button();

	}

	private void set_head() {
		head.setBounds(0, 0, 295, 100);
		head.setFont(new Font("", Font.BOLD, 30));
		head.setHorizontalAlignment(JLabel.CENTER);
		head.setVerticalAlignment(JLabel.CENTER);
		add(head);
	}

	private void set_line() {
		line.setBounds(70, 130, 200, 100);
		line.setText("차례 ☞");
		line.setFont(new Font("", Font.ROMAN_BASELINE, 20));
		add(line);
	}

	private void set_turn() {
		turn.setBounds(170, 130, 200, 100);
		turn.setText("검");
		turn.setFont(new Font("", Font.ROMAN_BASELINE, 20));
		add(turn);
	}

	public void changeTurn() {
		if (turn.getText().equals("검")) {
			turn.setText("흰");
		} else {
			turn.setText("검");
		}
	}

	private void set_timer() {
		mm.setBounds(90, 250, 50, 50);
		mm.setFont(new Font("", Font.BOLD, 30));

		c.setBounds(130, 250, 50, 50);
		c.setFont(new Font("", Font.BOLD, 30));

		ss.setBounds(150, 250, 50, 50);
		ss.setFont(new Font("", Font.BOLD, 30));

		add(mm);
		add(c);
		add(ss);

	}

	public void makeThread() {
		timer = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				while (isRun) {
					try {
						t++;
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
					m = t / 60;
					s = t % 60;

					mm.setText(String.format("%02d", m));
					ss.setText(String.format("%02d", s));
				}
			}
		});
	}

	private void set_button() {
		// reset
		reset.setBounds(45, 500, 200, 50);
		reset.setFont(new Font("", Font.BOLD, 15));
		reset.setBackground(new Color(219, 208, 192));
		add(reset);

		// guide
		guide.setBounds(45, 555, 200, 50);
		guide.setFont(new Font("", Font.BOLD, 15));
		guide.setBackground(new Color(219, 208, 192));
		add(guide);
	}

	boolean isRun = false;

}

class MainPanel extends JPanel {

	InfoPanel infopanel = new InfoPanel();
	GameBoard gameboard = new GameBoard();

	private int turn = 1;
	// 돌 색깔
	Color stone = Color.BLACK;

	public MainPanel() {
		setBounds(0, 0, MainFrame.W, MainFrame.H);
		setLayout(null);
		setBackground(new Color(249, 228, 200));

		add(infopanel);
		add(gameboard);
		button_setting();
	}

	private void button_setting() {
		infopanel.reset.addActionListener(new mal());
		infopanel.guide.addActionListener(new mal());

		for (int i = 0; i < gameboard.map.length; i++) {
			for (int j = 0; j < gameboard.map[i].length; j++) {
				gameboard.map[i][j].addActionListener(new mal());
			}
		}
	}

	class mal implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton) e.getSource();

			if (btn.getText().equals("RESET")) {
				System.out.println("리셋클릭");
				reset();
			} else if (btn.getText().equals("GUIDE")) {
				System.out.println("가이드클릭");
//				new GuidePop("가이드");

			} else {
				if (infopanel.timer == null) {
					System.out.println("타이머작동");
					infopanel.isRun = true;
					infopanel.makeThread();
					infopanel.timer.start();
				}
				if (btn.getBackground() != Color.BLACK && btn.getBackground() != Color.WHITE) {
					if (turn == 1) {
//						btn.setContentAreaFilled(true);
						btn.setBackground(stone);
						gameboard.repaint();
						if (gameboard.check(gameboard.getCo(btn), stone) != null) {
							Pop a = new Pop("검은색 승리!");
							infopanel.isRun = false;
							a.lb.setText(String.format("%s : %s", infopanel.mm.getText(), infopanel.ss.getText()));
							reset();
						} else {
							stone = Color.WHITE;
							infopanel.changeTurn();
							turn = 2;
						}
					} else if (turn == 2) {
//						btn.setContentAreaFilled(true);
						btn.setBackground(stone);
						gameboard.repaint();
						if (gameboard.check(gameboard.getCo(btn), stone) != null) {
							Pop a = new Pop("흰색 승리!");
							infopanel.isRun = false;
							a.lb.setText(String.format("%s : %s", infopanel.mm.getText(), infopanel.ss.getText()));
							reset();
						} else {
							stone = Color.BLACK;
							infopanel.changeTurn();
							turn = 1;
						}
					}
				}
			}
		}
	}

	public void reset() {
		infopanel.timer = null;
		infopanel.isRun = false;
		infopanel.t = 0;
		infopanel.m = 0;
		infopanel.s = 0;
		infopanel.mm.setText("00");
		infopanel.ss.setText("00");
		infopanel.turn.setText("검");
		stone = Color.BLACK;
		turn = 1;

		gameboard.resetMap();
		for (int i = 0; i < gameboard.map.length; i++) {
			for (int j = 0; j < gameboard.map[i].length; j++) {
				gameboard.map[i][j].addActionListener(new mal());
			}
		}
	}
}

class MainFrame extends JFrame {

	Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
	public static int W = 1000;
	public static int H = 800;

	public MainFrame() {
		super("MY OMOK GAME");
		setBounds((dm.width - W) / 2, (dm.height - H) / 2, W, H);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new MainPanel());

		setVisible(true);
		revalidate();
	}
}

public class OmokMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame game = new MainFrame();
	}
}
