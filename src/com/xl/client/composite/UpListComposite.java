package com.xl.client.composite;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xl.client.bean.Evidence;
import com.xl.client.dao.SqlDao;
import com.xl.client.shell.LoginShell;

public class UpListComposite extends Composite {

	private Composite listTopComposite;
	private ScrolledComposite scrolledComposite;
	private Composite wp;
	private SqlDao sqlDao = new SqlDao();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public UpListComposite(Composite parent, int style) {
		super(parent, style);
		createContents();
	}

	protected void createContents() {
		setSize(1024, 457);
		setLayout(new FillLayout());
		scrolledComposite = new ScrolledComposite(this, SWT.V_SCROLL);
		scrolledComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		wp = new Composite(scrolledComposite, SWT.NONE);
		wp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		scrolledComposite.setContent(wp);
		// wp.setBounds(0, 0, 1024, 457);
		GridLayout gl_wp = new GridLayout(1, true);
		gl_wp.horizontalSpacing = 0;
		gl_wp.marginHeight = 0;
		gl_wp.verticalSpacing = 0;
		gl_wp.marginWidth = 0;
		wp.setLayout(gl_wp);
		GridData data = new GridData(GridData.FILL_BOTH);
		wp.setLayoutData(data);

		listTopComposite = new Composite(wp, SWT.NONE);
		listTopComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 0;
		glayout.horizontalSpacing = 0;
		glayout.marginHeight = 0;
		glayout.verticalSpacing = 0;
		glayout.marginTop = 0;
		glayout.marginBottom = 0;
		listTopComposite.setLayout(glayout);
		// listTopComposite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		// scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setAlwaysShowScrollBars(true);

		// Evidence ev = new Evidence();
		// ev.setEvName("tqqq");
		// ev.setAddTime("sfsa");
		// ev.setCaseID(317);
		// new ListItemComposite(listTopComposite, SWT.NONE, ev);

		// wp.setSize(wp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		// scrolledComposite.setMinHeight(wp.getSize().y);
		// System.out.println(listTopComposite.getBounds());
		// System.out.println(wp.getBounds());
		// scrolledComposite.pack();
		this.layout();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 获取上传的证据列表
	public int getUpedList() {

		if (scrolledComposite != null) {
			scrolledComposite.dispose();
		}
		if (wp != null) {
			wp.dispose();
		}
		scrolledComposite = new ScrolledComposite(this, SWT.V_SCROLL);

		wp = new Composite(scrolledComposite, SWT.NONE);
		wp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		scrolledComposite.setContent(wp);
		GridLayout gl_wp = new GridLayout(1, true);
		gl_wp.horizontalSpacing = 0;
		gl_wp.marginHeight = 0;
		gl_wp.verticalSpacing = 0;
		gl_wp.marginWidth = 0;
		wp.setLayout(gl_wp);
		GridData data = new GridData(GridData.FILL_BOTH);
		wp.setLayoutData(data);
		if (listTopComposite != null) {
			listTopComposite.dispose();
			wp.layout();
		}
		listTopComposite = new Composite(wp, SWT.NONE);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 0;
		glayout.horizontalSpacing = 0;
		glayout.marginHeight = 0;
		glayout.verticalSpacing = 1;
		glayout.marginTop = 0;
		glayout.marginBottom = 0;
		listTopComposite.setLayout(glayout);

		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setAlwaysShowScrollBars(true);
		System.out.println(LoginShell.userName);
		String userName = LoginShell.userName;
		Evidence ev = new Evidence();
		// TODO 需要最终确认用户
		//ev.setEvAdmin("");
		ev.setIsdel(0);
		
		List<Evidence> orderListfromMysql = sqlDao.getOrderListfromMysql(ev, "addTime");
		for (Evidence evidence : orderListfromMysql) {
			new ListItemComposite(listTopComposite, SWT.NONE, evidence);
		}
		wp.setSize(wp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrolledComposite.setMinHeight(wp.getSize().y);
		scrolledComposite.pack();
		scrolledComposite.setFocus();
		this.layout();
		return orderListfromMysql.size();
	}

	public Composite getListTopComposite() {
		return listTopComposite;
	}

	public void setListTopComposite(Composite listTopComposite) {
		this.listTopComposite = listTopComposite;
	}

}
