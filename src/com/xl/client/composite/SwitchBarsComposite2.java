package com.xl.client.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class SwitchBarsComposite2 extends Composite {
	
	private Button bt_left;
	private Button bt_right;
	private UpComposite upc;
	private UpedComposite uc;

	public UpComposite getUpc() {
		return upc;
	}



	public void setUpc(UpComposite upc) {
		this.upc = upc;
	}



	public UpedComposite getUc() {
		return uc;
	}



	public void setUc(UpedComposite uc) {
		this.uc = uc;
	}



	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SwitchBarsComposite2(Composite parent, int style, UpComposite upc, UpedComposite uc) {
		super(parent, style);
		createContents();
		this.upc = upc;
		this.uc = uc;
	}
	
	
	
	protected void createContents() {
		setSize(1024, 45);
		bt_left = new Button(this, SWT.NONE);
		bt_right = new Button(this, SWT.NONE);
		bt_left.setBounds(0, 0, 512, 44);
		bt_right.setBounds(512, 0, 512, 44);
		bt_left.setImage(SWTResourceManager.getImage(SwitchBarsComposite2.class, "/images/zhengzaichuanshu_highlight.png"));
		bt_right.setImage(SWTResourceManager.getImage(SwitchBarsComposite2.class, "/images/chuanshuwc_gray.png"));
		
		// 点击事件
		bt_left.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				uc.setVisible(false);
				upc.setVisible(true);
				bt_left.setImage(SWTResourceManager.getImage(SwitchBarsComposite2.class, "/images/zhengzaichuanshu_highlight.png"));
				bt_right.setImage(SWTResourceManager.getImage(SwitchBarsComposite2.class, "/images/chuanshuwc_gray.png"));
			}
		});
		bt_right.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upc.setVisible(false);
				uc.setVisible(true);
				bt_left.setImage(SWTResourceManager.getImage(SwitchBarsComposite2.class, "/images/zhengzaichuanshu_gray.png"));
				bt_right.setImage(SWTResourceManager.getImage(SwitchBarsComposite2.class, "/images/chuanshuwc_highlight.png"));
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						int fnum = uc.getUpedList();
						updateFileNum(fnum);
					}
				});
			}
		});
		
//		upedComposite = new UpedComposite(this, SWT.NONE);
//		upedComposite.setBounds(0, 45, 1024, 517);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	// 更新上传成功数
	private void updateFileNum(int fnum) {
		UpedToolBarComposite upedToolBarComposite = uc.getUpedToolBarComposite();
		Composite fileNumComp = upedToolBarComposite.getFileNumComp();
		if (fileNumComp != null) {
			fileNumComp.dispose();
		}
		fileNumComp = new Composite(upedToolBarComposite, SWT.NONE);
		fileNumComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fileNumComp.setBounds(136, 5, 275, 28);
		RowLayout rlay = new RowLayout();
		fileNumComp.setLayout(rlay);
		Label nmFile = new Label(fileNumComp, SWT.NONE);
		nmFile.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		nmFile.setText(fnum+"");
		nmFile.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		nmFile.setForeground(SWTResourceManager.getColor(new RGB(58, 80, 140)));
		Label fComp2 = new Label(fileNumComp, SWT.NONE);
		fComp2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		fComp2.setText("个文件!");
		fComp2.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		fComp2.setForeground(SWTResourceManager.getColor(new RGB(58, 80, 140)));
		fileNumComp.layout();
	}

}
