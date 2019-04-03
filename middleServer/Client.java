package middleServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Client {

	public static void main(String[] args) {
		Socket sck = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		boolean endFlag = false;
		// Ŭ���̾�Ʈ�� �Է°��� ���� ���� input
		String input = null;
		// ������� ���̵�  
		String id = null;
		// ������� �Ϸù�ȣ  
		String number = null;
		try {
			sck = new Socket("localhost", 0525);
			pw = new PrintWriter(new OutputStreamWriter(sck.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sck.getInputStream()));
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("����� id�� �Է��Ͻÿ�:(���̵����4�ڸ�/�Ϸù�ȣ)");
			input = keyboard.readLine();
			
			// '/'��ū�� �̿��� �Է� �� ������
			StringTokenizer tokens = new StringTokenizer(input);
			id = tokens.nextToken("/");
			number = tokens.nextToken("/");
			
			System.out.println("==========="+id+"���� ��ȭâ=========");
			//������ id������
			pw.println(id);
			pw.flush();
			//������ ���� ��� �о���� ������ ����
			InputThread it = new InputThread(sck,br);
			it.start();
			String line = null;
			while((line = keyboard.readLine())!=null)
			{
				pw.println(line);
				pw.flush();
				if(line.equals("quit"))
				{
					System.out.println("�ý����� �����մϴ�.");
					endFlag = true;
					break;
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(br != null)
					br.close();
				if(pw != null)
					pw.close();
				if(sck != null)
					sck.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
}
class InputThread extends Thread
{
	Socket sck = null;
	BufferedReader br = null;
	public InputThread(Socket sck, BufferedReader br) {
		super();
		this.sck = sck;
		this.br = br;
	}
	public void run()//������� �����κ��� ��� �о����
	{
		try {
			String line = null;
			//null���� �ƴϸ� ��� �о�� ������ֱ�
			while((line = br.readLine()) !=null)
			{
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(sck != null)
					sck.close();
				if(br !=null)
					br.close();
					
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}