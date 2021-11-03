//package gui;
//
//import java.awt.Event;
//import java.awt.Graphics;
//import java.awt.event.MouseEvent;
//
//import javax.swing.JFrame;
//
//class Rect {
//
//	private int x, y, width, height;
//
//	public Rect(int x, int y, int width, int height) {
//		this.x = x;
//		this.y = y;
//		this.width = width;
//		this.height = height;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public int getWidth() {
//		return width;
//	}
//
//	public int getHeight() {
//		return height;
//	}
//}
//
//class ExPanel extends MyUtil {
//
//	private Rect rect = new Rect(100, 100, 100, 100);
//	private int x;
//	private int y;
//	private int xx;
//	private int yy;
//
//	public ExPanel() {
//		setBounds(0, 0, 500, 500);
//		setFocusable(true);
//		addMouseListener(this);
//		addMouseMotionListener(this);
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//
//		xx = e.getX();
//		yy = e.getY();
//		x = rect.getX() - xx;
//		y = rect.getY() - yy;
//		System.out.println("[" + x + "/" + y + "]");
//	}
//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//
//		if (e.getX() >= rect.getX() && e.getX() < rect.getX() + 100 && e.getY() >= rect.getY()
//				&& e.getY() < rect.getY() + 100) {
//			System.out.println((e.getX() - xx) + "/" + (e.getY() - yy));
//			rect.setX(e.getX() + x);
//			rect.setY(e.getY() + y);
//		}
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//	}
//
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
//		repaint();
//	}
//}
//
//class ExFrame extends JFrame {
//	public ExFrame() {
//		setLayout(null);
//		setBounds(100, 100, 500, 500);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//		add(new ExPanel());
//		setVisible(true);
//		revalidate();
//	}
//}
//
//public class Ex {
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		ExFrame e = new ExFrame();
//	}
//}
