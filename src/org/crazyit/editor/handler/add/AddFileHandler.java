package org.crazyit.editor.handler.add;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.crazyit.editor.AddFrame;
import org.crazyit.editor.EditorFrame;
import org.crazyit.editor.exception.FileException;
import org.crazyit.editor.tree.ProjectTreeNode;

/**
 * 添加文件处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class AddFileHandler implements AddHandler {

	public void afterAdd(EditorFrame editorFrame, AddFrame addFrame, Object data) {
		FileOutputStream fos = null;
		try {
			//获得当前所选择的树节点
			ProjectTreeNode selectNode = editorFrame.getSelectNode();
			//获取当前选择节点所对应的文件
			File folder = selectNode.getFile();
			//如果folder不是一个目录，则用selectNode的父节点（是一个目录）作为新文件的存放目录
			if (!folder.isDirectory()) {
				ProjectTreeNode parent = (ProjectTreeNode)selectNode.getParent();
				selectNode = parent;
				folder = parent.getFile();
			}
			//创建文件，放到folder下
			File newFile = new File(folder.getAbsoluteFile() + File.separator + data);
			//如果文件已经存在，就弹出提示并返回
			if (newFile.exists()) {
				JOptionPane.showMessageDialog(addFrame, "文件已经存在");
				return;
			}
			String absolutePath = folder.getAbsolutePath();
			String subPaths = "";
			if(!absolutePath.endsWith("src")){
				subPaths = absolutePath.substring(absolutePath.indexOf("src") + 4);
			}
			String packageInfo = "";
			if(subPaths != null && !"".equals(subPaths)){
				subPaths.replace(File.separator, "\\|");
				String[] paths = subPaths.split("\\|");
				String packagePath = "";
				if(paths != null){
					for(String path : paths){
						packagePath = packagePath + path + ".";
					}
					packageInfo = "package" + " " + packagePath.substring(0,packagePath.length() - 1) + ";\n";
				}
			}
			String className = ((String)data);
			className = className.substring(0, className.indexOf("."));
			String classInfo = "public class " + data + " {\n\n";
			String mainInfo = "    public static void main(String[] args){\n\n";
			mainInfo += "    }\n"; 
			String endInfo = "}";
			newFile.createNewFile();
			
			fos = new FileOutputStream(newFile);
			fos.write(packageInfo.getBytes());
			fos.write(classInfo.getBytes());
			fos.write(mainInfo.getBytes());
			fos.write(endInfo.getBytes());
			fos.flush();
			editorFrame.reloadNode(selectNode);
			//使主编辑frame可用
			editorFrame.setEnabled(true);
			///让添加的frame不可见
			addFrame.setVisible(false);
		} catch (Exception e) {
			throw new FileException("create file error: " + e.getMessage());
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
