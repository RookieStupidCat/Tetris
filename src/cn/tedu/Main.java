package cn.tedu;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		// 1.�½�����
		JFrame frame = new JFrame("����˹����");
		/**��������*/
		Tetris t=new Tetris();
		frame.add(t);
		// 2.���ڴ�С
		frame.setSize(527, 550);
		// 3.������ʾ
		frame.setLocationRelativeTo(null);
		//8.���ô�����ʧ
		frame.setUndecorated(true);
		//9.���ô���Ϊ���ϲ�
		frame.setAlwaysOnTop(true);
		// 4.�ر�ģʽ
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 5.ʼ�����Ϸ�����
		frame.setAlwaysOnTop(true);
		// 6.������ʾ
		frame.setVisible(true);
		//7.����ҵ��ִ��
		t.action();
	}
}
