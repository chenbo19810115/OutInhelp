import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainClass {

	private static JFrame f = null;
	private static TrayIcon trayIcon = null;
	static SystemTray tray = SystemTray.getSystemTray();
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private DefaultTableModel model = null;
	
    private static SimpleDateFormat df = null;
    private static BackUtil backUtil = null;
    private static String BACKUP_DIR = null;
	private final String DB_USER = "root";
	private final String DB_PWD = "root";
	private final String DB_NAME = "help";
	
	static{
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BACKUP_DIR = System.getProperty("user.dir");
		backUtil = new BackUtil();
	}
	
	public String BackdataInfo()
	{
		String filename = "backup-" + df.format(new Date()) + ".sql";
		
		String filename1 = filename.replace(" ", "");
		
		String filename2 = filename1.replace(":", "");
		
		String savePath = BACKUP_DIR + "\\" + filename2;
		
		String[] execCMD = new String[] {"mysqldump", "-u" + DB_USER, "-p" + DB_PWD, DB_NAME,  
		            "-r", savePath}; 
		
		try {
			
			Process process = Runtime.getRuntime().exec(execCMD);
			
			try {
				int processComplete = process.waitFor();
				if (processComplete == 0) {  
					backUtil.backdataInfo(savePath);
					return savePath;
				} else {
					return null;
				} 
			}
			catch(Exception e)
			{
				return null;
			}
		} catch (IOException e) {
			return null;
		}
		
	}
	
	public MainClass() {
		// TODO Auto-generated method stub
		f = new JFrame("MMDataBaseServer");
		f.setSize(800, 800);
		//��λ
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(screenSize.width - 800, 0);
		f.setLayout(new FlowLayout(0,10,10));
		// ˢ�°�ť
		JButton freshBtn = new JButton("��ȡ�������ݱ����ļ��б�");
		Dimension preferredSize = new Dimension(200,40);//���óߴ�
		freshBtn.setPreferredSize(preferredSize );
		freshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//�����Զ������⣬�����ֶ����ݣ������ڴ˴����ˢ������
				model.setRowCount(0);
				//��ʼ������
				List<BackEx> backls = backUtil.getBackList();
				for(BackEx backEx: backls)
				{
					model.addRow(new Object[] { backEx.getBackfile() });
				}
			}
		});
		f.getContentPane().add(freshBtn);
		
		// �ָ���ť
		JButton reBtn = new JButton("�ָ�ѡ�������");
		reBtn.setPreferredSize(preferredSize);
		reBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = (String) jTable.getValueAt(jTable.getSelectedRow(), jTable.getSelectedColumn());
				restoreData(filename);
			}
		});
		f.getContentPane().add(reBtn);
		
		// ���
		model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] { "�����ļ��б�" });
		//��ʼ������
		List<BackEx> backls = backUtil.getBackList();
		for(BackEx backEx: backls)
		{
			model.addRow(new Object[] { backEx.getBackfile() });
		}

		jTable = new JTable(model) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int ColIndex) {
				return false;
			}
		};

		jScrollPane = new JScrollPane(jTable);// Ϊ������ӹ�����
		Dimension jScrollPreferredSize = new Dimension(800,700);//���óߴ�
        jScrollPane.setPreferredSize(jScrollPreferredSize);
		f.getContentPane().add(jScrollPane);

		f.setVisible(true);

		f.setResizable(false);

		if(jTable.getRowCount() > 0)
		{
			jTable.setRowSelectionInterval(0, 0);
		}

		f.addWindowListener(new WindowAdapter() { // ���ڹر��¼�
			public void windowClosing(WindowEvent e) {
				f.setVisible(false);
				miniTray();
			};

			public void windowIconified(WindowEvent e) { // ������С���¼�
				f.setVisible(false);
				miniTray();
			}

		});
  
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date time = calendar.getTime();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		  public void run() {
			  String backfile = BackdataInfo();
			  model.addRow(new Object[] { backfile });
			  jTable.updateUI();
		  }
		}, time, 1000 * 60 * 60 * 24);
		
	}

	private static void miniTray() { // ������С��������������

		String dir = System.getProperty("user.dir") + "\\images\\MM.jpg";
		
		ImageIcon trayImg = new ImageIcon(dir);// ����ͼ��

		trayIcon = new TrayIcon(trayImg.getImage(), "MMDataBaseServer", new PopupMenu());
		trayIcon.setImageAutoSize(true);

		trayIcon.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 1) {// ���� 1 ˫�� 2

					tray.remove(trayIcon);
					f.setVisible(true);
					f.setExtendedState(JFrame.NORMAL);
					f.toFront();
					Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
					f.setLocation(screenSize.width - 800, 0);
				}

			}

		});

		try {

			tray.add(trayIcon);

		} catch (AWTException e1) {
			e1.printStackTrace();
		}

	}
	
	public boolean restoreData(String filename)
	{
		boolean bRet = true;
//		String[] execCMD = new String[] {"mysql", "-u" + DB_USER, "-p" + DB_PWD, DB_NAME,  
//	            "<", filename}; 
		
		String strcmd = "cmd /c start RestorData.bat " + "\"" + filename + "\"";
		
		try {
			Process process = Runtime.getRuntime().exec(strcmd);
			try {
				int processComplete = process.waitFor();
				if (processComplete == 0) {  
					bRet = true;
					process.destroy();
					process = null;
					killProcess();
				} else {
					bRet = false;
					process.destroy();
					process = null;
					killProcess();
				} 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				process.destroy();
				process = null;
				killProcess();
				e.printStackTrace();
				bRet = false;
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
			bRet = false;
		}
		
		return bRet;
		
	}

	public static void main(String[] args) {
		new MainClass();
	}
	
	public void killProcess(){
		  Runtime rt = Runtime.getRuntime();
		  try {
		   rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }

}
