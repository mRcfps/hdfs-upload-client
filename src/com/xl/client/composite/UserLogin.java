package com.xl.client.composite;



import java.awt.Toolkit;

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class UserLogin {
	

	public static void main(String[] args) {
		Display dislay = Display.getDefault();
		Shell shell = new Shell(dislay);
		 //JFrame frame = new JFrame("登陆系统");	
		shell.setText("登陆窗口");
		
		shell.setSize(1024, 650);
		shell.setBounds(10, 10, 1024, 650);
		 //frame.setLayout(null);
		System.out.println("aa");
		shell.setBackgroundImage(SWTResourceManager.getImage(UserLogin.class, "/images/client_login.jpg"));
		//shell.setImage(SWTResourceManager.getImage(UserLogin.class, "/images/client_login.jpg"));
//		Button button = new Button(shell,SWT.CENTER);
//		button.setText("登陆");
//		button.pack();

		shell.pack();
		shell.open();
		while(!shell.isDisposed()){
			if(!dislay.readAndDispatch()){
				dislay.sleep();
			}
		}
		dislay.dispose();
	}
}
