import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JRadioButton;
import java.awt.Toolkit;


public class EditFrame extends JFrame {

	private JPanel contentPane;
	
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	private JTextField category_txt;

	private JTextField quantity_txt;
	private JTextField price_txt;
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String query;
	static String date, category;
	static String prodName;
	static String matsID;
	static String matsName;
	static String quantity;
	static String price;
    private static String prodID;
    private static String inventoryID;
	private JTextField inventoryID_txt;
	private JTextField id_txt;
	public static String selectedProdID;
	public static String selectedInventoryID;
	public static JTable prodTable;
	public static String prodIDString;
	public static String inventoryIDString;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditFrame frame = new EditFrame(prodIDString, inventoryIDString);
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

	public EditFrame(String prodIDString, String inventoryIDString) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditFrame.class.getResource("/res/logoStock.png")));
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		setTitle("StockSign");
		setResizable(false);
		contentPane.setLayout(null);
		
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
		
		JPanel inventoryID_Pnl = new JPanel();
		inventoryID_Pnl.setLayout(null);
		inventoryID_Pnl.setBackground(new Color(248, 237, 217));
		inventoryID_Pnl.setBounds(10, 95, 559, 35);
		contentPane.add(inventoryID_Pnl);
		
		JLabel inventoryIDLbl = new JLabel("INVENTORY ID:");
		inventoryIDLbl.setForeground(new Color(84, 26, 5));
		inventoryIDLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		inventoryIDLbl.setBounds(10, 10, 123, 13);
		inventoryID_Pnl.add(inventoryIDLbl);
		
		inventoryID_txt = new JTextField();
		inventoryID_txt.setColumns(10);
		inventoryID_txt.setBounds(185, 10, 340, 19);
		inventoryID_Pnl.add(inventoryID_txt);
		inventoryID_txt.setText(inventoryIDString);
		
		JPanel date_panel = new JPanel();
		date_panel.setBackground(new Color(248, 237, 217));
		date_panel.setBounds(10, 307, 559, 35);
		contentPane.add(date_panel);
		date_panel.setLayout(null);
		
		JLabel datelbl = new JLabel("DATE:");
		datelbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		datelbl.setForeground(new Color(84, 26, 5));
		datelbl.setBounds(10, 10, 60, 13);
		date_panel.add(datelbl);
		
		final JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(185, 10, 339, 19);
		date_panel.add(dateChooser);
		
		JPanel categ_panel = new JPanel();
		categ_panel.setBackground(new Color(248, 237, 217));
		categ_panel.setLayout(null);
		categ_panel.setBounds(10, 168, 559, 35);
		contentPane.add(categ_panel);
		
		JLabel categlbl = new JLabel("CATEGORY:");
		categlbl.setForeground(new Color(84, 26, 5));
		categlbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
		categlbl.setBounds(10, 10, 123, 15);
		categ_panel.add(categlbl);
		
		category_txt = new JTextField();
		category_txt.setBounds(185, 10, 340, 19);
		categ_panel.add(category_txt);
		category_txt.setColumns(10);
		
		JPanel id_panel = new JPanel();
		id_panel.setBackground(new Color(248, 237, 217));
		id_panel.setLayout(null);
		id_panel.setBounds(10, 133, 559, 35);
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
		id_txt.setText(prodIDString);
		
		JPanel prodname_panel = new JPanel();
		prodname_panel.setBackground(new Color(248, 237, 217));
		prodname_panel.setLayout(null);
		prodname_panel.setBounds(10, 202, 559, 35);
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
		quantity_panel.setBounds(10, 237, 559, 35);
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
		price_panel.setBounds(10, 273, 559, 35);
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
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 94, 559, -31);
		contentPane.add(panel);

		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton rdbtn_prod = new JRadioButton("Products");
		rdbtn_prod.setForeground(new Color(84, 26, 5));
		rdbtn_prod.setFont(new Font("Segoe UI", Font.BOLD, 13));
		rdbtn_prod.setBackground(new Color(248, 237, 217));
		rdbtn_prod.setBounds(164, 68, 103, 21);
		contentPane.add(rdbtn_prod);

		JRadioButton rdbtn_mats = new JRadioButton("Materials");
		rdbtn_mats.setForeground(new Color(84, 26, 5));
		rdbtn_mats.setFont(new Font("Segoe UI", Font.BOLD, 13));
		rdbtn_mats.setBackground(new Color(248, 237, 217));
		rdbtn_mats.setBounds(319, 68, 103, 21);
		contentPane.add(rdbtn_mats);

		buttonGroup.add(rdbtn_prod);
		buttonGroup.add(rdbtn_mats);
		
		JPanel save_btn = new JPanel();
		// Add hover effect to the save_btn
        Color homeOriginalColor = new Color(255, 200, 102);
        Color homeHoverColor = new Color(241, 159, 11);
        save_btn.addMouseListener(new PanelButtonMouseAdapter(save_btn, homeOriginalColor, homeHoverColor));
		save_btn.setBackground(new Color(255, 200, 102));
		save_btn.setBounds(27, 352, 113, 35);
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
		        // Get the selected table based on the radio button
		        String table;
		        if (rdbtn_prod.isSelected()) {
		            table = "productstb";
		        } else if (rdbtn_mats.isSelected()) {
		            table = "materialstb";
		        } else {
		            // No table selected, handle accordingly
		            return;
		        }

		        // Get the values from the input fields
		        String id = id_txt.getText();
		        String name = prodname_txt.getText();
		        category = category_txt.getText();
		        quantity = quantity_txt.getText();
		        price = price_txt.getText();
		        date = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
		        inventoryID = inventoryID_txt.getText();

		        // Format the date to MySQL-compatible format
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        String formattedDate = dateFormat.format(dateChooser.getDate());

		        // Update the values in the specified table
		        dbconnect();
		        try {
		            // Update the inventorytb table
		            query = "UPDATE inventorytb SET quantity = " + quantity + ", critical_category = "
		                    + "CASE "
		                    + "WHEN " + quantity + " > 60 THEN 'Low' "
		                    + "WHEN " + quantity + " >= 26 THEN 'Medium' "
		                    + "ELSE 'High' "
		                    + "END WHERE inventoryID = " + inventoryID;
		            stmt.executeUpdate(query);

		            // Update the specified table
		         // Update the specified table
		            String query;
		            if (table.equals("productstb")) {
		                query = "UPDATE " + table + " SET prodName = '" + name + "', category = '" + category + "', quantity = "
		                        + quantity + ", price = " + price + ", EXPdate = '" + formattedDate + "' WHERE prodID = " + id;
		            } else if (table.equals("materialstb")) {
		                query = "UPDATE " + table + " SET matsName = '" + name + "', category = '" + category + "', quantity = "
		                        + quantity + ", price = " + price + ", EXPdate = '" + formattedDate + "' WHERE matsID = " + id;
		            } else {
		                // Invalid table name, handle accordingly
		                return;
		            }

		            stmt.executeUpdate(query);


		            System.out.println("Record updated successfully.");
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        dispose();
		        
		    }
		});


		
		JPanel clr_btn = new JPanel();
		// Add hover effect to the reset_btn
        Color clrOriginalColor = new Color(255, 200, 102);
        Color clrHoverColor = new Color(241, 159, 11);
        clr_btn.addMouseListener(new PanelButtonMouseAdapter(clr_btn, clrOriginalColor, clrHoverColor));
		clr_btn.setBackground(new Color(255, 200, 102));
		clr_btn.setBounds(233, 352, 113, 35);
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
		        inventoryID_txt.setText("");
		        dateChooser.setDate(null);
		    }
		});
		JPanel cancel_btn = new JPanel();
		// Add hover effect to the cancel_btn
        Color cancelOriginalColor = new Color(255, 200, 102);
        Color cancelHoverColor = new Color(241, 159, 11);
        cancel_btn.addMouseListener(new PanelButtonMouseAdapter(cancel_btn, cancelOriginalColor, cancelHoverColor));
		cancel_btn.setBackground(new Color(255, 200, 102));
		cancel_btn.setBounds(429, 352, 103, 35);
		contentPane.add(cancel_btn);
		cancel_btn.setLayout(null);
		
		JLabel lblNewLabel_2_2 = new JLabel("CLOSE");
		lblNewLabel_2_2.setForeground(new Color(84, 26, 5));
		lblNewLabel_2_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_2_2.setBounds(24, 10, 55, 13);
		cancel_btn.add(lblNewLabel_2_2);
		
		cancel_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose(); // Exit the application
		    }
		});
	}
}
