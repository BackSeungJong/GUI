package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

class Rect {

	private int x, y, width, height;

	public Rect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}

class PushPanel extends MyUtil {

	private int dir;// 0정지 1왼 2하 3오 4상
	private boolean check;
	private int xx;
	private int yy;

	// 사각형 2개
	private final int SIZE = 100;
	private Rect r1 = null;
	private Rect r2 = null;
	// 방향키
	private JButton[] btn = new JButton[4];

	public PushPanel() {
		// TODO Auto-generated constructor stub
		setLayout(null);
		setBounds(0, 0, 700, 700);
		setBackground(new Color(251, 243, 228));

		setRect();
		setBtn();

		setFocusable(true);
		addKeyListener(this);
	}

	private void setRect() {
		Random ran = new Random();

		int x = ran.nextInt(700 - 100);
		int y = ran.nextInt(700 - 100);

		this.r1 = new Rect(x, y, SIZE, SIZE);

		while (true) {
			x = ran.nextInt(700 - 100 - 200) + 100;
			y = ran.nextInt(700 - 100 - 200) + 100;

			if (x >= this.r1.getX() && x < this.r1.getX() + SIZE && y >= this.r1.getY() && y < this.r1.getY() + SIZE)
				continue;
			if (x + SIZE >= this.r1.getX() && x + SIZE < this.r1.getX() + SIZE && y >= this.r1.getY()
					&& y < this.r1.getY() + SIZE)
				continue;
			if (x >= this.r1.getX() && x < this.r1.getX() + SIZE && y + SIZE >= this.r1.getY()
					&& y + SIZE < this.r1.getY() + SIZE)
				continue;
			if (x + SIZE >= this.r1.getX() && x + SIZE < this.r1.getX() + SIZE && y + SIZE >= this.r1.getY()
					&& y + SIZE < this.r1.getY() + SIZE)
				continue;

			break;
		}
		this.r2 = new Rect(x, y, SIZE, SIZE);
	}

	private void setBtn() {
		String[] text = { "←", "↓", "→", "↑" };

		int size = 50;
		int x = 700 - 300;
		int y = 700 - 200;
		for (int i = 0; i < this.btn.length; i++) {
			this.btn[i] = new JButton();
			this.btn[i].setBounds(x, y, size, size);
			this.btn[i].setText(text[i]);
//			this.btn[i].addMouseListener(this);
			add(btn[i]);
			x += size;
			if (i == this.btn.length - 1 - 1) {
				x = 700 - 300 + size;
				y -= size;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		update();

		// r1 r2
		g.setColor(Color.black);
		g.drawRect(this.r1.getX(), this.r1.getY(), this.r1.getWidth(), this.r1.getHeight());

		if (check) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.blue);
		}
		g.drawRect(this.r2.getX(), this.r2.getY(), this.r2.getWidth(), this.r2.getHeight());

		// btn
		for (int i = 0; i < this.btn.length; i++) {
			g.setColor(Color.gray);
			g.drawRect(this.btn[i].getX(), this.btn[i].getY(), this.btn[i].getWidth(), this.btn[i].getHeight());
		}

		repaint();
	}

	private void update() {
		// TODO Auto-generated method stub
		xx = this.r1.getX();
		yy = this.r1.getY();

		// 왼
		if (this.dir == 1 && this.r1.getX() > 0) {

			xx--;
		}
		// 하
		else if (this.dir == 2 && this.r1.getY() + SIZE <= 659) {
			yy++;
		}
		// 오
		else if (this.dir == 3 && this.r1.getX() + SIZE <= 682) {
			xx++;
		}
		// 상
		else if (this.dir == 4 && this.r1.getY() > 0) {
			yy--;
		}

		checkAnother();

		if (dir == 1) {
			if (check && this.r2.getX() + SIZE > SIZE) {
				this.r2.setX(this.r2.getX() - 1);
			} else if (!check && xx > 0) {
				this.r1.setX(xx);
			}
		} else if (dir == 2) {
			if (check && this.r2.getY() + SIZE <= 659) {
				this.r2.setY(this.r2.getY() + 1);
			} else if (!check && yy < 700) {
				this.r1.setY(yy);
			}
		} else if (dir == 3) {
			if (check && this.r2.getX() + SIZE <= 682) {
				this.r2.setX(this.r2.getX() + 1);
			} else if (!check && xx < 700) {
				this.r1.setX(xx);
			}
		} else if (dir == 4) {
			if (check && this.r2.getY() + SIZE > SIZE) {
				this.r2.setY(this.r2.getY() - 1);
			} else if (!check && yy > 0) {
				this.r1.setY(yy);
			}
		}
	}

	private void checkAnother() {
		check = false;
		// TODO Auto-generated method stub
		if (this.dir == 1) {
			if (this.r2.getX() + SIZE == xx && this.r2.getY() >= this.r1.getY() - SIZE
					&& this.r2.getY() < this.r1.getY() + SIZE) {
				this.check = true;
			}
		}
		if (this.dir == 2) {
			if (this.r2.getY() == yy + SIZE && this.r2.getX() >= this.r1.getX() - SIZE
					&& this.r2.getX() < this.r1.getX() + SIZE) {
				this.check = true;
			}
		}
		if (this.dir == 3) {
			if (this.r2.getX() == xx + SIZE && this.r2.getY() >= this.r1.getY() - SIZE
					&& this.r2.getY() < this.r1.getY() + SIZE) {
				this.check = true;
			}
		}
		if (this.dir == 4) {
			if (this.r2.getY() + SIZE == yy && this.r2.getX() >= this.r1.getX() - SIZE
					&& this.r2.getX() < this.r1.getX() + SIZE) {
				this.check = true;
			}
		}
	}

//	@Override
//	public void mousePressed(MouseEvent e) {
//		// TODO Auto-generated method stub
//		// private int dir;//0정지 1왼 2하 3오 4상
//		if (e.getSource() == this.btn[0]) {
//			this.dir = 1;
//		} else if (e.getSource() == this.btn[1]) {
//			this.dir = 2;
//		} else if (e.getSource() == this.btn[2]) {
//			this.dir = 3;
//		} else if (e.getSource() == this.btn[3]) {
//			this.dir = 4;
//		}
//	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.dir = 0;
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println(e.getKeyCode());
		// 좌
		if (e.getKeyCode() == e.VK_LEFT) {
			System.out.println("좌");
			this.dir = 1;
		}
		// 하
		if (e.getKeyCode() == e.VK_DOWN) {
			System.out.println("아래");
			this.dir = 2;
		}
		// 우
		if (e.getKeyCode() == e.VK_RIGHT) {
			System.out.println("우");
			this.dir = 3;
		}
		// 상
		if (e.getKeyCode() == e.VK_UP) {
			System.out.println("업");
			this.dir = 4;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		this.dir = 0;
	}

}

class PushGame extends JFrame {

	private PushPanel panel = new PushPanel();

	public PushGame() {
		super("Push Push");
		setLayout(null);
		setBounds(50, 50, 700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new PushPanel());

		setVisible(true);
		revalidate();
	}
}

public class Main {
	public static void main(String[] args) {
		PushGame game = new PushGame();
	}
}
