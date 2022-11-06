package be.saouti.defaultpackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import be.saouti.models.Player;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JPasswordField;

public class WelcomePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField pseudoTfld;
	private JTextField dateOfBirthTfld;
	private JTextField usernameTfld;
	private JPasswordField passwordTfld;
	private JPasswordField confpasswordTField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomePage frame = new WelcomePage();
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
	public WelcomePage() {
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
		
		JPanel registerPnl = new JPanel();
		registerPnl.setBorder(new LineBorder(new Color(171, 173, 179), 3, true));
		registerPnl.setBackground(new Color(255, 247, 233));
		registerPnl.setBounds(150, 100, 350, 307);
		contentPane.add(registerPnl);
		registerPnl.setLayout(null);
		
		JLabel subTitlePnl = new JLabel("Register");
		subTitlePnl.setBounds(0, 11, 350, 37);
		subTitlePnl.setForeground(new Color(255, 115, 29));
		subTitlePnl.setHorizontalAlignment(SwingConstants.CENTER);
		subTitlePnl.setFont(new Font("Gill Sans MT", Font.BOLD, 23));
		registerPnl.add(subTitlePnl);
		
		JPanel registerFieldsPnl = new JPanel();
		registerFieldsPnl.setBorder(new EmptyBorder(0, 0, 0, 0));
		registerFieldsPnl.setBackground(new Color(255, 247, 233));
		registerFieldsPnl.setBounds(25, 60, 300, 135);
		registerPnl.add(registerFieldsPnl);
		registerFieldsPnl.setLayout(new GridLayout(5,1,40,5));
		
		JLabel usernameLbl = new JLabel("Username");
		usernameLbl.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		registerFieldsPnl.add(usernameLbl);
		
		JLabel pseudoLbl = new JLabel("Pseudo");
		pseudoLbl.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		registerFieldsPnl.add(pseudoLbl);
		
		usernameTfld = new JTextField();
		registerFieldsPnl.add(usernameTfld);
		usernameTfld.setColumns(10);
		
		pseudoTfld = new JTextField();
		registerFieldsPnl.add(pseudoTfld);
		pseudoTfld.setColumns(10);
		
		JLabel password_lbl = new JLabel("Password+Conf");
		password_lbl.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		registerFieldsPnl.add(password_lbl);
		
		JLabel dateOfBirthLbl = new JLabel("Date of birth");
		dateOfBirthLbl.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		registerFieldsPnl.add(dateOfBirthLbl);
		
		passwordTfld = new JPasswordField();
		registerFieldsPnl.add(passwordTfld);
		
		dateOfBirthTfld = new JTextField();
		dateOfBirthTfld.setToolTipText("The format should be like dd/mm/yyyy");
		registerFieldsPnl.add(dateOfBirthTfld);
		dateOfBirthTfld.setColumns(10);
		
		confpasswordTField = new JPasswordField();
		registerFieldsPnl.add(confpasswordTField);
		
		JButton registerBtn = new JButton("Register");
		registerBtn.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		registerBtn.setForeground(new Color(255, 255, 255));
		registerBtn.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		registerBtn.setBackground(new Color(23, 70, 162));
		registerBtn.setBorderPainted(false);
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String username = usernameTfld.getText();
				String pseudo = pseudoTfld.getText();
				String password = new String(passwordTfld.getPassword());
				String confpassword = new String(confpasswordTField.getPassword());
				String dateOfBirth = dateOfBirthTfld.getText();
				Player player = new Player();
				try {
					LocalDate dob = LocalDate.parse(dateOfBirth, formatter);
					Player.register(username,pseudo,password,dob);
					if(!password.equals(confpassword)) throw new Exception("The two passwords are not the same !");
					player.setUsername(username);
					player.setPassword(password);
					player.setPseudo(pseudo);
					player.setDateOfBirth(dob);
					player.create();
					JOptionPane.showMessageDialog(null, "You successfully registered\n You can login now !");
				}catch(DateTimeParseException pe) {
					JOptionPane.showMessageDialog(null, "Date should be like dd/MM/yyyy !");
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		registerBtn.setBounds(75, 210, 200, 23);
		registerPnl.add(registerBtn);
		
		JLabel AALbl = new JLabel("You already have an account? ");
		AALbl.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		AALbl.setBounds(75, 244, 149, 14);
		registerPnl.add(AALbl);
		JLabel loginPgBtn = new JLabel("Login");
		loginPgBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginPage lp = new LoginPage();
				lp.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				loginPgBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		loginPgBtn.setHorizontalAlignment(SwingConstants.CENTER);
		loginPgBtn.setFont(new Font("Gill Sans MT", Font.BOLD | Font.ITALIC, 11));
		loginPgBtn.setForeground(new Color(95, 157, 247));
		loginPgBtn.setBounds(226, 246, 49, 13);
		registerPnl.add(loginPgBtn);
	}
}
