package org.crazyit.editor.tree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.TreeSelectionEvent;

import org.crazyit.editor.EditorFrame;

/**
 * 项目树选择监听器
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version 1.0 <br/>
 *          网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> <br>
 *          Copyright (C), 2009-2010, yangenxiong <br>
 *          This program is protected by copyright laws.
 */
public class ProjectTreeSelectionListener extends MouseAdapter {

	// 主界面的frame
	private EditorFrame editorFrame;

	// 将主界面的frame(EditorFrame)作为构造参数传入监听器
	public ProjectTreeSelectionListener(EditorFrame editorFrame) {
		this.editorFrame = editorFrame;
	}

	public void mousePressed(MouseEvent e) {
		// 得到当前所选择的节点
		ProjectTreeNode selectNode = this.editorFrame.getSelectNode();
		// 如果没有选择节点，就返回
		if (selectNode == null)
			return;
		// 如果选择的是一个目录，返回
		if (selectNode.getFile().isDirectory())
			return;
		
		if (e.getButton() == MouseEvent.BUTTON3) {
			final EditorFrame feditorFrame = this.editorFrame;
			final ProjectTreeNode fselectNode = selectNode;
			JPopupMenu jp = new JPopupMenu();
			JMenuItem item1 = new JMenuItem("打开");
			item1.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					//System.out.println(id);// 输出按钮的文本
					// 使用EditorFrame的方法来打开文件
					feditorFrame.openFile(fselectNode.getFile());
				}
			});
			JMenuItem item2 = new JMenuItem("保存");
			item1.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					//System.out.println(id);// 输出按钮的文本
				}
			});
			JMenuItem item3 = new JMenuItem("删除");
			item1.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					//System.out.println(id);// 输出按钮的文本
				}
			});
			jp.add(item1);
			jp.add(item2);
			jp.add(item3);
			jp.show(editorFrame, e.getX() + 20, e.getY() + 50);
			return;
		}
		// 使用EditorFrame的方法来打开文件
		this.editorFrame.openFile(selectNode.getFile());
	}

}
