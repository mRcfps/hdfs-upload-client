package com.xl.client.shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.xl.client.composite.SwitchBarsComposite;
import com.xl.client.composite.UpComposite;
import com.xl.client.composite.UpedComposite;

public class MainShell extends Shell {

	private SwitchBarsComposite switchBarsComposite;
	private boolean isDraw = false;
	private int xx;
	private int yy;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			MainShell shell = new MainShell(display);
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
	public MainShell(Display display) {
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
		cp.setBackgroundImage(SWTResourceManager.getImage(MainShell.class, "/images/title.png"));

		Button minibt = new Button(cp, SWT.NONE);
		minibt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().setMinimized(true);
			}
		});
		minibt.setImage(SWTResourceManager.getImage(MainShell.class, "/images/mini.jpg"));
		minibt.setBounds(966, 4, 25, 18);

		Button closebt = new Button(cp, SWT.NONE);
		closebt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().close();
			}
		});
		closebt.setImage(SWTResourceManager.getImage(MainShell.class, "/images/close.jpg"));
		closebt.setBounds(997, 5, 17, 17);
		UpComposite upc = new UpComposite(this, SWT.NONE);
		upc.setBounds(0, 132, 1024, 518);
		upc.setVisible(true);
		UpedComposite uc = new UpedComposite(this, SWT.NONE);
		uc.setBounds(0, 132, 1024, 518);
		uc.setVisible(false);

		switchBarsComposite = new SwitchBarsComposite(this, SWT.NONE, upc, uc);
		switchBarsComposite.setBounds(0, 88, 1024, 44);
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
