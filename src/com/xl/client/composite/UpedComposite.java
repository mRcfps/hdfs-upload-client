package com.xl.client.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
/**
数据列表界面
  */
public class UpedComposite extends Composite {
	
	private UpedToolBarComposite upedToolBarComposite;
	private UpListComposite upListComposite;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UpedComposite(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		createContents();
	}
	
	protected void createContents() {
		setSize(1024, 517);
		upedToolBarComposite = new UpedToolBarComposite(this, SWT.NONE);
		upedToolBarComposite.setBounds(0, 0, 1024, 38);
		upListComposite = new UpListComposite(this, SWT.BORDER);
		upListComposite.setBounds(0, 39, 1024, 457);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 获取上传的证据列表
	public int getUpedList() {
		int fnum=upListComposite.getUpedList();
		return fnum;
	}

	public UpedToolBarComposite getUpedToolBarComposite() {
		return upedToolBarComposite;
	}

	public void setUpedToolBarComposite(UpedToolBarComposite upedToolBarComposite) {
		this.upedToolBarComposite = upedToolBarComposite;
	}

	public UpListComposite getUpListComposite() {
		return upListComposite;
	}

	public void setUpListComposite(UpListComposite upListComposite) {
		this.upListComposite = upListComposite;
	}
}
