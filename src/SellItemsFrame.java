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
import java.awt.event.ActionEvent;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.Toolkit;

public class SellItemsFrame extends JFrame {

	private JPanel contentPane;
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	JPanel confirm_btn = new JPanel();
	private static JTextField textField;
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String date, category, prodName, matsID, matsName, quantity, price, inventoryID;
	private static int prodID;
	private static int prodIDInt;
	private static String productNameString;
	private JLabel prodName_lbl;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellItemsFrame frame = new SellItemsFrame(prodID, prodName);
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
	    public void mouseClicked(MouseEvent e) {
            if (panel == confirm_btn) {
                // Open the EditFrame
            	SuccessSoldFrame successFrame = new SuccessSoldFrame();
            	successFrame.setVisible(true);
            	dispose();
            }
        }
        
    }
	
	public static Connection dbconnect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocksign", "root", "");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	
	
	/**
	 * Create the frame.
	 */
	public SellItemsFrame(int prodID, String productNameString) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SellItemsFrame.class.getResource("/res/logoStock.png")));
		this.prodIDInt = prodID;
		this.productNameString = prodName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 391);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SELL");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setForeground(new Color(241, 159, 11));
		lblNewLabel.setBounds(264, 64, 77, 40);
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
		
		JPanel cancel_btn = new JPanel();
		// Add hover effect to the cancel_btn
		Color cancelOriginalColor = new Color(255, 200, 102);
		Color cancelHoverColor = new Color(241, 159, 11);
		cancel_btn.addMouseListener(new PanelButtonMouseAdapter(cancel_btn, cancelOriginalColor, cancelHoverColor));
		cancel_btn.setLayout(null);
		cancel_btn.setBackground(new Color(255, 200, 102));
		cancel_btn.setBounds(119, 305, 98, 25);
		contentPane.add(cancel_btn);
		cancel_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose(); // Exit the application
		    }
		});
		
		
		JLabel add_lbl = new JLabel("CANCEL");
		add_lbl.setForeground(new Color(84, 26, 5));
		add_lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
		add_lbl.setBounds(23, 0, 54, 27);
		cancel_btn.add(add_lbl);
		cancel_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose(); // Exit the application
		    }
		});
		
	
		// Add hover effect to the cancel_btn
		Color confirmOriginalColor = new Color(255, 200, 102);
		Color confirmHoverColor = new Color(241, 159, 11);
		confirm_btn.addMouseListener(new PanelButtonMouseAdapter(confirm_btn, confirmOriginalColor, confirmHoverColor));
		confirm_btn.setLayout(null);
		confirm_btn.setBackground(new Color(255, 200, 102));
		confirm_btn.setBounds(363, 305, 114, 25);
		contentPane.add(confirm_btn);
		
		JLabel add_lbl_1 = new JLabel("CONFIRM");
		add_lbl_1.setForeground(new Color(84, 26, 5));
		add_lbl_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		add_lbl_1.setBounds(23, 0, 65, 27);
		confirm_btn.add(add_lbl_1);
		confirm_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	SellItemsFrame.sellItem(prodIDInt);; // Exit the application
		    }
		});
		
		SellFrame sellFrame = new SellFrame(prodID, productNameString);
		String sellProdName = SellFrame.getProductName();
		prodName_lbl = new JLabel();
		prodName_lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		prodName_lbl.setBounds(60, 123, 204, 25);
		contentPane.add(prodName_lbl);
		prodName_lbl.setText(sellProdName);
		
		JPanel minus_btn = new JPanel();
		//Add hover effect to the minus_btn
		Color minusOriginalColor = new Color(255, 200, 102);
		Color minusHoverColor = new Color(241, 159, 11);
		minus_btn.addMouseListener(new PanelButtonMouseAdapter(minus_btn, minusOriginalColor, minusHoverColor));
		minus_btn.setLayout(null);
		minus_btn.setBackground(new Color(255, 200, 102));
		minus_btn.setBounds(243, 123, 42, 25);
		contentPane.add(minus_btn);
		
		JLabel minus_lbl = new JLabel("-");
		minus_lbl.setBounds(16, 0, 29, 18);
		minus_btn.add(minus_lbl);
		minus_lbl.setForeground(new Color(84, 26, 5));
		minus_lbl.setFont(new Font("Arial Black", Font.BOLD, 22));
		
		minus_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Decrease the quantity by 1
		        String input = textField.getText();
		        if (!input.isEmpty()) {
		            int currentQuantity = Integer.parseInt(input);
		            if (currentQuantity > 0) {
		                textField.setText(String.valueOf(currentQuantity - 1));
		            }
		        }
		    }
		});
		
		textField = new JTextField();
		textField.setBounds(295, 124, 96, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText("0"); // Replace "0" with the desired initial quantity value
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 237, 217));
		panel.setBounds(234, 114, 216, 40);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel plus_btn_1 = new JPanel();
		//Add hover effect to the minus_btn
		Color plusOriginalColor = new Color(255, 200, 102);
		Color plusHoverColor = new Color(241, 159, 11);
		plus_btn_1.addMouseListener(new PanelButtonMouseAdapter(plus_btn_1, plusOriginalColor, plusHoverColor));
		plus_btn_1.setBounds(164, 10, 42, 25);
		panel.add(plus_btn_1);
		plus_btn_1.setLayout(null);
		plus_btn_1.setBorder(new LineBorder(new Color(80, 21, 0), 0));
		plus_btn_1.setBackground(new Color(255, 200, 102));
		
		JLabel plus_lbl_1 = new JLabel("+");
		plus_lbl_1.setForeground(new Color(84, 26, 5));
		plus_lbl_1.setFont(new Font("Arial Black", Font.BOLD, 22));
		plus_lbl_1.setBounds(15, 0, 29, 22);
		plus_btn_1.add(plus_lbl_1);
		
		plus_btn_1.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Increase the quantity by 1
		        String input = textField.getText();
		        if (!input.isEmpty()) {
		            int currentQuantity = Integer.parseInt(input);
		            textField.setText(String.valueOf(currentQuantity + 1));
		        }
		    }
		});
		
	}

	public void setProdID(int prodID) {
	    SellItemsFrame.prodID = prodID;
	}

	// You can access the prodID later when needed
	public int getProdID() {
	    return prodID;
	}

	@SuppressWarnings("resource")
	public static void sellItem(int prodID) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    // Access the prodID from SellFrame and SellItemFrame
	    SellFrame sellFrame = new SellFrame(prodID, productNameString);
	    int sellProdID = sellFrame.getProdID();

	    try {
	        // Establish a database connection
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocksign", "root", "");

	        // Retrieve the current quantity and other details of the product
	        String quantityQuery = "SELECT * FROM productstb WHERE prodID = ?";
	        stmt = conn.prepareStatement(quantityQuery);
	        stmt.setInt(1, sellProdID);
	        rs = stmt.executeQuery();
	        int sellQuantity = Integer.parseInt(textField.getText());
	        if (rs.next()) {
	            int availableQuantity = rs.getInt("quantity");
	            String prodName = rs.getString("prodName");
	            String category = rs.getString("category");
	            int price = rs.getInt("price");
	            String expDate = rs.getString("EXPdate");

	            System.out.println("Available Quantity: " + availableQuantity);

	            if (sellQuantity <= availableQuantity) {
	                // Update the quantity and status of the sold item in the inventorytb table
	                String updateInventoryQuery = "INSERT INTO inventorytb (quantity, status, critical_category) " +
	                        "VALUES (?, 'sold', 'sold')";
	                stmt = conn.prepareStatement(updateInventoryQuery, PreparedStatement.RETURN_GENERATED_KEYS);
	                stmt.setInt(1, sellQuantity);
	                stmt.executeUpdate();
	                rs = stmt.getGeneratedKeys();
	                int newInventoryID = -1;
	                if (rs.next()) {
	                    newInventoryID = rs.getInt(1);
	                }

	                // Update the quantity of the existing product in the productstb table
	                String updateProductQuery = "UPDATE productstb SET quantity = ? WHERE prodID = ?";
	                stmt = conn.prepareStatement(updateProductQuery);
	                stmt.setInt(1, availableQuantity - sellQuantity);
	                stmt.setInt(2, sellProdID);
	                stmt.executeUpdate();
	                // Ensures that no duplicates happen here :monet:
	                Random rand = new Random();
	                int int_random = rand.nextInt(1000000);  
	                int newProdID = sellProdID + int_random;
	                String insertSoldItemQuery = "INSERT INTO productstb (prodID, inventoryID, prodName, category, quantity, price, EXPdate) " +
	                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
	                stmt = conn.prepareStatement(insertSoldItemQuery);
	                stmt.setInt(1, newProdID); // Replace newProdID with the desired value for prodID
	                stmt.setInt(2, newInventoryID);
	                stmt.setString(3, prodName);
	                stmt.setString(4, category);
	                stmt.setInt(5, sellQuantity);
	                stmt.setInt(6, price);
	                stmt.setString(7, expDate);
	                stmt.executeUpdate();

	                System.out.println("Item sold successfully.");
	            } else {
	                System.out.println("Insufficient quantity available.");
	            }
	        } else {
	            System.out.println("Product not found.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close the result set, statement, and connection
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (stmt != null) {
	                stmt.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

}
