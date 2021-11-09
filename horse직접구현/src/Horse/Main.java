package Horse;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

class Panel extends MyUtil {

	private boolean isRun = false;
	private ImageIcon[] h = new ImageIcon[5];

	private int x[] = new int[5];
	private int y[] = new int[5];
	private int len[] = new int[5];
	private boolean goal[] = new boolean[5];
	private int Rank = 1;

	private JButton start = new JButton();
	private JButton reset = new JButton();

	private JLabel[] label = new JLabel[5];

	public Panel() {
		// TODO Auto-generated constructor stub
		setLayout(null);
		setBounds(0, 0, 1000, 900);
		setBackground(Color.white);

		horse();
		btn();
		label();
	}

	private void horse() {
		String str = "horse";
		int yy = 50;
		for (int i = 0; i < h.length; i++) {
			goal[i] = false;
			x[i] = 50;
			y[i] = yy;
			yy += 125;
			String filename = "images/" + str + (i + 1) + ".png";
			h[i] = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		}
	}

	private void btn() {
		// TODO Auto-generated method stub
		start.setBounds(300, 700, 130, 70);
		start.setText("start");
		start.setFont(new Font("", Font.BOLD, 30));
		start.addMouseListener(this);
		add(start);

		reset.setBounds(500, 700, 130, 70);
		reset.setText("reset");
		reset.setFont(new Font("", Font.BOLD, 30));
		reset.addMouseListener(this);
		add(reset);
	}

	private void label() {
		// TODO Auto-generated method stub
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel();
			label[i].setBounds(870, y[i], 100, 100);
			label[i].setFont(new Font("", Font.BOLD, 50));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isRun) {
			boolean check = true;
			for (int i = 0; i < len.length; i++) {
				if (!goal[i] && len[i] != 0) {
					check = false;
				}
			}
			if (check) {
				Game();
			}

			for (int i = 0; i < len.length; i++) {
				if (!goal[i] && len[i] > 0) {
					x[i]++;
					len[i]--;
				}
				if (!goal[i] && x[i] == 750) {
					goal[i] = true;
					label[i].setText(Rank + "");
					add(label[i]);
					Rank++;
				}
				g.drawImage(h[i].getImage(), x[i], y[i], null);
			}
			g.drawLine(850, 50, 850, 650);
		} else {
			for (int i = 0; i < len.length; i++) {
				g.drawImage(h[i].getImage(), x[i], y[i], null);
				g.drawLine(850, 50, 850, 650);
			}
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == start) {
			System.out.println("start");
			isRun = true;
		} else if (e.getSource() == reset) {
			System.out.println("reset");

			Rank = 1;
			for (int i = 0; i < h.length; i++) {
				goal[i] = false;
				x[i] = 50;
				isRun = false;
				remove(label[i]);
			}
		}
	}

	Random ran = new Random();

	private void Game() {
		ArrayList<Integer> check = new ArrayList<>();

		for (int i = 0; i < len.length; i++) {
			len[i] = ran.nextInt(100) + 1;
		}

		// 동시에 들어갔는지 확인
		for (int i = 0; i < len.length; i++) {
			if (!goal[i] && x[i] + 100 + len[i] >= 850) {
				check.add(i);
			}
		}
		if (check.size() > 1) {
			System.out.println();
			while (check.size() > 1) {
				int idx = ran.nextInt(check.size());
				len[idx] = 0;
				check.remove(idx);
			}
		}
	}
}

class Frame extends JFrame {
	public Frame() {
		// TODO Auto-generated constructor stub
		super("경마게임");
		setLayout(null);
		setBounds(300, 50, 1000, 900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new Panel());
		setVisible(true);
		revalidate();
	}
}

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame f = new Frame();
	}
}
