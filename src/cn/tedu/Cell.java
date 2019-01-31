package cn.tedu;

import java.awt.image.BufferedImage;

/**
 * ��Ԫ����
 * 
 * @author ���
 *
 */
public class Cell {
	/** ��Ա���� */
	private int row;//������
	private int col;//������
	private BufferedImage image;

	/** ���췽�� */
	public Cell(int row, int col,BufferedImage image) {
		this.row = row;
		this.col = col;
		this.image=image;
	}

	/** �Զ��巽�� */
	public void drop() {
		this.row++;
	}
	public void up() {
		this.row--;
	}

	public void moveLeft() {
		this.col--;
	}

	public void moveRight() {
		this.col++;
	}

	public String getCellInfo() {
		return "(" + this.row + "," + this.col + ")";
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
}
