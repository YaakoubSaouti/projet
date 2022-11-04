package be.saouti.defaultpackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import be.saouti.connection.VideoGamesConnection;
import be.saouti.daos.PlayerDAO;
import be.saouti.daos.UserDAO;
import be.saouti.models.Administrator;
import be.saouti.models.Player;
import be.saouti.models.User;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JTextField usernameTfld;
	private JPasswordField passwordTfld;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JPanel loginPnl = new JPanel();
		loginPnl.setBorder(new LineBorder(new Color(171, 173, 179), 3, true));
		loginPnl.setBackground(new Color(255, 247, 233));
		loginPnl.setBounds(168, 99, 300, 275);
		contentPane.add(loginPnl);
		loginPnl.setLayout(null);
		
		JLabel subTitlePnl = new JLabel("Login");
		subTitlePnl.setBounds(0, 11, 300, 37);
		subTitlePnl.setForeground(new Color(255, 115, 29));
		subTitlePnl.setHorizontalAlignment(SwingConstants.CENTER);
		subTitlePnl.setFont(new Font("Gill Sans MT", Font.BOLD, 23));
		loginPnl.add(subTitlePnl);
		
		JPanel loginFieldsPnl = new JPanel();
		loginFieldsPnl.setBorder(new EmptyBorder(0, 0, 0, 0));
		loginFieldsPnl.setBackground(new Color(255, 247, 233));
		loginFieldsPnl.setBounds(60, 62, 180, 130);
		loginPnl.add(loginFieldsPnl);
		loginFieldsPnl.setLayout(new GridLayout(4,1,40,5));
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		loginFieldsPnl.add(usernameLbl);
		
		usernameTfld = new JTextField();
		usernameTfld.setColumns(10);
		loginFieldsPnl.add(usernameTfld);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		loginFieldsPnl.add(lblPassword);
		
		passwordTfld = new JPasswordField();
		loginFieldsPnl.add(passwordTfld);
		
		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameTfld.getText();
				String password = new String(passwordTfld.getPassword());
				UserDAO userDAO = new UserDAO(VideoGamesConnection.getInstance());
				User user = userDAO.find(username);
				if(user!=null && user.login(username, password)) {
					Session.getInstance().set("id", Integer.toString(user.getId()));
					if(user instanceof Administrator) {
						dispose();
						AdminMainPage amp = new AdminMainPage((Administrator) user);
						amp.setVisible(true);
					}
					if(user instanceof Player) 
						JOptionPane.showMessageDialog(null, "You are a player!");
				}else JOptionPane.showMessageDialog(null, "Invalid credentials!");
			}
		});
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		loginBtn.setBorderPainted(false);
		loginBtn.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		loginBtn.setBackground(new Color(23, 70, 162));
		loginBtn.setBounds(60, 203, 180, 23);
		loginPnl.add(loginBtn);
		
		JLabel NALbl = new JLabel("No account yet?");
		NALbl.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		NALbl.setBounds(70, 237, 94, 14);
		loginPnl.add(NALbl);
		
		JLabel registerPgBtn = new JLabel("Register");
		registerPgBtn.setHorizontalAlignment(SwingConstants.CENTER);
		registerPgBtn.setForeground(new Color(95, 157, 247));
		registerPgBtn.setFont(new Font("Gill Sans MT", Font.BOLD | Font.ITALIC, 11));
		registerPgBtn.setBounds(164, 238, 49, 13);
		registerPgBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WelcomePage wp = new WelcomePage();
				wp.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				registerPgBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		loginPnl.add(registerPgBtn);
	}
}
