package com.mobilercn.sop.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilercn.base.JJBaseActivity;
import com.mobilercn.base.JJBaseApplication;
import com.mobilercn.base.JJBaseService;
import com.mobilercn.config.D2EConfigures;
import com.mobilercn.sop.R;
import com.mobilercn.sop.bt.SOPBluetoothService;
import com.mobilercn.sop.data.MessageItem;
import com.mobilercn.sop.data.MyBitmap;
import com.mobilercn.sop.data.MyMessageData;
import com.mobilercn.sop.data.PcoUser;
import com.mobilercn.task.JJTask;
import com.mobilercn.util.JJNetHelper;
import com.mobilercn.util.SaveLoginParams;
import com.mobilercn.util.YTFileHelper;
import com.mobilercn.util.YTStringHelper;

/**
 * ��¼ҳ��
 */
public class D2ELoginActivity extends JJBaseActivity {
	private MyMessageData myMessageData;
	
	//�����ס�û���
	private CheckBox login_cb_saveuna;
	//�����ס����
	private CheckBox login_cb_savepwd;
	//�û���
	private String sName;
	//����
	private String sPwd;
	private EditText name;
	private EditText pwd;
	private YTFileHelper ytfileHelper;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.d2e_loginpage);
        boolean print=false;
        if(print){
        	//��ȡ��Ļ�ֱ���
        	DisplayMetrics dm=new DisplayMetrics();
        	getWindowManager().getDefaultDisplay().getMetrics(dm);
        	Toast.makeText(D2ELoginActivity.this, "��Ļ�ķֱ���Ϊ��"+dm.widthPixels+"*"+dm.heightPixels, Toast.LENGTH_LONG).show();
        }
        
        ytfileHelper = YTFileHelper.getInstance();
        ytfileHelper.setContext(getApplicationContext());

        myMessageData=new MyMessageData();
        //change logo image
        byte[] img = ytfileHelper.readFile("logo.jpg");
        if(img != null){
            ImageView imgview = (ImageView)findViewById(R.id.loginLogo);
            imgview.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));        	
        }
        
        name = (EditText)findViewById(R.id.login_edit_account);
        pwd = (EditText)findViewById(R.id.login_edit_pwd);
        login_cb_saveuna=(CheckBox)findViewById(R.id.login_cb_saveuna);
        login_cb_savepwd=(CheckBox)findViewById(R.id.login_cb_savepwd);
        //��������ס����Ϊ��ѡ�����������
        login_cb_saveuna.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, 
					boolean isChecked) {
				if(!login_cb_saveuna.isChecked()){
					if(D2EConfigures.TEST){
						Log.i("login_cb_saveuna.isChecked()", "---"+login_cb_saveuna.isChecked());
					}
					
					//����ļ�����û���
					SaveLoginParams.deletParamsName(D2ELoginActivity.this);
					//����ļ�������
					login_cb_saveuna.setChecked(false);
				}
			}
		});
        //��������ס����Ϊ��ѡ�����������
        login_cb_savepwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, 
					boolean isChecked) {
				if(!login_cb_savepwd.isChecked()){
					if(D2EConfigures.TEST){
						Log.i("login_cb_savepwd.isChecked()", "---"+login_cb_savepwd.isChecked());
					}
					//����ļ�������
					SaveLoginParams.deletParamsPwd(D2ELoginActivity.this);
					login_cb_savepwd.setChecked(false);
				}
			}
		});
        //���뱣����û���
        String loginParamsN[]=SaveLoginParams.getParamsName(D2ELoginActivity.this);
        //���뱣�������
        String loginParamsP[]=SaveLoginParams.getParamsPwd(D2ELoginActivity.this);
        {
        	if(D2EConfigures.TEST){
        		Log.i("Name=:", "----------="+loginParamsN[0]);
        		Log.i("Password=:", "----------="+loginParamsP[0]);
        	}
        	if(loginParamsN[0]!=null&&loginParamsN[0].length()!=0
        			&&loginParamsP[0]!=null&&loginParamsP[0].length()!=0){
        		login_cb_saveuna.setChecked(true);
        	}
        	if(loginParamsP[0]!=null&&loginParamsP[0].length()!=0){
        		login_cb_savepwd.setChecked(true);
        	}
        	name.setText((String)loginParamsN[0]);
        	pwd.setText((String)loginParamsP[0]);
        }
        Button btn = (Button)findViewById(R.id.login_btn);
        btn.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				
				if(D2EConfigures.G_USEVIRCALDEVICE){
					Intent intent = new Intent(D2ELoginActivity.this, D2EMessageActivity.class);
					D2ELoginActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					finish();
					return;

				}
				//add by shawn 2012-11-1
				//û�����磬����ģʽ��ֱ�ӽ�����ҳ
				if(D2EConfigures.NO_NET){
					Intent intent=new Intent(D2ELoginActivity.this,D2EMainScreen.class);
					D2ELoginActivity.this.startActivity(intent);
					overridePendingTransition(R.anim.fade, R.anim.hold);
					D2ELoginActivity.this.finish();
					return;
				}
				//End
				sName = name.getText().toString();
				sPwd  = pwd.getText().toString();
				if(sName != null && sName.length() > 0 && 
						sPwd != null && sPwd.length() > 0){
			        String[] params = new String[]{
			        		"FunType", "Login", 
			        		"Username",sName, 
			        		"Password",sPwd};
			        if(D2EConfigures.TEST){
			        	YTStringHelper.array2string(params);
			        }
			        long id = JJBaseService.addMutilpartHttpTask("http://www.ohpest.com/ohpest-pco/webservices/get/pbitest.html", params, D2ELoginActivity.this.getClass().getName(),D2EConfigures.TASK_LOGIN);	        
			        
			        setCurTaskID(id);
			        showWaitDialog(getResources().getString(R.string.logining));				
				}
				else {
					((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.input_name_pwd));
				}
				
			}        	
        }); 
    }

	@Override
	public void taskSuccess(Object... param) {		
		try{
			
			int values = ((Integer)param[0]).intValue();
			if(values == JJBaseService.HTTP_SERVICE_INT){
			    JJTask task = (JJTask)param[1];
			    InputStream ins = task.getInputStream();
				byte[] bytes = JJNetHelper.readByByte(ins, -1);
				String tmp = new String(bytes, "UTF-8");
			    if(task.getTaskId() == D2EConfigures.TASK_LOGIN){
			    	
					//���ݴ���
					try{
						
						//tmp��Ϊ�����ʾ��¼�ɹ�,����Ϊѡ��״̬,���汣���¼��Ϣ,
						if(tmp!=null&&tmp.length()!=0){
							if(login_cb_saveuna.isChecked()){
								SaveLoginParams.saveParamsName(D2ELoginActivity.this, 
										sName);
							}
							if(login_cb_savepwd.isChecked()){
								SaveLoginParams.saveParamsPwd(D2ELoginActivity.this, 
										sPwd); 
										
							}
						}
						
			            ins.close();
			            if(D2EConfigures.TEST){
			            	Log.e("OUT 20 >>>>>> ", ""+(new JSONObject(tmp)));
			            }
			            
			            JSONObject j     = new JSONObject(tmp);
			            JSONObject j2=null;
			            String count=null;
			            String OrgID=null;
			            String id=null;
			            String url=null;
			            if(j.has("0")){
			            	j2    = new JSONObject(j.getString("0"));
				            count     = j.getString("CountTask");
				            OrgID     = j2.getString("OrgID");
				            id        = j2.getString("ID");
				            url         = j.getString("Logo");
				            if(D2EConfigures.TEST){
				            	Log.i("Logo------------->", url);
				            }
			            
			            
			            if(url != null && url.length() > 0){
			            	//dowload the image
			            	ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);        
			            	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();        
			            	if (networkInfo != null && networkInfo.isConnected()) {
			            		new DownloadImage().execute(url);        
			            	} 
			            }
			            
			            if(id != null && id.length() > 0){
			            	JJBaseApplication.user_ID = id;
			            }
			            if(OrgID != null && OrgID.length() > 0){
			            	JJBaseApplication.user_OrgID = OrgID;
			            }
			            String ID     = j2.getString("ID");
			            if(ID != null && ID.length() > 0){
			            	JJBaseApplication.user_SalesClientID = ID;
			            }
			            
			            if(count != null && count.length() > 0){
			            	JJBaseApplication.user_CountTask = count;
			            }
			            
			            String msgs = j.getString("PushInfoList");
			            //add by shawn 2012-9-7 Begin
			            //����Ա���б�
			            JSONArray userJSONArray=new JSONArray(j.getString("UserList"));
			            //�����û�ͷ��ļ���
			            HashMap<String,String> existUserLogo=new HashMap<String,String>();
			            for(int i=0;i<userJSONArray.length();i++){
			            	JSONObject userJSONObj=new JSONObject(userJSONArray.getString(i));
			            	PcoUser pcoUser=new PcoUser(userJSONObj.getString("ID"),userJSONObj.getString("Name"));
			            	String tagID=userJSONObj.getString("TagID");
			            	String logo=userJSONObj.getString("Logo");
			            	String numbers=userJSONObj.getString("Numbers");
			            	
			            	if(tagID!=null&&tagID.length()>0){
			            		pcoUser.setStrTagId(tagID);
			            	}
			            	if(logo!=null&&logo.length()>0){
			            		pcoUser.setStrLogo(logo);
			            	}else{
			            		pcoUser.setStrLogo("xxx");
			            		pcoUser.setmPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.check_people));
			            	}
			            	if(numbers!=null&&numbers.length()>0){
			            		pcoUser.setStrNumbers(numbers);
			            	}
			            	if(logo!=null&&logo.length()>0){
			            	Bitmap mBitmap=YTFileHelper.url2Bitmap(logo);
			            		if(mBitmap!=null){
			            			pcoUser.setmPhoto(mBitmap);
			            		}else{
			            			pcoUser.setmPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.check_people));
			            		}
			            	}
			            	//�����첽���غ�ͼƬ
			            	existUserLogo.put(userJSONObj.getString("Name"), userJSONObj.getString("Logo"));
			            	new AsyncDownImage().execute(existUserLogo);
			            	
			            	JJBaseApplication.pcoUserList.add(pcoUser);
			            	JJBaseApplication.pcoUsersMap.put(numbers, pcoUser);
			            	if(D2EConfigures.TEST){
			            		pcoUser.print();
			            	}
			            }
			            //End
			            Intent intent = new Intent();
			            intent.setClass(D2ELoginActivity.this,D2EMainScreen.class); 
			            //���ﱣ���ļ���ʱ�����һ����ǰ�û���Ա�����ţ�ͨ��JJBaseApplication.user_ID 
			            //��JJBaseApplication.pcoUserList�б��л�ȡ
			            JJBaseApplication.user_card_num=JJBaseApplication.getCurrentUserCardNum(JJBaseApplication.user_ID).getStrNumbers();
			            //��ȡ��ǰ�û�������
			            JJBaseApplication.current_user_name=JJBaseApplication.getCurrentUserCardNum(JJBaseApplication.user_ID).getStrName();
			            
			            if(D2EConfigures.TEST){
			            	Log.e("JJBaseApplication.user_card_numxxxxxxxxxxx>", ""+(JJBaseApplication.user_card_num));
			            }
			            MyMessageData messages=null;
			            if(D2EConfigures.TEST){
			            	Log.e("ytfileHelper.isFileExist"+JJBaseApplication.user_card_num, ""+(ytfileHelper.isFileExist(JJBaseApplication.user_card_num+"msg.data")));
			            }
			            if(ytfileHelper.isFileExist(JJBaseApplication.user_card_num+"msg.data")){
			            	messages=(MyMessageData) ytfileHelper.deSerialObject(JJBaseApplication.user_card_num+"msg.data");
			            	if(messages==null){
			            		messages=new MyMessageData();
			            	}
			            }else{
			            	messages=new MyMessageData();
			            }
			            if(D2EConfigures.TEST){
			            	Log.e("messages==null", ""+(messages==null));
			            }
			            if(D2EConfigures.TEST){
			            	Log.e("msgs != null", ""+(msgs != null));
			            	Log.e("msgs.length()", ""+msgs.length());
			            	Log.e("================", "===================");
			            }
						if(msgs != null && msgs.length() > 0){
							try{
								JSONArray ja = new JSONArray(msgs);	
								int len = ja.length();
								HashMap<String,String> userLogoMap=new HashMap<String,String>();
								for(int i = 0; i < len; i ++){
									String     sub = ja.getString(i);
									JSONObject jo  = new JSONObject(sub);
									MessageItem item = new MessageItem(jo.getString("Message"), jo.getString("Message"));
									item.setDate(jo.getString("CreateTime"));
									item.setID(jo.getString("ID"));
									//modify by shawn 2012-12-04 Begin
									//�������ر����û���ͼƬ
									String userName=null;
									String urlLogo=null;
									if(jo.has("UserName")){
										userName=jo.getString("UserName");
										item.setmCreateUserName(userName);
									}
									if(jo.has("UserLogo")){
										urlLogo=jo.getString("UserLogo");
										item.setmStrUserLogo(urlLogo);
									}
									//����Ҫ��һ�����ǰ�ᣬ����username logo ��Ϊnull
									if(D2EConfigures.TEST){
										Log.e("userName!=null&&urlLogo!=null", ""+(userName!=null&&urlLogo!=null));
									}
									if(userName!=null&&userName.length()>0
											&&urlLogo!=null&&urlLogo.length()>0){
										userLogoMap.put("userName", userName);
										userLogoMap.put("userLogo", urlLogo);
										new AsyncDownImage().execute(userLogoMap);
										byte[] result=ytfileHelper.readFile(userName+"logo");
										MyBitmap bitmap=new MyBitmap(result, userName);
										if(D2EConfigures.TEST){
											Log.e("result=========>", ""+result.length);
											Log.e("bitmap=========>", ""+bitmap.getBitmapBytes().length);
										}
										item.setMyBitmap(bitmap);
									}
									//End
									if(D2EConfigures.TEST){
										Log.e("jo.has CreateUserID ===========>", ""+(jo.has("CreateUserID")));
									}
									
									if(!messages.isContainMessageItem(item)){
										messages.getMessages().add(item);
									}
									
								}
								if(D2EConfigures.TEST){
									Log.e("д����....", ".....");
								}
								ytfileHelper.serialObject(JJBaseApplication.user_card_num+"msg.data", messages);
								if(D2EConfigures.TEST){
									Log.e("д��ɹ�", "oooooo");
								}
							}
							
							catch(Exception ex){
							}
							
						}
						

			            
			            D2ELoginActivity.this.startActivity(intent); 	            
			            finish();	
			            }else{
			            	JSONObject jo=new JSONObject(tmp);
							String error=null;
							if(jo.has("error")){
								error=jo.getString("error");
							}
							((JJBaseApplication)getApplication()).showMessage(error);
			            }
					}
					catch(Exception ex){
						ex.printStackTrace();
						dismissProcessDialog();
					}
			    }
			}
		}
		catch(Exception ex){
			dismissProcessDialog();
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.failed_try_again));
		}	
		dismissProcessDialog();
	}

	@Override
	public void taskFailed(Object... param) {
				
		dismissProcessDialog();
		int values = ((Integer)param[0]).intValue();
		if(values == JJBaseService.HTTP_SERVICE_INT){
			((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.neterror));
		}
		else if(values == JJBaseService.BT_SERVICE_INT){
			int state = ((Integer)param[1]).intValue();			
			if(state != SOPBluetoothService.BT_DISCONNECT && state !=  SOPBluetoothService.BT_CONNECT){
				((JJBaseApplication)getApplication()).showMessage(getResources().getString(R.string.bterror));
			}
			
		}
	}

	@Override
	public void taskProcessing(Object... param) {
		
	}


	@Override
	public boolean isExit() {
		return true;
	}


	@Override
	public void backAciton() {
		
	}
	
	//for download image 
	private class AsyncDownImage extends AsyncTask<HashMap<String,String>,Integer,byte[]>{
		String userName=null;
		String userLogo=null;
		
		@Override
		protected byte[] doInBackground(HashMap<String, String>... params) {
			byte[] results=null;
			userName=params[0].get("userName");
			userLogo=params[0].get("userLogo");
			try{
				results=downloadImage(userLogo);
				if(D2EConfigures.TEST){
					Log.e("result.length==========>", ""+(results.length));
				}
				
			}catch(Exception e){
				
			}
			
			return results;
		}
		@Override
		protected void onPostExecute(byte[] result) {
			super.onPostExecute(result);
			ytfileHelper.saveFile(userName+"logo", result);
		}
		
		
	}

	
	//for download image
	private class DownloadImage extends AsyncTask {

		@Override
		protected byte[] doInBackground(Object... params) {
			byte[] bytes = null;
			try{
				bytes = downloadImage((String)params[0]);				
			}
			catch(Exception e){}
			return bytes;
		}

		@Override
		protected void onPostExecute(Object result) {
			ytfileHelper.saveFile("logo.jpg", (byte[])result);
		}
	}
	
	
	private byte[] downloadImage(String imgUrl) throws IOException {
		InputStream is = null;
        try {
			URL url = new URL(imgUrl);        
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();        
			conn.setReadTimeout(10000 /* milliseconds */);        
			conn.setConnectTimeout(15000 /* milliseconds */);        
			conn.setRequestMethod("GET");       
			conn.setDoInput(true);        
			conn.connect();        
			int response = conn.getResponseCode();
			if(response != 200){
				return null;        				
			}
			is = conn.getInputStream();
			return JJNetHelper.readByByte(is, -1);
		} 
        finally {       
			if (is != null) {            
				is.close();       
			}    
        }
	}

	@Override
	public void init() {
		
	}

}
