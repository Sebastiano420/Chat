import java.io.Serializable;

public class Messaggio implements Serializable
{
	protected static final long serialVersionUID=1112122200L;
	static final int MESSAGGIO=1,LOGOUT=2;
	private int tipo;
	private String messaggio;
	
	public Messaggio(int tipo,String messaggio)
	{
		this.messaggio=messaggio;
		this.tipo=tipo;
	}
	public String getMessaggio()
	{
		return messaggio;
	}	
	public int getTipo()
	{
		return tipo;
	}
}
