import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class ModifySecurity extends JFrame {

	private JPanel contentPane;
	JPanel goBack_btn = new JPanel();
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
	private Image img_shield = new ImageIcon(HomeFrame.class.getResource("res/shield.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifySecurity frame = new ModifySecurity();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
                EnterSecurityCodeFrame entersecu= new EnterSecurityCodeFrame();
                entersecu.setVisible(true);
                dispose();
            }
            }
    }
	/**
	 * Create the frame.
	 */
	public ModifySecurity() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModifySecurity.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 454);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(155, 129, 123));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel stocklbl = new JLabel("");
		stocklbl.setBounds(167, -12, 203, 176);
	    stocklbl.setIcon(new ImageIcon(img_logo));
		contentPane.add(stocklbl);
		
		JPanel panel_password = new JPanel();
		panel_password.setLayout(null);
		panel_password.setBorder(null);
		panel_password.setBackground(Color.WHITE);
		panel_password.setBounds(10, 217, 466, 51);
		contentPane.add(panel_password);
		
		JLabel shield_lbl = new JLabel("");
		shield_lbl.setBounds(0, 0, 45, 53);
	    shield_lbl.setIcon(new ImageIcon(img_shield));
		panel_password.add(shield_lbl);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordField.setBorder(null);
		passwordField.setBounds(53, 10, 403, 31);
		panel_password.add(passwordField);
		
		JPanel save_btn = new JPanel();
		// Add hover effect to the cancelButton
        Color saveOriginalColor = new Color(255, 232, 190);
        Color saveHoverColor = new Color(242, 202, 154);
        save_btn.addMouseListener(new PanelButtonMouseAdapter(save_btn, saveOriginalColor, saveHoverColor));
		save_btn.setLayout(null);
		save_btn.setBackground(new Color(255, 232, 190));
		save_btn.setBounds(137, 341, 192, 37);
		contentPane.add(save_btn);
		
		JLabel save_lbl = new JLabel("SAVE CHANGES");
		save_lbl.setForeground(new Color(80, 21, 0));
		save_lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		save_lbl.setBounds(35, 10, 141, 13);
		save_btn.add(save_lbl);
		
		JLabel lblNewLabel_1 = new JLabel("ENTER  NEW SECURITY CODE:");
		lblNewLabel_1.setForeground(new Color(242, 202, 165));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 166, 348, 41);
		contentPane.add(lblNewLabel_1);
		
		JCheckBox chckbxShowSecurityCode = new JCheckBox("Show Security Code");
		chckbxShowSecurityCode.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		chckbxShowSecurityCode.setBackground(new Color(155, 129, 123));
		chckbxShowSecurityCode.setBounds(6, 277, 156, 21);
		contentPane.add(chckbxShowSecurityCode);
		
		// Add hover effect to the goBack
        Color backOriginalColor = new Color(155, 129, 123);
        Color backHoverColor = new Color(86, 26, 5);
        goBack_btn.addMouseListener(new PanelButtonMouseAdapter(goBack_btn, backOriginalColor, backHoverColor));
		goBack_btn.setLayout(null);
		goBack_btn.setBackground(new Color(155, 129, 123));
		goBack_btn.setBounds(411, 390, 75, 27);
		contentPane.add(goBack_btn);
		
		JLabel goBack_lbl = new JLabel("GO BACK");
		goBack_lbl.setForeground(new Color(242, 202, 165));
		goBack_lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
		goBack_lbl.setBounds(10, 10, 55, 13);
		goBack_btn.add(goBack_lbl);
		//Coding Part of showPassword JCheckBox
		chckbxShowSecurityCode.addItemListener(new ItemListener() {
	            public void itemStateChanged(ItemEvent e) {
	                if (chckbxShowSecurityCode.isSelected()) passwordField.setEchoChar((char)0);
	                else 
	                    if(!String.valueOf(passwordField.getPassword()).equals("Password"))passwordField.setEchoChar('●');
	            }
	        });

	}
}
