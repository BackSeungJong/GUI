package omok;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Nemo {
	boolean click;
	int id;
	int x, y, width, heigth;

	public Nemo(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = height;
	}
}

class Board extends JPanel implements MouseListener {

	private Nemo map[][] = new Nemo[10][10];

	public Board() {
		setBounds(0, 0, 700, 700);
		setLayout(null);
		setBackground(new Color(244, 164, 66));

		addMouseListener(this);
		setMap();
	}

	private void setMap() {
		// 그릴 사각형에 대한 정보만 Nemo 배열을 활용해서 세팅
		int x = 700 / 2 - 50 * 10 / 2;
		int y = 700 / 2 - 50 * 10 / 2;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = new Nemo(x, y, 50, 50);
				x += 50;
			}
			x = 700 / 2 - 50 * 10 / 2;
			y += 50;
		}
	}

	// 모든 컨테이너가 가지고있는 그림 그리기
	@Override
	protected void paintComponent(Graphics g) {
		// map 그리기
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				Nemo nemo = map[i][j];
//				g.drawRect(nemo.x, nemo.y, nemo.width, nemo.heigth);
				g.drawRoundRect(nemo.x, nemo.y, nemo.width, nemo.heigth, 50, 50);

			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {// 클릭
		int x = e.getX();
		int y = e.getY();

	}

	@Override
	public void mousePressed(MouseEvent e) {// 누르고있는 상태
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {// 눌렀다가 뗐을때
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {// listener가 add된 범위 안으로 마우스가 들어오면
		// TODO Auto-generated method stub
		System.out.println("hi");

	}

	@Override
	public void mouseExited(MouseEvent e) {// listener가 add된 범위 밖으로 마우스가 나가는 순간
		// TODO Auto-generated method stub
		System.out.println("bye");

	}
}

class OmokGame extends JFrame {

	Board board = new Board();

	public OmokGame() {
		super("오목게임");
		setBounds(800, 200, 700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		revalidate();

		add(board);
	}

}

public class Guide {
	public static void main(String[] args) {
		OmokGame og = new OmokGame();

	}
}
