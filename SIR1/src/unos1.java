import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.Normalizer.Form;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class unos1 extends Form1 {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private int num1,num2;
	private static String name1, name2;
	private JLabel lblNewLabel;	
	protected static Igrac[] players2;
	protected static int players2_len;	
	static int mesto;
	static Form1 f1;
	/**
	 * Launch the application.
	 */
	public static void main(String name11, String name22, Igrac[] i, int len, int mesto_klika, Form1 f) {
		EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
					unos1 window = new unos1();
					window.frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}); name1=name11; name2=name22; players2=i; players2_len=len; mesto=mesto_klika;  f1=f;
	}

	/**
	 * Create the application.
	 */
	public unos1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 266, 143);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(43, 56, 64, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(137, 56, 64, 20);
		frame.getContentPane().add(textField_1);
		
		lblNewLabel = new JLabel(name1+" VS "+name2);
		lblNewLabel.setBounds(43, 31, 158, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnUnesi = new JButton("Unesi");
		btnUnesi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 	
				try{
				num1=Integer.parseInt(textField.getText());				
				num2=Integer.parseInt(textField_1.getText());  
				
				if(GetNum1() > GetNum2()){
					int br=getPlayer_index(name1);						
					players2[br].Poeni_inc();	f1.azuriraj(mesto);				
				}
				if(GetNum1() < GetNum2()){
					int br=getPlayer_index(name2);
					players2[br].Poeni_inc();	f1.azuriraj(mesto); 				
				}  	
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(frame, "Nekorektno unet rezultat");
				}
				//br_klika++;
						
				
				frame.setVisible(false);				
			} 
		});
		
		btnUnesi.setBounds(86, 82, 89, 23);
		frame.getContentPane().add(btnUnesi);
		
		
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public int GetNum1(){return num1;}
	public int GetNum2(){return num2;}
	
	public int getPlayer_index(String s){
		int i=0;
		for(i=0;i<players2_len;i++){
			if(players2[i].getIme().equals(s)){
				return i;
			}
		}
		return -1;
	}
	
}

