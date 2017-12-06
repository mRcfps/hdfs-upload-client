package com.xl.client.composite;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xl.client.bean.Evidence;
import com.xl.client.dao.SqlDao;
import com.xl.client.shell.MainShell;
/**
数据列表界面
  */
public class UpedToolBarComposite extends Composite {

	private Button glButton;
	private Button qcButton;
	private List<String> selBtList;

	private RelatedComposite rec;
	private SqlDao sqlDao = new SqlDao();
	private Composite fileNumComp;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public UpedToolBarComposite(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		createContents();
	}

	protected void createContents() {
		setSize(1024, 36);
		setBounds(0, 0, 1024, 36);
		glButton = new Button(this, SWT.NONE);
		qcButton = new Button(this, SWT.NONE);
		qcButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UpedComposite upedComposite = (UpedComposite) getParent();
				UpListComposite upListComposite = upedComposite.getUpListComposite();
				Composite listTopComposite = upListComposite.getListTopComposite();
				Control[] children = listTopComposite.getChildren();
				for (Control control : children) {
					ListItemComposite lc = (ListItemComposite) control;
					if (lc.isGlflag() || !lc.isFinished()) {
						Button ckbt = lc.getCkbt();
						String evId = ckbt.getData("evId").toString();
						Evidence ev = new Evidence();
						ev.setId(Integer.parseInt(evId));
						ev.setIsdel(1);
						sqlDao.updateToMysql(ev);
					}
				}
				upedComposite.getUpedList();
			}
		});

		glButton.setBounds(802, 7, 92, 24);
		glButton.setImage(
				SWTResourceManager.getImage(UpedToolBarComposite.class, "/images/guanliananjian_highlight.png"));
		qcButton.setBounds(913, 7, 92, 24);
		qcButton.setImage(SWTResourceManager.getImage(UpedToolBarComposite.class, "/images/qcsy.png"));
		qcButton.setBackground(SWTResourceManager.getColor(new RGB(255, 255, 255)));

		Composite gouComposite = new Composite(this, SWT.NONE);
		gouComposite.setBounds(20, 11, 18, 14);
		gouComposite.setBackgroundImage(SWTResourceManager.getImage(UpedToolBarComposite.class, "/images/gou.png"));

		Label cs_label = new Label(this, SWT.NONE);
		cs_label.setForeground(SWTResourceManager.getColor(new RGB(58, 80, 140)));
		cs_label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		cs_label.setText("共传输完成");
		cs_label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		cs_label.setBounds(54, 7, 84, 22);

		fileNumComp = new Composite(this, SWT.NONE);
		fileNumComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fileNumComp.setBounds(136, 5, 275, 28);
		RowLayout rlay = new RowLayout();
		fileNumComp.setLayout(rlay);
		Label nmFile = new Label(fileNumComp, SWT.NONE);
		nmFile.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		nmFile.setText("0");
		nmFile.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		nmFile.setForeground(SWTResourceManager.getColor(new RGB(58, 80, 140)));
		Label fComp2 = new Label(fileNumComp, SWT.NONE);
		fComp2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fComp2.setText("个文件!");
		fComp2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		fComp2.setForeground(SWTResourceManager.getColor(new RGB(58, 80, 140)));
		fileNumComp.layout();

		// 点击事件实现跳转
		glButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				selBtList = new ArrayList<String>();
				UpedComposite upedComposite = (UpedComposite) getParent();
				UpListComposite upListComposite = upedComposite.getUpListComposite();
				Composite listTopComposite = upListComposite.getListTopComposite();
				Control[] children = listTopComposite.getChildren();
				long totalSize = 0;
				for (Control control : children) {
					ListItemComposite lc = (ListItemComposite) control;
					Button ckbt = lc.getCkbt();
					if (ckbt.getSelection()) {
						selBtList.add(ckbt.getData("evId").toString());
						String evSize = ckbt.getData("evSize").toString();
						try {
							long parseLong = Long.parseLong(evSize);
							totalSize += parseLong;
						} catch (Exception e2) {
						}
					}
				}
				if (selBtList.size() == 0) {
					JOptionPane.showMessageDialog(null, "请选择案件后进行关联", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				if (rec != null) {
					rec.dispose();
				}
				try {
					rec = new RelatedComposite(getShell(), getStyle(), selBtList, totalSize);
					System.out.println(rec);
					rec.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				//rec = new RelatedComposite(getShell(), getStyle(), selBtList, totalSize);
//				rec.setVisible(true);

				MainShell shell = (MainShell) getShell();
				UpComposite upc = shell.getSwitchBarsComposite().getUpc();
				UpedComposite uc = shell.getSwitchBarsComposite().getUc();
				upc.setVisible(false);
				uc.setVisible(false);
				shell.getSwitchBarsComposite().setVisible(false);
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public Composite getFileNumComp() {
		return fileNumComp;
	}

	public void setFileNumComp(Composite fileNumComp) {
		this.fileNumComp = fileNumComp;
	}

}
