import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class EnterSecurityCodeFrame extends JFrame {

	private JPanel contentPane;
	JPanel cancel_btn = new JPanel();
	JPanel modifySecu_btn = new JPanel();
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image img_kookaye = new ImageIcon(HomeFrame.class.getResource("res/kookaye.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image img_shield = new ImageIcon(HomeFrame.class.getResource("res/shield.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private JPasswordField passwordField;
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
					EnterSecurityCodeFrame frame = new EnterSecurityCodeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    private void codeAccess(int securityCode) {
        dbconnect();
        try {
            query = "SELECT * FROM usertb WHERE securityCode = '" + securityCode + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                // Login successful, do something
                System.out.println("Login successful.");
        		EventQueue.invokeLater(new Runnable() {
        		    public void run() {
        		        try {
        		        	ModifyAccount frame= new ModifyAccount();
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
        					SecurityCodeTryAgain frame = new SecurityCodeTryAgain();
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
            if (panel == cancel_btn) {
                // Open the ProductFrame
                LogInFrame login= new LogInFrame();
                login.setVisible(true);
                dispose();
            }
//            if (panel == modifySecu_btn) {
//                // Open the ProductFrame
//                ModifySecurity modify= new ModifySecurity();
//                modify.setVisible(true);
//                dispose();
//            }
            }
    }

	/**
	 * Create the frame.
	 */
	public EnterSecurityCodeFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EnterSecurityCodeFrame.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 348);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(155, 129, 123));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logo_lbl = new JLabel("");
		logo_lbl.setBounds(0, 0, 59, 56);
	    logo_lbl.setIcon(new ImageIcon(img_logo));
		contentPane.add(logo_lbl);
		
		JLabel lblStocksign = new JLabel("STOCKSIGN");
		lblStocksign.setForeground(new Color(65, 57, 83));
		lblStocksign.setFont(new Font("Britannic Bold", Font.PLAIN, 18));
		lblStocksign.setBounds(50, 21, 94, 23);
		contentPane.add(lblStocksign);
		
		JLabel kookaye_lbl = new JLabel("");
		kookaye_lbl.setBounds(537, 0, 50, 61);
	    kookaye_lbl.setIcon(new ImageIcon(img_kookaye));
		contentPane.add(kookaye_lbl);
		
		JLabel lblNewLabel_1 = new JLabel("ENTER SECURITY CODE");
		lblNewLabel_1.setForeground(new Color(242, 202, 165));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblNewLabel_1.setBounds(164, 74, 293, 41);
		contentPane.add(lblNewLabel_1);
		
		JPanel panel_password = new JPanel();
		panel_password.setLayout(null);
		panel_password.setBorder(null);
		panel_password.setBackground(Color.WHITE);
		panel_password.setBounds(35, 124, 517, 51);
		contentPane.add(panel_password);
		
		JLabel shield_icon_lbl = new JLabel("");
		shield_icon_lbl.setBounds(0, 0, 45, 53);
		shield_icon_lbl.setIcon(new ImageIcon(img_shield));
		panel_password.add(shield_icon_lbl);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBorder(null);
		passwordField.setBounds(65, 10, 442, 31);
		panel_password.add(passwordField);
		
		JPanel continue_btn = new JPanel();
		// Add hover effect to the continueButton
        Color continueOriginalColor = new Color(255, 232, 190);
        Color continueHoverColor = new Color(242, 202, 154);
        continue_btn.addMouseListener(new PanelButtonMouseAdapter(continue_btn, continueOriginalColor, continueHoverColor));	
        continue_btn.setLayout(null);
		continue_btn.setBackground(new Color(255, 232, 190));
		continue_btn.setBounds(123, 242, 141, 37);
		contentPane.add(continue_btn);
		
		JLabel continue_lbl = new JLabel("CONTINUE");
		continue_lbl.setForeground(new Color(80, 21, 0));
		continue_lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		continue_lbl.setBounds(30, 10, 96, 13);
		continue_btn.add(continue_lbl);
		
		continue_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	String securityCodeText = new String(passwordField.getPassword());
            	int securityCode = Integer.parseInt(securityCodeText);

            	codeAccess(securityCode);
            }
        });
		
		// Add hover effect to the cancelButton
        Color cancelOriginalColor = new Color(255, 232, 190);
        Color cancelHoverColor = new Color(242, 202, 154);
        cancel_btn.addMouseListener(new PanelButtonMouseAdapter(cancel_btn, cancelOriginalColor, cancelHoverColor));
		cancel_btn.setLayout(null);
		cancel_btn.setBackground(new Color(255, 232, 190));
		cancel_btn.setBounds(296, 242, 141, 37);
		contentPane.add(cancel_btn);
		
		JLabel cancel_lbl = new JLabel("CANCEL");
		cancel_lbl.setForeground(new Color(80, 21, 0));
		cancel_lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		cancel_lbl.setBounds(35, 10, 75, 13);
		cancel_btn.add(cancel_lbl);
		
//		// Add hover effect to the modifySecu
//        Color modifyOriginalColor = new Color(155, 129, 123);
//        Color modifyHoverColor = new Color(86, 26, 5);
//        modifySecu_btn.addMouseListener(new PanelButtonMouseAdapter(modifySecu_btn, modifyOriginalColor, modifyHoverColor));
//		modifySecu_btn.setLayout(null);
//		modifySecu_btn.setBackground(new Color(155, 129, 123));
//		modifySecu_btn.setBounds(222, 185, 148, 27);
//		contentPane.add(modifySecu_btn);
//		
//		JLabel modifySecu_lbl = new JLabel("MODIFY SECURITY CODE");
//		modifySecu_lbl.setForeground(new Color(242, 202, 165));
//		modifySecu_lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
//		modifySecu_lbl.setBounds(15, 10, 128, 13);
//		modifySecu_btn.add(modifySecu_lbl);
		
	}
}
