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
 * ����ļ�������
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class AddFileHandler implements AddHandler {

	public void afterAdd(EditorFrame editorFrame, AddFrame addFrame, Object data) {
		FileOutputStream fos = null;
		try {
			//��õ�ǰ��ѡ������ڵ�
			ProjectTreeNode selectNode = editorFrame.getSelectNode();
			//��ȡ��ǰѡ��ڵ�����Ӧ���ļ�
			File folder = selectNode.getFile();
			//���folder����һ��Ŀ¼������selectNode�ĸ��ڵ㣨��һ��Ŀ¼����Ϊ���ļ��Ĵ��Ŀ¼
			if (!folder.isDirectory()) {
				ProjectTreeNode parent = (ProjectTreeNode)selectNode.getParent();
				selectNode = parent;
				folder = parent.getFile();
			}
			//�����ļ����ŵ�folder��
			File newFile = new File(folder.getAbsoluteFile() + File.separator + data);
			//����ļ��Ѿ����ڣ��͵�����ʾ������
			if (newFile.exists()) {
				JOptionPane.showMessageDialog(addFrame, "�ļ��Ѿ�����");
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
			//ʹ���༭frame����
			editorFrame.setEnabled(true);
			///����ӵ�frame���ɼ�
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
