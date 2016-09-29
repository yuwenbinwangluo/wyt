package baseframe.net;




import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.InetSocketAddress;
import java.net.Socket;



/**
 * 底层socket
 * @author Administrator
 *
 */
public class NetSocket {	
	public static final int TIME_OUT = 2*60*1000;

	private String serverIp; // 服务器ip地址

	private int serverPort; // 服务器通信端口
	

	private Socket connection; // 连接socket

	private InputStream is; // socket输入流

	private OutputStream os; // socket输出流
	
	
	private int sid; // 网络层序列号,如果非法会遭到服务器的断开连接处理	
	
	
	public NetSocket(String serverIp, int port) {
		this.serverIp = serverIp;
		this.serverPort = port;
	}
	/**
	 * 连接服务器，成功后即可进行通信
	 * 
	 * @throws Exception
	 */
	private  void connectServer() throws Exception {
		connection = new Socket();
		connection.connect(new InetSocketAddress(serverIp,serverPort));
		connection.setSoTimeout(TIME_OUT);
		is = connection.getInputStream();
		os = connection.getOutputStream();
	}	
	/**
	 * 接受信息
	 * @return
	 * @throws Exception 
	 */
	private ResultValue receiveInfo() throws Exception {
			final byte[] bytes = new byte[4096];			
//			int readed = is.read(bytes);			
//			if (readed <= 0) {
//				return true;
//			}				
//			byte[] tmp = new byte[readed - 2];
//			System.arraycopy(bytes, 2, tmp, 0, readed - 2);
//			
//			if (tmp != null && tmp.length > 0) {
//				//System.out.println(tmp.length);
//				receiverPool.add(tmp);
//			}				
			int readed = is.read(bytes);
			if (readed < 0) {
				return new ResultValue(ReturnValue.LOSE,"网络错误1");
			}
			int cl = getUShort(bytes);
			int receivedL = 0;
			ByteArray t = new ByteArray();			
			while (true) {
				byte[] temp = new byte[readed];
				System.arraycopy(bytes, 0, temp, 0, readed);
				t.append(temp);
				receivedL += readed;
				if (receivedL >= cl) {
					break;
				}
				readed = is.read(bytes);
			}
			if (t.getLength() > 0) {
				ByteArray temp = new ByteArray((byte[])t.toArray());
				temp.readShort();
				temp.readInt();
				temp.readInt();
				return new ResultValue(temp.readInt(),temp.readUTF());
			}else{
				return new ResultValue(ReturnValue.LOSE,"网络错误2");
			}		
			
	}
	/**
	 * 发送命令
	 * @param msg
	 * @throws IOException 
	 */
	private void sendCommand(byte[] msg) throws Exception {
		if (msg == null) {
			return;
		}
//		getSocket().writeShort(_byte.length+4);//长度short
//		getSocket().writeByte(0x41);// 网络层命令
//		checkoutID++;
//		getSocket().writeByte(checkoutID);// 流水号
//		
//		
//		getSocket().writeBytes(_byte, 0, _byte.length);
//		getSocket().flush();

			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream d = new DataOutputStream(b);
			int dataL = 4 + msg.length;
			d.writeShort(dataL);
			d.writeByte(0x41);
			d.writeByte(sid % 256);
//			byte sum = 0;
//			for(byte b1:msg){
//				sum +=b1;
//			}
//			d.writeByte(sum);
			d.write(msg);
			byte[] tmpBuf=b.toByteArray();
			sid++;
			if (tmpBuf != null && tmpBuf.length > 0) {
				os.write(tmpBuf);
				os.flush();
			}
	}
	
	/**
	 * 关闭连接
	 *
	 */
	private  void close() {
		try {		
			if (connection != null && connection.isConnected()) {
				connection.close();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从这个字节数组的前两个字节 读取无符号型short 贰肆
	 * 
	 * @param bytes
	 *            byte[]
	 * @return int
	 * @throws Exception
	 */
	private int getUShort(byte bytes[]) throws Exception {
		if (bytes.length >= 2) {
			return ((bytes[0] << 8) & 0xff00) | (bytes[1] & 0xff);
		} else {
			throw new Exception("数据包长度非法");
		}
	}	
	public ResultValue sendData(byte[] msg){
		if (msg == null) {
			return new ResultValue(ReturnValue.LOSE,"数据错误");
		}
		ResultValue result;;
		try {
			connectServer();
			sendCommand(msg);
			result= receiveInfo();
		} catch (Exception e) {
            e.printStackTrace();
			result= new ResultValue(ReturnValue.LOSE,"网络错误3");
		}finally{
			close();
		}
		return result;
	}
}

