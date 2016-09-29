package baseframe.net;








  


/**
 * 发送命令
 * @author Administrator
 *
 */
public class SendCommand {
      private static final String NAME="y6rdD";
      private static final String PASSWORD="Q6S7uj";
      private static final String SKEY="Yjus93JhDd2234hs2CIkse&234vR#Gr-v-#!B43K7";
      public static final int CMD_CLIENT  = 0x00;//客户端
      public static final byte TYPE_MANAGER   = 0x30;//后台
	
	/**
	 * 通知客户端更新
	 * @param ip
	 * @param port
	 * @return
	 */
	public static ResultValue sendUpdateClient(String ip,int port) {
		ByteArray b = new ByteArray();
		b.writeInt(packClientCMD(TYPE_MANAGER,CommandList.C_UPDATE));
		b.writeUTF(NAME);
		b.writeUTF(PASSWORD);
		b.writeUTF(CheckMD5.md5(NAME+SKEY+PASSWORD));
		return new NetSocket(ip,port).sendData(b.toArray());
	}
	/**
	 * 设置第三方充值
	 * @param ip
	 * @param port
	 * @return
	 */
	public static ResultValue sendThirdCharge(String ip,int port,int thridFlag) {
		ByteArray b = new ByteArray();
		b.writeInt(packClientCMD(TYPE_MANAGER,CommandList.C_SET_THIRD_CHARGE));
		b.writeUTF(NAME);
		b.writeUTF(PASSWORD);
		b.writeUTF(CheckMD5.md5(NAME+SKEY+PASSWORD));
		b.writeInt(thridFlag);
		return new NetSocket(ip,port).sendData(b.toArray());
	}
	/**
	 * 封装成客户端命令
	 * @param type
	 * @param cmd
	 * @return
	 */
	private static int packClientCMD(int type,int cmd){
		int command = CMD_CLIENT << 24;
		command += type << 16;
		command += cmd;
		return command;
	}
}
