package com.pfms.dev.ffzhz.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.pfms.dev.ffzhz.api.UserProfileProvider;
import com.pfms.dev.ffzhz.common.UserProfile;
import com.pfms.dev.ffzhz.common.Util;
import com.pfms.dev.ffzhz.impl.MusicComposerImpl;
import com.pfms.dev.ffzhz.impl.UserProfileProviderImpl;

public class Client440hz {

	List<File> images = new ArrayList<File>();

	public static void main(String[] args) {

		new Client440hz();
	}

	public Client440hz() {
		final JFrame guiFrame = new JFrame();
		JMenuBar menuBar;
		JMenu menu, moreInfo, help;
		JMenuItem save, addImage, helpInfo, about;
		
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		moreInfo = new JMenu("Add more info");
		help = new JMenu("Help");
		
		save = new JMenuItem("Create the song");
		addImage = new JMenuItem("Add an image");
		helpInfo = new JMenuItem("Help");
		about = new JMenuItem("About");
		
		menu.add(save);
		moreInfo.add(addImage);
		help.add(helpInfo);
		help.add(about);
		
		menuBar.add(menu);
		menuBar.add(moreInfo);
		menuBar.add(help);

		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("440Hz");
		guiFrame.setSize(800, 600);

		guiFrame.setLocationRelativeTo(null);

		JPanel personalData = new JPanel();

		JPanel name = new JPanel();
		JLabel namelbl = new JLabel("Name");
		final JTextField nameTxt = new JTextField(16);
		name.add(namelbl);
		name.add(nameTxt);

		JPanel age = new JPanel();
		JLabel agelbl = new JLabel("Age");
		final JTextField ageTxt = new JTextField(3);
		age.add(agelbl);
		age.add(ageTxt);

		personalData.add(name);
		personalData.add(age);

		JPanel west = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JPanel east = new JPanel(new FlowLayout(FlowLayout.CENTER));

		List<String> trends;
		try {
			JLabel tendslbl = new JLabel("Google Trends");
	
			trends = Util.getGoogleTrends();
			JList myList = new JList(trends.toArray());
			myList.setEnabled(false);
			west.add(tendslbl);
			west.add(myList);
			west.setSize(40, 500);
			west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
			
			myList.addMouseListener(new MouseListener(){
				public void mouseEntered(MouseEvent e) {
				}

				public void mouseClicked(MouseEvent e) {
					JOptionPane.showMessageDialog(null,
							"The items in the list are the Hot Topics in Google searches.\n"
							+ "Take a look to the site in http://www.google.com/trends/hottrends/atom/hourly\n"
							+ "This terms will be included in your song since they provide a small snapshot of "
							+ "your enviroment.", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					
				}

				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			       
		    });
			

		} catch (Exception e) {
			west.add(new JLabel("The Google Trends could not be loaded"));
		}

		final ImageIcon image = new ImageIcon("images/cover.jpg");
		final JPanel pan = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(image.getImage(), 0, 0, null);
			}
		};
		pan.setPreferredSize(new Dimension(600, 450));
		pan.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		pan.setAlignmentY(JComponent.CENTER_ALIGNMENT);

		east.add(pan);


		addImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				FileFilter imageFilter = new FileNameExtensionFilter(
						"Image files", ImageIO.getReaderFileSuffixes());
				JFileChooser openFile = new JFileChooser();
				openFile.addChoosableFileFilter(imageFilter);
				openFile.setAcceptAllFileFilterUsed(false);
				openFile.showOpenDialog(null);
				File open = openFile.getSelectedFile();
				if (open != null) {
					System.out.println(open.getAbsoluteFile());
					images.add(open);
					pan.repaint();
					try {
						image.setImage(Util.resize(ImageIO.read(open),
								pan.getWidth(), pan.getHeight()));
					} catch (IOException e) {
						System.err.println("Could not read image");
					}
				} else {
					System.out.println("Could not open image");
				}
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (nameTxt.getText().isEmpty() || ageTxt.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"The information fields are empty. Please fill them.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JFileChooser file = new JFileChooser();
					file.addChoosableFileFilter(new FileNameExtensionFilter(
							"MIDI files (.mid)", "mid"));
					file.showSaveDialog(null);
					File save = file.getSelectedFile();

					if (save != null) {

						MusicComposerImpl composer = new MusicComposerImpl();

						UserProfileProvider provider = new UserProfileProviderImpl();
						UserProfile profile = provider.getNewUserProfile();

						profile.setId("ID001");
						profile.setName(nameTxt.getText());
						profile.setAge(Integer.parseInt(ageTxt.getText()));
						profile.addImages(images);

						System.out.println(profile.getName() + "-"
								+ profile.getAge() + " "
								+ save.getAbsolutePath());
						String name = save.getAbsolutePath();

						if (!name.contains(".mid")) {
							name = name + ".mid";
						}

						File song = composer.createSong(profile, name);

						if (song.canRead()) {
							JOptionPane.showMessageDialog(null,
									"The song was succesfully created",
									"Info",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"The song could not be created", "Info",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		
		helpInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null,
						"Follow the next steps in order to create a song:\n"
						+ "1.Write your name and age in the text boxes\n"
						+ "2.Add some images that mean something to you using the menu 'Add more info'>'Add an image'\n"
						+ " You can add as many images as you want and all will be included in the result, but only "
						+ "the last one will be displayed in the window\n"
						+ "3.Save your song using the menu 'Menu'>'Create the song'",
						"Información",
						JOptionPane.QUESTION_MESSAGE);
			}
		});
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null,
						"This project was created by Pedro Marquez for "
						+ "the DevArt competition and it's unrelated to any other "
						+ "work made by the author. \nThis project is not for commercial "
						+ "purposes. \nIf you feel that any licence has been violated "
						+ "or you want more information, please "
						+ "contact me to pfernandom@gmail.com",
						"Información",
						JOptionPane.QUESTION_MESSAGE);
			}
		});
		
		guiFrame.add(menuBar, BorderLayout.NORTH);
		guiFrame.add(west, BorderLayout.WEST);
		guiFrame.add(east, BorderLayout.EAST);
		guiFrame.add(personalData, BorderLayout.SOUTH);

		// make sure the JFrame is visible
		guiFrame.setVisible(true);
	}
}