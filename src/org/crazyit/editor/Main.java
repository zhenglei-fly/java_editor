package org.crazyit.editor;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.crazyit.editor.tree.TreeCreator;
import org.crazyit.editor.tree.TreeCreatorImpl;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;

/**
 * ���������
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version 1.0 <br/>
 *          ��վ: <a href="http://www.crazyit.org">���Java����</a> <br>
 *          Copyright (C), 2009-2010, yangenxiong <br>
 *          This program is protected by copyright laws.
 */
public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			UIManager.put("swing.boldMetal", false);
			if (System.getProperty("substancelaf.useDecorations") == null) {
				// ʹ�ñ������ͶԻ��������۱仯
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
			}
			System.setProperty("sun.awt.noerasebackground", "true");
			// ���õ�ǰ��������ͬ�����������õ�ǰ�İ�ť��״��ˮӡ���ȵ�
			SubstanceLookAndFeel.setCurrentTheme(new SubstanceLightAquaTheme());
		} catch (Exception e) {
			System.err.println("Oops!  Something went wrong!");
		}
		TreeCreator treeCreator = new TreeCreatorImpl();
		// ����EditorFrame����ʱ�������ÿɼ�
		EditorFrame editorFrame = new EditorFrame("Java - editor", treeCreator);
		// ��editorFrame������ΪSpaceFrame�Ĺ������
		SpaceFrame spaceFrame = new SpaceFrame(editorFrame);
		// ��SpaceFrame�ɼ�
		spaceFrame.setVisible(true);
	}
}
