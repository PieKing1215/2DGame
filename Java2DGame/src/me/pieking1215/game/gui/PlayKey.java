package me.pieking1215.game.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.ExitState;
import me.pieking1215.game.net.sound.Sound;

public class PlayKey extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton goButton, exitButton;
	private JLabel title;
	private JTextField key;
	
	public PlayKey(){
		start();
	}
	
	private void start(){

		setVisible(true);
		setSize(400, 400);
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setTitle(Game.NAME);
		
		setLayout(null);
		
		setResizable(false);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		title=new JLabel("Enter Key:");
		title.setFont(new Font("Verdana", 0, 50));
		title.setBounds(this.getWidth()/2 - 130, -10, 1000, 120);
		
		goButton= new JButton();
		goButton.setBounds((this.getWidth()/2)-100, 190, 200, 60);
		goButton.setText("Play!");
		goButton.setFocusable(true);
		
		
		key= new JTextField();
		key.setFont(new Font("Arial", 0, 48));
		key.setBounds((this.getWidth()/2)-100, 100, 200, 60);
		
		
		exitButton= new JButton();
		exitButton.setBounds((this.getWidth()/2)-100, 270, 200, 50);
		exitButton.setText("Exit");
		
		
		
		add(goButton);
		add(exitButton);
		add(title);
		add(key);
		
		repaint();
		
		goButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if(key.getText().equals(Game.activationKey)){
					Game.activationKey=null;
					key.setText("");
					Sound.playSound("Select1.wav", 0.5f);
					new MainMenu();
					dispose();
				}else{
					key.setText("");
					Sound.playSound("Fail1.wav", 0.5f);
				}
				
				
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Game.exit(ExitState.NORMAL);
			}
		});
		
	}
	
	
	
}
