package drawingboard2;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class Line {
	int x1, y1, x2, y2;
	Color c;

	public Line(int x1, int y1, int x2, int y2, Color c) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.c = c;
	}
}

class Rect {
	int mod;
	int x1, y1;
	int width, height;
	Color c;

	public Rect(int mod, int x1, int y1, int x2, int y2, Color c) {
		this.mod = mod;
		this.x1 = x1;
		this.y1 = y1;
		this.width = x2;
		this.height = y2;
		this.c = c;
	}
}

class Triangle {
	int x[];
	int y[];
	int len;
	Color c;

	public Triangle(int x[], int y[], int len, Color c) {
		this.x = x;
		this.y = y;
		this.len = len;
		this.c = c;
	}
}

class panel extends MyUtil {

	private int mod = 3;
	private JButton mode[] = new JButton[4];
	private int cc = 0;
	private Color color[];
	private JButton Mycolor[] = new JButton[4];
	public JButton close = new JButton();

	private ArrayList<Line> line = new ArrayList<>();
	private ArrayList<Rect> rect = new ArrayList<>();
	private ArrayList<Triangle> tri = new ArrayList<>();
	private int x1, y1, x2, y2;
	int ax[] = new int[3];
	int ay[] = new int[3];
	int lx[] = new int[2];
	int ly[] = new int[2];

	public panel() {
		setLayout(null);
		setBounds(0, 0, 800, 800);
		setBackground(Color.white);
		setFocusable(true);
		setColor();
		Btn();

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	private void setColor() {
		color = new Color[4];
		color[0] = Color.black;
		color[1] = Color(47, 221, 146);
		color[2] = Color.blue;
		color[3] = Color(242, 240, 19);
	}

	private Color Color(int i, int j, int k) {
		// TODO Auto-generated method stub
		return new Color(i, j, k);
	}

	private void Btn() {
		close.setText("CLOSE");
		close.setBounds(670, 700, 80, 40);
		add(close);

		int x = 10;
		int y = 10;
		String shape[] = { "□", "○", "△", "붓" };
		for (int i = 0; i < mode.length; i++) {
			mode[i] = new JButton();
			mode[i].setText(shape[i]);
			mode[i].setFont(new Font("", 0, 15));
			mode[i].addMouseListener(this);
			mode[i].setBounds(x, y, 50, 50);
			add(mode[i]);
			y += 60;
		}
		x = 720;
		y = 10;

		for (int i = 0; i < Mycolor.length; i++) {
			Mycolor[i] = new JButton();
			Mycolor[i].setBackground(color[i]);
			Mycolor[i].setFont(new Font("", 0, 15));
			Mycolor[i].addMouseListener(this);
			Mycolor[i].setBounds(x, y, 50, 50);
			add(Mycolor[i]);
			y += 60;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < mode.length; i++) {
			if ((JButton) e.getSource() == mode[i]) {
				mod = i;
				System.out.println(mod);
			}
		}
		for (int i = 0; i < Mycolor.length; i++) {
			if ((JButton) e.getSource() == Mycolor[i]) {
				cc = i;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x2 = 0;
		y2 = 0;
		x1 = e.getX();
		y1 = e.getY();
		// System.out.printf("클(%d,%d)\n", x1, y1);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (mod == 0 || mod == 1) {
			x2 = e.getX();
			y2 = e.getY();
		} else if (mod == 2) {
			ax = new int[3];
			ay = new int[3];
			x2 = e.getX();
			y2 = e.getY();

			if (x1 < x2) {
				ax[0] = x1;
				ay[0] = y1;
				ax[1] = x1 - Math.abs(x1 - x2);
				ay[1] = y2;
				ax[2] = x2;
				ay[2] = y2;
			} else {
				ax[0] = x1;
				ay[0] = y1;
				ax[2] = x1 + Math.abs(x1 - x2);
				ay[2] = y2;
				ax[1] = x2;
				ay[1] = y2;
			}
		} else if (mod == 3) {

			x2 = e.getX();
			y2 = e.getY();
			System.out.printf("(%d,%d)~(%d,%d)\n", x1, y1, x2, y2);
			line.add(new Line(x1, y1, x2, y2, color[cc]));
			x1 = x2;
			y1 = y2;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (mod == 0 || mod == 1) {
			if (x2 != 0 && y2 != 0) {
				if (x1 > x2 && y1 > y2) {
					rect.add(new Rect(mod, x2, y2, Math.abs(x1 - x2), Math.abs(y1 - y2), color[cc]));
				} else if (x1 > x2 && y1 < y2) {
					rect.add(new Rect(mod, x2, y1, Math.abs(x1 - x2), Math.abs(y1 - y2), color[cc]));
				} else if (x1 < x2 && y1 > y2) {
					rect.add(new Rect(mod, x1, y2, Math.abs(x1 - x2), Math.abs(y1 - y2), color[cc]));
				} else {
					rect.add(new Rect(mod, x1, y1, Math.abs(x1 - x2), Math.abs(y1 - y2), color[cc]));
				}
			}
		} else if (mod == 2) {
			tri.add(new Triangle(ax, ay, 3, color[cc]));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color[cc]);
		// 현재 그리기
		if (x2 != 0 && y2 != 0) {
			if (mod == 0) {
				if (x1 > x2 && y1 > y2) {
					g.drawRect(x2, y2, Math.abs(x1 - x2), Math.abs(y1 - y2));
				} else if (x1 > x2 && y1 < y2) {
					g.drawRect(x2, y1, Math.abs(x1 - x2), Math.abs(y1 - y2));
				} else if (x1 < x2 && y1 > y2) {
					g.drawRect(x1, y2, Math.abs(x1 - x2), Math.abs(y1 - y2));
				} else {
					g.drawRect(x1, y1, Math.abs(x1 - x2), Math.abs(y1 - y2));
				}
			} else if (mod == 1) {
				if (x1 > x2 && y1 > y2) {
					g.drawRoundRect(x2, y2, Math.abs(x1 - x2), Math.abs(y1 - y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
				} else if (x1 > x2 && y1 < y2) {
					g.drawRoundRect(x2, y1, Math.abs(x1 - x2), Math.abs(y1 - y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
				} else if (x1 < x2 && y1 > y2) {
					g.drawRoundRect(x1, y2, Math.abs(x1 - x2), Math.abs(y1 - y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
				} else {
					g.drawRoundRect(x1, y1, Math.abs(x1 - x2), Math.abs(y1 - y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
				}

			} else if (mod == 2) {
				g.drawPolygon(ax, ay, 3);
			} else if (mod == 3) {
				for (int i = 0; i < line.size(); i++) {
					g.drawLine(line.get(i).x1, line.get(i).y1, line.get(i).x2, line.get(i).y2);
				}
			}

		}

		// 저장된 그림
		// 사각, 동그라미
		if (!rect.isEmpty()) {
			for (int i = 0; i < rect.size(); i++) {
				if (rect.get(i).mod == 0) {
					g.setColor(rect.get(i).c);
					g.drawRect(rect.get(i).x1, rect.get(i).y1, rect.get(i).width, rect.get(i).height);
				} else if (rect.get(i).mod == 1) {
					g.setColor(rect.get(i).c);
					g.drawRoundRect(rect.get(i).x1, rect.get(i).y1, rect.get(i).width, rect.get(i).height,
							rect.get(i).width, rect.get(i).height);
				}
			}

		}
		// 삼각형
		if (!tri.isEmpty()) {
			for (int i = 0; i < tri.size(); i++) {
				g.setColor(tri.get(i).c);
				g.drawPolygon(tri.get(i).x, tri.get(i).y, tri.get(i).len);
			}
		}
		if (!line.isEmpty()) {
			for (int i = 0; i < line.size(); i++) {
				g.setColor(line.get(i).c);
				g.drawLine(line.get(i).x1, line.get(i).y1, line.get(i).x2, line.get(i).y2);
			}
		}
		repaint();
	}
}

class frame extends JFrame implements MouseListener {

	panel p = new panel();

	public frame() {
		setLayout(null);
		setBounds(500, 150, 800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(p);
		p.close.addMouseListener(this);

		setVisible(true);
		revalidate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if ((JButton) e.getSource() == p.close) {
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
		frame f = new frame();
	}
}
