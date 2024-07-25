import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class LogInFrame extends JFrame {

	private JPanel contentPane;
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
	private Image img_kookaye = new ImageIcon(HomeFrame.class.getResource("res/kookaye.png")).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
	private Image img_login = new ImageIcon(HomeFrame.class.getResource("res/login.png")).getImage().getScaledInstance(380, 380, Image.SCALE_SMOOTH);
	private Image img_user = new ImageIcon(HomeFrame.class.getResource("res/user.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private Image img_lock = new ImageIcon(HomeFrame.class.getResource("res/lock.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
	private JTextField textField_user;
	private JPasswordField passwordField;
	JPanel modifyAccBtn_Panel = new JPanel();
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
					LogInFrame frame = new LogInFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    private void login(String username, String password) {
        dbconnect();
        try {
            query = "SELECT * FROM usertb WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                // Login successful, do something
                System.out.println("Login successful.");
        		EventQueue.invokeLater(new Runnable() {
        		    public void run() {
        		        try {
        		            HomeFrame frame = new HomeFrame();
        		            frame.setVisible(true);
        		            dispose();
        		        } catch (Exception e) {
        		            e.printStackTrace();
        		        }
        		    }
        		});
            } else {
                // Login failed, show error message or perform appropriate action
                System.out.println("Login failed.");
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                        	LoginTryAgainFrame frame = new LoginTryAgainFrame();
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
            if (panel == modifyAccBtn_Panel) {
                // Open the ProductFrame
            	EnterSecurityCodeFrame entersecurityCodeFrame= new EnterSecurityCodeFrame();
            	entersecurityCodeFrame.setVisible(true);
                dispose();
            }
            }
        
    }
    


	/**
	 * Create the frame.
	 */
	public LogInFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LogInFrame.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1048, 726);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(155, 129, 123));
		panel.setBounds(70, 59, 508, 566);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel stocklbl = new JLabel("");
		stocklbl.setBounds(0, 0, 70, 70);
		stocklbl.setIcon(new ImageIcon(img_logo));
		panel.add(stocklbl);
		
		JLabel lblNewLabel = new JLabel("STOCKSIGN");
		lblNewLabel.setForeground(new Color(65, 57, 83));
		lblNewLabel.setFont(new Font("Britannic Bold", Font.PLAIN, 20));
		lblNewLabel.setBounds(69, 25, 140, 23);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Know your stock,");
		lblNewLabel_1.setForeground(new Color(242, 202, 165));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 40));
		lblNewLabel_1.setBounds(10, 80, 488, 54);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("sign your management.");
		lblNewLabel_1_1.setForeground(new Color(242, 202, 165));
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 40));
		lblNewLabel_1_1.setBounds(10, 126, 488, 54);
		panel.add(lblNewLabel_1_1);
		
		JLabel loginlbl = new JLabel("");
		loginlbl.setBounds(20, 181, 455, 427);
		loginlbl.setIcon(new ImageIcon(img_login));
		panel.add(loginlbl);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(242, 202, 165));
		panel_1.setBounds(571, 59, 401, 566);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel kookayelbl = new JLabel("");
		kookayelbl.setBounds(134, 10, 165, 184);
		kookayelbl.setIcon(new ImageIcon(img_kookaye));
		panel_1.add(kookayelbl);
		
		JPanel panel_user = new JPanel();
		panel_user.setBackground(new Color(255, 255, 255));
		panel_user.setBorder(null);
		panel_user.setBounds(40, 229, 326, 51);
		panel_1.add(panel_user);
		panel_user.setLayout(null);
		
		textField_user = new JTextField();
		textField_user.setBounds(60, 10, 256, 30);
		panel_user.add(textField_user);
		textField_user.setForeground(Color.BLACK);
		textField_user.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_user.setColumns(10);
		textField_user.setBorder(null);
		
		JLabel user_icon_lbl = new JLabel("");
		user_icon_lbl.setBounds(10, 0, 45, 53);
		panel_user.add(user_icon_lbl);
		user_icon_lbl.setIcon(new ImageIcon(img_user));
		
		JPanel panel_password = new JPanel();
		panel_password.setLayout(null);
		panel_password.setBorder(null);
		panel_password.setBackground(Color.WHITE);
		panel_password.setBounds(40, 306, 326, 51);
		panel_1.add(panel_password);
		
		JLabel lock_icon_lbl = new JLabel("");
		lock_icon_lbl.setBounds(10, 0, 45, 53);
		lock_icon_lbl.setIcon(new ImageIcon(img_lock));
		panel_password.add(lock_icon_lbl);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBorder(null);
		passwordField.setBounds(65, 10, 251, 31);
		panel_password.add(passwordField);
		
		JPanel login_panel = new JPanel();
		// Add hover effect to the loginPanel
        Color homeOriginalColor = new Color(84, 26, 5);
        Color homeHoverColor = new Color(64, 18, 2);
        login_panel.addMouseListener(new PanelButtonMouseAdapter(login_panel, homeOriginalColor, homeHoverColor));
		login_panel.setBackground(new Color(84, 26, 5));
		login_panel.setBounds(116, 417, 198, 37);
		panel_1.add(login_panel);
		login_panel.setLayout(null);
		
		JLabel log_lbl = new JLabel("LOG IN");
		log_lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		log_lbl.setForeground(new Color(255, 200, 102));
		log_lbl.setBounds(70, 10, 66, 13);
		login_panel.add(log_lbl);
		
		login_panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = textField_user.getText();
                String password = new String(passwordField.getPassword());
                login(username, password);
            }
        });

		final JCheckBox showPassword = new JCheckBox("Show Password");
		showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		showPassword.setBackground(new Color(242, 202, 165));
		showPassword.setBounds(40, 363, 121, 21);
		panel_1.add(showPassword);
		
		// Add hover effect to the modAcc
        Color modifyOriginalColor = new Color(242, 202, 165);
        Color modifyHoverColor = new Color(235, 180, 130);
        modifyAccBtn_Panel.addMouseListener(new PanelButtonMouseAdapter(modifyAccBtn_Panel, modifyOriginalColor, modifyHoverColor));
		modifyAccBtn_Panel.setLayout(null);
		modifyAccBtn_Panel.setBackground(new Color(242, 202, 165));
		modifyAccBtn_Panel.setBounds(155, 459, 112, 27);
		panel_1.add(modifyAccBtn_Panel);
		
		JLabel modifyAcc_lbl = new JLabel("MODIFY ACCOUNT");
		modifyAcc_lbl.setForeground(new Color(80, 21, 0));
		modifyAcc_lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
		modifyAcc_lbl.setBounds(10, 10, 95, 13);
		modifyAccBtn_Panel.add(modifyAcc_lbl);
		
//		modifyAcc_lbl.addMouseListener(new MouseAdapter() {
//		    @Override
//		    public void mouseClicked(MouseEvent e) {
//		    	EventQueue.invokeLater(new Runnable() {
//                    public void run() {
//                        try {
//                        	ModifyAccount frame = new ModifyAccount();
//                            frame.setVisible(true);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//		        System.out.println("Debugging Flamer Moment!");
//		    }
//		});
		
		//Coding Part of showPassword JCheckBox
		  showPassword.addItemListener(new ItemListener() {
	            public void itemStateChanged(ItemEvent e) {
	                if (showPassword.isSelected()) passwordField.setEchoChar((char)0);
	                else 
	                    if(!String.valueOf(passwordField.getPassword()).equals("Password"))passwordField.setEchoChar('●');
	            }
	        });
	
	}
	
}
