package org.crazyit.editor.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.event.TreeSelectionEvent;

import org.crazyit.editor.EditorFrame;

/**
 * ��Ŀ��ѡ�������
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ProjectTreeSelectionListener extends MouseAdapter {

	//�������frame
	private EditorFrame editorFrame;
	
	//���������frame(EditorFrame)��Ϊ����������������
	public ProjectTreeSelectionListener(EditorFrame editorFrame) {
		this.editorFrame = editorFrame;
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3){
			final JPopupMenu jp = new JPopupMenu();
	        jp.add("��");
	        jp.add("����");
	        jp.add("ˢ��");
	        int x = e.getX();
	        int y = e.getY();
	        jp.show(editorFrame, e.getX() + 20, e.getY() + 50);
			return;
		}
		//�õ���ǰ��ѡ��Ľڵ�
		ProjectTreeNode selectNode = this.editorFrame.getSelectNode();
		//���û��ѡ��ڵ㣬�ͷ���
		if (selectNode == null) return;
		//���ѡ�����һ��Ŀ¼������
		if (selectNode.getFile().isDirectory()) return;
		//ʹ��EditorFrame�ķ��������ļ�
		this.editorFrame.openFile(selectNode.getFile());
	}

}
