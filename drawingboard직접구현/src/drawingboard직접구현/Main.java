package drawingboard직접구현;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.MouseListener;

class Mypanel extends MyUtil {
	private int x = 0;
	private int y = 0;
	private int xx = 0;
	private int yy = 0;
	private int l1 = 0;
	private int l2 = 0;

	private boolean c = false;

	public Mypanel() {
		setLayout(null);
		setBounds(0, 0, 700, 700);
		setBackground(Color.white);
		setFocusable(true);

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		c = true;
		xx = 0;
		yy = 0;
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		xx = e.getX();
		yy = e.getY();
		l1 = Math.abs(xx - x);
		l2 = Math.abs(yy - y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		c = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (c && e.isShiftDown() == true && yy != 0 && xx != 0) {
			System.out.println("쉬프트");
			l1 = Math.abs(xx - x);
			l2 = Math.abs(yy - y);

			if (l1 > l2) {
				l2 = l1;
			} else {
				l1 = l2;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// System.out.println(c);
		if (yy != 0 && xx != 0) {
			if (xx - x < 0 && yy - y < 0) {
				g.drawRect(xx, yy, l1, l2);
			} else if (xx - x < 0 && yy - y >= 0) {
				g.drawRect(xx, y, l1, l2);
			} else if (xx - x >= 0 && yy - y < 0) {
				g.drawRect(x, yy, l1, l2);
			} else {
				g.drawRect(x, y, l1, l2);
			}
		}
		repaint();
	}
}

class Myframe extends JFrame implements MouseListener {

	JButton btn = new JButton();

	private void set() {
		btn.setText("Close");
		btn.setBounds(600, 700, 70, 50);
		btn.addMouseListener(this);
		add(btn);
	}

	public Myframe() {
		super("그림판");
		setLayout(null);
		setBounds(500, 200, 700, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new Mypanel());
		set();

		setVisible(true);
		revalidate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton tmp = (JButton) e.getSource();
		if (tmp.equals(btn)) {
			dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Myframe m = new Myframe();
	}
}
