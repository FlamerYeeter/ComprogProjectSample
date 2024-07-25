import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;




public class HomeFrame extends JFrame {
		
	JPanel panel = new JPanel();
    JPanel productPanel = new JPanel();
    //To insert Images
    private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    private Image img_homeIcon = new ImageIcon(HomeFrame.class.getResource("res/homeIcon.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    private Image img_productIcon = new ImageIcon(HomeFrame.class.getResource("res/productIcon.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    private Image img_kookaye = new ImageIcon(HomeFrame.class.getResource("res/kookaye.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    private Image img_likeicon = new ImageIcon(HomeFrame.class.getResource("res/likeicon.png")).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
    private Image img_warning = new ImageIcon(HomeFrame.class.getResource("res/warning.png")).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
    private JPanel goodjobPanel;
    private JPanel warningPanel;
	private JTextField prodname_fixed_txt;
	private JTextField quanti_fixed_txt;
	static Connection conn;
	static Statement stmt;
    private JPanel contentPane;
    private List<JPanel> warningPanels;
    private JScrollPane warningScrollPane;
	
    //Create and display an instance of the HomeFrame class, which extends the JFrame class in Swing.
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HomeFrame frame = new HomeFrame();
                    frame.setVisible(true);
                    //AlertSystem.checkWarnings(frame);
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
    
   //Rounded Corners of the kookayeContainer and goodjobPanel 
    public class PanelBorder extends JPanel {

        public PanelBorder() {
            setOpaque(false); // Make the panel transparent

        }

        protected void paintComponent(Graphics grphcs) {
            super.paintComponent(grphcs);
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        }
    }
    class RoundBorder implements javax.swing.border.Border {
        private int radius;

        public RoundBorder(int radius) {
            this.radius = radius;
        }
        

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getBackground());
            g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
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
            if (panel == productPanel) {
                // Open the ProductFrame
                ProductFrame productFrame = new ProductFrame();
                productFrame.setVisible(true);
                dispose();
            }
        }
    }
	/**
	 * Create the frame.
	 */
	public HomeFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HomeFrame.class.getResource("/res/logoStock.png")));
	setResizable(false);
	setTitle("StockSign");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	// 	Calculate the centered position
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		int frameWidth = 861; // Set your desired width
		int frameHeight = 709; // Set your desired height
		int x = (screenWidth - frameWidth) / 2;
		int y = (screenHeight - frameHeight) / 2;
		setBounds(x, y, 833, 579);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 232, 190));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		//For the Navigation Bar
		JPanel navigationPanel = new JPanel();
		navigationPanel.setLayout(null);
		navigationPanel.setBackground(new Color(84, 26, 5));
		navigationPanel.setBounds(0, 0, 821, 88);
		contentPane.add(navigationPanel);
		ImageIcon logoIcon = new ImageIcon(img_logo);
    	logoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
    	
    	JLabel logoLabel = new JLabel();
    	logoLabel.setBounds(0, 0, 79, 89);
    	navigationPanel.add(logoLabel);
    	logoLabel.setIcon(logoIcon);
    	logoLabel.setPreferredSize(new java.awt.Dimension(90, 90));
    	logoLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        
    	//For the word, "STOCKSIGN"
		JLabel lblNewLabel = new JLabel("STOCKSIGN");
		lblNewLabel.setForeground(new Color(242, 202, 165));
		lblNewLabel.setFont(new Font("Britannic Bold", Font.BOLD, 30));
		lblNewLabel.setBounds(89, 27, 188, 30);
		navigationPanel.add(lblNewLabel);
		
		JPanel homePanel = new JPanel();
		// Add hover effect to the homePanel
        Color homeOriginalColor = new Color(84, 26, 5);
        Color homeHoverColor = new Color(64, 18, 2);
        homePanel.addMouseListener(new PanelButtonMouseAdapter(homePanel, homeOriginalColor, homeHoverColor));
	    homePanel.setBackground(new Color(84, 26, 5));
	    homePanel.setBounds(533, 0, 124, 89);
	    navigationPanel.add(homePanel);
	    homePanel.setLayout(null);
	    
		JLabel homelbl = new JLabel("HOME");
		homelbl.setForeground(new Color(255, 232, 190));
		homelbl.setFont(new Font("Open Sans", Font.BOLD, 17));
		homelbl.setBounds(59, 35, 65, 24);
		homePanel.add(homelbl);
		
		//container of the home icon picture
		JLabel homeIconlbl = new JLabel("");
	    homeIconlbl.setBounds(19, 24, 30, 41);
	    homePanel.add(homeIconlbl);
	    homeIconlbl.setIcon(new ImageIcon(img_homeIcon));
		
	    
	    // Add hover effect to the productPanel
        Color productOriginalColor = new Color(84, 26, 5);
        Color productHoverColor = new Color(64, 18, 2);
        productPanel.addMouseListener(new PanelButtonMouseAdapter(productPanel, productOriginalColor, productHoverColor));
        productPanel.setLayout(null);
        productPanel.setBackground(new Color(84, 26, 5));
        productPanel.setBounds(667, 0, 141, 89);
        navigationPanel.add(productPanel);
		
		JLabel lblProduct = new JLabel("PRODUCT");
		lblProduct.setForeground(new Color(255, 232, 190));
		lblProduct.setFont(new Font("Open Sans", Font.BOLD, 17));
		lblProduct.setBounds(48, 32, 83, 24);
		productPanel.add(lblProduct);
		
		//container of the product icon picture
		JLabel productIconlbl = new JLabel("");
	    productIconlbl.setBounds(8, 32, 30, 30);
	    productPanel.add(productIconlbl);
	    productIconlbl.setIcon(new ImageIcon(img_productIcon));
		
	    //kookaye panel for the greeting
	    JPanel kookayeContainer = new JPanel();
	    //PanelBorder kookayeContainer = new PanelBorder();		
	    kookayeContainer.setBackground(new Color(250, 208, 133));
		kookayeContainer.setBounds(10, 114, 793, 137);
		kookayeContainer.setBorder(new RoundBorder(40)); // Set rounded border
		contentPane.add(kookayeContainer);
		kookayeContainer.setLayout(null);
		
		//container of kookaye's logo
		JLabel businesslogolbl = new JLabel("");
	    businesslogolbl.setBounds(10, 22, 87, 84);
	    kookayeContainer.add(businesslogolbl);
	    businesslogolbl.setIcon(new ImageIcon(img_kookaye));
	    
	    JLabel greetinglbl = new JLabel("HELLO, KOOKAYE'S D'LITE!");
	    greetinglbl.setForeground(new Color(84, 26, 5));
	    greetinglbl.setFont(new Font("Arial Black", Font.PLAIN, 23));
	    greetinglbl.setBounds(100, 34, 414, 30);
	    kookayeContainer.add(greetinglbl);
	    
	    JLabel lblNewLabel_1 = new JLabel("It's nice to see you, again!");
	    lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
	    lblNewLabel_1.setBounds(100, 61, 350, 31);
	    kookayeContainer.add(lblNewLabel_1);
		
		JLabel inventorylbl = new JLabel("INVENTORY");
		inventorylbl.setForeground(new Color(80, 21, 0));
		inventorylbl.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		inventorylbl.setBounds(10, 262, 136, 19);
		contentPane.add(inventorylbl);
		
		JLabel alertlbl = new JLabel("ALERT");
		alertlbl.setForeground(new Color(210, 140, 11));
		alertlbl.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		alertlbl.setBounds(142, 262, 75, 19);
		contentPane.add(alertlbl);
		
		//Good Job panel indicates that there are no items from the inventory that are in high critical category
        //PanelBorder goodjobPanel = new PanelBorder();
        // Add hover effect to the goodjobPanel
        //Color goodjobOriginalColor = new Color(219, 134, 90);
        //Color goodjobHoverColor = new Color(29, 46, 96);
        //goodjobPanel.addMouseListener(new PanelButtonMouseAdapter(goodjobPanel, goodjobOriginalColor, goodjobHoverColor));
        goodjobPanel = new JPanel();
		goodjobPanel.setBackground(new Color(219, 134, 90));
		goodjobPanel.setBounds(10, 292, 793, 217);
		goodjobPanel.setBorder(new RoundBorder(40)); // Set rounded border
		contentPane.add(goodjobPanel);
		goodjobPanel.setLayout(null);
		
		//container of the picture inside the goodjobPanel
		JLabel likeIcon = new JLabel("");
	    likeIcon.setBounds(466, -24, 364, 255);
	    goodjobPanel.add(likeIcon);
	    likeIcon.setIcon(new ImageIcon(img_likeicon));
	    
	    JLabel lblNewLabel_2 = new JLabel("Good Job!");
	    lblNewLabel_2.setForeground(new Color(255, 227, 152));
	    lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.BOLD, 39));
	    lblNewLabel_2.setBounds(27, 43, 213, 65);
	    goodjobPanel.add(lblNewLabel_2);
	    
	    JLabel lblNewLabel_1_1 = new JLabel("All items are adequately filled! Keep up");
	    lblNewLabel_1_1.setForeground(new Color(255, 255, 255));
	    lblNewLabel_1_1.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
	    lblNewLabel_1_1.setBounds(53, 105, 350, 25);
	    goodjobPanel.add(lblNewLabel_1_1);
	    
	    JLabel lblNewLabel_1_1_1 = new JLabel("the good work!");
	    lblNewLabel_1_1_1.setForeground(Color.WHITE);
	    lblNewLabel_1_1_1.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
	    lblNewLabel_1_1_1.setBounds(53, 129, 350, 25);
	    goodjobPanel.add(lblNewLabel_1_1_1);
	    
	    WarningApp();
	    
	}
	
    public void WarningApp() {
        // Initialize warningPanels list
        warningPanels = new ArrayList<>();

        // Initialize warningScrollPane
        warningScrollPane = new JScrollPane();
        warningScrollPane.setBounds(10, 292, 793, 217);
        contentPane.add(warningScrollPane);

        // Example usage
        checkQuantityAlert();
        checkExpirationAlert();
        checkQuantityMatsAlert();
        checkExpirationMatsAlert();

        // Show/hide the warning panels and good job panel based on the presence of alerts
        updateWarningPanelsVisibility();

        // Refresh the contentPane to reflect the changes
        contentPane.revalidate();
        contentPane.repaint();
    }

//	private void addWarningPanel(JPanel warningPanel) {
//	    // Add the warning panel to the contentPane
//	    contentPane.add(warningPanel);
//	    // Refresh the contentPane to reflect the changes
//	    contentPane.revalidate();
//	    contentPane.repaint();
//	    // Set the warning panel to be visible
//	    warningPanel.setVisible(true);
//	}
	
    private JPanel createWarningPanel(String productName, String alertMessage) {
        JPanel warningPanel = new JPanel();
        warningPanel.setBackground(new Color(246, 211, 173));
        warningPanel.setBorder(new LineBorder(new Color(80, 21, 0), 2));
        warningPanel.setLayout(new BorderLayout(5, 5)); // Adjust spacing between components

        ImageIcon warningIcon = new ImageIcon(img_warning.getScaledInstance(120, 120, Image.SCALE_SMOOTH)); // Adjust icon size
        JLabel warninglbl_img = new JLabel(warningIcon);
        warningPanel.add(warninglbl_img, BorderLayout.WEST); // Add icon to the left side

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5)); // Adjust vertical spacing between texts
        textPanel.setBackground(new Color(246, 211, 173)); // Set background color to match the warning panel

        JPanel alertPanel = new JPanel(new BorderLayout());
        alertPanel.setOpaque(false);
        alertPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding to the panel

        JLabel alertLabel = new JLabel("Alert:   ");
        alertLabel.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Decrease font size for the label
        alertPanel.add(alertLabel, BorderLayout.WEST);

        JTextField alertTextField = new JTextField(alertMessage);
        alertTextField.setEditable(false);
        alertTextField.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Decrease font size for the text field
        alertTextField.setPreferredSize(new Dimension(300, 10)); // Reduce the width of the text field
        alertPanel.add(alertTextField, BorderLayout.CENTER);

        textPanel.add(alertPanel);

        JPanel productNamePanel = new JPanel(new BorderLayout());
        productNamePanel.setOpaque(false);
        productNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding to the panel

        JLabel itemLabel = new JLabel("Item:    ");
        itemLabel.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Decrease font size for the label
        productNamePanel.add(itemLabel, BorderLayout.WEST);

        JTextField productNameTextField = new JTextField(productName);
        productNameTextField.setEditable(false);
        productNameTextField.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Decrease font size for the text field
        productNameTextField.setPreferredSize(new Dimension(300, 10)); // Reduce the width of the text field
        productNamePanel.add(productNameTextField, BorderLayout.CENTER);

        textPanel.add(productNamePanel);

        warningPanel.add(textPanel, BorderLayout.CENTER); // Add text panel to the center

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Use FlowLayout for the button panel
        buttonPanel.setBackground(new Color(246, 211, 173)); // Set background color to match the warning panel

        JButton okayButton = new JButton("OKAY");
        okayButton.setForeground(new Color(80, 21, 0));
        okayButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Increase font size for the button text
        okayButton.setFocusPainted(false); // Remove button focus border
        okayButton.setBackground(new Color(241, 159, 11));
        okayButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16)); // Add padding to the button

        // Set hover color
        okayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                okayButton.setBackground(new Color(241, 139, 11)); // Specify the hover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                okayButton.setBackground(new Color(241, 159, 11)); // Revert back to the original color
            }
        });

        // Set click color
        okayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                okayButton.setBackground(new Color(241, 119, 11)); // Specify the click color
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                okayButton.setBackground(new Color(241, 159, 11)); // Revert back to the original color
            }
        });

        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the warning panel
                warningPanel.setVisible(false);
            }
        });


        buttonPanel.add(okayButton);
        warningPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom

        // Adjust the preferred size of the warning panel
        int panelWidth = 325; // Adjust the width as needed
        int panelHeight = warningPanel.getPreferredSize().height; // Use the preferred height of the panel
        warningPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Add the warning panel to the contentPane
        contentPane.add(warningPanel);

        return warningPanel;
    }


	private void checkQuantityAlert() {
	    List<String> alerts = new ArrayList<>();
	    try {
	        dbconnect();
	        ResultSet rs = stmt.executeQuery("SELECT P.prodName, P.quantity FROM productstb P INNER JOIN inventorytb I ON P.prodID = I.inventoryid WHERE P.quantity < 5 AND I.status != 'sold' AND I.critical_category != 'sold'");
	        while (rs.next()) {
	            String productName = rs.getString("prodName");
	            int quantity = rs.getInt("quantity");
	            alerts.add(productName + " - Quantity: " + quantity);

	            // Create a new warning panel for each alert
	            JPanel warningPanel = createWarningPanel(productName, "Quantity: " + quantity);
	            warningPanels.add(warningPanel);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    if (!alerts.isEmpty()) {
	        showWarningPanels();
	    }
	}

	private void checkExpirationAlert() {
	    List<String> alerts = new ArrayList<>();
	    try {
	        // Get the current date
	        LocalDate currentDate = LocalDate.now();
	        // Calculate the date 5 days from now
	        LocalDate expirationThreshold = currentDate.plusDays(5);
	        dbconnect();
	        ResultSet rs = stmt.executeQuery("SELECT P.prodName, P.EXPdate FROM productstb P INNER JOIN inventorytb I ON P.prodID = I.inventoryid WHERE P.EXPdate <= '" + expirationThreshold + "' AND I.status != 'sold' AND I.critical_category != 'sold'");
	        while (rs.next()) {
	            String productName = rs.getString("prodName");
	            Date expirationDate = rs.getDate("EXPdate");
	            alerts.add(productName + " - EXP Date: " + expirationDate);

	            // Create a new warning panel for each alert
	            JPanel warningPanel = createWarningPanel(productName, "EXP Date: " + expirationDate);
	            warningPanels.add(warningPanel);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    if (!alerts.isEmpty()) {
	        showWarningPanels();
	    }
	}
	
	private void checkQuantityMatsAlert() {
	    List<String> alerts = new ArrayList<>();
	    try {
	        dbconnect();
	        ResultSet rs = stmt.executeQuery("SELECT M.matsName, M.quantity FROM materialstb M INNER JOIN inventorytb I ON M.matsID = I.inventoryid WHERE M.quantity < 5 AND I.status != 'sold' AND I.critical_category != 'sold'");
	        while (rs.next()) {
	            String materialName = rs.getString("matsName");
	            int quantity = rs.getInt("quantity");
	            alerts.add(materialName + " - Quantity: " + quantity);

	            // Create a new warning panel for each alert
	            JPanel warningPanel = createWarningPanel(materialName, "Quantity: " + quantity);
	            warningPanels.add(warningPanel);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    if (!alerts.isEmpty()) {
	        showWarningPanels();
	    }
	}

	private void checkExpirationMatsAlert() {
	    List<String> alerts = new ArrayList<>();
	    try {
	        // Get the current date
	        LocalDate currentDate = LocalDate.now();
	        // Calculate the date 5 days from now
	        LocalDate expirationThreshold = currentDate.plusDays(5);
	        dbconnect();
	        ResultSet rs = stmt.executeQuery("SELECT M.matsName, M.EXPdate FROM materialstb M INNER JOIN inventorytb I ON M.matsID = I.inventoryid WHERE M.EXPdate <= '" + expirationThreshold + "' AND I.status != 'sold' AND I.critical_category != 'sold'");
	        while (rs.next()) {
	            String materialName = rs.getString("matsName");
	            Date expirationDate = rs.getDate("EXPdate");
	            alerts.add(materialName + " - EXP Date: " + expirationDate);

	            // Create a new warning panel for each alert
	            JPanel warningPanel = createWarningPanel(materialName, "EXP Date: " + expirationDate);
	            warningPanels.add(warningPanel);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    if (!alerts.isEmpty()) {
	        showWarningPanels();
	    }
	}


    private void showWarningPanels() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); // Use BoxLayout to display panels horizontally

        for (JPanel warningPanel : warningPanels) {
            panel.add(warningPanel);
        }

        // Set the panel as the view of the warningScrollPane
        warningScrollPane.setViewportView(panel);
        warningScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        warningScrollPane.revalidate();
    }

	private void updateWarningPanelsVisibility() {
	    if (warningPanels.isEmpty()) {
	        hideWarningPanels();
	        goodjobPanel.setVisible(true);
	    } else {
	        showWarningPanels();
	        goodjobPanel.setVisible(false);
	    }
	}

    private void hideWarningPanels() {
        warningPanels.clear();

        // Set null as the view of the warningScrollPane
        warningScrollPane.setViewportView(null);
        warningScrollPane.revalidate();
    }

}