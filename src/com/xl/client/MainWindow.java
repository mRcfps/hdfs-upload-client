//package com.xl.client;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.DirectoryDialog;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Text;
//
//public class MainWindow {
//	private Text text;
//	private Shell shell;
//	private Text text_log;
//	private String localSrc;
//	private String dstStr;
//	private Label label_1;
//	private Text text_dst;
//	private Button button_1;
//
//	/**
//	 * Launch the application.
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			MainWindow window = new MainWindow();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Open the window.
//	 */
//	public void open() {
//		Display display = Display.getDefault();
//		shell = new Shell(display, SWT.DIALOG_TRIM);
//		shell.setSize(619, 432);
//		shell.setText("HDFS客户端上传工具");
//
//		Label label = new Label(shell, SWT.NONE);
//		label.setBounds(22, 23, 61, 17);
//		label.setText("本地目录:");
//
//		text = new Text(shell, SWT.BORDER);
//		text.setBounds(108, 15, 300, 23);
//
//		Button button = new Button(shell, SWT.NONE);
//		button.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//
//				DirectoryDialog folderdlg = new DirectoryDialog(shell);
//				// 设置文件对话框的标题
//				folderdlg.setText("文件选择");
//				// 设置初始路径
//				folderdlg.setFilterPath("SystemDrive");
//				// 设置对话框提示文本信息
//				folderdlg.setMessage("请选择相应的文件夹");
//				// 打开文件对话框，返回选中文件夹目录
//				String selecteddir = folderdlg.open();
//				if (selecteddir == null) {
//					return;
//				} else {
//					text.setText(selecteddir);
//				}
//			}
//		});
//		button.setBounds(423, 15, 80, 27);
//		button.setText("选择目录");
//
//		button_1 = new Button(shell, SWT.NONE);
//		button_1.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//				localSrc = text.getText();
//				dstStr = text_dst.getText();
//				if (localSrc == null || "".equals(localSrc)) {
//					System.out.println("未选择目录");
//					return;
//				}
//				if (dstStr == null || "".equals(dstStr)) {
//					System.out.println("未选择服务器目录");
//					return;
//				}
//				
//				text_log.setText("");
//				Thread t1 = new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						UploadHdfs uploadHdfs = new UploadHdfs();
//						
//						Display.getDefault().asyncExec(new Runnable() {
//							@Override
//							public void run() {
//								button_1.setEnabled(false);
//							}
//						});
//						try {
//							uploadHdfs.upload(localSrc, dstStr, text_log, -1, null);
//						} catch (Exception e1) {
//							e1.printStackTrace();
//						}
//						Display.getDefault().asyncExec(new Runnable() {
//							@Override
//							public void run() {
//								button_1.setEnabled(true);
//							}
//						});
//					}
//				});
//				t1.start();
//			}
//		});
//		button_1.setBounds(509, 15, 80, 27);
//		button_1.setText("上传");
//
//		text_log = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
//		text_log.setEditable(false);
//		text_log.setBounds(22, 90, 567, 277);
//		
//		label_1 = new Label(shell, SWT.NONE);
//		label_1.setText("服务器目录:");
//		label_1.setBounds(22, 56, 80, 17);
//		
//		text_dst = new Text(shell, SWT.BORDER);
//		text_dst.setBounds(108, 53, 300, 23);
//		text_dst.setText("hdfs://172.16.102.220:8020/tmp/test");
//
//		shell.open();
//		shell.layout();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//	}
//}
