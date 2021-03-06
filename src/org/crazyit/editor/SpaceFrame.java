package org.crazyit.editor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.crazyit.editor.commons.WorkSpace;

/**
 * 工作空间界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class SpaceFrame extends JFrame {

	private JPanel mainPanel;

	private JPanel infoPanel;
	private JLabel infoLabel;
	private JLabel infoLabel2;
	private JLabel infoLabel3;
	
	private JPanel nullPanel;
	
	private JPanel chosePanel;
	
	private JLabel workTextLabel;
	
	//工作空间中显示用户选择文件目录的JTextField
	private JTextField pathText;
	
	private JButton choseButton;
	
	
	private JPanel buttonPanel;
	
	//工作空间中的确定按钮
	private JButton confirmButton;
	
	private JButton cancelButton;
	
	private SpaceChooser chooser;
	
	//用户选择的文件目录对象
	private File folder;
	
	public SpaceFrame(EditorFrame editorFrame) {
		mainPanel = new JPanel();
		infoPanel = new JPanel();
		infoLabel = new JLabel("Select a workspace");
		infoLabel2 = new JLabel("Java-editor stores your projects in a folder called a workspace.");
		infoLabel3 = new JLabel("Choose a workspace folder to use for this session.");
		nullPanel = new JPanel();
		nullPanel.setPreferredSize(new Dimension(300, 50));//关键代码,设置JPanel的大小
		chosePanel = new JPanel();
		workTextLabel = new JLabel("Workspace: ");
		pathText = new JTextField("", 40);
		choseButton = new JButton("Browse...");
		buttonPanel = new JPanel();
		confirmButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		chooser = new SpaceChooser(this);
		
		infoPanel.setLayout(new GridLayout(3,1));
		infoPanel.add(infoLabel);
		infoPanel.add(infoLabel2);
		infoPanel.add(infoLabel3);
		infoPanel.setSize(50,200);
		//设置主Panel的布局
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(infoPanel);
		mainPanel.add(nullPanel);
		//设置选择区的布局
		chosePanel.setLayout(new BoxLayout(chosePanel, BoxLayout.X_AXIS));
		choseButton.addActionListener(new ChoseButtonListener(chooser));
		pathText.setEditable(false);
		chosePanel.add(workTextLabel);
		chosePanel.add(pathText);
		chosePanel.add(choseButton);
		mainPanel.add(chosePanel);
		confirmButton.setEnabled(false);
		
		FileInputStream fis = null;
		String workSpacePath = ""; 
		try{
			File importantFile = new File("important");
			if(importantFile.exists()){
				String p = importantFile.getAbsolutePath();
				fis = new FileInputStream(importantFile);
				byte[] pathByte = new byte[(int) importantFile.length()];
				fis.read(pathByte);
				workSpacePath = new String(pathByte);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		if(workSpacePath != null && !"".equals(workSpacePath)){
			pathText.setText(workSpacePath);
			confirmButton.setEnabled(true);
			//获取用户选择的文件
			File folder = new File(workSpacePath);
			this.setFolder(folder); 
		}
		//为确定按钮添加确定的事件, 即创建一个WorkSpace对象
		confirmButton.addActionListener(new ConfirmButtonListener(this, editorFrame));
		buttonPanel.add(confirmButton);
		buttonPanel.add(new Label("    "));
		buttonPanel.add(cancelButton);
		//为取消按钮添加退出事件
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mainPanel.add(buttonPanel);
		add(mainPanel);
		this.setTitle("Workspace Launcher");
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300, 200);
		setResizable(false);
	}

	public File getFolder() {
		return folder;
	}

	public void setFolder(File folder) {
		this.folder = folder;
	}
		
	public JTextField getPathText() {
		return pathText;
	}

	public JButton getConfirmButton() {
		return confirmButton;
	}
}

/**
 * 确定按钮的监听器
 * @author hp
 *
 */
class ConfirmButtonListener implements ActionListener {
	
	private SpaceFrame spaceFrame;
	
	private EditorFrame editorFrame;
	
	public ConfirmButtonListener(SpaceFrame spaceFrame, EditorFrame editorFrame) {
		this.spaceFrame = spaceFrame;
		this.editorFrame = editorFrame;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		//调EditorFrame的initFrame方法初始化界面
		editorFrame.initFrame(new WorkSpace(spaceFrame.getFolder(), editorFrame));
		//将EditorFrame设为可见
		editorFrame.setVisible(true);
		editorFrame.setSize(900, 600);
		//让工作选择空间界面不可见
		spaceFrame.setVisible(false);
		FileOutputStream fos = null;
		try{
			File file = spaceFrame.getFolder();
			if(file != null){
				String workSpacePath = "";
				workSpacePath = file.getAbsolutePath();
				File importantFile = new File("important");
				if(!importantFile.exists()){
					importantFile.createNewFile();
				}
				if(workSpacePath != null && !"".equals(workSpacePath)){
					fos = new FileOutputStream(importantFile);
					fos.write(workSpacePath.getBytes());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

/**
 * 选择按钮的监听器
 * @author hp
 *
 */
class ChoseButtonListener implements ActionListener {

	private JFileChooser chooser;
	
	public ChoseButtonListener(JFileChooser chooser) {
		this.chooser = chooser;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showOpenDialog(null);
	}
	
}

/**
 * 文件选择器
 * @author hp
 *
 */
class SpaceChooser extends JFileChooser {
	
	private SpaceFrame spaceFrame;
	
	//需要将SpaceFrame作为构造参数
	public SpaceChooser(SpaceFrame spaceFrame) {
		//设置选择器打开时的目录
		super("/");
		this.spaceFrame = spaceFrame;
	}
	
	//重写父类的选择文件方法
	public void approveSelection() {
		//获取用户选择的文件
		File folder = getSelectedFile();
		//设置SpaceFrame的属性folder的值
		spaceFrame.setFolder(folder);
		//设置SpaceFrame文本框
		spaceFrame.getPathText().setText(folder.getAbsolutePath());
		//设置确定按钮可用
		spaceFrame.getConfirmButton().setEnabled(true);
		//调用父类的选择文件方法
		super.approveSelection();
	}
}
