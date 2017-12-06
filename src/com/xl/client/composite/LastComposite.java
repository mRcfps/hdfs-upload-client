package com.xl.client.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xl.client.bean.Caseinfo;
import com.xl.client.bean.Evidence;
import com.xl.client.bean.Section;
import com.xl.client.dao.SqlDao;
import com.xl.client.shell.MainShell;
/**
关联案件成功后的界面
  */
public class LastComposite extends Composite {
	private Label mid_Label;
	private Table table_1;
	private TableColumn tblclmnNewColumn_1;
	private TableColumn tblclmnNewColumn_2;
	private TableColumn tblclmnNewColumn_3;
	private TableColumn tblclmnNewColumn_4;
	private TableColumn tblclmnNewColumn_5;
	private Table table;
	private Button backbut;
	private String caseid;
	private List<String> selBtList;
	private SqlDao sqlDao = new SqlDao();
	private TableItem tableItem;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LastComposite(Composite parent, int style, boolean flag, String caseid, List<String> selBtList) {
		super(parent, style);
		this.caseid = caseid;
		this.selBtList = selBtList;
		setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		createTopLabel();
		createTable();
	}

	protected void createTopLabel() {
		setSize(1024, 563);
		setBounds(0, 88, 1024, 563);

		// 案件关联成功图片
		mid_Label = new Label(this, SWT.NONE);
		mid_Label.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		mid_Label.setBounds(438, 25, 138, 100);
		mid_Label.setBackgroundImage(SWTResourceManager.getImage(RelatedComposite.class, "/images/success.png"));

		// 返回按钮
		backbut = new Button(this, SWT.NONE);
		backbut.setBounds(440, 142, 138, 30);
		backbut.setImage(SWTResourceManager.getImage(RelatedComposite.class, "/images/butt.png"));

		backbut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MainShell shell = (MainShell) getShell();
				dispose();
				shell.getSwitchBarsComposite().setVisible(true);
				shell.getSwitchBarsComposite().getUc().redraw();
				shell.getSwitchBarsComposite().getUc().setVisible(true);
			}
		});

		/*
		 * butt_Label = new Label(this,SWT.NONE); butt_Label.setBounds( 438,
		 * 142, 138, 30);
		 * butt_Label.setBackgroundImage(SWTResourceManager.getImage(
		 * "C:\\Users\\Administrator\\Desktop\\临时img\\butt.png"));
		 */
	}

	protected void createTable() {

		table_1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(26, 242, 973, 57);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		table_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));

		table_1.addListener(SWT.MeasureItem, new Listener() {

			@Override
			public void handleEvent(Event event) {
				event.width = table_1.getGridLineWidth();
				event.height = (int) Math.floor(event.gc.getFontMetrics().getHeight() * 1.5);
			}
		});

		TableColumn tblclmnNewColumn = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn.setWidth(165);
		tblclmnNewColumn.setText("案件编号");

		tblclmnNewColumn_1 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_1.setWidth(165);
		tblclmnNewColumn_1.setText("案件名称");

		tblclmnNewColumn_2 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_2.setWidth(165);
		tblclmnNewColumn_2.setText("案件受理日期");

		tblclmnNewColumn_3 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_3.setWidth(165);
		tblclmnNewColumn_3.setText("案件类型");

		tblclmnNewColumn_4 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_4.setWidth(165);
		tblclmnNewColumn_4.setText("案件所属科室");

		tblclmnNewColumn_5 = new TableColumn(table_1, SWT.NONE);
		tblclmnNewColumn_5.setWidth(142);
		tblclmnNewColumn_5.setText("案件负责人");

		Caseinfo caseinfo = new Caseinfo();
		caseinfo.setId(Integer.parseInt(caseid));
		List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(caseinfo);
		Caseinfo casedb = new Caseinfo();
		if (listfromMysql.size() > 0) {
			casedb = listfromMysql.get(0);
		}

		String ptters = casedb.getSection();
		Section Section = new Section();
		Section.setId(Integer.parseInt(ptters) );
							List<Section> roles2 = sqlDao.getListfromMysql(Section);
							Section roles3 = roles2.get(0);
							casedb.setSection(roles3.getSectionName());
		
		tableItem = new TableItem(table_1, SWT.NONE);
		tableItem.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		tableItem.setText(new String[] { casedb.getCaseNum(), casedb.getCaseName(), casedb.getCreatedTime(),casedb.getCaseType(),
				casedb.getSection(), casedb.getUserName() });

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));
		lblNewLabel.setBounds(26, 205, 973, 36);
		lblNewLabel.setText("案件信息");

		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));
		lblNewLabel_1.setBounds(26, 332, 973, 41);
		lblNewLabel_1.setText("关联记录");

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(26, 374, 973, 85);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));

		TableColumn tblclmnNewColumn_6 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_6.setWidth(113);
		tblclmnNewColumn_6.setText("导入日期");

		TableColumn tblclmnNewColumn_7 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_7.setWidth(105);
		tblclmnNewColumn_7.setText("操作人");

		TableColumn tblclmnNewColumn_8 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_8.setWidth(107);
		tblclmnNewColumn_8.setText("上传方式");

		TableColumn tblclmnNewColumn_9 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_9.setWidth(105);
		tblclmnNewColumn_9.setText("文件大小");

		TableColumn tblclmnNewColumn_10 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_10.setWidth(107);
		tblclmnNewColumn_10.setText("数据名称");

		TableColumn tblclmnNewColumn_11 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_11.setWidth(107);
		tblclmnNewColumn_11.setText("数据描述");

		TableColumn tblclmnNewColumn_12 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_12.setWidth(107);
		tblclmnNewColumn_12.setText("上传数");

		TableColumn tblclmnNewColumn_13 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_13.setWidth(107);
		tblclmnNewColumn_13.setText("成功数");

		TableColumn tblclmnNewColumn_14 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_14.setWidth(107);
		tblclmnNewColumn_14.setText("失败数");

		List<Evidence> evlist = new ArrayList<Evidence>();
		for (String evid : selBtList) {
			Evidence ev = new Evidence();
			ev.setId(Integer.parseInt(evid));
			List<Evidence> listev = sqlDao.getListfromMysql(ev);
			if (listev.size() > 0) {
				Evidence evidence = listev.get(0);
				evidence.setEvSize(Integer.valueOf(evidence.getEvSize())/1024+"");
				sqlDao.updateToMysql(evidence);
//				ev = listev.get(0);
//				ev.setEvSize(Integer.valueOf(ev.getEvSize())/1024+"");
//				sqlDao.updateToMysql(ev);
				//Evidence evidence2 = new Evidence();
				evlist.add(evidence);
			}
		}

		for (Evidence evidence : evlist) {
			TableItem tableItem_1 = new TableItem(table, SWT.NONE);
			tableItem_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
			tableItem_1.setText(new String[] { evidence.getAddTime(), evidence.getEvAdmin(), "客户端上传",evidence.getEvSize()+"kb",
					evidence.getEvName(), evidence.getComment(),evidence.getUploadNum(),evidence.getSuccessNum(),evidence.getErrorNum() });
		}

		/*
		 * TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		 * tableItem_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI",
		 * 10, SWT.NORMAL)); tableItem_1.setText(new String[]
		 * {"2017-06-26","苏小妍","服务器上传","1.6GB","硬盘数据","数据非常大","265155","265155",
		 * "0"});
		 */
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
