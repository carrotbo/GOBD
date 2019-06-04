package com.gwkj.qixiubaodian.obd.thread;

import android.os.Handler;
import android.os.Message;

import com.gwkj.qixiubaodian.obd.contants.Common;
import com.gwkj.qixiubaodian.obd.item.OBDPid;
import com.gwkj.qixiubaodian.obd.service.SendSocketService;

import java.util.List;

public class TableCmdBleThread extends Thread
{
	private static TableCmdBleThread instanceInitThread = null;
	private static List<OBDPid.DataBean> mlist;
	public static Handler rehandler;
	private boolean isBreak=false;

	public static TableCmdBleThread getInstnceInitThread(Handler handler, List<OBDPid.DataBean> list)
	{
		mlist=list;
		rehandler=handler;
		if (instanceInitThread == null)
			instanceInitThread = new TableCmdBleThread();
		return instanceInitThread;
	}

	private TableCmdBleThread()
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
			synchronized (Common.sendLock2) {
				OBDPid.DataBean data = mlist.get(i);
				String cmd=data.getSid()+data.getPid();
				Message msg = new Message();
				msg.what = 5;
				msg.obj = data;
				rehandler.sendMessage(msg);
				SendSocketService.sendBleMessage(cmd,rehandler);
				rehandler.sendEmptyMessageDelayed(6,200);
				try
				{
					Common.sendLock2.wait(10000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				if(i==(length-1)){
					rehandler.sendEmptyMessageDelayed(7,5);
					try
					{
						Common.sendLock2.wait(10000);
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
		synchronized (Common.sendLock2)
		{
			Common.sendLock2.notify();
		}
	}
}
