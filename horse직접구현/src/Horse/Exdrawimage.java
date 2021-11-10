//package Horse;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//
//import java.awt.*;
//
//class Panel extends MyUtil {
//
//	private JLabel label = new JLabel();
//
//	private Image im = new ImageIcon("images/horse1.png").getImage().getScaledInstance(200, 500, Image.SCALE_SMOOTH);
//	private JLabel image = new JLabel(new ImageIcon(im));
//	private JLabel image2 = new JLabel(new ImageIcon("images/horse2.png"));
//	private ImageIcon icon = new ImageIcon(im);
//	private int x = 100;
//
//	public Panel() {
//		// TODO Auto-generated constructor stub
//		setLayout(null);
//		setBounds(0, 0, 1000, 1000);
//		setBackground(Color.white);
//
//		label.setBounds(0, 0, 500, 500);
//		label.setText("test");
//		add(label);
//
////		image.setBounds(500, 500, 500, 500);
////		//image.setIcon(new ImageIcon("image/horse4.jpg"));
////		image.setVisible(true);
////		add(image, 0);
//
//	}
//
//	@Override
//	protected void paintComponent(Graphics g) {
//		// TODO Auto-generated method stub
//		super.paintComponent(g);
//
//		g.drawImage(icon.getImage(), 0, 0, null);
//		repaint();
//	}
//}
//
//class Frame extends JFrame {
//	public Frame() {
//		super("경마게임");
//		setLayout(null);
//		setBounds(300, 50, 1000, 1000);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//		add(new Panel());
//
//		setVisible(true);
//		revalidate();
//	}
//}
//
//public class Exdrawimage {
//	public static void main(String[] args) {
//		Frame f = new Frame();
//	}
//}
