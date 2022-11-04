package be.saouti.defaultpackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import be.saouti.connection.VideoGamesConnection;
import be.saouti.daos.AdministratorDAO;
import be.saouti.models.Administrator;

import javax.swing.SwingConstants;
import javax.swing.JButton;

public class AdminMainPage extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainPage frame = new AdminMainPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param user 
	 */
	public AdminMainPage(){}
	public AdminMainPage(Administrator admin){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AdministratorDAO administratorDAO = new AdministratorDAO(VideoGamesConnection.getInstance());
		admin = administratorDAO.find(Integer.parseInt(Session.getInstance().get("id")));
		setBounds(300, 300, 650, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel topbarPnl = new JPanel();
		topbarPnl.setBackground(new Color(255, 247, 233));
		topbarPnl.setBorder(new LineBorder(new Color(192, 192, 192), 3));
		topbarPnl.setBounds(0, 0, 636, 67);
		contentPane.add(topbarPnl);
		topbarPnl.setLayout(null);
		
		JLabel title = new JLabel("Game Trading");
		title.setFont(new Font("Garamond", Font.BOLD, 28));
		title.setBounds(10, 11, 191, 45);
		topbarPnl.add(title);
		
		JLabel usernameLbl = new JLabel(admin.getUsername());
		usernameLbl.setForeground(new Color(215, 115, 29));
		usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameLbl.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		usernameLbl.setBounds(254, 22, 302, 28);
		topbarPnl.add(usernameLbl);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.setBorder(null);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBounds(568, 27, 58, 23);
		topbarPnl.add(btnNewButton);
	}
}
