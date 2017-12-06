package com.xl.client.shell;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.sun.java_cup.internal.runtime.lr_parser;
import com.xl.client.bean.Caseinfo;
import com.xl.client.bean.Role;
import com.xl.client.bean.Section;
import com.xl.client.bean.User;
import com.xl.client.composite.LastComposite2;
import com.xl.client.composite.RelatedComposite;
import com.xl.client.dao.SqlDao;

public class MainShell2 extends Shell {

	private boolean isDraw = false;
	private int xx;
	private int yy;
	public static Table table_1;
	private SqlDao sqlDao = new SqlDao();
	protected LastComposite2 lc2;
	private static MainShell2 shell;
	

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			shell = new MainShell2(display);
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
	public MainShell2(Display display) {
		super(display, SWT.NO_TRIM);
		//setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		createContents();
		getShell().setLocation(Display.getCurrent().getClientArea().width / 2 - getShell().getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - getShell().getSize().y / 2);
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setSize(1024, 650);
		Composite cp = new Composite(this, SWT.NONE);
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
		cp.setBounds(0, 0, 1024, 88);
		cp.setBackgroundImage(SWTResourceManager.getImage(MainShell2.class, "/images/title.png"));

		Button minibt = new Button(cp, SWT.NONE);
		minibt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().setMinimized(true);
			}
		});
		minibt.setImage(SWTResourceManager.getImage(MainShell2.class, "/images/mini.jpg"));
		minibt.setBounds(966, 4, 25, 18);

		Button closebt = new Button(cp, SWT.NONE);
		closebt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().close();
				lc2.dispose();
				System.exit(0);
			}
		});
		closebt.setImage(SWTResourceManager.getImage(MainShell2.class, "/images/close.jpg"));
		closebt.setBounds(997, 5, 17, 17);


//		if (table_1 != null) {
//			table_1.dispose();
//		}

		table_1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));

		table_1.setBounds(20, 145, 984, 448);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn.setWidth(135);
		tblclmnNewColumn.setText("案件编号");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_1.setWidth(163);
		tblclmnNewColumn_1.setText("案件名称");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_2.setWidth(135);
		tblclmnNewColumn_2.setText("案件类型");

		TableColumn tblclmnNewColumn_3 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_3.setWidth(135);
		tblclmnNewColumn_3.setText("案件所属科室");

		TableColumn tblclmnNewColumn_4 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_4.setWidth(135);
		tblclmnNewColumn_4.setText("案件负责人");

		TableColumn tblclmnNewColumn_5 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_5.setWidth(135);
		tblclmnNewColumn_5.setText("案件状态");

		TableColumn tblclmnNewColumn_6 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_6.setWidth(120);
		tblclmnNewColumn_6.setText("上传文件");

		Caseinfo cas = new Caseinfo();
		List<Caseinfo> case_handling = new ArrayList<Caseinfo>();
		List<Caseinfo> listfromMysql = new ArrayList<Caseinfo>();
		
		User user = new User();
		user.setUsername(LoginShell.userName);
		List<User> userlist = sqlDao.getListfromMysql(user);
		if(userlist.size()>0){
		
		User user2= new User();
		user2=userlist.get(0);
		System.out.println("user2.getUserrole()"+user2.getUserrole());
		String roleName="" ;
		if(user2 != null){
			String userrole = user2.getUserrole();
			Role role = new Role();
			role.setId(Integer.parseInt(userrole));
			List<Role> roleList = sqlDao.getListfromMysql(role);
			if(roleList.size()>0){
				Role role2 = roleList.get(0);
				roleName = role2.getDataScope();
				 
			}
		}
		if (roleName.equals("科室数据") ) {// 科长
			cas.setSection(user2.getSection()+"/"+user.getUsername());
		} else if (roleName.equals("部门数据") ) {// 部长
			cas.setDepartment(user2.getPartment());
		} else if (roleName.equals("个人数据") ) {// 科员
			cas.setUserName(user2.getUsername());
		} else if (roleName.equals("所有数据")) {// 局长
			cas.setId(-1);
		} else {
			cas.setId(-2);
		}
		List<Caseinfo> logs =new ArrayList<Caseinfo>();
		
			logs = sqlDao.getListfromMysqlLikTimeecaseOR(cas);
			
		
		
		for (Caseinfo section2 : logs) {
			String sectionw = section2.getSection();
			Section section3 = new Section();
			section3.setId(Integer.parseInt(sectionw));
			List<Section> listfromMysqlse = sqlDao.getListfromMysql(section3);
			if (listfromMysqlse.size() > 0) {
				Section departmentse = listfromMysqlse.get(0);
				String seName = departmentse.getSectionName();
				section2.setSection(seName);
			}
			listfromMysql.add(section2);
			
		}
		
		
		
		for (Caseinfo caseinfo2 : listfromMysql) {
			if ("办案中".equals(caseinfo2.getStatus())) {
				case_handling.add(caseinfo2);
			}
		}
		
		for (Caseinfo list : case_handling) {
			String num = list.getCaseNum();
			String name = list.getCaseName();
			String leixing = list.getCaseType();
			String section = list.getSection();
			String mainparty = list.getUserName();
			String status = list.getStatus();
			
			TableItem tableItem_1 = new TableItem(table_1, SWT.NONE);
			tableItem_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			tableItem_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
			tableItem_1.setText(new String[] { num, name, leixing, section, mainparty, status });
			
			TableEditor delEditor = new TableEditor(table_1);
			delEditor.horizontalAlignment = SWT.CENTER;
			delEditor.minimumWidth = 60;
			delEditor.minimumHeight = 22;
			final Button relateBut = new Button(table_1, SWT.NONE);

			relateBut.setText(list.getId() + "");

			// 关联按钮跳转 关联完成
			relateBut.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("1");
					String caseid = relateBut.getText();

					 if (lc2 != null) {
						 lc2.dispose();
						}
						try {
							 lc2 = new LastComposite2(getShell(), getStyle(),caseid);
							 //lc2.setVisible(false);
							 table_1.setVisible(false);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
				}
			});
			relateBut.setImage(SWTResourceManager.getImage(RelatedComposite.class, "/images/667.png"));

			delEditor.setEditor(relateBut, tableItem_1, 6);
		}
		}
		//caseinfolist.removeAll(list_remove);
	
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}



}
