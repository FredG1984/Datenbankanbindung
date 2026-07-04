package db.dbp6;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DatenbankConnecter {

	private static Connection con;
	private static Statement stmt;
	static DatenbankGUI ui;
	

	public static void main(String args[]) throws SQLException {

		try {
			// load the jdbc driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// show login dialog and wait for a correct database login
			LoginDialogGUI lg = new LoginDialogGUI();

			// get the database connection from the login dialog
			con = lg.getConn();

			// build a statement
			stmt = con.createStatement();

			ui = new DatenbankGUI(lg.getUsernameField().getText());

			// execute a query
			ResultSet rset = stmt.executeQuery("select table_name from user_tables");
			ResultSetMetaData rsetmd = rset.getMetaData();
			int numberOfColumns = rsetmd.getColumnCount();

			while (rset.next()) {
				System.out.println(" ");
				for (int i = 1; i <= numberOfColumns; i++) {
					ui.get_comboBox().addItem(" " + rset.getString(i));
				}
			}

			ui.getFrame().pack();
			ui.getFrame().setVisible(true);

			ui.get_comboBox().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						update();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally{
					       try{
						       
						       rset.close();
						       
						       }catch(Exception e2){
						           JOptionPane.showMessageDialog(null, "ERROR CLOSE");
						       }
						       
						   }
				}
			});
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR CLOSE");
		} 
	}

	static void update() throws SQLException {
		String tabelle = ui.get_comboBox().getSelectedItem().toString();
		ResultSet rset = stmt.executeQuery("select * from " + tabelle);
		druckeTabelle(rset);
	}

	static void druckeTabelle(ResultSet rset) throws SQLException {
		// String[][] ary = {{},{}};
		try{
		ResultSetMetaData rsetmd = rset.getMetaData();
		int c = rsetmd.getColumnCount();
		Vector column = new Vector(c);
		for (int i = 1; i <= c; i++) {
			// System.out.print(rsetmd.getColumnName(i));
			column.add(rsetmd.getColumnName(i));

		}
		// get the results from query
		Vector data = new Vector();
		Vector row = new Vector();
		while (rset.next()) {
			row = new Vector(c);
			// System.out.println(" ");
			for (int i = 1; i <= c; i++) {
				// System.out.print(rset.getString(i));
				row.add(rset.getString(i));

			}
			data.add(row);
		}
		JFrame frame = new JFrame();
	       frame.setSize(500,120);
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        JPanel panel = new JPanel();
	        JTable table = new JTable(data,column);
	        JScrollPane jsp = new JScrollPane(table);
	        panel.setLayout(new BorderLayout());
	        panel.add(jsp,BorderLayout.CENTER);
	        frame.setContentPane(panel);
	        frame.setVisible(true);
		}catch(Exception e){
		       JOptionPane.showMessageDialog(null, "ERROR");
		   }

	}

	
}
