/*
 * Copyright (c) 2014, jasper
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package jarbundler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author jasper
 */
public class OpenAndSaveFileManager {
	
	public static void saveFile(Frame frame){
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setFileFilter(new FileFilter() {
				
				@Override
				public boolean accept(File f) {
					return f.getName().endsWith(".bundleconfig") || f.isDirectory();
				}
				
				@Override
				public String getDescription() {
					return ".bundleconfig files";
				}
			});
			fileChooser.showSaveDialog(frame);
			
			File newFile = fileChooser.getSelectedFile();
			
			if (!newFile.getName().endsWith(".bundleconfig")){
				newFile = new File(newFile.getAbsolutePath() + ".bundleconfig");
			}
			
			if(!newFile.exists()){newFile.createNewFile();}
			else{JOptionPane.showMessageDialog(null, newFile.getAbsolutePath() + " already exists!");
			return;
			}
			
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
				writer.write("DO NOT EDIT THIS FILE" + Frame.newline + 
						frame.outputDirectoryField.getText() + Frame.newline +
						frame.jarFileField.getText() + Frame.newline +
						frame.mainClassNameField.getText() + Frame.newline +
						frame.iconFileFeild.getText() + Frame.newline +
						frame.versionStringField.getText());
				writer.flush();
			}
			
		} catch (IOException ex) {
			Logger.getLogger(OpenAndSaveFileManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	public static void openFile(Frame frame){
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileFilter() {
				
				@Override
				public boolean accept(File f) {
					return f.getName().endsWith(".bundleconfig") || f.isDirectory();
				}
				
				@Override
				public String getDescription() {
					return ".bundleconfig files";
				}
			});
			fileChooser.showOpenDialog(frame);
			File openedFile = fileChooser.getSelectedFile();
			Scanner scanner = new Scanner(openedFile);
			List<String> list = new ArrayList<>();
			while(scanner.hasNext()){
				list.add(scanner.nextLine());
			}
			
			frame.outputDirectoryField.setText(list.get(1));
			frame.outputDirectoryFile = new File(list.get(1));
			frame.jarFileField.setText(list.get(2));
			frame.jarfileFile = new File(list.get(2));
			frame.mainClassNameField.setText(list.get(3));
			frame.iconFileFeild.setText(list.get(4));
			frame.iconFile = new File(list.get(4));
			frame.versionStringField.setText(list.get(5));
			
		} catch (FileNotFoundException | ArrayIndexOutOfBoundsException ex) {
			Logger.getLogger(OpenAndSaveFileManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		
	}
	
}