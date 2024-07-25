import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.awt.Toolkit;


public class AddFrame extends JFrame {

	private JPanel contentPane;
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private JTextField category_txt;
	private JTextField id_txt;
	private JTextField quantity_txt;
	private JTextField price_txt;
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String query;
	static String date, category, prodID, prodName, matsID, matsName, quantity, price, inventoryID;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFrame frame = new AddFrame();
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
	}

	/**
	 * Create the frame.
	 */
	public AddFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddFrame.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 409);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(84, 26, 5));
		headerPanel.setBounds(0, 0, 579, 54);
		contentPane.add(headerPanel);
		headerPanel.setLayout(null);
		
		JLabel logolbl = new JLabel("");
		logolbl.setBounds(10, 0, 49, 53);
		logolbl.setIcon(new ImageIcon(img_logo));
		headerPanel.add(logolbl);
		
		JLabel lblNewLabel = new JLabel("STOCKSIGN");
		lblNewLabel.setForeground(new Color(242, 202, 165));
		lblNewLabel.setFont(new Font("Britannic Bold", Font.PLAIN, 19));
		lblNewLabel.setBounds(57, 20, 114, 24);
		headerPanel.add(lblNewLabel);
		
		JPanel categ_panel = new JPanel();
		categ_panel.setBackground(new Color(248, 237, 217));
		categ_panel.setLayout(null);
		categ_panel.setBounds(10, 64, 559, 35);
		contentPane.add(categ_panel);
		
		JLabel categlbl = new JLabel("CATEGORY:");
		categlbl.setForeground(new Color(84, 26, 5));
		categlbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		categlbl.setBounds(10, 10, 123, 13);
		categ_panel.add(categlbl);
		
		category_txt = new JTextField();
		category_txt.setBounds(185, 10, 340, 19);
		categ_panel.add(category_txt);
		category_txt.setColumns(10);
		
		JPanel id_panel = new JPanel();
		id_panel.setBackground(new Color(248, 237, 217));
		id_panel.setLayout(null);
		id_panel.setBounds(10, 98, 559, 35);
		contentPane.add(id_panel);
		
		JLabel prodidlbl = new JLabel("PRODUCT ID:");
		prodidlbl.setForeground(new Color(84, 26, 5));
		prodidlbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		prodidlbl.setBounds(10, 10, 123, 13);
		id_panel.add(prodidlbl);
		
		id_txt = new JTextField();
		id_txt.setColumns(10);
		id_txt.setBounds(185, 10, 340, 19);
		id_panel.add(id_txt);
		
		JPanel prodname_panel = new JPanel();
		prodname_panel.setBackground(new Color(248, 237, 217));
		prodname_panel.setLayout(null);
		prodname_panel.setBounds(10, 132, 559, 35);
		contentPane.add(prodname_panel);
		
		JLabel prodnamelbl = new JLabel("PRODUCT NAME:");
		prodnamelbl.setForeground(new Color(84, 26, 5));
		prodnamelbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		prodnamelbl.setBounds(10, 10, 149, 13);
		prodname_panel.add(prodnamelbl);
		
		final JTextField prodname_txt = new JTextField();
		prodname_txt.setColumns(10);
		prodname_txt.setBounds(185, 10, 340, 19);
		prodname_panel.add(prodname_txt);
		
		JPanel quantity_panel = new JPanel();
		quantity_panel.setBackground(new Color(248, 237, 217));
		quantity_panel.setLayout(null);
		quantity_panel.setBounds(10, 167, 559, 35);
		contentPane.add(quantity_panel);
		
		JLabel quantitylbl = new JLabel("QUANTITY:");
		quantitylbl.setForeground(new Color(84, 26, 5));
		quantitylbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		quantitylbl.setBounds(10, 10, 149, 13);
		quantity_panel.add(quantitylbl);
		
		quantity_txt = new JTextField();
		quantity_txt.setColumns(10);
		quantity_txt.setBounds(185, 10, 340, 19);
		quantity_panel.add(quantity_txt);
		
		JPanel price_panel = new JPanel();
		price_panel.setBackground(new Color(248, 237, 217));
		price_panel.setLayout(null);
		price_panel.setBounds(10, 202, 559, 35);
		contentPane.add(price_panel);
		
		JLabel pricelbl = new JLabel("PRICE:");
		pricelbl.setForeground(new Color(84, 26, 5));
		pricelbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		pricelbl.setBounds(10, 10, 149, 13);
		price_panel.add(pricelbl);
		
		price_txt = new JTextField();
		price_txt.setColumns(10);
		price_txt.setBounds(185, 10, 340, 19);
		price_panel.add(price_txt);
		
		JPanel date_panel = new JPanel();
		date_panel.setBounds(10, 239, 559, 35);
		contentPane.add(date_panel);
		date_panel.setBackground(new Color(248, 237, 217));
		date_panel.setLayout(null);
		
		JLabel datelbl = new JLabel("EXPIRATION DATE:");
		datelbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		datelbl.setForeground(new Color(84, 26, 5));
		datelbl.setBounds(10, 10, 154, 13);
		date_panel.add(datelbl);
		
		final JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(185, 10, 339, 19);
		date_panel.add(dateChooser);
		
		JPanel save_btn = new JPanel();
		// Add hover effect to the save_btn
        Color homeOriginalColor = new Color(255, 200, 102);
        Color homeHoverColor = new Color(241, 159, 11);
        save_btn.addMouseListener(new PanelButtonMouseAdapter(save_btn, homeOriginalColor, homeHoverColor));
		save_btn.setBackground(new Color(255, 200, 102));
		save_btn.setBounds(28, 308, 113, 35);
		contentPane.add(save_btn);
		save_btn.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("SAVE");
		lblNewLabel_2.setForeground(new Color(84, 26, 5));
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_2.setBounds(34, 10, 45, 13);
		save_btn.add(lblNewLabel_2);

		save_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Get the values from the input fields
		        prodID = id_txt.getText();
		        prodName = prodname_txt.getText();
		        category = category_txt.getText();
		        quantity = quantity_txt.getText();
		        price = price_txt.getText();

		        // Insert the values into the inventorytb table
		        dbconnect();
		        try {
		            // Insert the values into the inventorytb table
		            query = "INSERT INTO inventorytb (quantity, status, critical_category) VALUES ("
		                    + quantity + ",'in inventory',"
		                    + "CASE "
		                    + "WHEN " + quantity + " > 60 THEN 'Low' "
		                    + "WHEN " + quantity + " >= 20 THEN 'Medium' "
		                    + "ELSE 'High' "
		                    + "END)";
		            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

		            // Get the generated inventoryID
		            ResultSet rs = stmt.getGeneratedKeys();
		            int inventoryID = 0;
		            if (rs.next()) {
		                inventoryID = rs.getInt(1);
		            }
		            rs.close();

		            // Check if the category is "Dry Goods" or "Wet Goods"
		            if (category.equalsIgnoreCase("Dry Goods") || category.equalsIgnoreCase("Wet Goods")) {
		                // Insert the values into the materialstb table
		                query = "INSERT INTO materialstb (matsID, inventoryID, matsName, category, quantity, price, EXPdate, addedDate) VALUES ("
		                		+ prodID + "," + inventoryID + ",'" + prodName + "','" + category + "'," + quantity + ","
		                        + price + ", CURRENT_DATE(), CURRENT_DATE())";
		            } else {
		                // Insert the values into the productstb table
		                query = "INSERT INTO productstb (prodID, inventoryID, prodName, category, quantity, price, EXPdate, addedDate) VALUES ("
		                        + prodID + "," + inventoryID + ",'" + prodName + "','" + category + "'," + quantity + ","
		                        + price + ", CURRENT_DATE(), CURRENT_DATE())";
		            }

		            stmt.executeUpdate(query);
		            System.out.println("Product added successfully.");
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});


		
		JPanel clr_btn = new JPanel();
		// Add hover effect to the reset_btn
        Color clrOriginalColor = new Color(255, 200, 102);
        Color clrHoverColor = new Color(241, 159, 11);
        clr_btn.addMouseListener(new PanelButtonMouseAdapter(clr_btn, clrOriginalColor, clrHoverColor));
		clr_btn.setBackground(new Color(255, 200, 102));
		clr_btn.setBounds(234, 308, 113, 35);
		contentPane.add(clr_btn);
		clr_btn.setLayout(null);
		
		JLabel reset_lbl = new JLabel("RESET");
		reset_lbl.setForeground(new Color(84, 26, 5));
		reset_lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
		reset_lbl.setBounds(33, 10, 45, 13);
		clr_btn.add(reset_lbl);
		// Reset button functionality
		clr_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Clear the input fields
		        prodname_txt.setText(""); // Clear product name field
		        category_txt.setText("");
		        id_txt.setText("");
		        quantity_txt.setText("");
		        price_txt.setText("");
		        dateChooser.setDate(null);
		    }
		});
		JPanel cancel_btn = new JPanel();
		// Add hover effect to the cancel_btn
        Color cancelOriginalColor = new Color(255, 200, 102);
        Color cancelHoverColor = new Color(241, 159, 11);
        cancel_btn.addMouseListener(new PanelButtonMouseAdapter(cancel_btn, cancelOriginalColor, cancelHoverColor));
		cancel_btn.setBackground(new Color(255, 200, 102));
		cancel_btn.setBounds(430, 308, 103, 35);
		contentPane.add(cancel_btn);
		cancel_btn.setLayout(null);
		
		JLabel lblNewLabel_2_2 = new JLabel("CLOSE");
		lblNewLabel_2_2.setForeground(new Color(84, 26, 5));
		lblNewLabel_2_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_2_2.setBounds(28, 10, 51, 13);
		cancel_btn.add(lblNewLabel_2_2);
		
		
		cancel_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose(); // Exit the application
		    }
		});
	}
	
}