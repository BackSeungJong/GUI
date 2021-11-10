package horse2직접구현;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

class Panel extends MyUtil {

	private Horse horse[] = new Horse[5];

	public Panel() {
		// TODO Auto-generated constructor stub
		setLayout(null);
		setBounds(0, 0, 800, 800);

		sethorse();
		setImage();
	}

	private void sethorse() {
		// TODO Auto-generated method stub
		String filename = "";
		for (int i = 0; i < horse.length; i++) {
			horse[i] = new Horse();
		}
	}

	private void setImage() {
		// TODO Auto-generated method stub

	}
}

class Frame extends JFrame {
	public Frame() {
		// TODO Auto-generated constructor stub
		super("경마게임2");
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 100, 800, 800);

		add(new Panel());

		setVisible(true);
		revalidate();

	}
}

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
