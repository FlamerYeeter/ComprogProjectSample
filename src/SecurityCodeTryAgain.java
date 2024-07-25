import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

public class SecurityCodeTryAgain extends JFrame {

	private JPanel contentPane;
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image img_kookaye = new ImageIcon(HomeFrame.class.getResource("res/kookaye.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image img_error = new ImageIcon(HomeFrame.class.getResource("res/error.png")).getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

    }


	/**
	 * Create the frame.
	 */
	public SecurityCodeTryAgain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SecurityCodeTryAgain.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 567, 291);
		setTitle("StockSign");
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(155, 129, 123));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

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
		kookaye_lbl.setBounds(499, 0, 50, 61);
	    kookaye_lbl.setIcon(new ImageIcon(img_kookaye));
		contentPane.add(kookaye_lbl);
		
		JLabel cannotfind_img_lbl = new JLabel("");
		cannotfind_img_lbl.setBounds(-67, 51, 245, 166);
		cannotfind_img_lbl.setIcon(new ImageIcon(img_error));
		contentPane.add(cannotfind_img_lbl);
		
		JLabel lblNewLabel_1_1 = new JLabel("INCORRECT SECURITY CODE!");
		lblNewLabel_1_1.setForeground(new Color(242, 202, 165));
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 30));
		lblNewLabel_1_1.setBounds(107, 102, 423, 54);
		contentPane.add(lblNewLabel_1_1);
		
		JPanel tryAgain_btn = new JPanel();
		// Add hover effect to the loginPanel
        Color homeOriginalColor = new Color(255, 232, 190);
        Color homeHoverColor = new Color(242, 202, 154);
        tryAgain_btn.addMouseListener(new PanelButtonMouseAdapter(tryAgain_btn, homeOriginalColor, homeHoverColor));
		tryAgain_btn.setLayout(null);
		tryAgain_btn.setBackground(new Color(255, 232, 190));
		tryAgain_btn.setBounds(147, 196, 234, 37);
		contentPane.add(tryAgain_btn);
		
		tryAgain_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose(); // Exit the application
		    }
		});

		JLabel tryAgain_lbl = new JLabel("TRY AGAIN");
		tryAgain_lbl.setForeground(new Color(80, 21, 0));
		tryAgain_lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		tryAgain_lbl.setBounds(68, 10, 96, 13);
		tryAgain_btn.add(tryAgain_lbl);
	}
}
