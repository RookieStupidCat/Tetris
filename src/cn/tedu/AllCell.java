package cn.tedu;
/**
 * 为整个画布建立所有不可旋转cell对象
 * @author 孙辉
 *
 */

/**所有单元格类*/
public class AllCell{
	protected Cell[][] cells;//行，列
	public static int grade=0;
	public AllCell() {
		cells=new Cell[21][11];
		for(int i=0;i<21;i++){
			for(int j=0;j<10;j++){
				cells[i][j]=new Cell(-100,-100, Tetris.T);
			}
		}
	}
	
	public void PutCell(Tetromino tetromino){
		for (int i = 0; i < tetromino.cells.length; i++) {
			int row = tetromino.cells[i].getRow();
			int col = tetromino.cells[i].getCol();
			cells[row][col].setImage(tetromino.cells[i].getImage());
			cells[row][col].setCol(col);
			cells[row][col].setRow(row);
		}
	}
	
	public void clear() {
		for(int i=0;i<21;i++){
			for(int j=0;j<10;j++){
				if(cells[i][j].getRow()<0 || cells[i][j].getCol()<0){
					break;
				}else if(j==9){
					clearRow(i);
					grade+=100;
				}
			}
		}
	}
	private void clearRow(int n) {
		//清除第i行的单元格
		for(int i=n;i>0;i--){
			for(int j=0;j<10;j++){
				if(cells[i-1][j].getRow()<0 || cells[i-1][j].getCol()<0){
					cells[i][j].setRow(-100);
					cells[i][j].setCol(-100);
				}else
					cells[i][j].setImage(cells[i-1][j].getImage());
			}
		}
	}
	
}



