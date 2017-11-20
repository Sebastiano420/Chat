import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server
{
	private ArrayList<ClientThread> ct;
	private ServerFrame sf;
	private SimpleDateFormat data;
	private int porta;
	private boolean running;
	private static int uid=0;
	
	public Server(int porta,ServerFrame sf)
	{
		this.porta=porta;
		this.sf=sf;
		this.data=new SimpleDateFormat("HH:mm:ss");
		ct=new ArrayList<ClientThread>();
	}
	public void start()
	{
		running=true;
		
		try
		{
			ServerSocket serverSocket=new ServerSocket(porta);
			
			while(running)
			{
				stampa("SERVER IN ASCOLTO SULLA PORTA "+porta+".");
				Socket socket=serverSocket.accept();
				
				if(!running)
				{
					break;
				}
				
				ClientThread t=new ClientThread(socket);
				ct.add(t);
				t.start();
				sf.getM().addElement(""+t.username);
			}
			try
			{
				serverSocket.close();
				
				for(int i=0;i<ct.size();++i)
				{
					try
					{
						ct.get(i).sInput.close();
						ct.get(i).sOutput.close();
						ct.get(i).close();
					}
					catch(IOException ioe) {}
				}
			}
			catch(Exception e)
			{
				stampa("ERRORE CHIUDENDO IL SERVER E I CLIENT: "+e);
			}
		}
		catch(IOException ioe)
		{
			stampa(data.format(new Date())+" - ERRORE NELLA CREAZIONE DEL SERVERSOCKET: "+ioe+"\n");
		}
	}
	protected void stop()
	{
		running=false;
		
		try
		{
			new Socket("127.0.0.1",porta);
		}
		catch(Exception e) {}
	}
	private void stampa(String msg)
	{
		sf.stampa(data.format(new Date())+" - "+msg+"\n");
	}
	private synchronized void broadcast(String msg)
	{
		String s=data.format(new Date())+" - "+msg+"\n";

		sf.stampa(s);
		
		for(int i=ct.size();--i>=0;)
		{
			if(!ct.get(i).scriviMessaggio(s))
			{
				ct.remove(i);
				stampa(ct.get(i).username+" ha lasciato la chat.");
			}
		}
	}
	synchronized void remove(int id)
	{
		for(int i=0;i<ct.size();++i)
		{
			if(ct.get(i).id==id)
			{
				ct.remove(i);
				return;
			}
		}
	}
	class ClientThread extends Thread
	{
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		String username;
		Messaggio m;
		String data;
		int id;
		
		ClientThread(Socket socket)
		{
			this.id=uid;
			this.socket=socket;
			
			try
			{
				sOutput=new ObjectOutputStream(socket.getOutputStream());
				sInput=new ObjectInputStream(socket.getInputStream());
				username=(String) sInput.readObject();
				stampa(username+" si è connesso.");
			}
			catch(IOException ioe)
			{
				stampa("Errore creando gli input/output streams: "+ioe);
				return;
			}
			catch(ClassNotFoundException e) {}
			data=new Date().toString()+"\n";
		}
		public void run()
		{
			boolean running=true;
			
			while(running)
			{	
				try
				{
					m=(Messaggio) sInput.readObject();
				}
				catch(IOException e)
				{
					stampa(username+" ERRORE DURANTE LA LETTURA DEGLI STREAMS: "+e);
					break;
				}
				catch(ClassNotFoundException e1)
				{
					break;
				}
				
				String msg=m.getMessaggio();
		
				switch(m.getTipo())
				{
					case Messaggio.MESSAGGIO: broadcast(username+": "+msg);
						 uid++;
						 break;
					case Messaggio.LOGOUT:  stampa(username+" ha lasciato la chat. \n");
						 running=false;
						 uid--;
						 break; 
				}
			}
			remove(id);
			close();
			sf.getM().remove(id);
		}
		private void close()
		{
			try 
			{
				if(sOutput != null) sOutput.close();
			}
            catch(Exception e) {}
			try 
			{
				if(sInput != null) sInput.close();
			}
            catch(Exception e) {}
			try 
			{
				if(socket != null) socket.close();
			}
            catch(Exception e) {}
		}
		private boolean scriviMessaggio(String msg)
		{
			if(!socket.isConnected())
			{
				close();
				return false;
			}
			try
			{
				sOutput.writeObject(msg);
			}
			catch(IOException e)
			{
				stampa("ERRORE MANDANDO IL MESSAGGIO A "+username);
				stampa(e.toString());
			}
			return true;
		}
	}
	public ArrayList<ClientThread> getCt() {
		return ct;
	}
	public void setCt(ArrayList<ClientThread> ct) {
		this.ct = ct;
	}
	
	
}
