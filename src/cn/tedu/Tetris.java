package cn.tedu;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * ������
 * 
 * @author ���
 *
 */
@SuppressWarnings("serial")
public class Tetris extends JPanel {
	/** ��һ���֣������������� */
	// ����ͼƬ
	public static BufferedImage background;
	// ����ͼƬ
	public static BufferedImage T;
	public static BufferedImage J;
	public static BufferedImage O;
	public static BufferedImage S;
	public static BufferedImage Z;
	public static BufferedImage I;
	public static BufferedImage L;
	// ��������ķ���
	Tetromino tetromino[]=new Tetromino[200];
	int cellNum=1;
	Tetromino nextOne;
	//���侫��
	int step;
	//�����ƶ�cell
	AllCell allCell;

	// 1.4��ʱ��
	Timer time;
	/** �ڶ����֣���̬������ʼ������ */
	static {
		try {
			background = ImageIO.read(Tetris.class.getResource("TETRIS.png"));
			T = ImageIO.read(Tetris.class.getResource("T.png"));
			J = ImageIO.read(Tetris.class.getResource("J.png"));
			I = ImageIO.read(Tetris.class.getResource("I.png"));
			O = ImageIO.read(Tetris.class.getResource("O.png"));
			S = ImageIO.read(Tetris.class.getResource("S.png"));
			L = ImageIO.read(Tetris.class.getResource("L.png"));
			Z = ImageIO.read(Tetris.class.getResource("Z.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** �������֣����ʷ��� */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 3.1����ͼƬ����
		g.drawImage(background, 0, 0, null);
		// g.drawImage(background, 0, 0, 507, 505, null);
		// ����ƫ��
		g.translate(15, 15);
		// 3.2ÿһ���������
		paintAllOne(g);
		paintTetromino(g);
		//3.3��һ���������
		paintNextOne(g);
		//3.4�÷�
		g.setFont(new Font("name",0,30));
		g.drawString(""+AllCell.grade, 14*26, 11*25);
	}

	// 3.2ÿһ���������
	private void paintAllOne(Graphics g) {
		for(int i=0;i<21;i++){
			for(int j=0;j<10;j++){
				int row=allCell.cells[i][j].getRow();
				int col=allCell.cells[i][j].getRow();
				if(row>=0 && col>=0)
					g.drawImage(allCell.cells[i][j].getImage(), allCell.cells[i][j].getCol() * 26, allCell.cells[i][j].getRow() * 26, null);
			}
		}
	}
	private void paintTetromino(Graphics g) {
//		for(int num=0;num<cellNum-1;num++){
//			for (int i = 0; i < tetromino[num].cells.length; i++) {
//				Cell cell = tetromino[num].cells[i];
//				g.drawImage(cell.getImage(), cell.getCol() * 26, cell.getRow() * 26, null);
//			}
//		}
		//����������ķ��龫ϸ����
		for (int i = 0; i < tetromino[cellNum-1].cells.length; i++) {
			Cell cell = tetromino[cellNum-1].cells[i];
			g.drawImage(cell.getImage(), cell.getCol() * 26, cell.getRow() * 26-2*step, null);
			if(step!=0)
				cell.setRow(cell.getRow()-1);
		}
		if(step!=0){
			step--;
		}else{
			step=12;
		}
	}
	//3.3��һ���������
	private void paintNextOne(Graphics g){
		for (int i = 0; i < nextOne.cells.length; i++) {
			Cell cell = nextOne.cells[i];
			g.drawImage(cell.getImage(), (cell.getCol()+10) * 26, cell.getRow() * 26, null);
		}
	}
	/** ���Ĳ��֣�ҵ���߼�ִ������ */
	public void action() {
		// 4.1��ʼ�����䷽��
		tetromino[0] = Tetromino.randomOne();
		nextOne=Tetromino.randomOne();
		allCell=new AllCell();
		// 4.5��ʱ����ʼ��
		time = new Timer();
		time.schedule(new TimerTask() {
			public void run() {
				// 4.4���� ������
				softDropAction();
				// 4.5�ػ�
				repaint();
			}
		}, 0, 50);//���ӳ�ʱ�䣬ʱ������
		// 4.5�������̼����¼�
		this.addKeyListener(new KeyAdapter() {
			// �ڲ����������̰���
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				// �жϰ����Ƿ���ϵͳ����ֵ��Ӧ
				int type = e.getKeyCode();
				// ѡ�����
				switch (type) {
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					LeftAction();
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					RightAction();
					break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					RotateRightAction();
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					DownAction();
					break;
				case KeyEvent.VK_Q:
					System.exit(0);
					break;
				case KeyEvent.VK_P://δʵ�ֳɹ�
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
		});
		// 4.6���̼����¼���������
		this.setFocusable(true);
		this.requestFocus();
	}

	// 4.4���䷽��
	private void softDropAction() {
		// ������������ķ�������䷽��
		tetromino[cellNum-1].dropAction();
		if (outOfBounds()) {//�Ӵ����棬��һ��
			tetromino[cellNum-1].moveUp();
			//�ⶥ����Ϸ����
			for (int i = 0; i < tetromino[cellNum-1].cells.length; i++) {
				if(tetromino[cellNum-1].cells[i].getRow()<=1){
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
			//���뾲̬cell����
			allCell.PutCell(tetromino[cellNum-1]);
			//�Ƿ������
			allCell.clear();
			//��ʼ��һ������
			cellNum++;
			tetromino[cellNum-1]=nextOne;
			nextOne=Tetromino.randomOne();
		}
	}

	// 4.7����
	private void LeftAction() {
		tetromino[cellNum-1].moveLeft();
		// �ж��Ƿ����
		if (outOfBounds()) {
			tetromino[cellNum-1].moveRight();
		}
	}

	// 4.8����
	private void RightAction() {
		tetromino[cellNum-1].moveRight();
		if (outOfBounds()) {
			tetromino[cellNum-1].moveLeft();
		}
	}
	//4.9��ת
	public void RotateRightAction(){
		tetromino[cellNum-1].rotateRight();
		if(outOfBounds())
			tetromino[cellNum-1].rotateLeft();
	}

	//��������
	public void DownAction(){
		int n=cellNum;
		do{
			softDropAction();
		}while(cellNum==n);
	}
	/** ���� */
	private boolean outOfBounds() {
		for (int i = 0; i < tetromino[cellNum-1].cells.length; i++) {
			int row = tetromino[cellNum-1].cells[i].getRow();
			int col = tetromino[cellNum-1].cells[i].getCol();
			if(row<0 || row > 19 || col < 0 || col > 9 )
				return true;
			if(allCell.cells[row][col].getRow()>=0 && allCell.cells[row][col].getCol()>=0)
				return true;
		}
		return false;
	}
	/** ���岿�֣����̼������� */

}
