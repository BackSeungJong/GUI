package horse풀이;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Race extends JFrame {

	public static Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int W = dm.width;
	public static final int H = dm.height;

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;

	private RacingPanel rp = new RacingPanel();


	public Race() {
		super("Racing Game");
		setLayout(null);
		setBounds((W - WIDTH) / 2, (H - HEIGHT) / 2, WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(rp);

		setVisible(true);
		revalidate();
		
		rp.run();

	}

}
