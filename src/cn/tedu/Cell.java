package cn.tedu;

import java.awt.image.BufferedImage;

/**
 * 单元格类
 * 
 * @author 孙辉
 *
 */
public class Cell {
	/** 成员变量 */
	private int row;//纵坐标
	private int col;//横坐标
	private BufferedImage image;

	/** 构造方法 */
	public Cell(int row, int col,BufferedImage image) {
		this.row = row;
		this.col = col;
		this.image=image;
	}

	/** 自定义方法 */
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
