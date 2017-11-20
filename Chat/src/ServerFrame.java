import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class ServerFrame extends JFrame implements ActionListener, WindowListener
{

	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	private JScrollPane scrollPane_1 = new JScrollPane();
	private JButton logout = new JButton("DISCONETTI UTENTE");
	private JButton reset = new JButton("CANCELLA CHAT");
	private JList list = new JList();
	private JLabel lblUtentiConnessi = new JLabel("UTENTI CONNESSI");
	private JTextArea textArea = new JTextArea();
	private JLabel lblChat = new JLabel("CHAT");
	private JButton btnAvviaServer = new JButton("AVVIA SERVER");
	private DefaultListModel m=new DefaultListModel();
	public Server server;
	private JLabel lblPorta = new JLabel("PORTA");
	private JTextField porta = new JTextField();
	private static final long serialVersionUID=1L;
	private ImageIcon icon=new ImageIcon();
	private JLabel immagine=new JLabel();
	
	
	public ServerFrame() 
	{
		server=null;
		porta.setText("5000");
		porta.setBounds(66, 406, 86, 20);
		porta.setColumns(10);
		setTitle("CHAT - Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 539);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		icon = new ImageIcon("graffiti2.jpg");
		immagine = new JLabel(icon);
		immagine.setBounds(0, 0,755,539);
		immagine.setIcon(icon);
		
		
		scrollPane.setBounds(10, 11, 552, 383);
		
		contentPane.add(scrollPane);
		textArea.setEditable(false);
		textArea.setEnabled(false);
		
		scrollPane.setViewportView(textArea);
		scrollPane.setColumnHeaderView(lblChat);
		
		scrollPane_1.setBounds(572, 11, 157, 383);
		scrollPane_1.setViewportView(list);
		scrollPane_1.setColumnHeaderView(lblUtentiConnessi);

		
		list.setModel(m);
		
		contentPane.add(scrollPane_1);
		
		logout.setEnabled(false);
		logout.setBounds(572, 405, 157, 23);
		logout.addActionListener(this);
		
		contentPane.add(logout);
		reset.setEnabled(false);
		reset.setBounds(402, 405, 157, 23);
		reset.addActionListener(this);
		
		contentPane.add(reset);
		btnAvviaServer.setBounds(10, 453, 124, 23);
		btnAvviaServer.addActionListener(this);
		
		contentPane.add(btnAvviaServer);
		lblPorta.setBounds(10, 409, 46, 14);
		lblPorta.setForeground(Color.white);
		contentPane.add(lblPorta);
		
		contentPane.add(porta);
		addWindowListener(this);
		contentPane.add(immagine);
		setVisible(true);
	}
	public void stampa(String msg)
	{
		textArea.append(msg);
		textArea.setCaretPosition(textArea.getText().length()-1);
	}
	public void actionPerformed(ActionEvent evt)
	{
		Object o=evt.getSource();
		
		if(btnAvviaServer==o)
		{
			if(server!=null)
			{
				server.stop();
				server=null;
				porta.setEditable(true);
				btnAvviaServer.setText("AVVIA SERVER");
				reset.setEnabled(false);
				logout.setEnabled(false);
				m.clear();
				textArea.setText(null);
				return;
			}
			if(server==null)
			{
				int p=isIntOrNot(porta.getText());
				
				if(p<1 || p>65535)
				{
					 JOptionPane.showMessageDialog(null, "Inserire una porta valida!", "ERRORE", JOptionPane.ERROR_MESSAGE);
					 return;
				}
				
				server=new Server(p,this);
				new ServerRunning().start();
				btnAvviaServer.setText("CHIUDI SERVER");
				porta.setEnabled(false);
				list.setEnabled(true);
				logout.setEnabled(true);
				reset.setEnabled(true);
			}
		}
		if(reset==o)
		{
			textArea.setText(null);
		}
		if(logout==o)
		{
			int y=list.getSelectedIndex();
			
			if(y!=-1)
			{
				m.remove(y);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Selezionare prima un utente", "ERRORE", JOptionPane.ERROR_MESSAGE);
			}
			
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
	public void windowClosing(WindowEvent evt)
	{
		if(server!=null)
		{
			try
			{
				server.stop();
			}
			catch(Exception eClose) {}
			server=null;
		}
		dispose();
		System.exit(0);
	}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	public static void main(String args[])
	{
		new ServerFrame();
	}
	
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	public JButton getLogout() {
		return logout;
	}
	public JButton getReset() {
		return reset;
	}
	public JList getList() {
		return list;
	}
	public JTextArea getTextArea() {
		return textArea;
	}
	public JButton getBtnAvviaServer() {
		return btnAvviaServer;
	}
	public DefaultListModel getM() {
		return m;
	}
	public JTextField getPorta() {
		return porta;
	}

	class ServerRunning extends Thread
	{
		public void run()
		{
			server.start();
			btnAvviaServer.setText("AVVIA SERVER");
			porta.setEditable(true);
			stampa("Il server è crashato\n");
			server=null;
		}
	}

}
