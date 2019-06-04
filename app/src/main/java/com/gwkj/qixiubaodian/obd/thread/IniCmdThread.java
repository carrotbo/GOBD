package com.gwkj.qixiubaodian.obd.thread;

import android.os.Handler;
import android.os.Message;

import com.gwkj.qixiubaodian.obd.appliaction.BltAppliaction;
import com.gwkj.qixiubaodian.obd.contants.Common;
import com.gwkj.qixiubaodian.obd.item.OBDItem;
import com.gwkj.qixiubaodian.obd.service.SendSocketService;

import java.util.List;

public class IniCmdThread extends Thread
{
	private static IniCmdThread instanceInitThread = null;
	private OBDItem item;
	public static Handler rehandler;
	private boolean isBreak=false;

	public static IniCmdThread getInstnceInitThread(Handler handler)
	{
		rehandler=handler;
		if (instanceInitThread == null)
			instanceInitThread = new IniCmdThread();
		return instanceInitThread;
	}

	private IniCmdThread()
	{
		item = BltAppliaction.getBaseOBD();
	}

	@Override
	public void run()
	{
		if(item==null){
			return;
		}
		List<OBDItem.InitCmdBean> inicmd = item.getInit_cmd();

		int length = inicmd.size();
		isBreak=false;
		for (int i = 0; i < length; i++) {
			if(isBreak){
				break;
			}
			synchronized (Common.sendLock1) {
				OBDItem.InitCmdBean data = inicmd.get(i);
//                            content_ly.addView(getRightTextView("发送到OBD："+data.getCmd()));
				Message msg = new Message();
				msg.what = 0;
				msg.obj = data;
				rehandler.sendMessage(msg);
				SendSocketService.sendMessage(data.getCmd(),null);
				rehandler.sendEmptyMessageDelayed(3,500);
				try
				{
					Common.sendLock1.wait(10000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
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
