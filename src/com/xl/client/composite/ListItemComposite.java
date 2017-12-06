package com.xl.client.composite;

import java.net.URLDecoder;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xl.client.bean.Caseinfo;
import com.xl.client.bean.Evidence;
import com.xl.client.dao.SqlDao;
import com.xl.client.util.UploadUtils;

public class ListItemComposite extends Composite {

	private SqlDao sqlDao = new SqlDao();
	private Button ckbt;
	private boolean glflag;
	private boolean isFinished;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ListItemComposite(Composite parent, int style, Evidence ev) {
		super(parent, style);
		createContents(ev);
	}

	public Button getCkbt() {
		return ckbt;
	}

	public void setCkbt(Button ckbt) {
		this.ckbt = ckbt;
	}

	public boolean isGlflag() {
		return glflag;
	}

	public void setGlflag(boolean glflag) {
		this.glflag = glflag;
	}

	protected void createContents(Evidence ev) {
		setSize(1000, 57);

		String caseName = "";
		glflag = false;

		// 判断是否关联案件
		int caseID = ev.getCaseID();
		List<Caseinfo> listfromMysql = sqlDao.getListfromMysql(new Caseinfo(), "id=" + caseID);
		if (listfromMysql.size() > 0) {
			glflag = true;
			Caseinfo caseinfo = listfromMysql.get(0);
			caseName = caseinfo.getCaseName();
		}
		// 判断是否上传完成
		if ("true".equals(ev.getFinished())) {
			isFinished = true;
		} else {
			isFinished = false;
		}

		RowLayout layout = new RowLayout();
		layout.marginRight = 0;
		layout.type = SWT.HORIZONTAL;
		layout.marginTop = 0;
		layout.marginLeft = 0;
		layout.marginBottom = 0;
		layout.spacing = 0;
		setLayout(layout);
		Composite c1 = new Composite(this, SWT.NONE);
		c1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		c1.setLayoutData(new RowData(56, 56));
		ckbt = new Button(c1, SWT.TOGGLE);
		ckbt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selected = ckbt.getSelection();
				if (selected) {
					ckbt.setImage(SWTResourceManager.getImage(ListItemComposite.class, "/images/xzk_hi.png"));
				} else {
					ckbt.setImage(SWTResourceManager.getImage(ListItemComposite.class, "/images/xzk_dx.png"));
				}
			}
		});
		ckbt.setImage(SWTResourceManager.getImage(ListItemComposite.class, "/images/xzk_dx.png"));
		ckbt.setLocation(20, 19);
		ckbt.setSize(18, 18);
		ckbt.setData("evId", ev.getId() + "");
		ckbt.setData("evSize", ev.getEvSize());
		if (glflag || !isFinished) {
			ckbt.setImage(SWTResourceManager.getImage(ListItemComposite.class, "/images/xzk_dis.png"));
			ckbt.setEnabled(false);
		}

		Composite c2 = new Composite(this, SWT.NONE);
		c2.setLayoutData(new RowData(24, 56));
		c2.setBackground(SWTResourceManager.getColor(new RGB(255, 255, 255)));
		Composite c2cd = new Composite(c2, SWT.NONE);
		c2cd.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		c2cd.setBounds(0, 14, 24, 28);
		c2cd.setBackgroundImage(SWTResourceManager.getImage(ListItemComposite.class, "/images/cloudfile.png"));

		Composite c3 = new Composite(this, SWT.NONE);
		c3.setLayoutData(new RowData(432, 56));
		Composite fc = new Composite(c3, SWT.NONE);
		fc.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fc.setSize(432, 56);
		GridLayout glayout = new GridLayout();
		glayout.marginTop = 3;
		fc.setLayout(glayout);
		Label fname = new Label(fc, SWT.NONE);
		fname.setForeground(SWTResourceManager.getColor(new RGB(51, 51, 51)));
		fname.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		String eviName = ev.getEvName();
		try {
			eviName = eviName.replace("X_L", "%");
			eviName = URLDecoder.decode(eviName, "UTF-8");
		} catch (Exception e) {
		}

		fname.setText(eviName);
		Label fsize = new Label(fc, SWT.NONE);
		fsize.setForeground(SWTResourceManager.getColor(new RGB(153, 153, 153)));
		fsize.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		String evSize = ev.getEvSize();
		String showSize = "";
		try {
			long sizeLong = Long.parseLong(evSize);
			showSize = UploadUtils.convertFileSize(sizeLong);
		} catch (NumberFormatException e) {
		}
		fsize.setText(showSize);
		fc.layout();

		Composite c4 = new Composite(this, SWT.NONE);
		c4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		c4.setLayoutData(new RowData(181, 56));
		Label timelb = new Label(c4, SWT.NONE);
		timelb.setForeground(SWTResourceManager.getColor(new RGB(153, 153, 153)));
		timelb.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		timelb.setBounds(20, 20, 150, 20);
		timelb.setText(ev.getAddTime());

		Composite c5 = new Composite(this, SWT.NONE);
		c5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		c5.setLayoutData(new RowData(150, 56));
		Composite c5img = new Composite(c5, SWT.NONE);
		c5img.setBounds(22, 22, 12, 12);
		c5img.setBackgroundImage(SWTResourceManager.getImage(ListItemComposite.class, "/images/up_finish.png"));
		Label uplb = new Label(c5, SWT.NONE);
		uplb.setForeground(SWTResourceManager.getColor(new RGB(153, 153, 153)));
		uplb.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		uplb.setBounds(41, 20, 84, 17);
		if (isFinished) {
			c5img.setBackgroundImage(SWTResourceManager.getImage(ListItemComposite.class, "/images/up_finish.png"));
			uplb.setText("上传完成");
		} else {
			c5img.setBackgroundImage(SWTResourceManager.getImage(ListItemComposite.class, "/images/up_fail.png"));
			uplb.setText("上传失败");
		}

		Composite c6 = new Composite(this, SWT.NONE);
		c6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		c6.setLayoutData(new RowData(163, 56));
		c5.setLayoutData(new RowData(144, 56));
		Label caselb = new Label(c6, SWT.NONE);
		caselb.setForeground(SWTResourceManager.getColor(new RGB(153, 153, 153)));
		caselb.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		caselb.setBounds(20, 20, 120, 20);
		if (glflag) {
			caselb.setText(caseName);
		}

		this.layout();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

}
