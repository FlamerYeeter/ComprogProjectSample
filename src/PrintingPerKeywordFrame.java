import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.IOException;
import java.awt.Desktop;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.awt.Toolkit;


public class PrintingPerKeywordFrame extends JFrame {

	private JPanel contentPane;
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	JPanel confirm_btn = new JPanel();
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String query;
	static String date, category, prodID, prodName, matsID, matsName, quantity, price, inventoryID;
	public static int IDInt;
	public static String NameString;
	private static int selectedID;
	private static String selectedCategory;
	private static int selectedQuantity;
	private static String selectedDate;
	private static int selectedPrice;
	private static String selectedStatus;
	private static String selectedName;
	private static JTable table = new JTable();
	private PrintFrame printFrame;
	private JCheckBox chckbxProdID;
	private JCheckBox chckbxPrice;
	private JCheckBox chckbxQuantity;
	private JCheckBox chckbxStatus;
	private JCheckBox chckbxDate;
	private JCheckBox chckbxCategory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintingPerKeywordFrame frame = new PrintingPerKeywordFrame(IDInt, NameString);
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
	public PrintingPerKeywordFrame(int id, String name) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrintingPerKeywordFrame.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 373);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setForeground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(null);
		headerPanel.setBackground(new Color(84, 26, 5));
		headerPanel.setBounds(0, 0, 533, 54);
		contentPane.add(headerPanel);
		
		JLabel logolbl = new JLabel("");
		logolbl.setBounds(10, 0, 49, 53);
		logolbl.setIcon(new ImageIcon(img_logo));
		headerPanel.add(logolbl);
		
		JLabel lblNewLabel = new JLabel("STOCKSIGN");
		lblNewLabel.setForeground(new Color(242, 202, 165));
		lblNewLabel.setFont(new Font("Britannic Bold", Font.PLAIN, 19));
		lblNewLabel.setBounds(57, 20, 114, 24);
		headerPanel.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 237, 217));
		panel.setBorder(new LineBorder(new Color(84, 26, 5)));
		panel.setBounds(29, 75, 205, 35);
		contentPane.add(panel);
		panel.setLayout(null);
		
	    chckbxProdID = new JCheckBox("prodID");
	    chckbxProdID.setForeground(new Color(84, 26, 5));
	    chckbxProdID.setFont(new Font("Segoe UI", Font.BOLD, 13));
	    chckbxProdID.setBackground(new Color(248, 237, 217));
	    chckbxProdID.setBounds(6, 6, 136, 21);
	    panel.add(chckbxProdID);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(84, 26, 5)));
		panel_1.setBackground(new Color(248, 237, 217));
		panel_1.setBounds(281, 75, 205, 35);
		contentPane.add(panel_1);
		
	    chckbxPrice = new JCheckBox("Price");
	    chckbxPrice.setForeground(new Color(84, 26, 5));
	    chckbxPrice.setFont(new Font("Segoe UI", Font.BOLD, 13));
	    chckbxPrice.setBackground(new Color(248, 237, 217));
	    chckbxPrice.setBounds(6, 6, 136, 21);
	    panel_1.add(chckbxPrice);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(new Color(84, 26, 5)));
		panel_2.setBackground(new Color(248, 237, 217));
		panel_2.setBounds(29, 140, 205, 35);
		contentPane.add(panel_2);
		
	    chckbxQuantity = new JCheckBox("Quantity");
	    chckbxQuantity.setForeground(new Color(84, 26, 5));
	    chckbxQuantity.setFont(new Font("Segoe UI", Font.BOLD, 13));
	    chckbxQuantity.setBackground(new Color(248, 237, 217));
	    chckbxQuantity.setBounds(6, 6, 136, 21);
	    panel_2.add(chckbxQuantity);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBorder(new LineBorder(new Color(84, 26, 5)));
		panel_2_1.setBackground(new Color(248, 237, 217));
		panel_2_1.setBounds(281, 140, 205, 35);
		contentPane.add(panel_2_1);
		
	    chckbxStatus = new JCheckBox("Status");
	    chckbxStatus.setForeground(new Color(84, 26, 5));
	    chckbxStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
	    chckbxStatus.setBackground(new Color(248, 237, 217));
	    chckbxStatus.setBounds(6, 6, 136, 21);
	    panel_2_1.add(chckbxStatus);
		
		JPanel panel_2_1_1 = new JPanel();
		panel_2_1_1.setLayout(null);
		panel_2_1_1.setBorder(new LineBorder(new Color(84, 26, 5)));
		panel_2_1_1.setBackground(new Color(248, 237, 217));
		panel_2_1_1.setBounds(29, 207, 205, 35);
		contentPane.add(panel_2_1_1);
		
	    chckbxDate = new JCheckBox("Date");
	    chckbxDate.setForeground(new Color(84, 26, 5));
	    chckbxDate.setFont(new Font("Segoe UI", Font.BOLD, 13));
	    chckbxDate.setBackground(new Color(248, 237, 217));
	    chckbxDate.setBounds(6, 6, 136, 21);
	    panel_2_1_1.add(chckbxDate);
		
		JPanel panel_2_1_1_1 = new JPanel();
		panel_2_1_1_1.setLayout(null);
		panel_2_1_1_1.setBorder(new LineBorder(new Color(84, 26, 5)));
		panel_2_1_1_1.setBackground(new Color(248, 237, 217));
		panel_2_1_1_1.setBounds(281, 207, 205, 35);
		contentPane.add(panel_2_1_1_1);
		
	    chckbxCategory = new JCheckBox("Category");
	    chckbxCategory.setForeground(new Color(84, 26, 5));
	    chckbxCategory.setFont(new Font("Segoe UI", Font.BOLD, 13));
	    chckbxCategory.setBackground(new Color(248, 237, 217));
	    chckbxCategory.setBounds(6, 6, 136, 21);
	    panel_2_1_1_1.add(chckbxCategory);
		
		Color confirmOriginalColor = new Color(255, 200, 102);
        Color confirmHoverColor = new Color(241, 159, 11);
        confirm_btn.addMouseListener(new PanelButtonMouseAdapter(confirm_btn, confirmOriginalColor, confirmHoverColor));
		confirm_btn.setFont(new Font("Tahoma", Font.PLAIN, 10));
		confirm_btn.setBackground(new Color(255, 200, 102));
		confirm_btn.setBounds(53, 279, 169, 35);
		contentPane.add(confirm_btn);
		confirm_btn.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("CONFIRM");
		lblNewLabel_1.setForeground(new Color(84, 26, 5));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNewLabel_1.setBounds(52, 10, 80, 13);
		confirm_btn.add(lblNewLabel_1);
		
		JPanel cancel_btn = new JPanel();
		Color cancelOriginalColor = new Color(255, 200, 102);
        Color cancelHoverColor = new Color(241, 159, 11);
        cancel_btn.addMouseListener(new PanelButtonMouseAdapter(cancel_btn, cancelOriginalColor, cancelHoverColor));
		cancel_btn.setBounds(281, 279, 169, 35);
		contentPane.add(cancel_btn);
		cancel_btn.setLayout(null);
		cancel_btn.setBackground(new Color(255, 200, 102));
		
		JLabel lblNewLabel_2_2 = new JLabel("CANCEL");
		lblNewLabel_2_2.setForeground(new Color(84, 26, 5));
		lblNewLabel_2_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_2_2.setBounds(55, 10, 72, 13);
		cancel_btn.add(lblNewLabel_2_2);
		
		cancel_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose(); // Exit the application
		    }
		});
		
		confirm_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Perform the search in the database
		        dbconnect();

		        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {
		            // Write the CSV header
		        	// Write the CSV header
		        	List<String> columnNames = new ArrayList<>();
		        	columnNames.add("Type");

		        	if (chckbxProdID.isSelected()) {
		        	    columnNames.add("ID");
		        	}

		        	columnNames.add("InventoryID");
		        	columnNames.add("Name");

		        	if (chckbxCategory.isSelected()) {
		        	    columnNames.add("Category");
		        	}

		        	if (chckbxQuantity.isSelected()) {
		        	    columnNames.add("Quantity");
		        	}

		        	if (chckbxPrice.isSelected()) {
		        	    columnNames.add("Price");
		        	}

		        	if (chckbxDate.isSelected()) {
		        	    columnNames.add("EXPDate");
		        	}

		        	if (chckbxStatus.isSelected()) {
		        	    columnNames.add("InventoryStatus");
		        	    columnNames.add("InventoryCriticalCategory");
		        	}

		        	// Write the column names as the first row in the CSV
		        	writer.write(String.join(",", columnNames));
		        	writer.newLine();

		            PrintingKeywordFrame printingkeywordFrame = new PrintingKeywordFrame(id, name);

		            selectedID = printingkeywordFrame.getSelectedID();
		            selectedName = printingkeywordFrame.getSelectedName();
		            // Construct the search query based on the search criteria for productstb
		            StringBuilder queryBuilder = new StringBuilder("SELECT p.*, i.quantity AS inventory_quantity, i.status AS inventory_status, i.critical_category AS inventory_critical_category ");
		            queryBuilder.append("FROM productstb p ");
		            queryBuilder.append("INNER JOIN inventorytb i ON p.inventoryID = i.inventoryID ");
		            queryBuilder.append("WHERE p.prodID = ").append(selectedID);

		            // Execute the search query for productstb
		            String query = queryBuilder.toString();
		            ResultSet resultSet = stmt.executeQuery(query);

		            // Process the search results for productstb
		            while (resultSet.next()) {
		                // Retrieve the search results for productstb
		                String foundProdID = resultSet.getString("prodID");
		                String foundProdInventoryID = resultSet.getString("inventoryID");
		                String foundProdName = resultSet.getString("prodName");
		                String foundProdCategory = resultSet.getString("category");
		                String foundProdQuantity = resultSet.getString("quantity");
		                String foundProdPrice = resultSet.getString("price");
		                String foundProdExpDate = resultSet.getString("EXPdate");
		                String foundInventoryStatus = resultSet.getString("inventory_status");
		                String foundInventoryCriticalCategory = resultSet.getString("inventory_critical_category");

		                // Check the checkboxes and show the corresponding information for productstb
		             // Check the checkboxes and show the corresponding information for productstb
		                if (chckbxProdID.isSelected()) {
		                    writer.write("Product,");
		                }

		                writer.write(foundProdID + ",");
		                writer.write(foundProdInventoryID + ",");
		                writer.write(foundProdName + ",");

		                if (chckbxCategory.isSelected()) {
		                    writer.write(foundProdCategory + ",");
		                }

		                if (chckbxQuantity.isSelected()) {
		                    writer.write(foundProdQuantity + ",");
		                }

		                if (chckbxPrice.isSelected()) {
		                    writer.write(foundProdPrice + ",");
		                }

		                if (chckbxDate.isSelected()) {
		                    writer.write(foundProdExpDate + ",");
		                }

		                if (chckbxStatus.isSelected()) {
		                    writer.write(foundInventoryStatus + ",");
		                    writer.write(foundInventoryCriticalCategory);
		                }

		                writer.newLine();

		            }

		            // Construct the search query based on the search criteria for materialstb
		            queryBuilder.setLength(0); // Clear the previous query
		            queryBuilder.append("SELECT m.*, i.quantity AS inventory_quantity, i.status AS inventory_status, i.critical_category AS inventory_critical_category ");
		            queryBuilder.append("FROM materialstb m ");
		            queryBuilder.append("INNER JOIN inventorytb i ON m.inventoryID = i.inventoryID ");
		            queryBuilder.append("WHERE m.matsID = ").append(selectedID);

		            // Execute the search query for materialstb
		            query = queryBuilder.toString();
		            resultSet = stmt.executeQuery(query);

		            // Process the search results for materialstb
		            while (resultSet.next()) {
		                // Retrieve the search results for materialstb
		                String foundMatsID = resultSet.getString("matsID");
		                String foundMatsInventoryID = resultSet.getString("inventoryID");
		                String foundMatsName = resultSet.getString("matsName");
		                String foundMatsCategory = resultSet.getString("category");
		                String foundMatsQuantity = resultSet.getString("quantity");
		                String foundMatsPrice = resultSet.getString("price");
		                String foundMatsExpDate = resultSet.getString("EXPdate");
		                String foundInventoryStatus = resultSet.getString("inventory_status");
		                String foundInventoryCriticalCategory = resultSet.getString("inventory_critical_category");

		                // Check the checkboxes and show the corresponding information for materialstb
		             // Check the checkboxes and show the corresponding information for materialstb
		                if (chckbxProdID.isSelected()) {
		                    writer.write("Material,");
		                }

		                writer.write(foundMatsID + ",");
		                writer.write(foundMatsInventoryID + ",");
		                writer.write(foundMatsName + ",");

		                if (chckbxCategory.isSelected()) {
		                    writer.write(foundMatsCategory + ",");
		                }

		                if (chckbxQuantity.isSelected()) {
		                    writer.write(foundMatsQuantity + ",");
		                }

		                if (chckbxPrice.isSelected()) {
		                    writer.write(foundMatsPrice + ",");
		                }

		                if (chckbxDate.isSelected()) {
		                    writer.write(foundMatsExpDate + ",");
		                }

		                if (chckbxStatus.isSelected()) {
		                    writer.write(foundInventoryStatus + ",");
		                    writer.write(foundInventoryCriticalCategory);
		                }

		                writer.newLine();

		            }

		            resultSet.close();
		        } catch (IOException | SQLException ex) {
		            ex.printStackTrace();
		        }

		        // Open the CSV file
		        try {
		            Path csvFile = Path.of("output.csv");
		            if (Files.exists(csvFile)) {
		                Desktop desktop = Desktop.getDesktop();
		                desktop.open(csvFile.toFile());
		            }
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

	}
	
    public void setPrintFrame(PrintFrame printFrame) {
        this.printFrame = printFrame;
    }
	
}
