package com.zj.study.rtext.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class ButtonMain {

	public static void main(String[] args) {
		JFrame jf = new JFrame("测试窗口");
		jf.setSize(200, 200);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		// 创建一个按钮
		final JButton btn = new JButton("测试按钮");

		// 添加按钮的点击事件监听器
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 获取到的事件源就是按钮本身
				// JButton btn = (JButton) e.getSource();
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("按钮被点击");
			}
		});

		panel.add(btn);

		jf.setContentPane(panel);
		jf.setVisible(true);
	}
}