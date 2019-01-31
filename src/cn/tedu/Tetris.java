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
 * 画布类
 * 
 * @author 孙辉
 *
 */
@SuppressWarnings("serial")
public class Tetris extends JPanel {
	/** 第一部分：变量声明区域 */
	// 背景图片
	public static BufferedImage background;
	// 方格图片
	public static BufferedImage T;
	public static BufferedImage J;
	public static BufferedImage O;
	public static BufferedImage S;
	public static BufferedImage Z;
	public static BufferedImage I;
	public static BufferedImage L;
	// 正在下落的方块
	Tetromino tetromino[]=new Tetromino[200];
	int cellNum=1;
	Tetromino nextOne;
	//下落精度
	int step;
	//不可移动cell
	AllCell allCell;

	// 1.4计时器
	Timer time;
	/** 第二部分：静态代码块初始化区域 */
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

	/** 第三部分：画笔方法 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 3.1背景图片绘制
		g.drawImage(background, 0, 0, null);
		// g.drawImage(background, 0, 0, 507, 505, null);
		// 画笔偏移
		g.translate(15, 15);
		// 3.2每一个方块绘制
		paintAllOne(g);
		paintTetromino(g);
		//3.3下一个方块绘制
		paintNextOne(g);
		//3.4得分
		g.setFont(new Font("name",0,30));
		g.drawString(""+AllCell.grade, 14*26, 11*25);
	}

	// 3.2每一个方块绘制
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
		//对正在下落的方块精细绘制
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
	//3.3下一个方块绘制
	private void paintNextOne(Graphics g){
		for (int i = 0; i < nextOne.cells.length; i++) {
			Cell cell = nextOne.cells[i];
			g.drawImage(cell.getImage(), (cell.getCol()+10) * 26, cell.getRow() * 26, null);
		}
	}
	/** 第四部分：业务逻辑执行区域 */
	public void action() {
		// 4.1初始化下落方块
		tetromino[0] = Tetromino.randomOne();
		nextOne=Tetromino.randomOne();
		allCell=new AllCell();
		// 4.5计时器初始化
		time = new Timer();
		time.schedule(new TimerTask() {
			public void run() {
				// 4.4下落 软下落
				softDropAction();
				// 4.5重绘
				repaint();
			}
		}, 0, 50);//（延迟时间，时间间隔）
		// 4.5监听键盘监听事件
		this.addKeyListener(new KeyAdapter() {
			// 内部方法：键盘按下
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				// 判断按键是否与系统按键值对应
				int type = e.getKeyCode();
				// 选择语句
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
				case KeyEvent.VK_P://未实现成功
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
		// 4.6键盘监听事件焦点设置
		this.setFocusable(true);
		this.requestFocus();
	}

	// 4.4下落方法
	private void softDropAction() {
		// 调用正在下落的方块的下落方法
		tetromino[cellNum-1].dropAction();
		if (outOfBounds()) {//接触地面，下一块
			tetromino[cellNum-1].moveUp();
			//封顶，游戏结束
			for (int i = 0; i < tetromino[cellNum-1].cells.length; i++) {
				if(tetromino[cellNum-1].cells[i].getRow()<=1){
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
			//载入静态cell对象
			allCell.PutCell(tetromino[cellNum-1]);
			//是否清除行
			allCell.clear();
			//开始下一个方块
			cellNum++;
			tetromino[cellNum-1]=nextOne;
			nextOne=Tetromino.randomOne();
		}
	}

	// 4.7左移
	private void LeftAction() {
		tetromino[cellNum-1].moveLeft();
		// 判断是否出界
		if (outOfBounds()) {
			tetromino[cellNum-1].moveRight();
		}
	}

	// 4.8右移
	private void RightAction() {
		tetromino[cellNum-1].moveRight();
		if (outOfBounds()) {
			tetromino[cellNum-1].moveLeft();
		}
	}
	//4.9旋转
	public void RotateRightAction(){
		tetromino[cellNum-1].rotateRight();
		if(outOfBounds())
			tetromino[cellNum-1].rotateLeft();
	}

	//快速下落
	public void DownAction(){
		int n=cellNum;
		do{
			softDropAction();
		}while(cellNum==n);
	}
	/** 出界 */
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
	/** 第五部分：键盘监听区域 */

}
