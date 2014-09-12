package com.mobilercn.util;

import java.util.HashMap;
import java.lang.ref.SoftReference;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;

/**
 * 异步加载图片工具类
 * @author ShawnXiao
 *
 */
public class AsyncImageLoader {
	private HashMap<String,SoftReference<Drawable>> imageCache;
	
	public AsyncImageLoader(){
		imageCache=new HashMap<String,SoftReference<Drawable>>();
	}
	
	public Drawable loadDrawable(final String imageUrl,final ImageCallBack imageCallBack){
		if(imageCache.containsKey(imageUrl)){
			SoftReference<Drawable> softReference=imageCache.get(imageUrl);
			Drawable drawable=softReference.get();
			if(drawable!=null){
				return drawable;
			}
		}
		
		final Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				imageCallBack.imageLoaded((Drawable)msg.obj, imageUrl);
			}
		};
		new Thread(){

			@Override
			public void run() {
				super.run();
				Drawable drawable=loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message=handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
			
		}.start();
		return null;
	}
	
	public static Drawable loadImageFromUrl(String url){
		URL m;
		InputStream i=null;
		try{
			m=new URL(url);
			i=(InputStream)m.getContent();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		Drawable d=Drawable.createFromStream(i, "src");
		return d;
	}
	
	
	public interface ImageCallBack{
		public void imageLoaded(Drawable imageDrawable,String imageUrl);
	}
}
