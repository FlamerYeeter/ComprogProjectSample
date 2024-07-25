import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.geom.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.*;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JMonthChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;



public class ProductFrame extends JFrame {
	//To insert images
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    private Image img_homeIcon = new ImageIcon(HomeFrame.class.getResource("res/homeIcon.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    private Image img_productIcon = new ImageIcon(HomeFrame.class.getResource("res/productIcon.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    private Image img_kookaye = new ImageIcon(HomeFrame.class.getResource("res/kookaye.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    private Image img_userimg = new ImageIcon(HomeFrame.class.getResource("res/UserImage.png")).getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH);
	JPanel homePanel = new JPanel();
    JPanel add_btn = new JPanel();
    JPanel edit_btn = new JPanel();
    JPanel print_btn = new JPanel();
    JPanel sell_btn = new JPanel();
    JPanel use_btn = new JPanel();
	private JPanel contentPane;
	private JTextField searchTxt;
	private JComboBox<String> comboBox;
	private JDateChooser dateChooser_1_1;
    private JDateChooser dateChooser_1;
	public static JTable prodTable;
	public static JTable matsTable;
	public static int prodIDInt;
	public static int matsIDInt;
	public static String productNameString;
	public String prodIDString;
	public static String matsNameString;
	public String inventoryIDString;
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String query;
	static String date, category, prodName, quantity, price, status, crit_cat, inventoryID; // Obidos - these static are important  
	static int prodID;
	private static JTable table;
	public static int IDInt;
	public static String NameString;

	public static void dbconnect() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocksign", "root", "");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    //Create and display an instance of the ProductFrame class, which extends the JFrame class in Swing.
	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                ProductFrame frame = new ProductFrame();
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            try {
	                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocksign", "root", "");
	                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

	                Statement statement = conn.createStatement();

	                StringBuilder queryBuilder = new StringBuilder("SELECT pt.*, it.status, it.critical_category FROM productstb pt INNER JOIN inventorytb it ON pt.inventoryID = it.inventoryID WHERE 1=1");
	                StringBuilder queryBuilderMaterial = new StringBuilder("SELECT mt.*, it.status, it.critical_category FROM materialstb mt INNER JOIN inventorytb it ON mt.inventoryID = it.inventoryID WHERE 1=1");

	                String query = queryBuilder.toString();
	                ResultSet resultSet = stmt.executeQuery(query);

	                // Clear the existing data from the table model
	                // Clear the existing data from the product table model
	                DefaultTableModel productModel = new DefaultTableModel() {
	                    @Override
	                    public Class<?> getColumnClass(int columnIndex) {
	                        if (columnIndex == 0) {
	                            return Boolean.class; // Set the checkbox column to Boolean type
	                        }
	                        return super.getColumnClass(columnIndex);
	                    }
	                    @Override
	                    public boolean isCellEditable(int row, int column) {
	                        // Enable checkbox column for editing
	                        return column == 0;
	                    }
	                };

	                prodTable.setModel(productModel);

	                ResultSetMetaData productMetaData = resultSet.getMetaData();
	                int productColumnCount = productMetaData.getColumnCount();

	                // Add column names to the product table model, including checkbox column
	                productModel.addColumn("Select"); // Checkbox column
	                for (int columnIndex = 1; columnIndex <= productColumnCount; columnIndex++) {
	                    productModel.addColumn(productMetaData.getColumnName(columnIndex));
	                }

	                // Set the selection mode to single selection
	                prodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	                // Add rows to the product table model
	                while (resultSet.next()) {
	                    Object[] rowData = new Object[productColumnCount + 1]; // +1 for checkbox column
	                    rowData[0] = false; // Set initial checkbox value to false
	                    for (int columnIndex = 1; columnIndex <= productColumnCount; columnIndex++) {
	                        rowData[columnIndex] = resultSet.getObject(columnIndex);
	                    }
	                    productModel.addRow(rowData);
	                }

	                resultSet.close();
	                
	                // Add a listener to the product table model for checkbox changes
	                productModel.addTableModelListener(new TableModelListener() {
	                    @Override
	                    public void tableChanged(TableModelEvent e) {
	                        int row = e.getFirstRow();
	                        int column = e.getColumn();
	                        if (column == 0) {
	                            Boolean checked = (Boolean) productModel.getValueAt(row, column);
	                            if (checked) {
	                                Object prodID = productModel.getValueAt(row, 1);
	                                prodIDInt = Integer.parseInt(prodID.toString());
	                                Object productName = productModel.getValueAt(row, 3);
	                                productNameString = productName.toString();
	                                // Perform desired actions with the retrieved values

	                                // Get the selected product's information
	                                Object[] productInfo = getSelectedProductInfo(prodIDInt, productNameString);

	                                // For the print function
	                                Object id = productModel.getValueAt(row, 1);
	                                Object name = productModel.getValueAt(row, 3);
	                                IDInt = Integer.parseInt(id.toString());
	                                NameString = name.toString();
	                                
	                                if (table != null) {
		                                // Get the table model
		                                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
	
		                                // Pass the selected product's information and table model to SellFrame
		                                SellFrame.updateTable(productInfo, tableModel);
		                                PrintFrame.updateTable(productInfo, tableModel);
	                                }
	                            }
	                        }
	                    }
	                });


	                
	                // Refresh the material table using the same logic
	                String materialQuery = queryBuilderMaterial.toString();
	                ResultSet materialResultSet = stmt.executeQuery(materialQuery);

	                // Clear the existing data from the material table model
	                DefaultTableModel materialModel = new DefaultTableModel() {
	                    @Override
	                    public Class<?> getColumnClass(int columnIndex) {
	                        if (columnIndex == 0) {
	                            return Boolean.class; // Set the checkbox column to Boolean type
	                        }
	                        return super.getColumnClass(columnIndex);
	                    }
	                    public boolean isCellEditable(int row, int column) {
	                        return true; // Make all cells editable
	                    }
	                };

	                matsTable.setModel(materialModel);

	                ResultSetMetaData materialMetaData = materialResultSet.getMetaData();
	                int materialColumnCount = materialMetaData.getColumnCount();

	                // Add column names to the material table model, including checkbox column
	                materialModel.addColumn("Select"); // Checkbox column
	                for (int columnIndex = 1; columnIndex <= materialColumnCount; columnIndex++) {
	                    materialModel.addColumn(materialMetaData.getColumnName(columnIndex));
	                }

	                // Set the selection mode to single selection
	                matsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	                // Add rows to the material table model
	                while (materialResultSet.next()) {
	                    Object[] rowData = new Object[materialColumnCount + 1]; // +1 for checkbox column
	                    rowData[0] = false; // Set initial checkbox value to false
	                    for (int columnIndex = 1; columnIndex <= materialColumnCount; columnIndex++) {
	                        rowData[columnIndex] = materialResultSet.getObject(columnIndex);
	                    }
	                    materialModel.addRow(rowData);
	                }

	                materialResultSet.close();

	                // Add a listener to the material table model for checkbox changes
	                materialModel.addTableModelListener(new TableModelListener() {
	                    @Override
	                    public void tableChanged(TableModelEvent e) {
	                        int row = e.getFirstRow();
	                        int column = e.getColumn();
	                        if (column == 0) {
	                            Boolean checked = (Boolean) materialModel.getValueAt(row, column);
	                            if (checked) {
	                                Object matsID = materialModel.getValueAt(row, 1);
	                                matsIDInt = Integer.parseInt(matsID.toString());
	                                Object matsName = materialModel.getValueAt(row, 3);
	                                matsNameString = matsName.toString();
	                                // Perform desired actions with the retrieved values

	                                // Get the selected product's information
	                                Object[] materialInfo = getSelectedMaterialInfo(matsIDInt, matsNameString);

	                                // For the print function
	                                Object id = materialModel.getValueAt(row, 1);
	                                Object name = materialModel.getValueAt(row, 3);
	                                IDInt = Integer.parseInt(id.toString());
	                                NameString = name.toString();
	                                
	                                if (table != null) {
	                                    // Get the table model
	                                    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

	                                    // Pass the selected product's information and table model to SellFrame
	                                    UseFrame.updateTable(materialInfo, tableModel);
	                                    PrintFrame.updateTable(materialInfo, tableModel);
	                                }
	                            }
	                        }
	                    }

	                });
	                
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
		
	
	//For rounded corners on panels
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
    
    //implement a round-shaped JTextField
    class RoundedJTextField extends JTextField {
       private Shape shape;
       public RoundedJTextField(int size) {
       super(size);
       setOpaque(false);
    }
    protected void paintComponent(Graphics g) {
       g.setColor(getBackground());
       g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
       super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
       g.setColor(getForeground());
       g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
       if (shape == null || !shape.getBounds().equals(getBounds())) {
          shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
       }
       return shape.contains(x, y);
       }
    }
	/**
	 * Create the frame.
	 */
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
            if (panel == homePanel) {
                // Open the ProductFrame
                HomeFrame homeFrame = new HomeFrame();
                homeFrame.setVisible(true);
                dispose();
            }
            if (panel == add_btn) {
                // Open the AddFrame
                AddFrame addFrame = new AddFrame();
                addFrame.setVisible(true);
            }
            if (panel == edit_btn) {
                // Open the EditFrame
                EditFrame editFrame = new EditFrame(prodIDString, inventoryIDString);
                editFrame.setVisible(true);
            }
            if (panel == print_btn) {
                // Open the EditFrame
            	PrintFrame printFrame = new PrintFrame(IDInt, NameString);
                printFrame.setVisible(true);
            }
            if (panel == sell_btn) {
                // Open the EditFrame
            	SellFrame sellFrame = new SellFrame(prodIDInt, productNameString);
                sellFrame.setVisible(true);
            }
            if (panel == use_btn) {
                // Open the EditFrame
            	UseFrame useFrame = new UseFrame(matsIDInt, matsNameString);
            	useFrame.setVisible(true);
            }
        }
        
    }
    
	public ProductFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductFrame.class.getResource("/res/logoStock.png")));
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 // Calculate the centered position
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = 861; // Set your desired width
        int frameHeight = 709; // Set your desired height
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        setBounds(x, y, 1034, 817);
        contentPane = new JPanel();
        contentPane.setToolTipText("");
        contentPane.setBackground(new Color(248, 237, 217));
        contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        setContentPane(contentPane);
        setTitle("StockSign");
        contentPane.setLayout(null);
        
        //navigation bar
		JPanel navigationPanel = new JPanel();
		navigationPanel.setLayout(null);
		navigationPanel.setBackground(new Color(84, 26, 5));
		navigationPanel.setBounds(0, 0, 1020, 88);
		contentPane.add(navigationPanel);
		ImageIcon logoIcon = new ImageIcon(img_logo);
        logoIcon = new ImageIcon(logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        navigationPanel.setLayout(null);
		
		JLabel logoLabel = new JLabel();
		logoLabel.setPreferredSize(new Dimension(90, 90));
		logoLabel.setAlignmentX(0.0f);
		logoLabel.setBounds(0, 0, 79, 89);
		navigationPanel.add(logoLabel);
		logoLabel.setIcon(logoIcon);
	    logoLabel.setPreferredSize(new java.awt.Dimension(90, 90));
	    logoLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
		
		JLabel lblNewLabel = new JLabel("STOCKSIGN");
		lblNewLabel.setForeground(new Color(242, 202, 165));
		lblNewLabel.setFont(new Font("Britannic Bold", Font.BOLD, 30));
		lblNewLabel.setBounds(89, 27, 188, 30);
		navigationPanel.add(lblNewLabel);
		
		//hover effect
		Color homeOriginalColor = new Color(84, 26, 5);
        Color homeHoverColor = new Color(64, 18, 2);
        homePanel.addMouseListener(new PanelButtonMouseAdapter(homePanel, homeOriginalColor, homeHoverColor));
		homePanel.setLayout(null);
		homePanel.setBackground(new Color(84, 26, 5));
		homePanel.setBounds(735, 0, 124, 89);
		navigationPanel.add(homePanel);
		
		JLabel homelbl = new JLabel("HOME");
		homelbl.setForeground(new Color(255, 232, 190));
		homelbl.setFont(new Font("Open Sans", Font.BOLD, 17));
		homelbl.setBounds(59, 35, 65, 24);
		homePanel.add(homelbl);
		
		//container for the home icon picture
		JLabel homeIconlbl = new JLabel("");
		homeIconlbl.setBounds(19, 24, 30, 41);
		homePanel.add(homeIconlbl);
		homeIconlbl.setIcon(new ImageIcon(img_homeIcon));
		
		JPanel productPanel = new JPanel();
		 //Add hover effect to the productPanel
	    Color productOriginalColor = new Color(84, 26, 5);
	    Color productHoverColor = new Color(64, 18, 2);
	    productPanel.addMouseListener(new PanelButtonMouseAdapter(productPanel, productOriginalColor, productHoverColor));		
	    productPanel.setLayout(null);
		productPanel.setBackground(new Color(84, 26, 5));
		productPanel.setBounds(869, 0, 141, 89);
		navigationPanel.add(productPanel);
		
		JLabel lblProduct = new JLabel("PRODUCT");
		lblProduct.setForeground(new Color(255, 232, 190));
		lblProduct.setFont(new Font("Open Sans", Font.BOLD, 17));
		lblProduct.setBounds(48, 32, 83, 24);
		productPanel.add(lblProduct);
		
		JLabel productIconlbl = new JLabel("");
		productIconlbl.setBounds(8, 32, 30, 30);
		productPanel.add(productIconlbl);
        productIconlbl.setIcon(new ImageIcon(img_productIcon));
        
        JLabel businesslbl = new JLabel("");
        businesslbl.setBounds(10, 99, 85, 88);
        businesslbl.setIcon(new ImageIcon(img_kookaye));
        contentPane.add(businesslbl);
        
        JLabel lblNewLabel_1 = new JLabel("KOOKAYE'S D'LITE");
        lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 20));
        lblNewLabel_1.setForeground(new Color(84, 26, 5));
        lblNewLabel_1.setBounds(96, 127, 262, 25);
        contentPane.add(lblNewLabel_1);
        
        //for the dropdown box/ combo box
        String[] options = {"FILTER", "Category", "Product Name (A-Z)", "Quantity (highest-lowest)", "Price (highest-lowest)", "Status", "Product ID"};

        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Open Sans", Font.BOLD, 13));
        // Set the custom renderer
        comboBox.setRenderer(new CustomComboBoxRenderer());
        comboBox.setToolTipText("Select an option");
        comboBox.setBorder(new LineBorder(new Color(57, 16, 2)));
        comboBox.setBackground(new Color(255, 200, 102));
        comboBox.setForeground(new Color(57, 16, 2));
        comboBox.setBounds(478, 248, 169, 22);
        
        //to view the options
        for (String option : options) {
            comboBox.addItem(option);
        }
        
        //default selection for the drop down box
        comboBox.setSelectedItem("FILTER");
        contentPane.add(comboBox);
        //Add hover effect to the productPanel
	    Color dateOriginalColor = new Color(255, 200, 102);
	    Color dateHoverColor = new Color(241, 159, 11);
        //PanelBorder txtBtn = new PanelBorder();
        Color txtOriginalColor = new Color(255, 200, 102);
	    Color txtHoverColor = new Color(241, 159, 11);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 237, 217));
        panel.setBounds(35, 197, 975, 146);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel_1_1 = new JLabel("DATE:");
        lblNewLabel_1_1.setBounds(39, 10, 56, 25);
        panel.add(lblNewLabel_1_1);
        lblNewLabel_1_1.setForeground(new Color(84, 26, 5));
        lblNewLabel_1_1.setFont(new Font("Arial Black", Font.PLAIN, 13));
        
        JLabel lblFrom = new JLabel("FROM:");
        lblFrom.setBounds(91, 11, 40, 24);
        panel.add(lblFrom);
        lblFrom.setForeground(new Color(0, 0, 0));
        lblFrom.setFont(new Font("Open Sans Light", Font.PLAIN, 11));
        
        dateChooser_1_1 = new JDateChooser();
        dateChooser_1_1.setBounds(141, 15, 135, 20);
        panel.add(dateChooser_1_1);
        dateChooser_1_1.setBorder(new LineBorder(new Color(57, 16, 2)));
        
        JLabel lblTo = new JLabel("TO:");
        lblTo.setBounds(286, 11, 40, 24);
        panel.add(lblTo);
        lblTo.setForeground(Color.BLACK);
        lblTo.setFont(new Font("Open Sans Light", Font.PLAIN, 11));
        
        dateChooser_1 = new JDateChooser();
        dateChooser_1.setBounds(316, 15, 135, 20);
        panel.add(dateChooser_1);
        dateChooser_1.setBorder(new LineBorder(new Color(57, 16, 2)));
        
        //SEARCH BUTTON FOR THE DATE PICKER
        //PanelBorder dateBtn = new PanelBorder();
        JPanel dateBtn = new JPanel();
        dateBtn.setBounds(477, 10, 132, 25);
        panel.add(dateBtn);
        dateBtn.addMouseListener(new PanelButtonMouseAdapter(dateBtn, dateOriginalColor, dateHoverColor));	
        dateBtn.setBackground(new Color(255, 200, 102));
        dateBtn.setBorder(new RoundBorder(40));
        dateBtn.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("SEARCH");
        lblNewLabel_2.setForeground(new Color(84, 26, 5));
        lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNewLabel_2.setBounds(41, 0, 61, 27);
        dateBtn.add(lblNewLabel_2);
        
        dateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshTableDate();
            }
        });
        
        //searchTxt = new RoundedJTextField(40);
        searchTxt = new JTextField();
        searchTxt.setBounds(36, 47, 280, 23);
        panel.add(searchTxt);
        searchTxt.setFont(new Font("Open Sans Light", Font.PLAIN, 11));
        searchTxt.setBorder(new LineBorder(new Color(57, 16, 2)));
        searchTxt.setColumns(10);
        
        //SEARCH BUTTON FOR THE TEXT FIELD
        JPanel txtBtn = new JPanel();
        txtBtn.setBounds(326, 45, 98, 25);
        panel.add(txtBtn);
        txtBtn.addMouseListener(new PanelButtonMouseAdapter(txtBtn, txtOriginalColor, txtHoverColor));	
        txtBtn.setLayout(null);
        txtBtn.setBorder(new RoundBorder(40)); // Set rounded border
        txtBtn.setBackground(new Color(255, 200, 102));
        
        JLabel lblNewLabel_2_1 = new JLabel("SEARCH");
        lblNewLabel_2_1.setForeground(new Color(84, 26, 5));
        lblNewLabel_2_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNewLabel_2_1.setBounds(25, 0, 52, 27);
        txtBtn.add(lblNewLabel_2_1);
        
        txtBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshTable();
            }
        });


        
     // Add hover effect to the homePanel
        Color addOriginalColor = new Color(255, 200, 102);
        Color addHoverColor = new Color(241, 159, 11);
        add_btn.addMouseListener(new PanelButtonMouseAdapter(add_btn, addOriginalColor, addHoverColor));
        add_btn.setLayout(null);
        add_btn.setBorder(new RoundBorder(40));
        add_btn.setBackground(new Color(255, 200, 102));
        add_btn.setBounds(124, 80, 98, 25);
        panel.add(add_btn);
        
        JLabel add_lbl = new JLabel("ADD");
        add_lbl.setForeground(new Color(84, 26, 5));
        add_lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        add_lbl.setBounds(34, 0, 31, 27);
        add_btn.add(add_lbl);
        
        // Add hover effect to the editButton
        Color editOriginalColor = new Color(255, 200, 102);
        Color editHoverColor = new Color(241, 159, 11);
        edit_btn.addMouseListener(new PanelButtonMouseAdapter(edit_btn, editOriginalColor, editHoverColor));
        edit_btn.setLayout(null);
        edit_btn.setBorder(new RoundBorder(40));
        edit_btn.setBackground(new Color(255, 200, 102));
        edit_btn.setBounds(265, 80, 110, 25);
        panel.add(edit_btn);
        
        JLabel edit_lbl = new JLabel("EDIT");
        edit_lbl.setForeground(new Color(84, 26, 5));
        edit_lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        edit_lbl.setBounds(40, 0, 31, 27);
        edit_btn.add(edit_lbl);
        
        //Add hover effect to the printButton
        Color sellOriginalColor = new Color(255, 200, 102);
        Color sellHoverColor = new Color(241, 159, 11);
        sell_btn.addMouseListener(new PanelButtonMouseAdapter(sell_btn, sellOriginalColor, sellHoverColor));
        sell_btn.setLayout(null);
        sell_btn.setBorder(new RoundBorder(40));
        sell_btn.setBackground(new Color(255, 200, 102));
        sell_btn.setBounds(414, 80, 107, 25);
        panel.add(sell_btn);
        
        JLabel sell_lbl = new JLabel("SELL");
        sell_lbl.setForeground(new Color(84, 26, 5));
        sell_lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sell_lbl.setBounds(38, 0, 31, 27);
        sell_btn.add(sell_lbl);
        
        use_btn = new JPanel();
        Color useOriginalColor = new Color(255, 200, 102);
	    Color useHoverColor = new Color(241, 159, 11);
	    use_btn.addMouseListener(new PanelButtonMouseAdapter(use_btn, useOriginalColor, useHoverColor));
        use_btn.setLayout(null);
        use_btn.setBorder(new RoundBorder(40));
        use_btn.setBackground(new Color(255, 200, 102));
        use_btn.setBounds(414, 115, 107, 25);
        panel.add(use_btn);
        
        JLabel use_lbl = new JLabel("USE");
        use_lbl.setForeground(new Color(84, 26, 5));
        use_lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        use_lbl.setBounds(41, 0, 31, 27);
        use_btn.add(use_lbl);
        
        // Add hover effect to the printButton
        Color printOriginalColor = new Color(255, 200, 102);
        Color printHoverColor = new Color(241, 159, 11);
        print_btn.addMouseListener(new PanelButtonMouseAdapter(print_btn, printOriginalColor, printHoverColor));
        print_btn.setLayout(null);
        print_btn.setBorder(new RoundBorder(40));
        print_btn.setBackground(new Color(255, 200, 102));
        print_btn.setBounds(124, 115, 98, 25);
        panel.add(print_btn);
        
        JLabel print_lbl = new JLabel("PRINT");
        print_lbl.setForeground(new Color(84, 26, 5));
        print_lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        print_lbl.setBounds(34, 0, 54, 27);
        print_btn.add(print_lbl);
        
        JPanel refreshBtn = new JPanel(); // Obidos - Refresh btn need 
        //Add hover effect to the printButton
        Color refreshOriginalColor = new Color(255, 200, 102);
        Color refreshHoverColor = new Color(241, 159, 11);
        refreshBtn.addMouseListener(new PanelButtonMouseAdapter(refreshBtn, refreshOriginalColor, refreshHoverColor));
        refreshBtn.setLayout(null);
        refreshBtn.setBorder(new RoundBorder(40));
        refreshBtn.setBackground(new Color(255, 200, 102));
        refreshBtn.setBounds(265, 115, 110, 25);
        panel.add(refreshBtn);
        
        refreshBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshTable();
            }
        });

        
        JLabel lblNewLabel_2_1_1 = new JLabel("REFRESH");
        lblNewLabel_2_1_1.setForeground(new Color(84, 26, 5));
        lblNewLabel_2_1_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNewLabel_2_1_1.setBounds(24, 0, 64, 27);
        refreshBtn.add(lblNewLabel_2_1_1);
        
        JLabel img1_lbl = new JLabel("");
        img1_lbl.setBounds(548, 0, 417, 146);
        panel.add(img1_lbl);
        img1_lbl.setIcon(new ImageIcon(img_userimg));
        
//        JPanel saveBtn = new JPanel();
//      //Add hover effect to the productPanel
//	    Color saveOriginalColor = new Color(255, 200, 102);
//	    Color saveHoverColor = new Color(241, 159, 11);
//	    saveBtn.addMouseListener(new PanelButtonMouseAdapter(saveBtn, saveOriginalColor, saveHoverColor));
//        saveBtn.setLayout(null);
//        saveBtn.setBorder(new RoundBorder(40));
//        saveBtn.setBackground(new Color(255, 200, 102));
//        saveBtn.setBounds(432, 115, 106, 25);
//        panel.add(saveBtn);
//        
//        JLabel saveLbl = new JLabel("SAVE");
//        saveLbl.setForeground(new Color(84, 26, 5));
//        saveLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
//        saveLbl.setBounds(35, 0, 41, 27);
//        saveBtn.add(saveLbl);
//        
//        saveBtn.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                save();
//            }
//        });
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(35, 381, 975, 172);
        contentPane.add(scrollPane);
        
        prodTable = new JTable();
        prodTable.setCellSelectionEnabled(true);
        prodTable.setColumnSelectionAllowed(true);
        prodTable.setBackground(new Color(248, 237, 217));
        prodTable.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        prodTable.setSelectionBackground(new Color(239, 193, 112));
        //prodTable.setGridColor(new Color(84, 26, 5));
        
        JTableHeader Theader = prodTable.getTableHeader();
        Theader.setBackground(new Color(84, 26, 5));
        Theader.setForeground(new Color(255, 200, 102));
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        scrollPane.setViewportView(prodTable); // Obidos- until here for the table as well
        
        JScrollPane  scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(35, 584, 975, 172);
        contentPane.add(scrollPane_1);
        
        matsTable = new JTable();
        matsTable.setCellSelectionEnabled(true);
        matsTable.setColumnSelectionAllowed(true);
        matsTable.setBackground(new Color(248, 237, 217));
        matsTable.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        matsTable.setSelectionBackground(new Color(239, 193, 112));
        
        JTableHeader MatsHeader = matsTable.getTableHeader();
        MatsHeader.setBackground(new Color(84, 26, 5));
        MatsHeader.setForeground(new Color(255, 200, 102));
        MatsHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        scrollPane_1.setViewportView(matsTable);
        
        JLabel lblNewLabel_3 = new JLabel("PRODUCTS:");
        lblNewLabel_3.setForeground(new Color(210, 140, 11));
        lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblNewLabel_3.setBounds(35, 358, 115, 13);
        contentPane.add(lblNewLabel_3);
        
        JLabel lblNewLabel_3_1 = new JLabel("MATERIALS:");
        lblNewLabel_3_1.setForeground(new Color(210, 140, 11));
        lblNewLabel_3_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblNewLabel_3_1.setBounds(35, 561, 115, 13);
        contentPane.add(lblNewLabel_3_1);

	}
	
    private String formatDate(Date date) {
        // Implement your desired date formatting logic here
        // For example:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
	
	 // Custom renderer class - to change the background of the options container in the dropdown box
    static class CustomComboBoxRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            component.setBackground(new Color(255, 239, 208)); // Set the desired background color
            return component;
        }
    }
    
    private void refreshTable() {
        dbconnect();
        try {
            String searchText = searchTxt.getText();
            String selectedFilterOption = comboBox.getSelectedItem().toString();

            StringBuilder queryBuilder = new StringBuilder("SELECT pt.*, it.status, it.critical_category FROM productstb pt INNER JOIN inventorytb it ON pt.inventoryID = it.inventoryID WHERE 1=1");
            StringBuilder queryBuilderMaterial = new StringBuilder("SELECT mt.*, it.status, it.critical_category FROM materialstb mt INNER JOIN inventorytb it ON mt.inventoryID = it.inventoryID WHERE 1=1");

            if (!searchText.isEmpty()) {
                queryBuilder.append(" AND (pt.prodName LIKE '%").append(searchText).append("%'");
                queryBuilder.append(" OR pt.category LIKE '%").append(searchText).append("%')");

                queryBuilderMaterial.append(" AND (mt.matsName LIKE '%").append(searchText).append("%'");
                queryBuilderMaterial.append(" OR mt.category LIKE '%").append(searchText).append("%')");
            }

            if (selectedFilterOption.equals("Product Name (A-Z)")) {
                queryBuilder.append(" ORDER BY pt.prodName ASC");
                queryBuilderMaterial.append(" ORDER BY mt.matsName ASC");
            } else if (selectedFilterOption.equals("Quantity (highest-lowest)")) {
                queryBuilder.append(" ORDER BY pt.quantity DESC");
                queryBuilderMaterial.append(" ORDER BY mt.quantity DESC");
            } else if (selectedFilterOption.equals("Price (highest-lowest)")) {
                queryBuilder.append(" ORDER BY pt.price DESC");
                queryBuilderMaterial.append(" ORDER BY mt.price DESC");
            } else if (selectedFilterOption.equals("Status")) {
                queryBuilder.append(" ORDER BY it.status DESC");
                queryBuilderMaterial.append(" ORDER BY it.status DESC");
            } else if (selectedFilterOption.equals("Product ID")) {
                queryBuilder.append(" ORDER BY pt.prodID ASC");
                queryBuilderMaterial.append(" ORDER BY mt.matsID ASC");
            }

            String query = queryBuilder.toString();
            ResultSet resultSet = stmt.executeQuery(query);

            // Clear the existing data from the table model
            // Clear the existing data from the product table model
            DefaultTableModel productModel = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class; // Set the checkbox column to Boolean type
                    }
                    return super.getColumnClass(columnIndex);
                }
                public boolean isCellEditable(int row, int column) {
                    return true; // Make all cells editable
                }
            };

            prodTable.setModel(productModel);

            ResultSetMetaData productMetaData = resultSet.getMetaData();
            int productColumnCount = productMetaData.getColumnCount();

            // Add column names to the product table model, including checkbox column
            productModel.addColumn("Select"); // Checkbox column
            for (int columnIndex = 1; columnIndex <= productColumnCount; columnIndex++) {
                productModel.addColumn(productMetaData.getColumnName(columnIndex));
            }

            // Set the selection mode to single selection
            prodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Add rows to the product table model
            while (resultSet.next()) {
                Object[] rowData = new Object[productColumnCount + 1]; // +1 for checkbox column
                rowData[0] = false; // Set initial checkbox value to false
                for (int columnIndex = 1; columnIndex <= productColumnCount; columnIndex++) {
                    rowData[columnIndex] = resultSet.getObject(columnIndex);
                }
                productModel.addRow(rowData);
            }

            resultSet.close();

            // Add a listener to the product table model for checkbox changes
            productModel.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 0) {
                        Boolean checked = (Boolean) productModel.getValueAt(row, column);
                        if (checked) {
                            Object prodID = productModel.getValueAt(row, 1);
                            prodIDInt = Integer.parseInt(prodID.toString());
                            Object productName = productModel.getValueAt(row, 3);
                            productNameString = productName.toString();
                            // Perform desired actions with the retrieved values
                            // Get the selected product's information
                            Object[] productInfo = getSelectedProductInfo(prodIDInt, productNameString);
                            
                            // For the print function
                            Object id = productModel.getValueAt(row, 1);
                            Object name = productModel.getValueAt(row, 3);
                            IDInt = Integer.parseInt(id.toString());
                            NameString = name.toString();

                            if (table != null) {
	                            // Get the table model
	                            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
	
	                            // Pass the selected product's information and table model to SellFrame
	                            SellFrame.updateTable(productInfo, tableModel);
	                            PrintFrame.updateTable(productInfo, tableModel);
                            }
                        }
                    }
                }
            });

            // Refresh the material table using the same logic
            String materialQuery = queryBuilderMaterial.toString();
            ResultSet materialResultSet = stmt.executeQuery(materialQuery);

            // Clear the existing data from the material table model
            DefaultTableModel materialModel = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class; // Set the checkbox column to Boolean type
                    }
                    return super.getColumnClass(columnIndex);
                }
                public boolean isCellEditable(int row, int column) {
                    return true; // Make all cells editable
                }
            };

            matsTable.setModel(materialModel);

            ResultSetMetaData materialMetaData = materialResultSet.getMetaData();
            int materialColumnCount = materialMetaData.getColumnCount();

            // Add column names to the material table model, including checkbox column
            materialModel.addColumn("Select"); // Checkbox column
            for (int columnIndex = 1; columnIndex <= materialColumnCount; columnIndex++) {
                materialModel.addColumn(materialMetaData.getColumnName(columnIndex));
            }

            // Set the selection mode to single selection
            matsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Add rows to the material table model
            while (materialResultSet.next()) {
                Object[] rowData = new Object[materialColumnCount + 1]; // +1 for checkbox column
                rowData[0] = false; // Set initial checkbox value to false
                for (int columnIndex = 1; columnIndex <= materialColumnCount; columnIndex++) {
                    rowData[columnIndex] = materialResultSet.getObject(columnIndex);
                }
                materialModel.addRow(rowData);
            }

            materialResultSet.close();

            // Add a listener to the material table model for checkbox changes
            materialModel.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 0) {
                        Boolean checked = (Boolean) materialModel.getValueAt(row, column);
                        if (checked) {
                            Object matsID = materialModel.getValueAt(row, 1);
                            matsIDInt = Integer.parseInt(matsID.toString());
                            Object matsName = materialModel.getValueAt(row, 3);
                            matsNameString = matsName.toString();
                            // Perform desired actions with the retrieved values
                            // Get the selected product's information
                            Object[] materialInfo = getSelectedMaterialInfo(matsIDInt, matsNameString);

                            // For the print function
                            Object id = materialModel.getValueAt(row, 1);
                            Object name = materialModel.getValueAt(row, 3);
                            IDInt = Integer.parseInt(id.toString());
                            NameString = name.toString();
                            
                            if (table != null) {
                                // Get the table model
                                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

                                // Pass the selected product's information and table model to SellFrame
                                UseFrame.updateTable(materialInfo, tableModel);
                                PrintFrame.updateTable(materialInfo, tableModel);
                            }
                        }
                    }
                }
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    
    private void refreshTableDate() {
        dbconnect();
        try {
            searchTxt.getText();
            Date fromDate = dateChooser_1_1.getDate();
            Date toDate = dateChooser_1.getDate();

            StringBuilder queryBuilder = new StringBuilder("SELECT pt.*, it.status, it.critical_category FROM productstb pt INNER JOIN inventorytb it ON pt.inventoryID = it.inventoryID WHERE 1=1");
            StringBuilder queryBuilderMaterial = new StringBuilder("SELECT mt.*, it.status, it.critical_category FROM materialstb mt INNER JOIN inventorytb it ON mt.inventoryID = it.inventoryID WHERE 1=1");

            if (fromDate != null) {
                queryBuilder.append(" AND pt.addedDate >= '").append(formatDate(fromDate)).append("'");
                queryBuilderMaterial.append(" AND mt.addedDate >= '").append(formatDate(fromDate)).append("'");
            }
            if (toDate != null) {
                queryBuilder.append(" AND pt.addedDate <= '").append(formatDate(toDate)).append("'");
                queryBuilderMaterial.append(" AND mt.addedDate <= '").append(formatDate(toDate)).append("'");
            }

            String query = queryBuilder.toString();
            ResultSet resultSet = stmt.executeQuery(query);

            // Clear the existing data from the product table model
            DefaultTableModel productModel = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class; // Set the checkbox column to Boolean type
                    }
                    return super.getColumnClass(columnIndex);
                }
                public boolean isCellEditable(int row, int column) {
                    return true; // Make all cells editable
                }
            };

            prodTable.setModel(productModel);

            ResultSetMetaData productMetaData = resultSet.getMetaData();
            int productColumnCount = productMetaData.getColumnCount();

            // Add column names to the product table model, including checkbox column
            productModel.addColumn("Select"); // Checkbox column
            for (int columnIndex = 1; columnIndex <= productColumnCount; columnIndex++) {
                productModel.addColumn(productMetaData.getColumnName(columnIndex));
            }

            // Set the selection mode to single selection
            prodTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Add rows to the product table model
            while (resultSet.next()) {
                Object[] rowData = new Object[productColumnCount + 1]; // +1 for checkbox column
                rowData[0] = false; // Set initial checkbox value to false
                for (int columnIndex = 1; columnIndex <= productColumnCount; columnIndex++) {
                    rowData[columnIndex] = resultSet.getObject(columnIndex);
                }
                productModel.addRow(rowData);
            }

            resultSet.close();
            
            // Add a listener to the product table model for checkbox changes
            productModel.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 0) {
                        Boolean checked = (Boolean) productModel.getValueAt(row, column);
                        if (checked) {
                            Object prodID = productModel.getValueAt(row, 1);
                            prodIDInt = Integer.parseInt(prodID.toString());
                            Object productName = productModel.getValueAt(row, 3);
                            productNameString = productName.toString();
                            // Perform desired actions with the retrieved values
                            // Get the selected product's information
                            Object[] productInfo = getSelectedProductInfo(prodIDInt, productNameString);
                            
                            // For the print function
                            Object id = productModel.getValueAt(row, 1);
                            Object name = productModel.getValueAt(row, 3);
                            IDInt = Integer.parseInt(id.toString());
                            NameString = name.toString();
                            
                            if (table != null) {
	                            // Get the table model
	                            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
	
	                            // Pass the selected product's information and table model to SellFrame
	                            SellFrame.updateTable(productInfo, tableModel);
	                            PrintFrame.updateTable(productInfo, tableModel);
                            }
                        }
                    }
                }
            });

            // Refresh the material table using the same logic
            String materialQuery = queryBuilderMaterial.toString();
            ResultSet materialResultSet = stmt.executeQuery(materialQuery);

            // Clear the existing data from the material table model
            DefaultTableModel materialModel = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class; // Set the checkbox column to Boolean type
                    }
                    return super.getColumnClass(columnIndex);
                }
                public boolean isCellEditable(int row, int column) {
                    return true; // Make all cells editable
                }
            };

            matsTable.setModel(materialModel);

            ResultSetMetaData materialMetaData = materialResultSet.getMetaData();
            int materialColumnCount = materialMetaData.getColumnCount();

            // Add column names to the material table model, including checkbox column
            materialModel.addColumn("Select"); // Checkbox column
            for (int columnIndex = 1; columnIndex <= materialColumnCount; columnIndex++) {
                materialModel.addColumn(materialMetaData.getColumnName(columnIndex));
            }

            // Set the selection mode to single selection
            matsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Add rows to the material table model
            while (materialResultSet.next()) {
                Object[] rowData = new Object[materialColumnCount + 1]; // +1 for checkbox column
                rowData[0] = false; // Set initial checkbox value to false
                for (int columnIndex = 1; columnIndex <= materialColumnCount; columnIndex++) {
                    rowData[columnIndex] = materialResultSet.getObject(columnIndex);
                }
                materialModel.addRow(rowData);
            }
            
            materialModel.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    if (column == 0) {
                        Boolean checked = (Boolean) materialModel.getValueAt(row, column);
                        if (checked) {
                            Object matsID = materialModel.getValueAt(row, 1);
                            matsIDInt = Integer.parseInt(matsID.toString());
                            Object matsName = materialModel.getValueAt(row, 3);
                            matsNameString = matsName.toString();
                            // Perform desired actions with the retrieved values
                            // Get the selected product's information
                            Object[] materialInfo = getSelectedMaterialInfo(matsIDInt, matsNameString);

                           // For the print function
                            Object id = materialModel.getValueAt(row, 1);
                            Object name = materialModel.getValueAt(row, 3);
                            IDInt = Integer.parseInt(id.toString());
                            NameString = name.toString();
                            
                            if (table != null) {
                                // Get the table model
                                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

                                // Pass the selected product's information and table model to SellFrame
                                UseFrame.updateTable(materialInfo, tableModel);
                                PrintFrame.updateTable(materialInfo, tableModel);
                            }
                        }
                    }
                }
            });
            
            materialResultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private static Object[] getSelectedProductInfo(int prodID, String productName) {
        int productColumnCount = 2;
        Object[] productInfo = new Object[productColumnCount];
        productInfo[0] = prodID;
        productInfo[1] = productName;
        // Add more columns as needed
        // productInfo[2] = ...;
        // productInfo[3] = ...;
        return productInfo;
    }

    private static Object[] getSelectedMaterialInfo(int matsID, String matsName) {
        int materialColumnCount = 2;
        Object[] materialInfo = new Object[materialColumnCount];
        materialInfo[0] = matsID;
        materialInfo[1] = matsName;
        // Add more columns as needed
        // materialInfo[2] = ...;
        // materialInfo[3] = ...;
        return materialInfo;
    }
    
    private static Object[] getSelectedInfo(int id, String name) {
    	int columnCount = 2;
        Object[] info = new Object[columnCount];
        info[0] = id;
        info[1] = name;
        // Add more columns as needed
        // info[2] = ...;
        // info[3] = ...;
        return info;
    }
    
//    private void save() {
//        dbconnect();
//        try {
//            // Get the table model
//        	DefaultTableModel productModel = new DefaultTableModel();
//
//            // Loop through each row and update the database
//            for (int i = 0; i < productModel.getRowCount(); i++) {
//
//                // Get the checkbox value from the table
//                Boolean checked = (Boolean) productModel.getValueAt(i, 0);
//
//                if (checked) {
//                    // Get the values from the table
//                    String prodID = productModel.getValueAt(i, 1).toString();
//                    String inventoryID = productModel.getValueAt(i, 2).toString();
//                    String prodName = productModel.getValueAt(i, 4).toString();
//                    String category = productModel.getValueAt(i, 5).toString();
//                    String quantity = productModel.getValueAt(i, 6).toString();
//                    String price = productModel.getValueAt(i, 7).toString();
//                    String expDate = productModel.getValueAt(i, 8).toString();
//                    String addDate = productModel.getValueAt(i, 9).toString();
//                    String status;
//                    String crit_cat;
//
//                    if (Integer.parseInt(quantity) > 60) {
//                        crit_cat = "Low";
//                    } else if (Integer.parseInt(quantity) > 20) {
//                        crit_cat = "Medium";
//                    } else {
//                        crit_cat = "High";
//                    }
//
//                    if (Integer.parseInt(quantity) > 0) {
//                        status = "in inventory";
//                    } else {
//                        status = "out of stock";
//                    }
//
//                    // Update the database with the values
//                    String updateQuery = "UPDATE productstb SET ProdName = ?, Category = ?, Quantity = ?, Price = ?, EXPdate = ?, addedDate = ? WHERE ProdID = ?";
//                    String updateQuery2 = "UPDATE inventorytb SET Quantity = ?, Status = ?, critical_category = ? WHERE inventoryID = ?";
//
//                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
//                    updateStmt.setString(1, prodName);
//                    updateStmt.setString(2, category);
//                    updateStmt.setInt(3, Integer.parseInt(quantity));
//                    updateStmt.setDouble(4, Double.parseDouble(price));
//                    updateStmt.setString(5, expDate);
//                    updateStmt.setString(6, addDate);
//                    updateStmt.setInt(7, Integer.parseInt(prodID));
//                    updateStmt.executeUpdate();
//
//                    PreparedStatement updateStmt2 = conn.prepareStatement(updateQuery2);
//                    updateStmt2.setInt(1, Integer.parseInt(quantity));
//                    updateStmt2.setString(2, status);
//                    updateStmt2.setString(3, crit_cat);
//                    updateStmt2.setInt(4, Integer.parseInt(inventoryID));
//                    updateStmt2.executeUpdate();
//
//                    updateStmt.close();
//                    updateStmt2.close();
//                }
//            }
//
//            // Display a success message
//            JOptionPane.showMessageDialog(ProductFrame.this, "Data saved successfully!");
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(ProductFrame.this, "Error saving data.");
//        }
//    }
    
    class EnabledCheckBoxEditor extends DefaultCellEditor {
        public EnabledCheckBoxEditor() {
            super(new JCheckBox());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JCheckBox checkbox = (JCheckBox) super.getTableCellEditorComponent(table, value, isSelected, row, column);
            checkbox.setEnabled(true); // Set the checkbox as enabled
            checkbox.setHorizontalAlignment(SwingConstants.CENTER);
            return checkbox;
        }
    }
}