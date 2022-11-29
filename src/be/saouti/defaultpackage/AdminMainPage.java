package be.saouti.defaultpackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import be.saouti.models.Administrator;
import be.saouti.models.VideoGame;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

public class AdminMainPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField costFld;
	private JTable table = new JTable();;

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
		refresh(VideoGame.getAll());
		setBounds(300, 300, 650, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea lblName = new JTextArea();
		lblName.setLineWrap(true);
		lblName.setBounds(437, 169, 163, 72);
		contentPane.add(lblName);
		
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
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage lp = new LoginPage();
				lp.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBorder(null);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBounds(568, 27, 58, 23);
		topbarPnl.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 106, 400, 230);
		contentPane.add(scrollPane);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				costFld.setText((String)table.getValueAt(i, 3));
				lblName.setText((String)table.getValueAt(i, 1)+"("+(String)table.getValueAt(i, 2)+")");
			}
		});
		table.setFont(new Font("Gill Sans MT", Font.PLAIN, 11));
		scrollPane.setViewportView(table);
		
		JLabel lblChangeTheCost = new JLabel("Change the cost");
		lblChangeTheCost.setFont(new Font("Gill Sans MT", Font.BOLD, 15));
		lblChangeTheCost.setBounds(437, 130, 115, 28);
		contentPane.add(lblChangeTheCost);
		
		costFld = new JTextField();
		costFld.setBounds(437, 256, 157, 28);
		contentPane.add(costFld);
		costFld.setColumns(10);
		
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = table.getSelectedRow();
				if(i >= 0) {
					VideoGame vg = VideoGame.getById(Integer.parseInt((String)table.getValueAt(i, 0)));
					try {
						vg.setCreditCost(Integer.parseInt(costFld.getText()));
						vg.update();
					}catch(NumberFormatException ex){
						JOptionPane.showMessageDialog(null, costFld.getText()+"is not a valid number !");
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
				lblName.setText("");
				refresh(VideoGame.getAll());
			}
		});
		updateBtn.setForeground(Color.WHITE);
		updateBtn.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		updateBtn.setBorderPainted(false);
		updateBtn.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		updateBtn.setBackground(new Color(23, 70, 162));
		updateBtn.setBounds(437, 295, 157, 23);
		contentPane.add(updateBtn);
	}
	
	private void refresh(List<VideoGame> videogames) {
		List<String[]> rows = new ArrayList<String[]>();
		for (VideoGame vg : videogames) {
			String[] row = {Integer.toString(vg.getId()),vg.getName(),
					vg.getConsole(),Integer.toString(vg.getCreditCost())
			};
			rows.add(row);
		}
		table.setModel(new DefaultTableModel(
			rows.toArray(new Object[][] {}),
			new String[] {
				"Id", "Name", "Console", "Cost"
			}
		));
	}
}
