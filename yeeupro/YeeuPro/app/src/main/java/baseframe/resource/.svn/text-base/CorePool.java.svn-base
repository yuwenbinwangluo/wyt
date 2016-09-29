package baseframe.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import baseframe.base.RefObject;
import baseframe.tools.Util;

public class CorePool {

	private static HashMap<String, ArrayList<RefObject>> pool = new HashMap<String, ArrayList<RefObject>>();
	private static RefObject temp = null;

	public static RefObject getInstanceFromPool(String className) {
		if (!CorePool.pool.containsKey(className)) {
			CorePool.pool.put(className, new ArrayList<RefObject>());
		}
		RefObject file = null;
		ArrayList<RefObject> classArr = CorePool.pool.get(className);
		int len = classArr.size();
		Util.logInfo(className + "----" + len);
		for (int i = 0; i < len; i++) {
			file = classArr.get(i);
			if (file.actived == RefObject.NO_ACTIVED) {
				file.actived = RefObject.ACTIVED;
				return file;
			}
		}
		Class<?> cla;
		try {
			cla = Class.forName(className);
			try {
				file = (RefObject) cla.newInstance();
				classArr.add(file);
				file.actived = RefObject.ACTIVED;
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
		Iterator<Entry<String, ArrayList<RefObject>>> iter = pool.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, ArrayList<RefObject>> entry = (Entry<String, ArrayList<RefObject>>) iter
					.next();
			ArrayList<RefObject> val = (ArrayList<RefObject>) entry
					.getValue();
			for (int i = 0; i < val.size(); i++) {
				temp = val.get(i);
				if (temp.actived == RefObject.WAIT_DESTROY) {
					temp.time -= 17;
					if (temp.time <= 0) {
						temp.time = 0;
						temp.actived = RefObject.NO_ACTIVED;
					}
				}
			}
		}
	}

}
