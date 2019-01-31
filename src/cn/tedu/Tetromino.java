package cn.tedu;

/**
 * ͼ��ͼ������
 * 
 * @author ���
 *
 */
public class Tetromino {
	/** ��Ա���� */
	protected Cell[] cells;

	/** ���췽�� */
	public Tetromino() {
		cells = new Cell[4];
	}

	/** �Զ��巽�� */
	// ����
	public void dropAction() {
		for (int i = 0; i < this.cells.length; i++) {
			this.cells[i].drop();
		}
	}
	//���ƣ��������䣩
	public void moveUp() {
		for (int i = 0; i < this.cells.length; i++) {
			this.cells[i].up();
		}
	}

	// ����
	public void moveLeft() {
		for (int i = 0; i < this.cells.length; i++) {
			this.cells[i].moveLeft();
		}
	}

	// ����
	public void moveRight() {
		for (int i = 0; i < this.cells.length; i++) {
			this.cells[i].moveRight();
		}
	}

	// ��ת
	public void rotateRight() {
		for(int i=1;i<cells.length;i++){
			int step_x=cells[i].getRow()-cells[0].getRow();
			int step_y=cells[i].getCol()-cells[0].getCol();
			cells[i].setCol(cells[0].getCol()-step_x);//ͼ����ת���Ĺ���
			cells[i].setRow(cells[0].getRow()+step_y);
		}
	}
	// ����ת
	public void rotateLeft() {
		for(int i=1;i<cells.length;i++){
			int step_x=cells[i].getRow()-cells[0].getRow();
			int step_y=cells[i].getCol()-cells[0].getCol();
			cells[i].setCol(cells[0].getCol()+step_x);
			cells[i].setRow(cells[0].getRow()-step_y);
		}
	}

	/** �����������ģ�� */
	public static Tetromino randomOne() {
		// ���ֵ
		int type = (int) (Math.random() * 7);
		switch (type) {
		case 0:
			return new T();
		case 1:
			return new J();
		case 2:
			return new O();
		case 3:
			return new S();
		case 4:
			return new Z();
		case 5:
			return new I();
		case 6:
			return new L();
		}
		return null;
	}
}

class T extends Tetromino {
	/** ���췽�� */
	public T() {
		cells[0] = new Cell(0, 4, Tetris.T);
		cells[1] = new Cell(0, 3, Tetris.T);
		cells[2] = new Cell(0, 5, Tetris.T);
		cells[3] = new Cell(1, 4, Tetris.T);

	}
}

class J extends Tetromino {
	/** ���췽�� */
	public J() {
		cells[0] = new Cell(0, 4, Tetris.J);
		cells[1] = new Cell(0, 3, Tetris.J);
		cells[2] = new Cell(1, 4, Tetris.J);
		cells[3] = new Cell(2, 4, Tetris.J);

	}
}

class O extends Tetromino {
	/** ���췽�� */
	public O() {
		cells[0] = new Cell(0, 4, Tetris.O);
		cells[1] = new Cell(0, 5, Tetris.O);
		cells[2] = new Cell(1, 4, Tetris.O);
		cells[3] = new Cell(1, 5, Tetris.O);

	}
}

class S extends Tetromino {
	/** ���췽�� */
	public S() {
		cells[0] = new Cell(1, 4, Tetris.S);
		cells[1] = new Cell(0, 3, Tetris.S);
		cells[2] = new Cell(0, 4, Tetris.S);
		cells[3] = new Cell(1, 5, Tetris.S);

	}
}

class Z extends Tetromino {
	/** ���췽�� */
	public Z() {
		cells[0] = new Cell(1, 4, Tetris.Z);
		cells[1] = new Cell(0, 3, Tetris.Z);
		cells[2] = new Cell(0, 4, Tetris.Z);
		cells[3] = new Cell(1, 5, Tetris.Z);

	}
}

class I extends Tetromino {
	/** ���췽�� */
	public I() {
		cells[0] = new Cell(0, 4, Tetris.I);
		cells[1] = new Cell(0, 3, Tetris.I);
		cells[2] = new Cell(0, 5, Tetris.I);
		cells[3] = new Cell(0, 6, Tetris.I);

	}
}

class L extends Tetromino {
	/** ���췽�� */
	public L() {
		cells[0] = new Cell(0, 4, Tetris.L);
		cells[1] = new Cell(0, 3, Tetris.L);
		cells[2] = new Cell(0, 5, Tetris.L);
		cells[3] = new Cell(1, 3, Tetris.L);

	}
}