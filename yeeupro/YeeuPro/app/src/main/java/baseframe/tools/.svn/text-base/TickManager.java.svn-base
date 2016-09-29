package baseframe.tools;

import baseframe.base.RefObject;

/**
 * 滴答管理器
 */
public class TickManager extends RefObject{

    static final int FRAME_RATE=16;//帧频--毫秒

    private static TickManager _instance=null;
    public static TickManager getInstance()
    {
        if(_instance==null)
        {
            _instance=new TickManager();
        }
        return _instance;
    }
    public TickManager()
    {
        super();
    }

    public void start()
    {
        Thread thread=new Thread(new Runnable(){

            public void run()
            {
                while(true)
                {
                    try{
                        Thread.sleep(FRAME_RATE);
                        Tick();

                        Util.Log(TAG, "perform_____", Util.LogType.Debug);
                    }catch (Exception e)
                    {
                        Util.Log(TAG, e.getMessage(), Util.LogType.Debug);
                    }
                }
            }

        });

        thread.start();
    }

    private void Tick()
    {
        //刷新UI

    }




    public interface ITick
    {
        void Tick(long time);
    }




}
