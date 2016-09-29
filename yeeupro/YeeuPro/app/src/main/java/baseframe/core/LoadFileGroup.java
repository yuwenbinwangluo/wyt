package baseframe.core;

import android.os.Handler;

import java.util.ArrayList;

import baseframe.base.RefObject;
import baseframe.resource.CorePool;
import baseframe.resource.LoadManager;
import baseframe.tools.Util;

public class LoadFileGroup extends RefObject {
	
	public int id=0;
	private ArrayList<LoadFile> fileGroup=new ArrayList<LoadFile>();
	private LoadFile target=null;//
	private Handler handler=null;
	private LoadFile result=null;
	
	private int _allCounts=0;
	private int _curIndex=0;
	

	public void addOneLoadFile(String url,Handler loadComHandler)
	{
		this.handler=loadComHandler;
		if(result==null){
			result=(LoadFile)CorePool.getInstanceFromPool("baseframe.core.LoadFile");
			result.id=id;
			result.handler=handler;
			result.groupID=0;
		}
//		LoadFile file=new LoadFile();
		LoadFile file=(LoadFile)CorePool.getInstanceFromPool("baseframe.core.LoadFile");
		file.url=url;
		file.groupID=id;
		file.listener=new LoadCompleteListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void loadComHandler(LoadFile file) {
				if(actived== RefObject.ACTIVED){
					if(file!=null){
						if(result==null){
							result=(LoadFile)CorePool.getInstanceFromPool("baseframe.core.LoadFile");
							result.id=id;
							result.handler=handler;
							result.groupID=0;
						}
						if(result.data==null){
							result.data=new ArrayList<Object>();
						}
						((ArrayList<Object>)(result.data)).add(((ArrayList<Object>)(file.data)).get(0));
						file.dispose();
					}
					fileGroup.remove(0);
					if(target!=null){
						target.dispose();
						target=null;
					}
					startLoad();
				}else{
					if(file!=null){
						file.dispose();
					}
				}
			}
		};
		fileGroup.add(file);
		_allCounts++;
	}	
	public void startLoad()
	{
		if(fileGroup.size()>0){
			_curIndex++;
			target=fileGroup.get(0);
			LoadManager.addLoadFile(target);
		}else{
			if(handler!=null){
				LoadManager.sendMsg(result);
			}
			result=null;
		}
	}
		
	public int getCurIndex()
	{
		return _curIndex;
	}
		
	public int getAllCounts()
	{
		return _allCounts;
	}
	
	@Override
	public void dispose()
	{
		fileGroup.clear();
		Util.disposeObject(target);
		target=null;
		Util.disposeObject(result);
		result=null;
		handler=null;
		_allCounts=0;
		_curIndex=0;
		id=0;
	    super.dispose();
	}

	
	
	
	
	
	
	
	
	

}
