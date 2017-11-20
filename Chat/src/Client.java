import java.io.*;
import java.net.*;
import java.util.*;

public class Client 
{
	private ObjectInputStream sInput;		//Legge dal socket
	private ObjectOutputStream sOutput;		//Scrive nel socket
	private Socket socket;
	private ClientFrame cf;
	private String server;
	private String username;
	private int porta;
	
	public Client(String server,int porta,String username,ClientFrame cf)
	{
		this.server=server;
		this.porta=porta;
		this.username=username;
		this.cf=cf;
	}		
	public boolean start()					//Fa partire la chat
	{
		try
		{
			socket=new Socket(server,porta);
		}
		catch(Exception e)
		{
			stampa("ERRORE DURANTE LA CONNESSIONE AL SERVER: "+e);
			return false;
		}
		
		stampa("CONNESSIONE ACCETTATA: "+socket.getInetAddress()+":"+socket.getPort());
		
		try
		{
			sInput=new ObjectInputStream(socket.getInputStream());
			sOutput=new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException ioe)
		{
			stampa("ERRORE DURANTE LA CREAZIONE DEGLI STREAMS: "+ioe);
			return false;
		}
		
		new ListenFromServer().start();		//Crea il thread
		
		try
		{
			sOutput.writeObject(username);
		}
		catch(IOException ioe)
		{
			stampa("ERRORE DURANTE IL LOGIN: "+ioe);
			disconetti();
			return false;
		}
		
		return true;						//Il client si è connesso correttamente al server
	}
	public void stampa(String msg)
	{
		cf.stampa(msg+"\n");				//Stampa nella JTextArea della finestra
	}
	public void inviaMessaggio(Messaggio msg)
	{
		try
		{
			sOutput.writeObject(msg);
		}
		catch(IOException ioe)
		{
			stampa("ERRORE DURANTE LA SCRITTURA AL SERVER: "+ioe);
		}
	}
	public void disconetti()
	{
		try
		{
			if(sInput!=null)
				sInput.close();
		}
		catch(Exception e) {}
		
		try
		{
			if(sOutput!=null)
				sOutput.close();
		}
		catch(Exception e) {}
		
		try
		{
			if(socket!=null)
				socket.close();
		}
		catch(Exception e) {}
	
		cf.connessioneFallita();
	}
	
	class ListenFromServer extends Thread
	{
		public void run()
		{
			while(true)
			{
				try
				{
					String msg=(String) sInput.readObject();
					cf.stampa(msg);
				}
				catch(IOException ioe)
				{
					stampa("IL SERVER HA CHIUSO LA CONNESSIONE: "+ioe);
					cf.connessioneFallita();
					break;
				}
				catch(ClassNotFoundException e) {}
			}
		}
	}
	
}
