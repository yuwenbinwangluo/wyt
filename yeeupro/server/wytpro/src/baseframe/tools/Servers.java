package baseframe.tools;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import baseframe.conf.ServerConfig;

import com.thoughtworks.xstream.XStream;

public class Servers {
	private static Servers instance = new Servers();
	private Map<String, Server> servers = new HashMap<String, Server>();
	private String path;
	
	public static Servers getInstance() {
		return instance;
	}
	
	public void load(String path) {
		this.path = path;
		XStream xstream = new XStream();
		xstream.alias("servers", List.class);
		xstream.alias("server", Server.class);
		@SuppressWarnings("unchecked")
		List<Server> list = (List<Server>)xstream.fromXML(new File(path));
		for (Server server : list) {
			servers.put(server.getId(), server);
		}
		if(list.size()>0){
			Server server=list.get(0);
			ServerConfig.SERVER_HOST="http://"+server.ip+":"+server.port;
		}
	}
	
	public void reload() {
		load(path);
	}
	public void clearServers(){
		servers.clear();
	}
	public void put(Server server) {
		servers.put(server.getId(), server);
	}
	
	public Server get(String key) {
		return servers.get(key);
	}
	
	public Map<String, Server> getAll() {
		return servers;
	}
	
	public class Server {
		private String id;
		private String name;
		private String ip;
		private String port;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
	}
}
