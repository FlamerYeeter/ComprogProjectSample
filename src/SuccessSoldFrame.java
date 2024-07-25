import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class SuccessSoldFrame extends JFrame {

	private JPanel contentPane;
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	JPanel okay_btn = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SuccessSoldFrame frame = new SuccessSoldFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
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
	public SuccessSoldFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SuccessSoldFrame.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 437, 513);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Successfully Sold");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setForeground(new Color(241, 159, 11));
		lblNewLabel.setBounds(105, 64, 209, 40);
		contentPane.add(lblNewLabel);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(null);
		headerPanel.setBackground(new Color(84, 26, 5));
		headerPanel.setBounds(0, 0, 1086, 54);
		contentPane.add(headerPanel);
		
		JLabel logolbl = new JLabel("");
		logolbl.setBounds(10, 0, 49, 53);
		logolbl.setIcon(new ImageIcon(img_logo));
		headerPanel.add(logolbl);
		
		JLabel lblNewLabel_1 = new JLabel("STOCKSIGN");
		lblNewLabel_1.setForeground(new Color(242, 202, 165));
		lblNewLabel_1.setFont(new Font("Britannic Bold", Font.PLAIN, 21));
		lblNewLabel_1.setBounds(57, 20, 114, 24);
		headerPanel.add(lblNewLabel_1);
		
		// Add hover effect to the cancel_btn
		Color okayOriginalColor = new Color(255, 200, 102);
		Color okayHoverColor = new Color(241, 159, 11);
		okay_btn.addMouseListener(new PanelButtonMouseAdapter(okay_btn, okayOriginalColor, okayHoverColor));
		okay_btn.setLayout(null);
		okay_btn.setBackground(new Color(255, 200, 102));
		okay_btn.setBounds(145, 441, 114, 25);
		contentPane.add(okay_btn);
		okay_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose(); // Exit the application
		    }
		});
		
		
		JLabel add_lbl_1 = new JLabel("OKAY");
		add_lbl_1.setForeground(new Color(84, 26, 5));
		add_lbl_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		add_lbl_1.setBounds(36, 0, 49, 27);
		okay_btn.add(add_lbl_1);
	}

}
