package com.xl.client.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.util.BytesTrie.Iterator;
import com.xl.client.bean.Caseinfo;
import com.xl.client.bean.Evidence;
import com.xl.client.bean.Section;
import com.xl.client.dao.SqlDao;
import com.xl.client.shell.MainShell;
import com.xl.client.util.UploadUtils;
/**
案件列表界面
  */
public class RelatedComposite extends Composite {

	// private Label top_Label;
	private Label text_Label;
	private Text text;
	// private Label search_Label;
	private Table table_1;
	private SqlDao sqlDao = new SqlDao();
	private LastComposite lc;
	private List<String> selBtList;
	private Button searchBt;
	private long selSize;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 * @param selBtList
	 */
	public RelatedComposite(Composite parent, int style, List<String> selBtList, long totalSize) {
		super(parent, style);
		this.selBtList = selBtList;
		this.selSize = totalSize;
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		guanlian();
	}

	protected void guanlian() {
		setSize(1024, 563);
		setBounds(0, 88, 1024, 563);
		/*
		 * top_Label = new Label(this,SWT.NONE); top_Label.setLocation(0, 0);
		 * top_Label.setSize(1024, 89); top_Label.setBounds( 50, 50, 10, 100);
		 * top_Label.setBackgroundImage(SWTResourceManager.getImage(
		 * "C:\\Users\\Administrator\\Desktop\\临时img\\123.png"));
		 */

		/*
		 * Button btnNewButton_1 = new Button(this,SWT.NONE);
		 * btnNewButton_1.addSelectionListener(new SelectionAdapter() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent e) { MainShell
		 * shell = (MainShell) getShell();
		 * shell.getSwitchBarsComposite().setVisible(true);
		 * shell.getSwitchBarsComposite().getUc().setVisible(true); } });
		 * btnNewButton_1.setFont(SWTResourceManager.getFont(
		 * "Microsoft YaHei UI", 12, SWT.NORMAL)); btnNewButton_1.setBounds(24,
		 * 3, 89, 45); //btnNewButton_1.setText("返回");
		 * btnNewButton_1.setImage(SWTResourceManager.getImage(RelatedComposite.
		 * class, "/images/fanhui.png"));
		 * 
		 * 
		 * text = new Text(this, SWT.BORDER); text.setBounds(596, 16, 372, 30);
		 * 
		 * text_Label = new Label(this,SWT.NONE);
		 * text_Label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE)
		 * ); text_Label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI"
		 * , 12, SWT.NORMAL)); text_Label.setBounds( 123, 16, 372, 21);
		 * text_Label.setText("已选文件数: 71    文件大小: 730MB");
		 * 
		 * search_Label = new Label(this,SWT.NONE);
		 * search_Label.setImage(SWTResourceManager.getImage(RelatedComposite.
		 * class, "/images/se.png")); search_Label.setBounds( 968, 16, 33, 30);
		 */

		// table_1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		// table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12,
		// SWT.NORMAL));
		//
		// table_1.setBounds(24, 72, 980, 448);
		// table_1.setHeaderVisible(true);
		// table_1.setLinesVisible(true);
		//
		// TableColumn tblclmnNewColumn = new TableColumn(table_1, SWT.CENTER);
		// tblclmnNewColumn.setWidth(135);
		// tblclmnNewColumn.setText("案件编号");
		//
		// TableColumn tblclmnNewColumn_1 = new TableColumn(table_1,
		// SWT.CENTER);
		// tblclmnNewColumn_1.setWidth(163);
		// tblclmnNewColumn_1.setText("案件名称");
		//
		// TableColumn tblclmnNewColumn_2 = new TableColumn(table_1,
		// SWT.CENTER);
		// tblclmnNewColumn_2.setWidth(135);
		// tblclmnNewColumn_2.setText("案件类型");
		//
		// TableColumn tblclmnNewColumn_3 = new TableColumn(table_1,
		// SWT.CENTER);
		// tblclmnNewColumn_3.setWidth(135);
		// tblclmnNewColumn_3.setText("案件所属科室");
		//
		// TableColumn tblclmnNewColumn_4 = new TableColumn(table_1,
		// SWT.CENTER);
		// tblclmnNewColumn_4.setWidth(135);
		// tblclmnNewColumn_4.setText("案件负责人");
		//
		// TableColumn tblclmnNewColumn_5 = new TableColumn(table_1,
		// SWT.CENTER);
		// tblclmnNewColumn_5.setWidth(135);
		// tblclmnNewColumn_5.setText("案件状态");
		//
		// TableColumn tblclmnNewColumn_6 = new TableColumn(table_1,
		// SWT.CENTER);
		// tblclmnNewColumn_6.setWidth(120);
		// tblclmnNewColumn_6.setText("案件关联");
		genTable("");

		// 返回按钮一栏的标签
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		composite.setBackground(new Color(Display.getDefault(), 247, 249, 249));
		composite.setBounds(0, 0, 1024, 44);

		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setBounds(0, 0, 89, 44);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MainShell shell = (MainShell) getShell();
				shell.getSwitchBarsComposite().setVisible(true);
				shell.getSwitchBarsComposite().getUc().setVisible(true);
			}
		});
		btnNewButton_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		btnNewButton_1.setImage(SWTResourceManager.getImage(RelatedComposite.class, "/images/fanhui.png"));

		text_Label = new Label(composite, SWT.NONE);
		text_Label.setBounds(97, 13, 89, 17);
		text_Label.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		text_Label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		text_Label.setText("已选文件数:");

		text = new Text(composite, SWT.NONE);
		text.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		// text.setBackground(SWTResourceManager.getColor(new RGB(246, 248,
		// 248)));
		text.setBackgroundImage(SWTResourceManager.getImage(RelatedComposite.class, "/images/inputtext.png"));
		text.setBounds(650, 9, 325, 30);

		// search_Label = new Label(composite, SWT.NONE);
		// search_Label.setBounds(972, 9, 33, 30);
		// search_Label.setImage(SWTResourceManager.getImage(RelatedComposite.class,
		// "/images/se.png"));
		searchBt = new Button(composite, SWT.NONE);
		searchBt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String terms = text.getText();
				terms = terms.trim();
				final String term = terms;
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						genTable(term);
					}
				});
			}
		});
		searchBt.setImage(SWTResourceManager.getImage(RelatedComposite.class, "/images/se.png"));
		searchBt.setBounds(975, 9, 32, 30);

		Label upNumLb = new Label(composite, SWT.NONE);
		upNumLb.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		upNumLb.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		upNumLb.setBounds(192, 13, 50, 17);
		upNumLb.setText(selBtList.size() + "");

		Label fs_label = new Label(composite, SWT.NONE);
		fs_label.setText("文件大小:");
		fs_label.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		fs_label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		fs_label.setBounds(248, 13, 68, 17);

		Label fsize_lb = new Label(composite, SWT.NONE);
		fsize_lb.setText(UploadUtils.convertFileSize(selSize));
		fsize_lb.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		fsize_lb.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		fsize_lb.setBounds(317, 13, 68, 17);

		// Button turnleft = new Button(this, SWT.NONE);
		// turnleft.setImage(SWTResourceManager.getImage(RelatedComposite.class,
		// "/images/left.png"));
		// turnleft.setBounds(823, 526, 31, 29);
		//
		// Button turnright = new Button(this, SWT.NONE);
		// turnright.setImage(SWTResourceManager.getImage(RelatedComposite.class,
		// "/images/right.png"));
		// turnright.setBounds(951, 526, 31, 29);

		// Caseinfo caseinfo = new Caseinfo();
		// List<Caseinfo> caseinfolist = sqlDao.getOrderListfromMysql(caseinfo,
		// "createdTime");
		// for (Caseinfo caseinfo2 : caseinfolist) {
		// String num = caseinfo2.getCaseNum();
		// String name = caseinfo2.getCaseName();
		// String leixing = "";
		// String section = caseinfo2.getSection();
		// String mainparty = caseinfo2.getMainParty();
		// String status = caseinfo2.getStatus();
		//
		// TableItem tableItem_1 = new TableItem(table_1, SWT.NONE);
		// tableItem_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		// tableItem_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI",
		// 10, SWT.NORMAL));
		// tableItem_1.setText(new String[] { num, name, leixing, section,
		// mainparty, status });
		//
		// TableEditor delEditor = new TableEditor(table_1);
		// delEditor.horizontalAlignment = SWT.CENTER;
		// delEditor.minimumWidth = 35;
		// delEditor.minimumHeight = 12;
		// final Button relateBut = new Button(table_1, SWT.NONE);
		//
		// relateBut.setText(caseinfo2.getId() + "");
		//
		// // 关联按钮跳转 关联完成
		// relateBut.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// String caseid = relateBut.getText();
		// boolean bdflag = bindCase(caseid, selBtList);
		// lc = new LastComposite(getShell(), getStyle(), bdflag, caseid,
		// selBtList);
		// dispose();
		// lc.setVisible(true);
		// // setVisible(false);
		// }
		// });
		// relateBut.setImage(SWTResourceManager.getImage(RelatedComposite.class,
		// "/images/related.png"));
		//
		// delEditor.setEditor(relateBut, tableItem_1, 6);
		// }
	}

	// 更新table
	private void genTable(String term) {
		if (table_1 != null) {
			table_1.dispose();
		}

		table_1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));

		table_1.setBounds(20, 72, 984, 448);
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
		tblclmnNewColumn_6.setText("案件关联");

		Caseinfo caseinfo = new Caseinfo();
		List<Caseinfo> caseinfolist = null;
		List<Caseinfo> case_handling = new ArrayList<Caseinfo>();
	
		if (term != null && !"".equals(term)) {
			caseinfo.setCaseName(term);
			caseinfo.setCaseNum(term);
			caseinfolist = sqlDao.getOrderListfromMysqlLike(caseinfo, "createdTime");
		} else {
			caseinfolist = sqlDao.getOrderListfromMysql(caseinfo, "createdTime");
		}
		for (Caseinfo caseinfo2 : caseinfolist) {
			if ("办案中".equals(caseinfo2.getStatus())) {
				case_handling.add(caseinfo2);
			}
		}
		
		for (Caseinfo list : case_handling) {
			String num = list.getCaseNum();
			String name = list.getCaseName();
			String leixing = list.getCaseType();
			String section = list.getSection();
			Section Section = new Section();
			Section.setId(Integer.parseInt(section) );
			List<Section> roles2 = sqlDao.getListfromMysql(Section);
			Section roles3 = roles2.get(0);
			section=roles3.getSectionName();
			String mainparty = list.getUserName();
			String status = list.getStatus();
			
			TableItem tableItem_1 = new TableItem(table_1, SWT.NONE);
			tableItem_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			tableItem_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
			tableItem_1.setText(new String[] { num, name, leixing, section, mainparty, status });
			
			TableEditor delEditor = new TableEditor(table_1);
			delEditor.horizontalAlignment = SWT.CENTER;
			delEditor.minimumWidth = 35;
			delEditor.minimumHeight = 12;
			final Button relateBut = new Button(table_1, SWT.NONE);

			relateBut.setText(list.getId() + "");

			// 关联按钮跳转 关联完成
			relateBut.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("1");
					String caseid = relateBut.getText();
					boolean bdflag = bindCase(caseid, selBtList);
					lc = new LastComposite(getShell(), getStyle(), bdflag, caseid, selBtList);
					dispose();
					lc.setVisible(true);
				}
			});
			relateBut.setImage(SWTResourceManager.getImage(RelatedComposite.class, "/images/related.png"));

			delEditor.setEditor(relateBut, tableItem_1, 6);
		}
		//caseinfolist.removeAll(list_remove);
	}

	// 绑定案件
	protected boolean bindCase(String caseid, List<String> selBtList2) {
		boolean flag = false;
		try {
			for (String evid : selBtList2) {
				Evidence ev = new Evidence();
				ev.setId(Integer.valueOf(evid));
				List<Evidence> listfromMysql = sqlDao.getListfromMysql(ev);
				if (listfromMysql.size() > 0) {
					Evidence evidence = listfromMysql.get(0);
					evidence.setCaseID(Integer.parseInt(caseid));
					sqlDao.updateToMysql(evidence);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		flag = true;
		return flag;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
