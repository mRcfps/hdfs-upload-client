package com.xl.client.composite;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.text.SimpleDateFormat;
import com.xl.client.UnzipAndDocMerge;
import com.xl.client.UploadHdfs;
import com.xl.client.bean.Evidence;
import com.xl.client.common.Global;
import com.xl.client.dao.SqlDao;
/**
 数据传输界面
   */
public class UpComposite extends Composite {
	private Text text;
	private Text localsrc;
	private Text text_evi;
	private Text text_evi_desc;
	private Text text_log;
	private Shell parent;
	private Button upbt;
	private SqlDao sqlDao = new SqlDao();
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("YYYYMMddHHmmss");
	private ProgressBar progressBar;

	private String dstStrParent = "/tmp";
	private Label label_upNum;
	private Label label_usedTime;
	private boolean finished = false;
	private String path = "";
	public static List<String> geturl = new ArrayList<String>();
	// public static List<String> geturl = new ArrayList<String>();
	// public static List<String> geturl = new ArrayList<String>();
	private static String fileType;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public UpComposite(Composite parent, int style) {
		super(parent, style);
		this.parent = (Shell) parent;
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		createContents();
	}

	protected void createContents() {
		setSize(1024, 517);
		Label typelabel = new Label(this, SWT.NONE);
		typelabel.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		typelabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		typelabel.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		typelabel.setAlignment(SWT.RIGHT);
		typelabel.setBounds(23, 44, 90, 23);
		typelabel.setText("*数据源类型");

		Label dirlabel = new Label(this, SWT.NONE);
		dirlabel.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		dirlabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		dirlabel.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		dirlabel.setAlignment(SWT.RIGHT);
		dirlabel.setBounds(23, 92, 90, 23);
		dirlabel.setText("*选择目录");

		Label evnamelabel = new Label(this, SWT.NONE);
		evnamelabel.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		evnamelabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		evnamelabel.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		evnamelabel.setAlignment(SWT.RIGHT);
		evnamelabel.setBounds(23, 142, 90, 23);
		evnamelabel.setText("*数据名称");

		Label evdesclabel = new Label(this, SWT.NONE);
		evdesclabel.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		evdesclabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		evdesclabel.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		evdesclabel.setAlignment(SWT.RIGHT);
		evdesclabel.setBounds(23, 192, 90, 23);
		evdesclabel.setText("数据描述");

		Label datasources = new Label(this, SWT.NONE);
		datasources.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		datasources.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		datasources.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		datasources.setAlignment(SWT.RIGHT);
		datasources.setBounds(23, 242, 90, 23);
		datasources.setText("数据来源");

		// text = new Text(this, SWT.BORDER);
		// text.setBounds(122, 41, 358, 34);

		final Combo dataType = new Combo(this, SWT.READ_ONLY);
		dataType.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		dataType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		dataType.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		dataType.setBounds(122, 41, 358, 100);
		dataType.add("电子邮件");
		dataType.add("综合文档");
		dataType.add("电子话单");
		// dataType.add("手机取证");
		dataType.add("图片资料");
		// dataType.add("黑客数据");
		// dataType.add("混合数据");

		final Combo dataSources = new Combo(this, SWT.READ_ONLY);
		dataSources.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		dataSources.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		dataSources.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		dataSources.setBounds(122, 241, 358, 50);
		dataSources.add("移动设备");
		dataSources.add("通信运营");
		dataSources.add("社交网站");
		dataSources.add("音频视频");
		dataSources.add("采集数据");
		dataSources.add("口供资料");
		dataSources.add("其他来源");
		// System.out.println(dataType.getText());
		localsrc = new Text(this, SWT.BORDER);
		localsrc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DirectoryDialog folderdlg = new DirectoryDialog(parent);
				// 设置文件对话框的标题
				folderdlg.setText("文件选择");
				// 设置初始路径
				folderdlg.setFilterPath(".");
				// 设置对话框提示文本信息
				folderdlg.setMessage("请选择相应的文件夹");
				// 打开文件对话框，返回选中文件夹目录
				String selecteddir = folderdlg.open();
				if (selecteddir == null) {
					return;
				} else {
					localsrc.setText(selecteddir);
				}
			}
		});
		localsrc.setBounds(122, 91, 358, 28);

		text_evi = new Text(this, SWT.BORDER);
		text_evi.setBounds(122, 141, 358, 28);

		text_evi_desc = new Text(this, SWT.BORDER);
		text_evi_desc.setBounds(122, 191, 358, 28);

		upbt = new Button(this, SWT.NONE);
		final UpComposite tmp = this;
		upbt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				final String type;// 获取数据类型
				if (dataType.getText() == null || "".equals(dataType.getText())) {
					JOptionPane.showMessageDialog(null, "请选择上传数据类型", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if ("电子邮件".equals(dataType.getText())) {
					type = "1";
					fileType = dataType.getText();
				} else if ("综合文档".equals(dataType.getText())) {
					type = "2";
					fileType = dataType.getText();
				} else if ("电子话单".equals(dataType.getText())) {
					type = "3";
					fileType = dataType.getText();
				}
//				else if ("手机取证".equals(dataType.getText())) {
//					type = "4";
//				} 
				else if ("图片资料".equals(dataType.getText())) {
					type = "6";
					fileType = dataType.getText();
				} 
//				else if ("黑客数据".equals(dataType.getText())) {
//					type = "5";
//				}
				// else if ("混合数据".equals(dataType.getText())) {
				// type = "7";
				// }
				else {
					type = "-1";
				}
//				System.out.println("获取数据类型" + type);
//				System.out.println("---------11-------" + dataType.getText());
				final Integer dataSourcesType;
				if ("移动设备".equals(dataSources.getText())) {
					dataSourcesType = 1;
				} else if ("通信运营".equals(dataSources.getText())) {
					dataSourcesType = 2;
				} else if ("社交网站".equals(dataSources.getText())) {
					dataSourcesType = 3;
				} else if ("手音频视频".equals(dataSources.getText())) {
					dataSourcesType = 4;
				} else if ("采集数据".equals(dataSources.getText())) {
					dataSourcesType = 5;
				} else if ("口供资料".equals(dataSources.getText())) {
					dataSourcesType = 6;
				} else if ("其他来源".equals(dataSources.getText())) {
					dataSourcesType = 7;
				} else {
					dataSourcesType = -1;
				}
//				System.out.println("获取数据来源" + dataSourcesType);

				final String loc = localsrc.getText();// 获取上传文件夹路径
				// 检测上传文件类型
				File file = new File(loc);
				JOptionPane.showMessageDialog(null, "文件读取中，请耐心等待", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				getFiles(file);
				JOptionPane.showMessageDialog(null, "文件读取完毕，开始上传", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				// int error=0;
				// String s ="";
				// for (int i = 0; i < geturl.size(); i++) {
				// //System.out.println("获取集合数量"+geturl.size());
				// String[]geturl2 = geturl.get(i).split("\\.");
				// String geturl3 = geturl2[geturl2.length-1];
				// if ("电子邮件".equals(dataType.getText())) {
				// if (!("eml").equalsIgnoreCase(geturl3)) {
				// error=1;
				// s+=geturl.get(i)+"\r\n";
				// }
				// }
				// if ("综合文档".equals(dataType.getText())) {
				// if (!("docx").equalsIgnoreCase(geturl3) &&
				// !("doc").equalsIgnoreCase(geturl3) &&
				// !("pdf").equalsIgnoreCase(geturl3)
				// && !("xlsx").equalsIgnoreCase(geturl3) &&
				// !("pptx").equalsIgnoreCase(geturl3) &&
				// !("txt").equalsIgnoreCase(geturl3)
				// && !("xls").equalsIgnoreCase(geturl3) &&
				// !("ppt").equalsIgnoreCase(geturl3)) {
				// error=1;
				// s+=geturl.get(i)+"\r\n";
				// }
				// }
				// if ("电子话单".equals(dataType.getText())) {
				// if (!("xls").equalsIgnoreCase(geturl3) &&
				// !("xlsx").equalsIgnoreCase(geturl3)) {
				// error=1;
				// s+=geturl.get(i)+"\r\n";
				// }
				// }
				// if ("手机取证".equals(dataType.getText())) {
				// if (!(".html").equalsIgnoreCase(geturl3)) {
				// error=1;
				// s+=geturl.get(i)+"\r\n";
				// }
				// }
				// if ("黑客数据".equals(dataType.getText())) {
				// if (!("txt").equalsIgnoreCase(geturl3) &&
				// !("csv").equalsIgnoreCase(geturl3)) {
				// error=1;
				// s+=geturl.get(i)+"\r\n";
				// }
				// }
				// if ("图片资料".equals(dataType.getText())) {
				// if (!("bmp").equalsIgnoreCase(geturl3) &&
				// !("jpg").equalsIgnoreCase(geturl3) &&
				// !("jpeg").equalsIgnoreCase(geturl3)
				// && !("gif").equalsIgnoreCase(geturl3) &&
				// !("png").equalsIgnoreCase(geturl3)) {
				// error=1;
				// s+=geturl.get(i)+"\r\n";
				// }
				// }
				// }
//				if (file.isEmpty()) {
//					JOptionPane.showMessageDialog(null, "请勿上传空文件夹", "提示", JOptionPane.INFORMATION_MESSAGE);
//					return;
//				}
				// geturl.removeAll(geturl);
				// if(error==1){
				// JOptionPane.showMessageDialog(null, "上传数据文件夹中有文件与数据源类型不一致",
				// "提示",
				// JOptionPane.INFORMATION_MESSAGE);
				// JOptionPane.showMessageDialog(null,"不一致的文件名："+s, "提示",
				// JOptionPane.INFORMATION_MESSAGE);
				// return;
				// }
				if (loc == null || "".equals(loc)) {
					JOptionPane.showMessageDialog(null, "请选择本地上传文件目录", "提示", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("未选择目录");
					return;
				}
				 File fi =new File(loc.toString());
			  	   	File fi2 =new File(fi.toString().substring(0,2)+"WRONGFILE");
					JOptionPane.showMessageDialog(null, "若文件夹中有其他类型数据，该数据已移动到"+fi2+"目录下", "提示", JOptionPane.INFORMATION_MESSAGE);
				final Date now = new Date();
				final String addTime = sdf.format(now);
				final Evidence evi = new Evidence();
				final String evName = text_evi.getText();
				if (evName == null || "".equals(evName)) {
					JOptionPane.showMessageDialog(null, "请填写数据名称", "提示", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("未填写数据名称");
					return;
				}
				final String comment = text_evi_desc.getText();

				finished = false;
				text_log.setText("");
				progressBar.setSelection(0);
				label_upNum.setText("0/0");
				label_usedTime.setText("00:00:00");
				final long startTime = System.currentTimeMillis();
				Thread js = new Thread(new Runnable() {
					@Override
					public void run() {
						while (!finished) {
							long currTime = System.currentTimeMillis();
							long elapsed = currTime - startTime;
							final String format = format(elapsed);
							Display.getDefault().asyncExec(new Runnable() {
								@Override
								public void run() {
									label_usedTime.setText(format);
								}
							});
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});
				js.start();
				Thread t1 = new Thread(new Runnable() {

					@Override
					public void run() {
						dstStrParent = "/tmp/" + evName;
						String dirPath = dstStrParent + "/" + sdf2.format(now) + "_"//
								+ UUID.randomUUID().toString().replace("-", "").substring(0, 4);
						String[] dirPathOBO = dirPath.split("/");
						String PathOBO = "/" + dirPathOBO[dirPathOBO.length - 1];
						final String dstStr = Global.HDFS_UPLOADPATH + dirPath;

						// 对证据名编码
						// String uEvName = null;
						// try {
						// uEvName = URLEncoder.encode(evName, "UTF-8");
						// uEvName = uEvName.replace("%", "X_L");
						// } catch (Exception e) {
						// }
						evi.setEvType(Integer.valueOf(type));
						evi.setEvName(evName);
						evi.setComment(comment);
						evi.setEvAdmin("zhangsan");
						evi.setAddTime(addTime);
						evi.setFinished("false");
						evi.setDirPath(PathOBO);// 1.1版本存入数据库路径
						evi.setCurrFlag("1");
						evi.setUptype(2);
						evi.setDataTypes(dataSourcesType);
						Integer eviKey = sqlDao.setBeanToMysql(evi);
						System.out.println("asdfasdf"+eviKey);
						// System.out.println("----------------"+dataType.getText());

//						Display.getDefault().asyncExec(new Runnable() {
//							@Override
//							public void run() {
//
//								if ("混合数据".equals(dataType.getText())) {
//									System.out.println(loc);
//									path = UnzipAndDocMerge.getResources(loc);
//									System.out.println(path);
//								} else {
//									path = loc;
//								}
//							}
//						});

						// if ("混合数据".equals(dataType.getText())) {
						// System.out.println(loc);
						// path = UnzipAndDocMerge.getResources(loc);
						// System.out.println(path);
						// }else{
						// path=loc;
						// }
						UploadHdfs uploadHdfs = new UploadHdfs();
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								upbt.setEnabled(false);
							}
						});
						try {

							uploadHdfs.upload(loc, dstStr, eviKey, tmp);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						finished = true;
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								upbt.setEnabled(true);
							}
						});
					}
				});
				t1.start();
			}
		});
		upbt.setBounds(122, 291, 358, 34);
		upbt.setImage(SWTResourceManager.getImage(UpComposite.class, "/images/upbt_highlight.png"));

		text_log = new Text(this, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		text_log.setForeground(SWTResourceManager.getColor(new RGB(85, 85, 85)));
		text_log.setBounds(552, 41, 440, 380);

		Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(18, 339, 476, 2);

		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setBounds(32, 380, 448, 20);

		Label label_1 = new Label(this, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(new RGB(153, 153, 153)));
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setBounds(36, 410, 61, 17);
		label_1.setText("已上传数:");

		Label label_2 = new Label(this, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(new RGB(153, 153, 153)));
		label_2.setText("用时:");
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_2.setBounds(393, 410, 27, 17);

		label_upNum = new Label(this, SWT.NONE);
		label_upNum.setText("0/0");
		label_upNum.setForeground(SWTResourceManager.getColor(153, 153, 153));
		label_upNum.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_upNum.setBounds(100, 410, 108, 17);

		label_usedTime = new Label(this, SWT.NONE);
		label_usedTime.setText("00:00:00");
		label_usedTime.setForeground(SWTResourceManager.getColor(153, 153, 153));
		label_usedTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_usedTime.setBounds(426, 410, 48, 17);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 将毫秒数格式化
	private String format(long elapsed) {
		int hour, minute, second;

		elapsed = elapsed / 1000;

		second = (int) (elapsed % 60);
		elapsed = elapsed / 60;

		minute = (int) (elapsed % 60);
		elapsed = elapsed / 60;

		hour = (int) (elapsed % 60);

		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	public Text getText_log() {
		return text_log;
	}

	public void setText_log(Text text_log) {
		this.text_log = text_log;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public Label getLabel_upNum() {
		return label_upNum;
	}

	public void setLabel_upNum(Label label_upNum) {
		this.label_upNum = label_upNum;
	}

	public Label getLabel_usedTime() {
		return label_usedTime;
	}

	public void setLabel_usedTime(Label label_usedTime) {
		this.label_usedTime = label_usedTime;
	}

	public static void getFiles(File dir) {
		// 如果当前文件或目录存在
		if (dir.exists()) {
			// 如果是目录，则：
			if (dir.isDirectory()) {
				// 打印当前目录的路径
				// System.out.println(dir);
				// 获取该目录下的所有文件和目录组成的File数组
				File[] files = dir.listFiles();
				// 递归遍历每一个子文件
				for (File file : files) {
					getFiles(file);
				}
			}
			// 如果是文件，则打印该文件路径及名称
			else {
//				System.out.println("1" + dir);
//				System.out.println("2" + dir.toString());
				// geturl.add(dir.toString());
				if ("电子邮件".equals(fileType)) {
					if (!dir.toString().endsWith("eml")) {
						try{
					    	   File afile =new File(dir.toString());
					    	   File f =new File(afile.toString().substring(0,2)+"WRONGFILE");
					    	   if(!f.exists()){
					    			  f.mkdir();
					    		   }
					    	   afile.renameTo(new File(f.toString()+"\\" + afile.getName()));
					    	}catch(Exception e){
					    		e.printStackTrace();
					    	}
						// dir.delete();
//						JOptionPane.showMessageDialog(null, "上传数据文件夹中有文件与数据源类型不一致", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//						JOptionPane.showMessageDialog(null, "不一致的文件名：" + dir.toString(), "提示",
//								JOptionPane.INFORMATION_MESSAGE);
						//return;
					}
				}
				if ("综合文档".equals(fileType)) {
					String dir2 = dir.toString().substring(dir.toString().lastIndexOf("."), dir.toString().length());
					System.out.println(dir2);
					if (!(".doc").equalsIgnoreCase(dir2) && !(".docx").equalsIgnoreCase(dir2)
							&& !(".pdf").equalsIgnoreCase(dir2) && !(".xls").equalsIgnoreCase(dir2)
							&& !(".xlsx").equalsIgnoreCase(dir2) && !(".ppt").equalsIgnoreCase(dir2)
							&& !(".pptx").equalsIgnoreCase(dir2) && !(".txt").equalsIgnoreCase(dir2)) {
						// dir.delete();
						try{
					    	   File afile =new File(dir.toString());
					    	   File f =new File(afile.toString().substring(0,2)+"WRONGFILE");
					    	   if(!f.exists()){
					    			  f.mkdir();
					    		   }
					    	   afile.renameTo(new File(f.toString()+"\\" + afile.getName()));
					    	}catch(Exception e){
					    		e.printStackTrace();
					    	}
//						JOptionPane.showMessageDialog(null, "上传数据文件夹中有文件与数据源类型不一致", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//						JOptionPane.showMessageDialog(null, "不一致的文件名：" + dir.toString(), "提示",
//								JOptionPane.INFORMATION_MESSAGE);
						//return;
					}
				}
				if ("电子话单".equals(fileType)) {
					String dir3 = dir.toString().substring(dir.toString().lastIndexOf("."), dir.toString().length());
					System.out.println(dir3);
					if (!(".xls").equalsIgnoreCase(dir3) && !(".xlsx").equalsIgnoreCase(dir3)) {
						// dir.delete();
						try{
					    	   File afile =new File(dir.toString());
					    	   File f =new File(afile.toString().substring(0,2)+"WRONGFILE");
					    	   if(!f.exists()){
					    			  f.mkdir();
					    		   }
					    	   afile.renameTo(new File(f.toString()+"\\" + afile.getName()));
					    	}catch(Exception e){
					    		e.printStackTrace();
					    	}
//						JOptionPane.showMessageDialog(null, "上传数据文件夹中有文件与数据源类型不一致", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//						JOptionPane.showMessageDialog(null, "不一致的文件名：" + dir.toString(), "提示",
//								JOptionPane.INFORMATION_MESSAGE);
						//return;
					}
				}
				if ("图片资料".equals(fileType)) {
					String dir4 = dir.toString().substring(dir.toString().lastIndexOf("."), dir.toString().length());
					System.out.println(dir4);
					if (!(".bmp").equalsIgnoreCase(dir4) && !(".jpg").equalsIgnoreCase(dir4)
							&& !(".jpeg").equalsIgnoreCase(dir4) && !(".gif").equalsIgnoreCase(dir4)
							&& !(".png").equalsIgnoreCase(dir4)) {
						// dir.delete();
						try{
					    	   File afile =new File(dir.toString());
					    	   File f =new File(afile.toString().substring(0,2)+"WRONGFILE");
					    	   if(!f.exists()){
					    			  f.mkdir();
					    		   }
					    	   afile.renameTo(new File(f.toString()+"\\" + afile.getName()));
					    	}catch(Exception e){
					    		e.printStackTrace();
					    	}
//						JOptionPane.showMessageDialog(null, "上传数据文件夹中有文件与数据源类型不一致", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//						JOptionPane.showMessageDialog(null, "不一致的文件名：" + dir.toString(), "提示",
//								JOptionPane.INFORMATION_MESSAGE);
						//return;
					}
				}
			}
		}
	}
}
