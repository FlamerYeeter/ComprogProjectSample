import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.Color;
import java.awt.Dimension;
	
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class ModifyAccount extends JFrame {

	private JPanel contentPane;
	JPanel goBack_btn = new JPanel();
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
	private Image img_kookaye = new ImageIcon(HomeFrame.class.getResource("res/kookaye.png")).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
	private Image img_login = new ImageIcon(HomeFrame.class.getResource("res/login.png")).getImage().getScaledInstance(380, 380, Image.SCALE_SMOOTH);
	private Image img_user = new ImageIcon(HomeFrame.class.getResource("res/user.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private Image img_lock = new ImageIcon(HomeFrame.class.getResource("res/lock.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
	private JTextField textFieldModify_user;
	private JPasswordField passwordModifyField;
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String query;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifyAccount frame = new ModifyAccount();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void dbconnect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocksign", "root", "");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//For the hover effect
    class PanelButtonMouseAdapter extends MouseAdapter {
        JPanel panel;
        Color originalColor;
        Color hoverColor;

        public PanelButtonMouseAdapter(JPanel panel, Color originalColor, Color hoverColor) {
            this.panel = panel;
            this.originalColor = originalColor;
            this.hoverColor = hoverColor;
        }

        public void mouseEntered(MouseEvent e) {
            panel.setBackground(hoverColor);
        }

        public void mouseExited(MouseEvent e) {
            panel.setBackground(originalColor);
        }
        public void mouseClicked(MouseEvent e) {
            if (panel == goBack_btn) {
                // Open the ProductFrame
                LogInFrame login= new LogInFrame();
                login.setVisible(true);
                dispose();
            }
            }

    }

  

	/**
	 * Create the frame.
	 */
	public ModifyAccount() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModifyAccount.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 513);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("StockSign");
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(242, 202, 165));
		panel_1.setBounds(0, 0, 495, 480);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel kookayelbl = new JLabel("");
		kookayelbl.setBounds(169, -11, 165, 168);
		panel_1.add(kookayelbl);
		kookayelbl.setIcon(new ImageIcon(img_kookaye));
		
		JLabel lblNewLabel = new JLabel("Enter new username/password below");
		lblNewLabel.setForeground(new Color(162, 74, 56));
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel.setBounds(61, 149, 363, 28);
		panel_1.add(lblNewLabel);
		
		JPanel panel_user = new JPanel();
		panel_user.setBounds(35, 212, 425, 51);
		panel_1.add(panel_user);
		panel_user.setBackground(new Color(255, 255, 255));
		panel_user.setBorder(null);
		panel_user.setLayout(null);
		
		textFieldModify_user = new JTextField();
		textFieldModify_user.setBounds(60, 10, 341, 30);
		panel_user.add(textFieldModify_user);
		textFieldModify_user.setForeground(Color.BLACK);
		textFieldModify_user.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldModify_user.setColumns(10);
		textFieldModify_user.setBorder(null);
		
		JLabel user_icon_lbl = new JLabel("");
		user_icon_lbl.setBounds(10, 0, 45, 53);
		panel_user.add(user_icon_lbl);
		user_icon_lbl.setIcon(new ImageIcon(img_user));
		
		JPanel panel_password = new JPanel();
		panel_password.setBounds(35, 292, 425, 51);
		panel_1.add(panel_password);
		panel_password.setLayout(null);
		panel_password.setBorder(null);
		panel_password.setBackground(Color.WHITE);
		
		JLabel lock_icon_lbl = new JLabel("");
		lock_icon_lbl.setBounds(10, 0, 45, 53);
		lock_icon_lbl.setIcon(new ImageIcon(img_lock));
		panel_password.add(lock_icon_lbl);
		
		passwordModifyField = new JPasswordField();
		passwordModifyField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordModifyField.setBorder(null);
		passwordModifyField.setBounds(65, 10, 350, 31);
		panel_password.add(passwordModifyField);
		
		
				final JCheckBox showPassword = new JCheckBox("Show Password");
				showPassword.setBounds(45, 348, 121, 21);
				panel_1.add(showPassword);
				showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				showPassword.setBackground(new Color(242, 202, 165));
				
				JPanel SaveChanges_btn = new JPanel();
				// Add hover effect to the loginPanel
		        Color homeOriginalColor = new Color(84, 26, 5);
		        Color homeHoverColor = new Color(64, 18, 2);
		        SaveChanges_btn.addMouseListener(new PanelButtonMouseAdapter(SaveChanges_btn, homeOriginalColor, homeHoverColor));
				SaveChanges_btn.setBounds(152, 394, 198, 37);
				panel_1.add(SaveChanges_btn);
				SaveChanges_btn.addMouseListener(new PanelButtonMouseAdapter(SaveChanges_btn, homeOriginalColor, homeHoverColor));
				SaveChanges_btn.setBackground(new Color(84, 26, 5));
				SaveChanges_btn.setLayout(null);
				
				JLabel log_lbl = new JLabel("SAVE CHANGES");
				log_lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
				log_lbl.setForeground(new Color(255, 200, 102));
				log_lbl.setBounds(36, 10, 152, 13);
				SaveChanges_btn.add(log_lbl);
				
				// Add hover effect to the goBack
		        Color goBackOriginalColor = new Color(242, 202, 165);
		        Color goBackHoverColor = new Color(235, 180, 130);
		        goBack_btn.addMouseListener(new PanelButtonMouseAdapter(goBack_btn, goBackOriginalColor, goBackHoverColor));
				goBack_btn.setBounds(410, 453, 75, 27);
				panel_1.add(goBack_btn);
				goBack_btn.setLayout(null);
				goBack_btn.setBackground(new Color(242, 202, 165));
				
				JLabel goBack_lbl = new JLabel("GO BACK");
				goBack_lbl.setForeground(new Color(80, 21, 0));
				goBack_lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
				goBack_lbl.setBounds(10, 10, 55, 13);
				goBack_btn.add(goBack_lbl);
				
				SaveChanges_btn.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
				        String newUsername = textFieldModify_user.getText();
				        String newPassword = new String(passwordModifyField.getPassword());

				        try {
				            dbconnect();
				            query = "UPDATE usertb SET username = ?, password = ? WHERE id = 1";
				            PreparedStatement pstmt = conn.prepareStatement(query);
				            pstmt.setString(1, newUsername);
				            pstmt.setString(2, newPassword);
				            int rowsAffected = pstmt.executeUpdate();
				            pstmt.close();
				            conn.close();

				            if (rowsAffected > 0) {
				                // Update successful
				                System.out.println("Account details updated successfully.");
				            } else {
				                // Update failed
				                System.out.println("Failed to update account details.");
				            }
				    		EventQueue.invokeLater(new Runnable() {
				    			public void run() {
				    				try {
				    					LogInFrame frame = new LogInFrame();
				    					frame.setVisible(true);
				    				} catch (Exception e) {
				    					e.printStackTrace();
				    				}
				    			}
				    		});
				    		dispose();
				        } catch (SQLException ex) {
				            ex.printStackTrace();
				        }
				    }
				});
				
					//Coding Part of showPassword JCheckBox
					  showPassword.addItemListener(new ItemListener() {
				            public void itemStateChanged(ItemEvent e) {
				                if (showPassword.isSelected()) passwordModifyField.setEchoChar((char)0);
				                else 
				                    if(!String.valueOf(passwordModifyField.getPassword()).equals("Password"))passwordModifyField.setEchoChar('●');
				            }
				        });
		
	
	}
}
