package com.xl.client.shell;

import java.security.MessageDigest;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xl.client.composite.SwitchBarsComposite;
import com.xl.client.composite.UpComposite;
import com.xl.client.dao.SqlDao;
import com.xl.client.bean.User;

public class LoginShell extends Shell {

	private SwitchBarsComposite switchBarsComposite;
	private boolean isDraw = false;
	private int xx;
	private int yy;
	private Text text;
	private Text text2;
	private Button upbt;
	private SqlDao sqlDao = new SqlDao();
	private MainShell mainShell;
	private MainShell2 mainShell2;
	//private MainShell3 mainShell3;
	private static LoginShell shell;
	public static String userName;
	public static String policeNO;
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new LoginShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public LoginShell(Display display) {
		super(display, SWT.NO_TRIM);
		//setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		cteateContents2();
		
		getShell().setLocation(Display.getCurrent().getClientArea().width / 2 - getShell().getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - getShell().getSize().y / 2);
	}

	private void cteateContents2() {
		setSize(1024, 650);
		final Composite cp = new Composite(this, SWT.NONE);
		cp.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (isDraw) {
					getShell().setLocation(getShell().getLocation().x + e.x - xx,
							getShell().getLocation().y + e.y - yy);
				}
			}
		});
		cp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				isDraw = true;
				xx = e.x;
				yy = e.y;
			}

			@Override
			public void mouseUp(MouseEvent e) {
				isDraw = false;
			}
		});
		cp.setBounds(0, 0, 1024, 650);
		cp.setBackgroundImage(SWTResourceManager.getImage(LoginShell.class, "/images/client_login.jpg"));
		Button minibt = new Button(cp, SWT.NONE);
		minibt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().setMinimized(true);
			}
		});
		minibt.setImage(SWTResourceManager.getImage(LoginShell.class, "/images/bb.png"));
		minibt.setBounds(966, 4, 25, 18);

		Button closebt = new Button(cp, SWT.NONE);
		closebt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().close();
			}
		});
		closebt.setImage(SWTResourceManager.getImage(LoginShell.class, "/images/aa.png"));
		closebt.setBounds(997, 5, 17, 17);
			
		Label typelabel = new Label(cp, SWT.NONE);
		typelabel.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		typelabel.setBackground(SWTResourceManager.getColor(new RGB(247, 248, 250)));
		typelabel.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.NORMAL));
		typelabel.setAlignment(SWT.RIGHT);
		typelabel.setBounds(593, 235, 90, 23);
		typelabel.setText("账    号:");

		Label dirlabel = new Label(cp, SWT.NONE);
		dirlabel.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		dirlabel.setBackground(SWTResourceManager.getColor(new RGB(242, 243, 247)));
		dirlabel.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.NORMAL));
		dirlabel.setAlignment(SWT.RIGHT);
		dirlabel.setBounds(593, 310, 90, 23);
		dirlabel.setText("密    码:");
		
		 text = new Text(cp, SWT.BORDER);
		 //text.setText("用户名") text.setBounds(626, 220, 242, 36);;
		 text.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		 text.setBounds(690, 230, 178, 36);
		 
		 text2 = new Text(cp, SWT.BORDER|SWT.PASSWORD);
		 //text2.setText("密码");
		 text2.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		 text2.setBounds(690, 305, 178, 36);
		 upbt = new Button(cp, SWT.NONE);
		 upbt.setBounds(626, 380, 242, 36);
		 upbt.setImage(SWTResourceManager.getImage(UpComposite.class, "/images/denglu.png"));
		 upbt.addSelectionListener(new SelectionAdapter() {
			 
			 @Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				super.widgetSelected(e);
				String username = text.getText();
				//userName = username;
				String password = "xl_" + text2.getText();
				System.out.println(username);
				User user = new User();
				user.setPoliceNO(username);
				//user.setUsername(username);
				List<User> lists = sqlDao.getListfromMysql(user);
				if(lists.isEmpty()){
					JOptionPane.showMessageDialog(null, "没有该用户", "提示", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("sssss");
				}else{
					if(!string2MD5(password).equals(lists.get(0).getPassword())){
						System.out.println("获取输入密码"+password);
						System.out.println("加密《《《《《《《《《《《《》》》》》》》》》"+string2MD5(password));
						System.out.println("获取数据库密码"+lists.get(0).getPassword());
						JOptionPane.showMessageDialog(null, "密码错误", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					userName=lists.get(0).getUsername();
					policeNO=lists.get(0).getPoliceNO();
//					mainShell = new MainShell(getDisplay());
//					mainShell.open();
					mainShell2 = new MainShell2(getDisplay());
					mainShell2.open();
					//new MainShell(getDisplay()).open();
					shell.setVisible(false);
				}
				
			}
		});
		
	}
	
	// 加密
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		System.out.println(hexValue.toString());
		return hexValue.toString();

	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public SwitchBarsComposite getSwitchBarsComposite() {
		return switchBarsComposite;
	}

	public void setSwitchBarsComposite(SwitchBarsComposite switchBarsComposite) {
		this.switchBarsComposite = switchBarsComposite;
	}

}
