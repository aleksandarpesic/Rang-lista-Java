import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class Form1 {

	private JFrame frmRangLista;
	private JTextField textField_upisIgraca;
	private JCheckBoxMenuItem unetiSviIgraci; 
	private JButton btn_upis;
	private JLabel lblNewLabel;	
	public Igrac[] players;
		
	private int broj_igraca;
	private static int broj_utakmica;
	public  int broj_klikova=0;
	
	private String[][] lista;
	private JLabel[] lbl_lista; //Lista sa leve strane ( player1 vs player 2)
	private JLabel[] lbl_lista_Rangiranja;
	private JLabel label_staticText2;
	JButton btn_naredna; 
	JButton btn_zapamti;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form1 window = new Form1();
					window.frmRangLista.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Form1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRangLista = new JFrame();
		frmRangLista.setTitle("Rang lista - Aleksandar Pesic");
		frmRangLista.getContentPane().setBackground(new Color(255, 239, 213));
		frmRangLista.getContentPane().setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		
		players=new Igrac[10]; 
		lbl_lista=new JLabel[10];
		lbl_lista_Rangiranja=new JLabel[10];
		for(int j=0; j<10;j++){		
			lbl_lista[j] = new JLabel("");			
			lbl_lista[j].setBounds(351, j*28+156, 169, 14);			
			frmRangLista.getContentPane().add(lbl_lista[j]);
			
			lbl_lista_Rangiranja[j] = new JLabel("");
			lbl_lista_Rangiranja[j].setFont(new Font("Trebuchet MS", Font.BOLD, 13));
			lbl_lista_Rangiranja[j].setBounds(51, j*28+56, 169, 14);			
			frmRangLista.getContentPane().add(lbl_lista_Rangiranja[j]);
		}
		
		JLabel label_staticText1 = new JLabel("Rang lista:");
		label_staticText1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_staticText1.setBounds(49, 17, 92, 28);
		label_staticText1.setForeground(new Color(0, 204, 0));
		frmRangLista.getContentPane().add(label_staticText1);
		
		label_staticText2 = new JLabel("Partije");
		label_staticText2.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		label_staticText2.setBounds(351, 115, 86, 23);
		label_staticText2.setForeground(new Color(0, 204, 0));
		frmRangLista.getContentPane().add(label_staticText2);		
		label_staticText2.setVisible(false);
		
		btn_zapamti = new JButton("Zapamti Rang listu");
		btn_zapamti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zapamti_u_fail();
			}
		});
		btn_zapamti.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btn_zapamti.setBackground(Color.ORANGE);
		btn_zapamti.setBounds(351, 196, 140, 23);
		frmRangLista.getContentPane().add(btn_zapamti);
		btn_zapamti.hide();
		
		broj_igraca=0;
		lista=new String[10][2]; //sluzi kao lista meceva gde je i prvom [] prvi igrac, a [drugi] igrac
		
		btn_naredna = new JButton("Prikazu narednu listu");
		btn_naredna.hide();
		btn_naredna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 Sort_Write();				
				 broj_utakmica=sredi_sortirane(); 				
				 
				if(broj_utakmica>=1) 
					lista_utakmica(broj_utakmica);
				else{
					lbl_lista[0].setText("Kraj");
					btn_zapamti.show();
				}	
				btn_naredna.hide();
			}
		});
		
		
		btn_naredna.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		btn_naredna.setBackground(Color.ORANGE);
		btn_naredna.setBounds(427, 81, 131, 23);
		frmRangLista.getContentPane().add(btn_naredna);
		
		frmRangLista.getContentPane().addMouseListener(new MouseAdapter() {		
			public void mousePressed(MouseEvent arg0) {	
				
				if(unetiSviIgraci.getState()){					
					unos1 un=new unos1(); 
					for(int i=0;i<=broj_utakmica;i++){ 
						Sort_Write();
						if( arg0.getY()<i*28+156+11  && arg0.getY()>i*28+156 && arg0.getX()>=351){							
							un.main(lista[i][0], lista[i][1], players, broj_igraca, i, Form1.this);					
							
							break;
						}				
					}
				} 
			}
		});
		frmRangLista.setBounds(100, 100, 585, 423);
		frmRangLista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		frmRangLista.getContentPane().setLayout(null);
		
		textField_upisIgraca = new JTextField();
		textField_upisIgraca.setBounds(351, 49, 86, 20);
		textField_upisIgraca.setColumns(10);
		frmRangLista.getContentPane().add(textField_upisIgraca);
		textField_upisIgraca.setText("");
		
		lblNewLabel = new JLabel("Upis igraca");		
		lblNewLabel.setForeground(new Color(0, 204, 0));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
		lblNewLabel.setBounds(351, 17, 169, 22);
		frmRangLista.getContentPane().add(lblNewLabel);
				
		//check box za upis - ovde ulazi samo prvi put kad unesem sve igrace
		unetiSviIgraci = new JCheckBoxMenuItem("upisani svi igraci");
		unetiSviIgraci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(unetiSviIgraci.getState()){
					textField_upisIgraca.setEditable(false);
					btn_upis.setEnabled(false);
					unetiSviIgraci.hide(); 
					lblNewLabel.setText("Svi igraci su upisani");  lblNewLabel.disable();
					label_staticText2.setVisible(true);
					
					broj_utakmica=sredi_sortirane();
					lista_utakmica(broj_utakmica);					
				}
			}
		});		
		unetiSviIgraci.setBounds(450, 47, 119, 22);
		unetiSviIgraci.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmRangLista.getContentPane().add(unetiSviIgraci);
		
		
		btn_upis = new JButton("Unesi");
		btn_upis.setBounds(351, 80, 67, 23);
		btn_upis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				if ((findPlayer(textField_upisIgraca.getText())!=-1) || textField_upisIgraca.getText()==""){
					JOptionPane.showMessageDialog(null, "Niste popunili polje ili igrac sa imenom "+textField_upisIgraca.getText()+" vec postoji. Morate se upisati drugacije");				
				} else {
					players[broj_igraca]=new Igrac(textField_upisIgraca.getText());				
					lblNewLabel.setText("Unet "+players[broj_igraca].getIme()); 
					broj_igraca++; 
				}
				textField_upisIgraca.setText("");
			} 
		}); 
		btn_upis.setBackground(Color.ORANGE);
		btn_upis.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		frmRangLista.getContentPane().add(btn_upis);
	}
	
	public void azuriraj(int i ) { //kada se unese rezultat i klik na Ok u pod prozoru
		lbl_lista[i].setText(""); 
		broj_klikova++;							
		if(broj_klikova==broj_utakmica){
			//iscrtaj
			 obrisi_listu(broj_utakmica);								 
			 broj_klikova=0;
			 btn_naredna.show();								
		} 	
	}
	
	public void zapamti_u_fail() { //koristi btn_zapamti na klik		
		PrintWriter writer ;
		try {
			 writer = new PrintWriter("RangLista.txt", "UTF-8");
			 for(int i=0;i<broj_igraca;i++)
				 writer.println(players[i].getIme()+"-->"+players[i].getPoeni());
			 writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {	
			JOptionPane.showMessageDialog(null, "Doslo je do greske, nije moguce upisati na disk");
			e.printStackTrace();					
		}
		JOptionPane.showMessageDialog(null, "Rang lista uspesno zapamcena na disk");		
	}
	
	public void broj_klikaInc(){
		broj_klikova++;
	}
	public void setPlayer(Igrac i, int j){
		players[j]=i;
	}
	
	public void Sort_Write(){		
		Igrac[] playersTmp=new Igrac[10];
		for(int i=0;i<broj_igraca;i++){ //deep constructor copy
			Igrac tmp=players[i];
			if(tmp!=null) 
				playersTmp[i]=new Igrac(tmp);
		}		
		 sortiraj_igrace(playersTmp);		
		//sad ispisujem u levom delu igrace redom
		for(int i=0;i<broj_igraca;i++){
			lbl_lista_Rangiranja[i].setText(playersTmp[i].getIme()+" ->"+playersTmp[i].getPoeni());
		}			
	}	
	public int findPlayer(String n){
		int i=0;
		while(i<broj_igraca) { 
			if(n.equals(players[i].getIme())) 
				return i;
			i++;
		}		
		return -1;
	}
	public void sortiraj_igrace(Igrac[] players2){
		 Igrac tmp=players2[0]; //sortiram		 
		
		 for(int i=0;i<broj_igraca;i++){
				for(int j=i;j<broj_igraca;j++){
					if(players2[i].getPoeni()<players2[j].getPoeni()){
						tmp=players2[i];
						players2[i]=players2[j];
						players2[j]=tmp;
					}
				}
			}
		 
	 }
	public boolean imaju_razlicie_poene(){
		int i=0; //
		int br=0;
		
		 while(i<broj_igraca-1){ 			 
			 if(players[i].getPoeni()>players[i+1].getPoeni())
				 br++;
		 }		 
		if(br==broj_igraca-1) return false; 
			return true;
	}
	public void lista_utakmica(int broj_utakmica2){
		//pravim listu utakmica
		int i=0;
		Random r=new Random();
		/*
		if(broj_utakmica2%2 != 0){
			broj_utakmica=broj_utakmica2/2+1;
		} else broj_utakmica=broj_utakmica2/2;
		*/
			for(i=0;i<broj_utakmica2;i++){						
			int prvi=r.nextInt(broj_igraca),drugi=0;
					while(players[prvi].ukljucen!=true){
						prvi=r.nextInt(broj_igraca);
					}
				players[prvi].ukljucen=false;
				drugi=r.nextInt(broj_igraca);
				int brPonavljanja=0;
				while( (players[drugi].ukljucen!=true || players[drugi].getPoeni()!=players[prvi].getPoeni() || drugi==prvi ) && (brPonavljanja)<broj_igraca  ){
					drugi=brPonavljanja; brPonavljanja++;
				} 
				while( ( players[drugi].getPoeni()!=players[prvi].getPoeni()  || drugi==prvi)&& brPonavljanja>=broj_igraca  ){
					drugi=r.nextInt(broj_igraca);
				} 
				players[drugi].ukljucen=false;							
				lista[i][0]=players[prvi].getIme();
				lista[i][1]=players[drugi].getIme();			
			}						
		/*
		if(broj_utakmica2%2 != 0){			
								
				int prvi=r.nextInt(broj_igraca),drugi=0;
						while(players[prvi].ukljucen==true){
							prvi=r.nextInt(broj_igraca);
						}
					players[prvi].ukljucen=true;
					drugi=r.nextInt(broj_igraca);
					while( players[drugi].getPoeni()!=players[prvi].getPoeni() || prvi==drugi ){
						drugi=r.nextInt(broj_igraca);
					} 						
					lista[i][0]=players[prvi].getIme();
					lista[i][1]=players[drugi].getIme();			
					
		}*/		
		for(int j=0; j<broj_utakmica2;j++){							
			lbl_lista[j].setText(lista[j][0]+"  VS  "+lista[j][1]); 
		}		
	}
	
	public  void obrisi_listu(int broj_utakmica2){
		for(int j=0; j<broj_utakmica2;j++)						
			lbl_lista[j].setText(" ");		
	}
	
	public int sredi_sortirane(){
		int i,j,k=0,isti; 
		
		for(i=0;i<broj_igraca;i++){ players[i].ukljucen=false; }
		
		for(i=0;i<broj_igraca;i++){			
			isti=1;
			for(j=0;j<broj_igraca;j++){ 
				if(i!=j && players[i].getPoeni()==players[j].getPoeni() && players[j].ukljucen==false)	{				
					++isti;							
					players[j].ukljucen=true;	
				}
			}
			if(isti>1){ 
				if(isti%2 !=0) k+=(isti/2)+1; 
				else k+=isti/2;
				players[i].ukljucen=true;}
		} 
		return k; //vraca broj utakmica
	}
	
	public class Igrac{
		private String ime;
		public int poeni;
		public boolean ukljucen;		
		public Igrac(String s){
			ime=s;
			poeni=0;
			ukljucen=false;
		}
		Igrac (Igrac k){			
			this.poeni=k.getPoeni();
			this.ime=k.getIme();
			this.ukljucen=k.ukljucen;			
		}
		public void Poeni_inc(){ ++poeni;}
		public void Igra_Pauzira(boolean b){
			ukljucen=b;
		}
		public void SetIme(String s){ 
			ime=s;
		}
		public String getIme(){
			return ime;	
		}
		public int getPoeni(){
			return poeni;	
		}
	
		
	}
}
