import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PrintFrame extends JFrame {
	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	static String query;
	static String date, category, prodID, prodName, matsID, matsName, quantity, price, inventoryID;
	private JPanel contentPane;
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	JPanel confirm_btn = new JPanel();
	private static JTable table;
	static int id;
	static String name;
	public static int IDInt;
	public static String NameString;
    private int selectedID;
    private String selectedName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrintFrame frame = new PrintFrame(id, name);
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
            if (panel == confirm_btn) {
                // Open the EditFrame
                PrintFrame printFrame = new PrintFrame(selectedID, selectedName);
                PrintingKeywordFrame printingKeywordFrame = new PrintingKeywordFrame(selectedID, selectedName);
                printingKeywordFrame.setPrintFrame(printFrame);
                printingKeywordFrame.setVisible(true);
            	dispose();
            }
        }
        
    }
    
	/**
	 * Create the frame.
	 */
	public PrintFrame(int id, String name) {
		setSelectedID(id);
        setSelectedName(name);
		this.IDInt = id;
		this.NameString = name;
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrintFrame.class.getResource("/res/logoStock.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 391);
		setTitle("StockSign");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CONFIRM PRINTING ITEMS");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setForeground(new Color(241, 159, 11));
		lblNewLabel.setBounds(402, 64, 313, 40);
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
		cancel_btn.setBounds(400, 305, 98, 25);
		contentPane.add(cancel_btn);
		
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
		confirm_btn.setBounds(570, 305, 114, 25);
		contentPane.add(confirm_btn);
		
		JLabel add_lbl_1 = new JLabel("CONFIRM");
		add_lbl_1.setForeground(new Color(84, 26, 5));
		add_lbl_1.setFont(new Font("Segoe UI", Font.BOLD, 13));
		add_lbl_1.setBounds(23, 0, 65, 27);
		confirm_btn.add(add_lbl_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(94, 131, 874, 139);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setColumnSelectionAllowed(true);
        table.setBackground(new Color(248, 237, 217));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        table.setSelectionBackground(new Color(239, 193, 112));
        //prodTable.setGridColor(new Color(84, 26, 5));
        
        JTableHeader Theader = table.getTableHeader();
        Theader.setBackground(new Color(84, 26, 5));
        Theader.setForeground(new Color(255, 200, 102));
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        scrollPane.setViewportView(table); // Obidos- until here for the table as well
        
		scrollPane.setViewportView(table);
		initializeTable();
	}

	public int getID() {
	    return IDInt;
	}
	public String getName() {
	    return NameString;
	}
	private void initializeTable() {
	    DefaultTableModel tableModel = new DefaultTableModel();
	    tableModel.addColumn("Item ID");
	    tableModel.addColumn("Item Name");
	    // Add more columns as needed
	    // tableModel.addColumn("Column Name");
	    tableModel.addRow(new Object[] { getID(), getName() });
	    table.setModel(tableModel);
	}
	public static void updateTable(Object[] info, DefaultTableModel tableModel) {
	    tableModel.addRow(info);
	    table.setModel(tableModel); // Set the table model to the updated model
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
