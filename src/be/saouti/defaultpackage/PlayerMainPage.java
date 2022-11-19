package be.saouti.defaultpackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import be.saouti.models.Booking;
import be.saouti.models.Player;
import be.saouti.models.VideoGame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.SystemColor;
import javax.swing.JComboBox;

public class PlayerMainPage extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private JLabel subTitle;
	private JLabel credit;
	private JLabel usernameLbl;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable bookingTable;
	private Player player;
	private List<VideoGame> vgs;
	private JPanel seeBookingsPanel;
	private JPanel bookPnl;
	private JComboBox<VideoGame> videoGamesComboBox;
	JMenuItem seeBookingsItem;
	JMenuItem bookItem;
	private JLabel noItemsLbl;
	private JTextArea lblName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerMainPage frame = new PlayerMainPage();
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
	public PlayerMainPage() {}

	public PlayerMainPage(Player player){
		
		//Initialization
		
		this.player = player;
		initPanel();
		refreshUserInfos();
		
		//Creating the Panels
		
		bookingsPanel();
		bookPanel();
		
		//Showing the welcome panel(seeBookings);
		seeBookings();
	}
	
	
	//Menu management
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == seeBookingsItem)    
			seeBookings();  
		if(e.getSource() == bookItem)    
			seeBook();  
	}
	
	//Changing pages
	private void seeBook() {
		subTitle.setText("Book a game");
		noItemsLbl.setVisible(false);
		seeBookingsPanel.setVisible(false);
		bookPnl.setVisible(true);
		refreshVideoGame();
		populateVideoGameComboBoxForBookings();
	}
	
	private void seeBookings() {
		subTitle.setText("Your Bookings");
		lblName.setText("");
		noItemsLbl.setVisible(false);
		bookPnl.setVisible(false);
		seeBookingsPanel.setVisible(true);
		refreshVideoGame();
		populateBookingsTable();
	}
	
	private void noItems(String message) {
		seeBookingsPanel.setVisible(false);
		bookPnl.setVisible(false);
		noItemsLbl.setVisible(true);
		noItemsLbl.setText(message);	
	}
	
	//Populate and refresh
	private void populateBookingsTable() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		List<String[]> rows = new ArrayList<String[]>();
		for (VideoGame vg : vgs) {
			for(Booking b : vg.getBookingsOfPlayer(player)){
				String[] row = {
					Integer.toString(vg.getId()),
					Integer.toString(b.getId()),
					vg.getName()+"("+vg.getConsole()+")",
					b.getBookingDate().format(formatter),
				};
				rows.add(row);
			}
		}
		if(rows.size()>0) {
			bookingTable.setModel(new DefaultTableModel(
				rows.toArray(new Object[][] {}),
				new String[] {
					"", "", "VideoGame", "Booking Date"
				}
			));
			bookingTable.getColumnModel().getColumn(0).setWidth(0);
			bookingTable.getColumnModel().getColumn(0).setMinWidth(0);
			bookingTable.getColumnModel().getColumn(0).setMaxWidth(0);
			bookingTable.getColumnModel().getColumn(1).setWidth(0);
			bookingTable.getColumnModel().getColumn(1).setMinWidth(0);
			bookingTable.getColumnModel().getColumn(1).setMaxWidth(0);
		}else{
			noItems("You have no bookings yet, to add one go to Bookings -> Book a game");
		}
	}
	
	private void populateVideoGameComboBoxForBookings() {
		videoGamesComboBox.setModel(new DefaultComboBoxModel<VideoGame>(vgs.toArray(new VideoGame[0])));
	}
	
	public void refreshVideoGame(){
		vgs = VideoGame.getAll();
	}
	
	public void refreshUserInfos() {
		usernameLbl.setText(player.getUsername());
		credit.setText(player.getCredit()+" C");
	}
	
	//Creating the panels
	
	public void initPanel() {
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
		topbarPnl.setBounds(0, 28, 636, 67);
		contentPane.add(topbarPnl);
		topbarPnl.setLayout(null);
		
		JLabel title = new JLabel("Game Trading");
		title.setFont(new Font("Garamond", Font.BOLD, 28));
		title.setBounds(10, 12, 191, 45);
		topbarPnl.add(title);
		
		usernameLbl = new JLabel("");
		usernameLbl.setForeground(new Color(215, 115, 29));
		usernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameLbl.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		usernameLbl.setBounds(254, 22, 302, 28);
		topbarPnl.add(usernameLbl);
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage lp = new LoginPage();
				lp.setVisible(true);
				dispose();
			}
		});
		logoutBtn.setBorder(null);
		logoutBtn.setForeground(Color.WHITE);
		logoutBtn.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		logoutBtn.setBackground(Color.RED);
		logoutBtn.setBorderPainted(false);
		logoutBtn.setBounds(568, 27, 58, 23);
		topbarPnl.add(logoutBtn);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.text);
		menuBar.setBounds(0, 0, 525, 32);
		contentPane.add(menuBar);
		
		JMenu menu = new JMenu("Bookings");
		menu.setFont(new Font("Gill Sans MT", Font.BOLD, 12));
		menu.setForeground(new Color(95, 157, 247));
		menuBar.add(menu);
		
		seeBookingsItem = new JMenuItem("See Your bookings");
		menu.add(seeBookingsItem);
		seeBookingsItem.addActionListener(this);
		
		bookItem = new JMenuItem("Book a game");
		menu.add(bookItem);
		bookItem.addActionListener(this);
		
		credit = new JLabel("");
		credit.setForeground(new Color(255, 115, 29));
		credit.setHorizontalAlignment(SwingConstants.RIGHT);
		credit.setFont(new Font("Gill Sans MT", Font.BOLD, 13));
		credit.setBounds(535, 0, 91, 25);
		contentPane.add(credit);
		
		subTitle = new JLabel("My Subtitle");
		subTitle.setBounds(10, 106, 594, 45);
		contentPane.add(subTitle);
		subTitle.setForeground(new Color(23, 70, 162));
		subTitle.setFont(new Font("Gill Sans MT", Font.BOLD, 24));
		
		noItemsLbl = new JLabel("New label");
		noItemsLbl.setForeground(Color.RED);
		noItemsLbl.setFont(new Font("Gill Sans MT", Font.BOLD | Font.ITALIC, 14));
		noItemsLbl.setBounds(10, 162, 616, 32);
		contentPane.add(noItemsLbl);
		noItemsLbl.setVisible(false);
	}
	
	public void bookingsPanel() {
		seeBookingsPanel = new JPanel();
		seeBookingsPanel.setVisible(true);
		seeBookingsPanel.setBorder(null);
		seeBookingsPanel.setBackground(SystemColor.text);
		seeBookingsPanel.setBounds(10, 171, 616, 281);
		contentPane.add(seeBookingsPanel);
		seeBookingsPanel.setLayout(null);
		
		lblName = new JTextArea();
		lblName.setLineWrap(true);
		lblName.setBounds(420, 37, 163, 72);
		seeBookingsPanel.add(lblName);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(10, 11, 400, 230);
		seeBookingsPanel.add(scrollPane1);
		
		bookingTable = new JTable();
		bookingTable.setDefaultEditor(Object.class, null);
		bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookingTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = bookingTable.getSelectedRow();
				lblName.setText((String)bookingTable.getValueAt(i, 2)+"("+(String)bookingTable.getValueAt(i, 3)+")");
			}
		});;
		scrollPane1.setViewportView(bookingTable);
		
		JButton deletBookingBtn = new JButton("delete");
		deletBookingBtn.setBounds(477, 120, 58, 23);
		seeBookingsPanel.add(deletBookingBtn);
		deletBookingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int i = bookingTable.getSelectedRow();
					int idBooking = Integer.parseInt((String)bookingTable.getModel().getValueAt(i,1));
					int idVG = Integer.parseInt((String)bookingTable.getModel().getValueAt(i,0));
					Booking toDelete = Booking.getById(idBooking);
					VideoGame toDeleteFrom = VideoGame.getById(idVG);
					toDeleteFrom.removeBooking(toDelete);
					seeBookings();
				}catch(ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "Error: No selection!");
				}
			}
		});
		deletBookingBtn.setForeground(Color.WHITE);
		deletBookingBtn.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		deletBookingBtn.setBorderPainted(false);
		deletBookingBtn.setBorder(null);
		deletBookingBtn.setBackground(Color.RED);
		
		JLabel lblDelete = new JLabel("Delete a booking");
		lblDelete.setBounds(420, 10, 146, 28);
		seeBookingsPanel.add(lblDelete);
		lblDelete.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
	}
	
	public void bookPanel() {
		bookPnl = new JPanel();
		bookPnl.setVisible(false);
		bookPnl.setBackground(new Color(255, 255, 255));
		bookPnl.setBounds(20, 162, 606, 301);
		contentPane.add(bookPnl);
		bookPnl.setLayout(null);
		
		JPanel childBookPanel = new JPanel();
		childBookPanel.setLayout(null);
		childBookPanel.setBorder(new LineBorder(new Color(171, 173, 179), 3, true));
		childBookPanel.setBackground(new Color(255, 247, 233));
		childBookPanel.setBounds(156, 11, 300, 259);
		bookPnl.add(childBookPanel);
		
		JLabel lblBookAGame = new JLabel("Book a game");
		lblBookAGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookAGame.setForeground(new Color(255, 115, 29));
		lblBookAGame.setFont(new Font("Gill Sans MT", Font.BOLD, 23));
		lblBookAGame.setBounds(0, 11, 300, 37);
		childBookPanel.add(lblBookAGame);
		
		videoGamesComboBox = new JComboBox<VideoGame>();
		videoGamesComboBox.setBounds(10, 123, 280, 37);
		childBookPanel.add(videoGamesComboBox);
		
		JButton btnBook = new JButton("Book");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Booking toAdd = new Booking();
				toAdd.setPlayer(player);
				toAdd.setBookingDate(LocalDate.now());
				VideoGame vg = (VideoGame)videoGamesComboBox.getSelectedItem();
				vg.addBooking(toAdd);
				vg.update();
				seeBookings();
			}
		});
		btnBook.setForeground(Color.WHITE);
		btnBook.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		btnBook.setBorderPainted(false);
		btnBook.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		btnBook.setBackground(new Color(23, 70, 162));
		btnBook.setBounds(60, 188, 180, 23);
		childBookPanel.add(btnBook);
		
		JLabel lblSelectAGame = new JLabel("Select a game");
		lblSelectAGame.setBounds(10, 84, 180, 28);
		childBookPanel.add(lblSelectAGame);
		lblSelectAGame.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
	}
}
