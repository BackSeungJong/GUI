package otof;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Content extends JPanel implements ActionListener {

	private JButton front[][];
	private String back[][];
	private int check[][];
	private int CNT = 1;

	private JButton resetBtn;

	JLabel w1;
	JLabel w2;
	JLabel w3;
	JLabel c1;
	JLabel c2;

	int mm, ss, ms, t = 0;

	public Content() {
		setLayout(null);
		setBounds(0, 0, 1000, 800);
		// setBackground(Color.ORANGE);

		setTimer();

		setHead();
		setStartBtn();
		setMap();
		shuffle();
	}

	private void setTimer() {
		w1 = new JLabel("00");
		w1.setBounds(0, 0, 70, 50);
		w1.setFont(new Font("", Font.BOLD, 20));
		c1 = new JLabel(":");
		c1.setBounds(25, 0, 70, 50);
		c1.setFont(new Font("", Font.BOLD, 20));
		w2 = new JLabel("00");
		w2.setBounds(35, 0, 70, 50);
		w2.setFont(new Font("", Font.BOLD, 20));
		c2 = new JLabel(":");
		c2.setBounds(60, 0, 70, 50);
		c2.setFont(new Font("", Font.BOLD, 20));
		w3 = new JLabel("00");
		w3.setBounds(70, 0, 70, 50);
		w3.setFont(new Font("", Font.BOLD, 20));

		add(w1);
		add(c1);
		add(w2);
		add(c2);
		add(w3);
	}

	private void setMap() {
		front = new JButton[5][5];
		back = new String[5][5];
		check = new int[5][5];

		int count = 1;

		int x = 1000 / 2 - 500 / 2;
		int y = 800 / 2 - 550 / 2;
		for (int i = 0; i < front.length; i++) {
			for (int j = 0; j < front[i].length; j++) {
				front[i][j] = new JButton();
				front[i][j].setBounds(x, y, 100, 100);
				front[i][j].setBackground(new Color(209, 232, 200));
				front[i][j].setText(count + "");
				front[i][j].setFont(new Font("", Font.BOLD, 30));
				count++;
				front[i][j].addActionListener(this);

				// add(front[i][j]);
				x += 100 + 1;
			}
			x = 1000 / 2 - 500 / 2;
			y += 100 + 1;
		}

		// back 세팅
		for (int i = 0; i < back.length; i++) {
			for (int j = 0; j < back[i].length; j++) {
				back[i][j] = count + "";
				count++;
			}
		}
	}

	private void shuffle() {
		System.out.println("셔플");
		Random ran = new Random();

		for (int i = 0; i < 100; i++) {
			int x1 = ran.nextInt(5);
			int y1 = ran.nextInt(5);
			int x2 = ran.nextInt(5);
			int y2 = ran.nextInt(5);

			String tmp = front[x1][y1].getText();
			front[x1][y1].setText(front[x2][y2].getText());
			front[x2][y2].setText(tmp);

		}
		for (int i = 0; i < 100; i++) {
			int x1 = ran.nextInt(5);
			int y1 = ran.nextInt(5);
			int x2 = ran.nextInt(5);
			int y2 = ran.nextInt(5);

			String tmp = back[x1][y1];
			back[x1][y1] = back[x2][y2];
			back[x2][y2] = tmp;

		}
		for (int i = 0; i < front.length; i++) {
			for (int j = 0; j < front[i].length; j++) {
				add(front[i][j]);
			}
		}
	}

	private void resetMap() {
		pdisplay = null;
		w1.setText("00");
		w2.setText("00");
		w3.setText("00");
		t = 0;
		// 버튼다 지우자
		for (int i = 0; i < front.length; i++) {
			for (int j = 0; j < front[i].length; j++) {
				remove(front[i][j]);
			}
		}
		setMap();
		shuffle();
		CNT = 1;
	}

	Thread pdisplay;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();

		if (btn.equals(resetBtn)) {
			resetMap();
		} else {
			// 시간스레드 출발
			pdisplay = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mm = Integer.parseInt(w1.getText());
					ss = Integer.parseInt(w2.getText());
					ms = Integer.parseInt(w3.getText());

					while (pdisplay == Thread.currentThread()) {
						mm = t % (1000 * 60) / 100 / 60;
						ss = t % (1000 * 60) / 100 % 60;
						ms = t % 100;

						try {
							Thread.sleep(10);

							w1.setText(String.format("%02d", mm));
							w2.setText(String.format("%02d", ss));
							w3.setText(String.format("%02d", ms));
							t++;
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
				}
			});
			pdisplay.start();

			//
			for (int i = 0; i < front.length; i++) {
				for (int j = 0; j < front[i].length; j++) {
					if (check[i][j] == 0 && btn.equals(front[i][j]) && btn.getText().equals(CNT + "")) {
						System.out.println(btn.getText());
						btn.setText(back[i][j]);
						CNT++;
						check[i][j] = 1;
					} else if (check[i][j] == 1 && btn.equals(front[i][j]) && btn.getText().equals(CNT + "")) {
						System.out.println(btn.getText());
						btn.setText("");
						CNT++;
						check[i][j] = -1;
					}
				}
			}
			if (CNT == 51) {
				String result = w1.getText() + " : " + w2.getText() + " : " + w3.getText();
				new AlterFrame(result);
				resetMap();
			}
		}
	}

	private void setStartBtn() {
		resetBtn = new JButton("RESET");

		resetBtn.setBounds(450, 650, 100, 50);
		resetBtn.setFont(new Font("", Font.ITALIC, 20));
		resetBtn.addActionListener(this);
		add(resetBtn);
	}

	private void setHead() {
		// TODO Auto-generated method stub
		JLabel head = new JLabel("1 to 50");
		head.setBounds(0, 0, 1000, 150);
		head.setFont(new Font("", Font.ITALIC, 50));
		head.setHorizontalAlignment(JLabel.CENTER);

		add(head);
	}
}

class OtoF extends JFrame {
	public OtoF() {
		super("1 TO 50");
		setBounds(500, 200, 1000, 800);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new Content());

		setVisible(true);
		revalidate();
	}
}

public class Main {
	public static void main(String[] args) {
		OtoF o = new OtoF();
	}
}

class AlterFrame extends JFrame {
	JLabel text = new JLabel();

	public AlterFrame(String name) {
		setLayout(null);
		setBounds(800, 400, 250, 150);
		setVisible(true);

		text.setBounds(0, 0, 250, 75);
		text.setText(name);
		text.setFont(new Font("", Font.BOLD, 30));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setVerticalAlignment(JLabel.BOTTOM);
		text.setVisible(true);

		add(text);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
