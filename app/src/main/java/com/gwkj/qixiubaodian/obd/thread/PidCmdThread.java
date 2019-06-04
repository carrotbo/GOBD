package com.gwkj.qixiubaodian.obd.thread;

import android.os.Handler;
import android.os.Message;

import com.gwkj.qixiubaodian.obd.contants.Common;
import com.gwkj.qixiubaodian.obd.item.OBDPid;
import com.gwkj.qixiubaodian.obd.service.SendSocketService;

import java.util.List;

public class PidCmdThread extends Thread
{
	private static PidCmdThread instanceInitThread = null;
	private static List<OBDPid.DataBean> mlist;
	public static Handler rehandler;
	private boolean isBreak=false;
	private static int num=0;

	public static PidCmdThread getInstnceInitThread(Handler handler, List<OBDPid.DataBean> list)
	{
		mlist=list;
		rehandler=handler;
		if (instanceInitThread == null)
			instanceInitThread = new PidCmdThread();
		return instanceInitThread;
	}
	public static PidCmdThread getInstnceInitThread(Handler handler, List<OBDPid.DataBean> list,int what)
	{
		mlist=list;
		rehandler=handler;
		num=what;
		if (instanceInitThread == null)
			instanceInitThread = new PidCmdThread();
		return instanceInitThread;
	}
	private PidCmdThread()
	{
	}

	@Override
	public void run()
	{
		if(mlist==null){
			return;
		}
		isBreak=false;
		int length = mlist.size();
		for (int i = 0; i < length; i++) {
			if(isBreak){
				break;
			}
			synchronized (Common.sendLock1) {
				OBDPid.DataBean data = mlist.get(i);
				String cmd=data.getSid()+data.getPid();
				Message msg = new Message();
				msg.what = num;
				msg.obj = data;
				rehandler.sendMessage(msg);
				SendSocketService.sendMessage(cmd,rehandler);
				rehandler.sendEmptyMessageDelayed(3,200);
				try
				{
					Common.sendLock1.wait(10000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				if(i==(length-1)){
					rehandler.sendEmptyMessageDelayed(4,5);
					try
					{
						Common.sendLock1.wait(10000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					i=-1;
				}
			}

		}

	}
	public void setBreak(boolean mbread){
        isBreak=mbread;
		synchronized (Common.sendLock1)
		{
			Common.sendLock1.notify();
		}
	}
}
