import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Toolkit;


public class UseFrame extends JFrame {

	private JPanel contentPane;
	private Image img_logo = new ImageIcon(HomeFrame.class.getResource("res/logoStock.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	JPanel confirm_btn = new JPanel();
	static String date, category, matsName, quantity, price, status, crit_cat, inventoryID; // Obidos - these static are important  
	static int prodIDInt;
	static int matsIDInt;
	private static String productNameString;
    static String matsNameString;
	private static JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UseFrame frame = new UseFrame(matsIDInt, matsNameString);
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
	            // Open the SellItemsFrame
	            UseItemsFrame useItemsFrame = new UseItemsFrame(matsIDInt, matsNameString);
	            useItemsFrame.setVisible(true);
	            dispose();
	        }
	    }
        
    }
//    public void receiveProductDetails(int prodID, String productName) {
//        // Perform desired actions with the received values
//        // ...
//        // Pass the values to SellItemsFrame
//        SellItemsFrame sellItemsFrame = new SellItemsFrame(prodID, productName);
//        sellItemsFrame.setVisible(true);
//    }
	/**
	 * Create the frame.
	 */
	public UseFrame (int matsID, String matsName) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UseFrame.class.getResource("/res/logoStock.png")));
		this.matsIDInt = matsID;
		this.matsNameString = matsName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 391);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 237, 217));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("StockSign");
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CONFIRM USING ITEMS");
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
		Color cancelOriginalColor = new Color(255, 200, 102);
        Color cancelHoverColor = new Color(241, 159, 11);
        cancel_btn.addMouseListener(new PanelButtonMouseAdapter(cancel_btn, cancelOriginalColor, cancelHoverColor));
		cancel_btn.setLayout(null);
		cancel_btn.setBackground(new Color(255, 200, 102));
		cancel_btn.setBounds(400, 305, 98, 25);
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
		scrollPane.setBounds(97, 132, 874, 139);
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
	public int getMatsID() {
	    return matsIDInt;
	}
	public static String getMaterialName() {
	    return matsNameString;
	}
    private void initializeTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Material ID");
        tableModel.addColumn("Material Name");
        // Add more columns as needed
        // tableModel.addColumn("Column Name");

        // Fill in the table with the stored ID and name values
        tableModel.addRow(new Object[] { getMatsID(), getMaterialName() });

        table.setModel(tableModel);
    }
    
    public static void updateTable(Object[] materialInfo, DefaultTableModel tableModel) {
        tableModel.addRow(materialInfo);
        table.setModel(tableModel); // Set the table model to the updated model
    }

}
