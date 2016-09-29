package baseframe.base;

import baseframe.config.AppConfig;

public class Updater extends RefObject {
	
	private Thread thread=null;
	private long delay=16;
	private boolean isStart=false;
	private UpdaterListener listener=null;
	
	public void init(){
		delay=(long)AppConfig.deltaTime;

	}
	public void start(){
		isStart=true;
		thread=new Thread(new Runnable() {
			@Override
			public void run() {
				while(isStart){
					try {
						Thread.sleep(delay);
						if(listener!=null){
							listener.update();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}
	public void stop(){
		isStart=false;
	}
	public void setUpdaterListener(UpdaterListener lis){
		this.listener=lis;
	}
	public void dispose(){
		stop();
		thread=null;
		listener=null;
	}
}
