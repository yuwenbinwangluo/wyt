package baseframe.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import baseframe.base.BaseObject;
import baseframe.tools.Util;

public class CorePool {

	private static HashMap<String, ArrayList<BaseObject>> pool = new HashMap<String, ArrayList<BaseObject>>();
	private static BaseObject temp = null;

	public static BaseObject getInstanceFromPool(String className) {
		if (!CorePool.pool.containsKey(className)) {
			CorePool.pool.put(className, new ArrayList<BaseObject>());
		}
		BaseObject file = null;
		ArrayList<BaseObject> classArr = CorePool.pool.get(className);
		int len = classArr.size();
		Util.logInfo(className + "对象池长度：" + len);
		for (int i = 0; i < len; i++) {
			file = classArr.get(i);
			if (file.actived == BaseObject.NO_ACTIVED) {
				file.actived = BaseObject.ACTIVED;
				return file;
			}
		}
		Class<?> cla;
		try {
			cla = Class.forName(className);
			try {
				file = (BaseObject) cla.newInstance();
				classArr.add(file);
				file.actived = BaseObject.ACTIVED;
			} catch (InstantiationException e) {
				Util.logInfo(e.getMessage());
			} catch (IllegalAccessException e) {
				Util.logInfo(e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			Util.logInfo(e.getMessage());
		}
		return file;
	}

	public static void update() {
		Iterator<Entry<String, ArrayList<BaseObject>>> iter = pool.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, ArrayList<BaseObject>> entry = (Entry<String, ArrayList<BaseObject>>) iter
					.next();
			ArrayList<BaseObject> val = (ArrayList<BaseObject>) entry
					.getValue();
			for (int i = 0; i < val.size(); i++) {
				temp = val.get(i);
				if (temp.actived == BaseObject.WAIT_DESTROY) {
					temp.time -= 17;
					if (temp.time <= 0) {
						temp.time = 0;
						temp.actived = BaseObject.NO_ACTIVED;
					}
				}
			}
		}
	}

}
