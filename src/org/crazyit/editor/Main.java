package org.crazyit.editor;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.crazyit.editor.tree.TreeCreator;
import org.crazyit.editor.tree.TreeCreatorImpl;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;

/**
 * 程序入口类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version 1.0 <br/>
 *          网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> <br>
 *          Copyright (C), 2009-2010, yangenxiong <br>
 *          This program is protected by copyright laws.
 */
public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			UIManager.put("swing.boldMetal", false);
			if (System.getProperty("substancelaf.useDecorations") == null) {
				// 使得标题栏和对话框跟随外观变化
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
			}
			System.setProperty("sun.awt.noerasebackground", "true");
			// 设置当前的主题风格，同样还可以设置当前的按钮形状，水印风格等等
			SubstanceLookAndFeel.setCurrentTheme(new SubstanceLightAquaTheme());
		} catch (Exception e) {
			System.err.println("Oops!  Something went wrong!");
		}
		TreeCreator treeCreator = new TreeCreatorImpl();
		// 创建EditorFrame，暂时不用设置可见
		EditorFrame editorFrame = new EditorFrame("Java - editor", treeCreator);
		// 将editorFrame对象作为SpaceFrame的构造参数
		SpaceFrame spaceFrame = new SpaceFrame(editorFrame);
		// 让SpaceFrame可见
		spaceFrame.setVisible(true);
	}
}
