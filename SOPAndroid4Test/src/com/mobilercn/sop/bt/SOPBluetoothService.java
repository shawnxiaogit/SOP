package com.mobilercn.sop.bt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Stack;

import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.SOPAndroidActivity;
import com.mobilercn.util.JJHexHelper;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


public class SOPBluetoothService  extends Service {
	public static final String STATE_FAILED = "26264552524f52";
	public static final String STATE_SUCC   = "26264f4b";

	//version 2
	public static final String VER_2 = "55aa";

	public static String        g_Connect         = "�豸������";
	public static String        g_DisConnect      = "�豸δ����";
	public static String        g_bt_warning      = g_DisConnect;

	public static final int     BT_CONNECT        = 20;
	public static final int     BT_DISCONNECT     = 21;
	public static final int     BT_READMSG        = 22;
	public static final int     BT_TOAST          = 23;
	public static final int     BT_CALL_FINISHED  = 24;
		
    private static final String NO_CALL_MESSAGE   = "no_call";
    private static final int    NO_CALL           = -1;

	
    public static final int     STATE_NONE        = 0;       
    public static final int     STATE_CONNECTING  = 1; 
    public static final int     STATE_CONNECTED   = 2;  
    
	public static Stack<JJBaseActivity>      g_allActivity  = new Stack<JJBaseActivity>();   
	public static boolean                    g_runState     = false;
	public static String                     g_readerID     = null;
	public static String                     g_btMacAddress = null;
    
	public static SOPBluetoothService        SELF           = null;

	private static HashMap<Integer,String>   g_Relations    = new HashMap<Integer, String>(); 
    
    //��ǰָ��,�����жϵ�������
    private String                    mSendCallMessage  = NO_CALL_MESSAGE;
    //��ǰ����
    private static int                mCurrentCall      = NO_CALL;
    
    private final BluetoothAdapter    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();;
    private ConnectThread             mConnectThread;
    private ConnectedThread           mConnectedThread;
    private int                       mState;
    
	public boolean                    mIsRun        = false;

    private StringBuffer              mStringBuffer = new StringBuffer("");
    
    
    private int mStateCount = 0;
    
    
    
    public static SOPBluetoothService getService(){
    	return SELF;
    }
    
    
    private synchronized void setState(int state) {
        mState = state;
        if(mHandler != null){
            if(mState == STATE_CONNECTED){
            	g_bt_warning = g_Connect;
            	if(mHandler != null){
                	Message msg = mHandler.obtainMessage(BT_CONNECT);
                    mHandler.sendMessage(msg);
                    mStateCount = 0;
            	}
            } else{
            	g_bt_warning = g_DisConnect;
            }        	
        }
    }

    public synchronized int getState() {
        return mState;
    }
    
    private void write(byte[] out) {
    	    	
        ConnectedThread r;

        synchronized (this) {
            if (mState != STATE_CONNECTED) {
           	    if(D2EConfigures.G_DEBUG){
           	    	Log.i("out >>>>> ", "����ʧ�ܣ��豸δ���ӣ�");
           	    }
                mHandler.obtainMessage(BT_TOAST, -1, -1, getResources().getString(R.string.bluetooth_prompt)).sendToTarget(); 
                return;
            }
            r = mConnectedThread;
        }
        
   	    if(D2EConfigures.G_DEBUG){
   	    	Log.i("out >>>>> ", "r.write(out)");
   	    }
        r.write(out);
    }

    
    public synchronized void start() {
    	
    	    	
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        
        setState(STATE_NONE);
        
        try{
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(g_btMacAddress);
            connect(device);        	
        }
        catch(Exception ex){
        	Log.i("out >>>>> ", "start error: " + ex.getMessage());
        }     
    }
    
    public synchronized void connect(BluetoothDevice device) {
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device) {

        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}


        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        setState(STATE_CONNECTED);
    }

    public synchronized void stop() {
    	
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_NONE);
    }
    
    public synchronized void timeout(){
    	
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectedThread.interrupt();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread.interrupt();
            mConnectedThread = null;
        }

        setState(STATE_NONE);
        
    }
    
    private void connectionLost() {
    	if(mHandler != null){
    		if(mStateCount <= 0){
    			mStateCount ++;
            	Message msg = mHandler.obtainMessage(BT_DISCONNECT);
                mHandler.sendMessage(msg);
    		}
            SOPBluetoothService.this.start();    		
    	}
    }

    private void connectionFailed(String str) {
    	if(mHandler != null){
    		if(mStateCount <= 2){
    			mStateCount ++;
            	Message msg = mHandler.obtainMessage(BT_DISCONNECT);
                mHandler.sendMessage(msg);
    		}
            if(!SOPAndroidActivity.g_testBt){
                SOPBluetoothService.this.start();    		            	
            }
    	}
    }
   
    
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            try {
            	BluetoothDevice hxm = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
            	Method m;
            	m = hxm.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            	tmp = (BluetoothSocket)m.invoke(hxm, Integer.valueOf(1)); 
            } catch (Exception e) {
            }
            mmSocket = tmp;
        }

        public void run() {
            setName("ConnectThread");         

            try {
                mmSocket.connect();
            } 
            catch (IOException e) {

                try {
                    mmSocket.close();
                } 
                catch (IOException e2) {

                }
                connectionFailed(e.getMessage());
                return;
            }

            synchronized (SOPBluetoothService.this) {
                mConnectThread = null;
            }

            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } 
            catch (IOException e) {
            }
        }
    }
    
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream     mmInStream;
        private final OutputStream    mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } 
            catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
                

        public void run() {
            byte[] buffer = new byte[1];
            int bytes     = -1;
            while (true) {
            	
                try {                    
                    if(mStringBuffer != null && bytes > 0){
                        mStringBuffer.append(JJHexHelper.encode(buffer, 0, bytes));                     
                        if(D2EConfigures.TEST){
                        	Log.e("out >>>>>>>>>>> ԭʼ���ݣ�", mStringBuffer.toString());
                        }
                        
                        //��������
                        //�汾һ:BBXXXXXXEE
                        //&&error
                        int err  = mStringBuffer.toString().indexOf(STATE_FAILED);
                        int succ = mStringBuffer.toString().indexOf(STATE_SUCC); 
                        int ver2 = mStringBuffer.toString().indexOf(VER_2);
                    	int bb   = mStringBuffer.toString().indexOf("bb");

                        //��ȡ�ɹ� &&ok
                        if(succ >= 0 && mStringBuffer.toString().length() == 24){
                        	mStringBuffer.delete(0, mStringBuffer.length());
                        	
                            YTTimeoutMonitor monitor = YTTimeoutMonitor.getInstance();
                            monitor.init(mHandler);
                            monitor.startMonitor();
                        	
                        }
                        //��ȡʧ�� &&error
                        if(err >= 0){
                        	if(mStringBuffer.toString().length() == 30){
                            	mStringBuffer.delete(0, mStringBuffer.length());                        		
                        	}
                        	if(mHandler != null){
                        		mHandler.obtainMessage(BT_CALL_FINISHED, -1, -1, STATE_FAILED).sendToTarget();
                        	}
                        }
                        
                        if(bb >= 0 && mStringBuffer.toString().length() == 38){
                        	
                        	if(mHandler != null){
                        		mHandler.obtainMessage(BT_READMSG, -1, -1, mStringBuffer.toString().substring(0, mStringBuffer.length())).sendToTarget();
                        	}
                        	mStringBuffer.delete(0, mStringBuffer.length());
                        	
                        }
                        
                        if(ver2 >= 0){
                        	int len  = mStringBuffer.length();
                        	boolean normalcard = false;
                        	int total = -1;
                        	int pares = -1;
                        	//55 aa 5c 0c 66 30 00 3a
                        	if(len >= 8){
                        		String totalStr = mStringBuffer.toString().substring(4,6);
                        		String str = mStringBuffer.toString().substring(6,8);
                        		
                        		try{
                        			total = Integer.parseInt(totalStr, 16);
                        		}catch(Exception e){}
                        		
                        		if(str.toLowerCase().equals("00".toLowerCase())){
                        			normalcard = true;
                        		}
                        		else {
                        			normalcard = false;
                            		try{
                            			pares = Integer.parseInt(str, 16);
                            		}catch(Exception e){}
                        		}
                        	}
                        	   
                        	if(normalcard){
                            	if(len == total * 2){
                            		if(mHandler != null){
                            			//������ģ���ԭ���ĳ���
                            			StringBuffer sb = new StringBuffer("bb670200000261");
                            			sb.append(mStringBuffer.toString().substring(ver2 + 8)).append("00000000000000ee");
                            			
                                		mHandler.obtainMessage(BT_READMSG, -1, -1, sb.toString()).sendToTarget(); 
                                		mStringBuffer.delete(0, mStringBuffer.length());                            			
                            		}
                            	}                      		                        		
                        	}
                        	else {
                        		if(len == total * 2){
                            		mHandler.obtainMessage(BT_READMSG, -1, -1, mStringBuffer.toString()).sendToTarget(); 
                            		mStringBuffer.delete(0, mStringBuffer.length());                            			
                        		}
                        	}
                        	
                        }
                        
                    }
                    
                    
                    
                    bytes = mmInStream.read(buffer);
                    YTTimeoutMonitor monitor = YTTimeoutMonitor.getInstance();
                    monitor.stopMonitor();
                } 
                catch (Exception e) {                	
                	connectionLost();                    	
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
           	    if(D2EConfigures.G_DEBUG){
           	    	Log.e("out >>>>> ", "mmOutStream.write(buffer)");
           	    }
                mmOutStream.write(buffer);
                YTTimeoutMonitor monitor = YTTimeoutMonitor.getInstance();
                monitor.init(mHandler);
                monitor.startMonitor();
            } 
            catch (IOException e) {
           	    if(D2EConfigures.G_DEBUG){
           	    	Log.e("out write IOException >>>>> ", e.getMessage());
           	    }
            	connectionFailed(e.getMessage());
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } 
            catch (IOException e) {

            }
        }
    }
    
	public void onCreate() {
		super.onCreate();
		
		mIsRun     = true;
    	mState     = STATE_NONE;
    	SELF       = this;
    	g_runState = true;        	
    	start();
	}
	
	public boolean isRunning(){
		return mIsRun;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mIsRun = false;
	}

	// ����Activity�����ֵõ���Ӧ��Activity
	public static JJBaseActivity getActivityByName(String activityName) {
		for (JJBaseActivity activity : g_allActivity) {
			if (activity.getClass().getName().indexOf(activityName) >= 0) {
				return activity;
			}
		}
		return null;
	}

	public static final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try{
				//����ָ��Ѱ��activity name
				String name = g_Relations.get(new Integer(mCurrentCall));
								
				JJBaseActivity activity = null;
				if(name != null && name.length() > 0){
					activity = 	getActivityByName(name);	
				}
				if(activity == null){
					//case���ֽ��治����ָ���
					if(g_allActivity.size() > 0){
						activity = g_allActivity.get(g_allActivity.size() - 1);						
					}
				}
								
				switch(msg.what){
				
				case BT_CONNECT:{
					if(activity != null){
						activity.taskFailed(JJBaseService.BT_SERVICE,new Integer(msg.what), msg.obj);										
					}
				}break;
					
				case BT_DISCONNECT:
					if(activity != null){
						activity.taskFailed(JJBaseService.BT_SERVICE,new Integer(msg.what), msg.obj);										
					}
				break;
				
				case BT_TOAST:{
					if(activity != null){
						activity.taskFailed(JJBaseService.BT_SERVICE,new Integer(msg.what), msg.obj);									
					}
				}break;
				
				case BT_READMSG:{
					//��Ӧ���ķ��ظ�����
					if(activity != null){						
						activity.taskSuccess(JJBaseService.BT_SERVICE,new Integer(mCurrentCall), msg.obj, -2);
					}
					
				}break;
				
				//ָ�����
				case BT_CALL_FINISHED:{
					//��Ӧ���ķ��ظ�����
					if(activity != null){
						activity.taskSuccess(JJBaseService.BT_SERVICE,mCurrentCall, msg.obj, SOPBTCallHelper.STATE_RESPONSE);
					}
				}break;
								
				}				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	};

	public IBinder onBind(Intent intent) {		
		return null;
	}
	
	//�����������activity�������Ӧ,�����ݷ���ʱ�����ָ��activity��Ӧ
	public void doTask(int callType, String activityName, byte[] body){
		
		if(g_Relations == null){
			g_Relations = new HashMap<Integer, String>();
		}
		//������ָ���ʱ����滻��ǰָ��,�������activity��ϵ��
		g_Relations.clear();
		mCurrentCall = callType;
		g_Relations.put(callType, activityName);
   	    mSendCallMessage = JJHexHelper.encode(body); 
   	    
   	    mStringBuffer = new StringBuffer();
   	    if(D2EConfigures.G_DEBUG){
   	    	Log.i("out >>>>> ", "write(body): " + JJHexHelper.encode(body));
   	    }
		write(body);
	}
	
	public void cancel(){
		mCurrentCall = NO_CALL;
	}
}
