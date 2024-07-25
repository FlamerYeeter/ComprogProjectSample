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
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.ResultSet;
import java.io.IOException;
import java.awt.Desktop;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Toolkit;


public class PrintingKeywordFrame extends JFrame {

	private JPanel contentPane;
	JPanel allkey_btn = new JPanel();
	JPanel keyword_btn = new JPanel();
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String query;
	static String date, category, prodID, prodName, matsID, matsName, quantity, price, inventoryID;
	public static int IDInt;
	public static String NameString;
	private int selectedID;
	private String selectedName;
	private static JTable table = new JTable();
	private PrintFrame printFrame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintingKeywordFrame frame = new PrintingKeywordFrame(IDInt, NameString);
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
	    public void mouseClicked(MouseEvent e) {
            if (panel == keyword_btn) {
            	PrintFrame printFrame = new PrintFrame(selectedID, selectedName);
            	PrintingPerKeywordFrame printingperkeywordFrame = new PrintingPerKeywordFrame(selectedID, selectedName);
            	printingperkeywordFrame.setPrintFrame(printFrame);
            	printingperkeywordFrame.setVisible(true);
            	dispose();
            }
        }
        
    }
	/**
	 * Create the frame.
	 */
	public PrintingKeywordFrame(int id, String name) {
		setSelectedID(id);
        setSelectedName(name);
		this.IDInt = id;
		this.NameString = name;
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrintingKeywordFrame.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 243);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(null);
		headerPanel.setBackground(new Color(84, 26, 5));
		headerPanel.setBounds(0, 0, 554, 54);
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
		
		// Add hover effect to the cancel_btn
        Color keywordOriginalColor = new Color(255, 200, 102);
        Color keywordHoverColor = new Color(241, 159, 11);
        keyword_btn.addMouseListener(new PanelButtonMouseAdapter(keyword_btn, keywordOriginalColor, keywordHoverColor));
		keyword_btn.setBackground(new Color(255, 200, 102));
		keyword_btn.setBounds(25, 92, 186, 32);
		contentPane.add(keyword_btn);
		keyword_btn.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("PER KEYWORD");
		lblNewLabel_2.setForeground(new Color(84, 26, 5));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(25, 10, 133, 13);
		keyword_btn.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("OR");
		lblNewLabel_1.setForeground(new Color(84, 26, 5));
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
		lblNewLabel_1.setBounds(251, 103, 31, 13);
		contentPane.add(lblNewLabel_1);
		
		// Add hover effect to the allkeyword_btn
        Color allkeyOriginalColor = new Color(255, 200, 102);
        Color allkeyHoverColor = new Color(241, 159, 11);
        allkey_btn.addMouseListener(new PanelButtonMouseAdapter(allkey_btn, allkeyOriginalColor, allkeyHoverColor));
		allkey_btn.setLayout(null);
		allkey_btn.setBackground(new Color(255, 200, 102));
		allkey_btn.setBounds(318, 92, 186, 32);
		contentPane.add(allkey_btn);
		
		JLabel lblNewLabel_2_1 = new JLabel("ALL KEYWORD");
		lblNewLabel_2_1.setForeground(new Color(84, 26, 5));
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2_1.setBounds(25, 10, 133, 13);
		allkey_btn.add(lblNewLabel_2_1);
		
		allkey_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Perform the search in the database
		        dbconnect();

		        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {
		            // Write the CSV header
		        	List<String> columnNames = Arrays.asList("Type", "ID", "InventoryID", "Name", "Category", "Quantity", "Price", "EXPDate", "InventoryStatus", "InventoryCriticalCategory");
		        	// Write the column names as the first row in the CSV
		            writer.write(String.join(",", columnNames));
		            writer.newLine();
		            // Construct the search query based on the search criteria
		            StringBuilder queryBuilder = new StringBuilder("SELECT p.*, i.quantity AS inventory_quantity, i.status AS inventory_status, i.critical_category AS inventory_critical_category ");
		            queryBuilder.append("FROM productstb p ");
		            queryBuilder.append("INNER JOIN inventorytb i ON p.inventoryID = i.inventoryID ");

		            if (selectedID != 0) {
		                queryBuilder.append("WHERE p.prodID = ").append(selectedID); // Match by product ID
		            }

		            StringBuilder queryBuilderMaterial = new StringBuilder("SELECT m.*, i.quantity AS inventory_quantity, i.status AS inventory_status, i.critical_category AS inventory_critical_category ");
		            queryBuilderMaterial.append("FROM materialstb m ");
		            queryBuilderMaterial.append("INNER JOIN inventorytb i ON m.inventoryID = i.inventoryID ");

		            if (selectedID != 0) {
		                queryBuilderMaterial.append("WHERE m.matsID = ").append(selectedID); // Match by material ID
		            }

		            // Execute the search query for productstb
		            String query = queryBuilder.toString();
		            ResultSet resultSet = stmt.executeQuery(query);

		            // Process the search results for productstb
		            while (resultSet.next()) {
		                // Retrieve the search results
		                String foundProdID = resultSet.getString("prodID");
		                String foundProdInventoryID = resultSet.getString("inventoryID");
		                String foundProdName = resultSet.getString("prodName");
		                String foundProdCategory = resultSet.getString("category");
		                String foundProdQuantity = resultSet.getString("quantity");
		                String foundProdPrice = resultSet.getString("price");
		                String foundProdExpDate = resultSet.getString("EXPdate");
		                String foundInventoryStatus = resultSet.getString("inventory_status");
		                String foundInventoryCriticalCategory = resultSet.getString("inventory_critical_category");

		                // Write the results to the CSV file
		                writer.write("Product," + foundProdID + "," + foundProdInventoryID + "," + foundProdName + "," + foundProdCategory +
		                        "," + foundProdQuantity + "," + foundProdPrice + "," + foundProdExpDate +
		                        "," + foundInventoryStatus + "," + foundInventoryCriticalCategory);
		                writer.newLine();
		            }

		            resultSet.close();

		            // Execute the search query for materialstb
		            query = queryBuilderMaterial.toString();
		            ResultSet resultSetMaterial = stmt.executeQuery(query);

		            // Process the search results for materialstb
		            while (resultSetMaterial.next()) {
		                // Retrieve the search results
		                String foundMatsID = resultSetMaterial.getString("matsID");
		                String foundMatsInventoryID = resultSetMaterial.getString("inventoryID");
		                String foundMatsName = resultSetMaterial.getString("matsName");
		                String foundMatsCategory = resultSetMaterial.getString("category");
		                String foundMatsQuantity = resultSetMaterial.getString("quantity");
		                String foundMatsPrice = resultSetMaterial.getString("price");
		                String foundMatsExpDate = resultSetMaterial.getString("EXPdate");
		                String foundInventoryStatus = resultSetMaterial.getString("inventory_status");
		                String foundInventoryCriticalCategory = resultSetMaterial.getString("inventory_critical_category");

		                // Write the results to the CSV file
		                writer.write("Material," + foundMatsID + "," + foundMatsInventoryID + "," + foundMatsName + "," + foundMatsCategory +
		                        "," + foundMatsQuantity + "," + foundMatsPrice + "," + foundMatsExpDate +
		                        "," + foundInventoryStatus + "," + foundInventoryCriticalCategory);
		                writer.newLine();
		            }

		            resultSetMaterial.close();
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


		JPanel cancel_btn = new JPanel();
		// Add hover effect to the cancel_btn
        Color cancelOriginalColor = new Color(255, 200, 102);
        Color cancelHoverColor = new Color(241, 159, 11);
        cancel_btn.addMouseListener(new PanelButtonMouseAdapter(cancel_btn, cancelOriginalColor, cancelHoverColor));
		cancel_btn.setLayout(null);
		cancel_btn.setBackground(new Color(255, 200, 102));
		cancel_btn.setBounds(212, 148, 103, 35);
		contentPane.add(cancel_btn);
		
		JLabel lblNewLabel_2_2 = new JLabel("CANCEL");
		lblNewLabel_2_2.setForeground(new Color(84, 26, 5));
		lblNewLabel_2_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel_2_2.setBounds(21, 10, 72, 13);
		cancel_btn.add(lblNewLabel_2_2);

		cancel_btn.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        dispose(); // Exit the application
		    }
		});
		
	}
	
    public void setPrintFrame(PrintFrame printFrame) {
        this.printFrame = printFrame;
    }

    public void setSelectedID(int id) {
        selectedID = id;
    }

    public void setSelectedName(String name) {
        selectedName = name;
    }

    public int getSelectedID() {
        return selectedID;
    }

    public String getSelectedName() {
        return selectedName;
    }
	
}
