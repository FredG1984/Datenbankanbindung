package db.dbp6;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;




class DatenbankGUI
{
    
    JFrame tbWindow;
    JComboBox<String> tbComboBox;
    JButton tbOk;
    JButton tbchannel;
    
    DatenbankConnecter containDB = new DatenbankConnecter();
	

    DatenbankGUI(String titel) {
    	tbWindow = new JFrame(titel);
    	tbComboBox = new JComboBox<String>();
    	tbComboBox.setEditable( true );
        tbWindow.add(tbComboBox);        
    }
    
    public JFrame getFrame() {
        return tbWindow;
    }

    public JComboBox<String> get_comboBox()
    {
        return tbComboBox;
    }
}
