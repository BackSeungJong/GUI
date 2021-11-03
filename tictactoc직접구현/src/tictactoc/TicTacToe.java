package tictactoc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class HeadTitle extends JLabel {
	public HeadTitle() {
		setBounds((MyFrame.frameW - 300) / 2, 30, 300, 100);
		setText("Tic Tac Toe");
		setFont(new Font("", Font.BOLD, 50));
		setHorizontalAlignment(CENTER);
		setVisible(true);
	}
}

class MyPanel extends JPanel implements ActionListener {

	JButton map[] = new JButton[9];
	public int turn = 1;

	public MyPanel() {
		// 패널 세팅

		setLayout(null);
		System.out.println("패널생성");
//		System.out.println(MyFrame.frameW);
//		System.out.println(MyFrame.frameH);
		setBounds((MyFrame.frameW - 450) / 2, (MyFrame.frameH - 450) / 2, 450, 450);
		setBackground(Color.orange);

		// 버튼 세팅
		btnset();
	}

	public void btnset() {
		int x = 25;
		int y = 25;
		for (int i = 0; i < map.length; i++) {
			map[i] = new JButton();

			map[i].setBackground(Color.white);
			map[i].setBounds(x, y, 100, 100);
			map[i].setVisible(true);
			map[i].addActionListener(this);
			add(map[i]);

			x += 150;
			if (i % 3 == 2) {
				x = 25;
				y += 150;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource(); // 이벤트가 발생한 객체를 가져옴
		if (btn.getBackground().equals(Color.white)) {
			if (turn == 1) {
				btn.setBackground(Color.blue);
			} else if (turn == -1) {
				btn.setBackground(Color.red);
			}
			if (!check()) {
				if (turn == 1) {
					JOptionPane.showMessageDialog(this, "PLAYER 1 승리");
				} else if (turn == -1) {
					JOptionPane.showMessageDialog(this, "PLAYER 2 승리");
				}

			}
		}
	}

	public boolean check() {
		Color c = null;
		if (turn == 1) {
			c = Color.blue;
		} else if (turn == -1) {
			c = Color.red;
		}

		// 가로
		for (int i = 0; i < map.length; i += 3) {
			int count = 0;
			for (int j = 0; j < 3; j++) {
				if (map[i + j].getBackground().equals(c)) {
					count++;
				}
			}
			if (count == 3) {
				System.out.println(c.toString() + "승리");
				return false;
			}
		}

		// 세로
		for (int i = 0; i < 3; i++) {
			int count = 0;
			for (int j = 0; j < map.length; j += 3) {
				if (map[i + j].getBackground().equals(c)) {
					count++;
				}
			}
			if (count == 3) {
				System.out.println(c.toString() + "승리");
				return false;
			}
		}

		// /

		int count = 0;
		for (int i = 0; i < map.length; i += 4) {
			if (map[i].getBackground().equals(c)) {
				count++;
			}
		}
		if (count == 3) {
			System.out.println(c.toString() + "승리");
			return false;
		}

		// \
		count = 0;
		for (int i = 2; i < 7; i += 2) {
			if (map[i].getBackground().equals(c)) {
				count++;
			}
		}
		if (count == 3) {
			System.out.println(c.toString() + "승리");
			return false;
		}

		turn = turn == 1 ? -1 : 1;
		return true;
	}
}

class MyFrame extends JFrame {

	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenW = dm.width;
	private int screenH = dm.height;
	public static int frameW = 900;
	public static int frameH = 700;

	JButton start = new JButton();
	MyPanel mp = new MyPanel();

	public MyFrame(String name) {

		super(name);
		// 프레임 전체 몸통
		setLayout(null);
		setBounds((screenW - frameW) / 2, (screenH - frameH) / 2, frameW, frameH);
		// System.out.println(frameW);//960
		// System.out.println(frameH);//720
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		// 제목
		add(new HeadTitle());
		add(mp);
		// 게임시작버튼
		start.setText("게임시작");
		start.setFont(new Font("", Font.BOLD, 15));
		start.setBounds((frameW - 100) / 2, 600, 100, 50);
		start.setVisible(true);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				remove(mp);
				mp = new MyPanel();
				add(mp);
				System.out.println("게임다시시작");
			}
		});
		add(start);

	}
}

public class TicTacToe {
	public static void main(String[] args) {
		MyFrame game = new MyFrame("Green TTT");
	}
}
