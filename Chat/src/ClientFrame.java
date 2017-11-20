import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class ClientFrame extends JFrame implements ActionListener
{

	private JPanel contentPane;
	private final JLabel lblUsername = new JLabel("USERNAME");
	private final JLabel lblIndirizzo = new JLabel("INDIRIZZO");
	private final JLabel lblPorta = new JLabel("PORTA");
	private final JComboBox ip1 = new JComboBox();
	private final JTextField username = new JTextField();
	private final JTextField porta = new JTextField();
	private final JComboBox ip2 = new JComboBox();
	private final JComboBox ip3 = new JComboBox();
	private final JComboBox ip4 = new JComboBox();
	private final JButton btnConnetti = new JButton("CONNETTI");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JButton invia = new JButton("INVIA");
	private final JTextField messaggio = new JTextField();
	private final JButton reset = new JButton("CANCELLA CHAT");
	private final JList list = new JList();
	private final DefaultListModel m=new DefaultListModel();
	private final JLabel lblUtentiConnessi = new JLabel("UTENTI CONNESSI");
	private final JLabel lblChat = new JLabel("CHAT");
	private final JTextArea chat = new JTextArea();
	private static final long serialVersionUID=1L;
	private boolean connesso;
	private Client c;
	private JButton button=new JButton("☺");
	private JButton button1=new JButton("☻");
	private JButton button2=new JButton("♥");
	private JButton button3=new JButton("♦");
	private JButton button4=new JButton("♣");
	private JButton button5=new JButton("♠");
	private JButton button6=new JButton("♂");
	private JButton button7=new JButton("♀");
	private JButton button8=new JButton("☼");
	private JButton button9=new JButton("♫");
	private ImageIcon icon=new ImageIcon();
	private JLabel immagine=new JLabel();
	
	public ClientFrame() 
	{
		messaggio.setEnabled(false);
		messaggio.setBounds(10, 482, 551, 53);
		messaggio.setColumns(10);
		porta.setText("5000");
		porta.setBounds(79, 90, 78, 20);
		porta.setColumns(10);
		username.setText("Anonimo");
		username.setBounds(79, 20, 242, 20);
		username.setColumns(10);
		setTitle("CHAT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 807, 620);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		icon = new ImageIcon("graffiti.jpg");
		immagine = new JLabel(icon);
		immagine.setBounds(0, 0, 807, 620);
		immagine.setIcon(icon);

		lblUsername.setBounds(10, 23, 97, 14);
		lblUsername.setForeground(Color.white);
		contentPane.add(lblUsername);
		lblIndirizzo.setBounds(10, 58, 59, 14);
		lblIndirizzo.setForeground(Color.white);
		contentPane.add(lblIndirizzo);
		lblPorta.setBounds(10, 93, 97, 14);
		lblPorta.setForeground(Color.white);
		for(int i=0;i<256;i++)
		{
			ip1.addItem(i);
			ip2.addItem(i);
			ip3.addItem(i);
			ip4.addItem(i);
		}
		
		contentPane.add(lblPorta);
		ip1.setSelectedIndex(127);
		ip1.setBounds(79, 55, 53, 20);	
		contentPane.add(ip1);
		
		contentPane.add(username);
		
		contentPane.add(porta);
		ip2.setSelectedIndex(0);
		ip2.setBounds(142, 55, 53, 20);
		
		contentPane.add(ip2);
		ip3.setSelectedIndex(0);
		ip3.setBounds(205, 55, 53, 20);
		
		contentPane.add(ip3);
		ip4.setSelectedIndex(1);
		ip4.setBounds(268, 55, 53, 20);
	
		contentPane.add(ip4);
		btnConnetti.setBounds(635, 23, 147, 23);
		btnConnetti.addActionListener(this);
		
		contentPane.add(btnConnetti);
		scrollPane.setBounds(10, 126, 551, 344);
		
		contentPane.add(scrollPane);
		
		scrollPane.setColumnHeaderView(lblChat);
		chat.setEnabled(false);
		chat.setEditable(false);
		
		scrollPane.setViewportView(chat);
		scrollPane_1.setBounds(571, 126, 211, 344);
		
		contentPane.add(scrollPane_1);
	
		list.setEnabled(false);
		list.setModel(m);
		
		scrollPane_1.setViewportView(list);
		
		scrollPane_1.setColumnHeaderView(lblUtentiConnessi);
		invia.setEnabled(false);
		invia.addActionListener(this);
		invia.setBounds(571, 481, 211, 54);
		
		contentPane.add(invia);
		
		contentPane.add(messaggio);
		reset.setEnabled(false);
		reset.setBounds(635, 66, 147, 23);
		reset.addActionListener(this);
		
		contentPane.add(reset);
		
		button.setBounds(18, 546, 51, 23);
		contentPane.add(button);
		button.addActionListener(this);
		
		button1.setBounds(70,546,51,23);
		contentPane.add(button1);
		button1.addActionListener(this);
		
		button2.setBounds(122,546,51,23);
		contentPane.add(button2);
		button2.addActionListener(this);
		
		button3.setBounds(175,546,51,23);
		contentPane.add(button3);
		button3.addActionListener(this);
		
		button4.setBounds(222,546,51,23);
		contentPane.add(button4);
		button4.addActionListener(this);
		
		button5.setBounds(275,546,51,23);
		contentPane.add(button5);
		button5.addActionListener(this);
		
		button6.setBounds(322,546,51,23);
		contentPane.add(button6);
		button6.addActionListener(this);
		
		button7.setBounds(375,546,51,23);
		contentPane.add(button7);
		button7.addActionListener(this);
		
		button8.setBounds(422,546,51,23);
		contentPane.add(button8);
		button8.addActionListener(this);
		
		button9.setBounds(475,546,51,23);
		contentPane.add(button9);
		button9.addActionListener(this);
		
		contentPane.add(immagine);
		
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		Object o=evt.getSource();
		
		if(o==invia)
		{
				String s=messaggio.getText();
				
				if(s.length()>0)
				{
					c.inviaMessaggio(new Messaggio(Messaggio.MESSAGGIO,s));
					AudioInputStream audioInputStream = null;
					try {
						audioInputStream = AudioSystem.getAudioInputStream(new File("suonooo.wav").getAbsoluteFile());
					} catch (UnsupportedAudioFileException | IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
			        Clip clip = null;
					try {
						clip = AudioSystem.getClip();
					} catch (LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        try {
						clip.open(audioInputStream);
					} catch (LineUnavailableException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        clip.start();
					messaggio.setText("");
				}
		}
		if(o==reset)
		{
			chat.setText(null);
		}
		if(o==btnConnetti)
		{
			if(connesso==false)
			{
				String u=username.getText();
				int p=isIntOrNot(porta.getText());
				String ip=ip1.getSelectedIndex()+"."+ip2.getSelectedIndex()+"."+ip3.getSelectedIndex()+"."+ip4.getSelectedIndex();
				String inet=u+"------>["+ip+"]\n";
				if(u.length()<1)
				{
					JOptionPane.showMessageDialog(null, "Inserire un username!", "ERRORE", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(p<1 || p>65535)
				{
					JOptionPane.showMessageDialog(null, "Inserire una porta valida!", "ERRORE", JOptionPane.ERROR_MESSAGE);
					return;
				}
			
				c=new Client(ip,p,u,this);
			
				if(c.start()==false)
				{
					return;
				}
				
				btnConnetti.setText("DISCONETTI");
				ip1.setEnabled(false);
				ip2.setEnabled(false);
				ip3.setEnabled(false);
				ip4.setEnabled(false);
				username.setEnabled(false);
				porta.setEnabled(false);
				messaggio.setEnabled(true);
				invia.setEnabled(true);
				chat.setEnabled(true);
				list.setEnabled(true);
				m.addElement(inet);
				list.setModel(m);
				reset.setEnabled(true);
				connesso=true;
				
			}
			else
			{
				c.inviaMessaggio(new Messaggio(Messaggio.LOGOUT,""));
				JOptionPane.showMessageDialog(null,"TI SEI DISCONNESSO!","ATTENZIONE",JOptionPane.INFORMATION_MESSAGE);
				btnConnetti.setText("CONNETTI");
				ip1.setEnabled(true);
				ip2.setEnabled(true);
				ip3.setEnabled(true);
				ip4.setEnabled(true);
				username.setEnabled(true);
				porta.setEnabled(true);
				messaggio.setEnabled(false);
				invia.setEnabled(false);
				chat.setEnabled(false);
				list.setEnabled(false);
				reset.setEnabled(false);
				connesso=false;
				m.clear();
				
			}
			
		}
		if(evt.getSource()==button){
			messaggio.setText(messaggio.getText()+"☺");
		}
		
		if(evt.getSource()==button1){
			messaggio.setText(messaggio.getText()+"☻");
		}
		if(evt.getSource()==button2){
			messaggio.setText(messaggio.getText()+"♥");
		}
		if(evt.getSource()==button3){
			messaggio.setText(messaggio.getText()+"♦");
		}
		if(evt.getSource()==button4){
			messaggio.setText(messaggio.getText()+"♣");
		}
		if(evt.getSource()==button5){
			messaggio.setText(messaggio.getText()+"♠");
		}
		if(evt.getSource()==button6){
			messaggio.setText(messaggio.getText()+"♂");
		}
		if(evt.getSource()==button7){
			messaggio.setText(messaggio.getText()+"♀");
		}
		if(evt.getSource()==button8){
			messaggio.setText(messaggio.getText()+"☼");
		}
		if(evt.getSource()==button9){
			messaggio.setText(messaggio.getText()+"♫");
		}
		
		
	}
	public int isIntOrNot(String s)
	{
		int i;
		
		try
		{
			i=Integer.parseInt(s);
		}
		catch(Exception e)
		{
			i=-1;
		}
		return i;
	}
	
	public void stampa(String msg)
	{
		chat.append(msg);
		chat.setCaretPosition(chat.getText().length()-1);
	}
	
	public void connessioneFallita()
	{
		btnConnetti.setText("CONNETTI");
		ip1.setEnabled(true);
		ip2.setEnabled(true);
		ip3.setEnabled(true);
		ip4.setEnabled(true);
		username.setEnabled(true);
		porta.setEnabled(true);
		messaggio.setEnabled(false);
		invia.setEnabled(false);
		chat.setEnabled(false);
		chat.setText(null);
		list.setEnabled(false);
		m.clear();
		reset.setEnabled(false);
		connesso=false;
	}
	
	public JComboBox getIp1() {
		return ip1;
	}

	public JTextField getUsername() {
		return username;
	}

	public JTextField getPorta() {
		return porta;
	}

	public JComboBox getIp2() {
		return ip2;
	}

	public JComboBox getIp3() {
		return ip3;
	}

	public JComboBox getIp4() {
		return ip4;
	}

	public JButton getBtnConnetti() {
		return btnConnetti;
	}

	public JButton getInvia() {
		return invia;
	}

	public JTextField getMessaggio() {
		return messaggio;
	}

	public JButton getReset() {
		return reset;
	}

	public JList getList() {
		return list;
	}

	public DefaultListModel getM() {
		return m;
	}

	public JTextArea getChat() {
		return chat;
	}

	public static void main(String[] args)
	{
		new ClientFrame();
	}
}
