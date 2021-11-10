package horse풀이;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;

public class RacingPanel extends MyUtil implements Runnable {

	private int ms;
	private boolean isRun;

	private JLabel timer = new JLabel();
	private JButton reset = new JButton("start");

	private final int SIZE = 5;
	private Horse[] horse = new Horse[SIZE];

	private Random ran = new Random();
	private int rank;

	public RacingPanel() {
		setLayout(null);
		setBounds(0, 0, Race.WIDTH, Race.HEIGHT);

		settimer();
		setbtn();
		setgame();
	}

	private void setgame() {

		this.ms = 0;
		this.rank = 1;
		this.timer.setText("ready");
		int x = 30;
		int y = 120;

		for (int i = 0; i < SIZE; i++) {
			this.horse[i] = new Horse(i + 1, x, y, 120, 90, String.format("images/horse%d.png", i + 1));
			y += 100;
		}
	}

	private void setbtn() {
		this.reset.setBounds(30, 50, 100, 50);
		this.reset.addActionListener(this);
		add(this.reset);
	}

	private void settimer() {
		timer.setBounds(135, 50, 100, 50);
		add(this.timer);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JButton) {
			JButton target = (JButton) e.getSource();

			if (target == this.reset) {
				this.isRun = !this.isRun;
				this.reset.setText(this.isRun ? "reset" : "start");

				if (!this.isRun) {
					setgame();
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// hores
		g.drawLine(30, 120, Race.WIDTH - 30, 120);
		for (int i = 0; i < SIZE; i++) {
			Horse h = this.horse[i];
			g.drawImage(h.getImage().getImage(), h.getX(), h.getY(), null);
			g.drawLine(30, h.getY() + h.getH(), Race.WIDTH - 30, h.getY() + h.getH());

			//
			//
			// 수정
			if (h.getState() == h.GOAL) {
				g.setFont(new Font("", Font.PLAIN, 10));
				g.drawString(h.getRecord(), Race.WIDTH - 180, h.getY() + h.getH() / 2);
				g.setFont(new Font("", Font.BOLD, 20));
				g.drawString(h.getRank() + "등", Race.WIDTH - 180 - 30, h.getY() + h.getH() / 2);
			}
		}
		try {
			Thread.sleep(50);
		} catch (Exception e) {
			// TODO: handle exception
		}
		update();

		repaint();
	}

	private void update() {
		boolean goal = false;

		if (this.isRun) {
			for (int i = 0; i < SIZE; i++) {

				Horse h = this.horse[i];
				int jump = ran.nextInt(10) * 3;
				int tmpX = h.getX() + jump;

				if (h.getState() == h.RUN) {
					// goal
					if (tmpX >= Race.WIDTH - 30 - h.getW() && !goal) {
						h.setState(h.GOAL);
						h.setRecord(String.format("%3d.%3d", this.ms / 1000, this.ms % 1000));

						h.setRank(this.rank);
						this.rank++;
						goal = !goal;

					} else if (tmpX >= Race.WIDTH - 30 - h.getW()) {
						i--;
						// 되돌아가는 말이 발생할 경우 -> ms 홀딩하는 변수를 활용
						break;
					}
					h.setX(tmpX);
				}

			}
		}
	}

	@Override
	public void run() {
		while (true) {
			if (isRun) {
				this.ms++;
				this.timer.setText(String.format("%3d.%3d", this.ms / 1000, this.ms % 1000));
			}

			try {
				Thread.sleep(1);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
