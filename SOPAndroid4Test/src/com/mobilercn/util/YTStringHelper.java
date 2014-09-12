package com.mobilercn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobilercn.config.D2EConfigures;

import android.util.Log;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class YTStringHelper {
	
	public static String cn2FirstSpell(String chinese) { 
        StringBuffer pybf = new StringBuffer(); 
        char[] arr = chinese.toCharArray(); 
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); 
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
        for (int i = 0; i < arr.length; i++) { 
                if (arr[i] > 128) { 
                        try { 
                                String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat); 
                                if (_t != null) { 
                                        pybf.append(_t[0].charAt(0)); 
                                } 
                        } catch (BadHanyuPinyinOutputFormatCombination e) { 
                                e.printStackTrace(); 
                        } 
                } else { 
                        pybf.append(arr[i]); 
                } 
        } 
        return pybf.toString().replaceAll("\\W", "").trim(); 
    }
	
	public static final void array2string(String[] params){
		if(D2EConfigures.TEST){
			int len = params.length;
			for(int i = 0; i < len; i += 2){
				Log.e("--------", params[i] + ": " + params[i+1]);
			}
		}
	}
	
	/**
	 * 将字符串中的空格，回车，换行符，制表符
	 * @param strData
	 * @return
	 */
	public static String replaceBlank(String strData){
		String strResult="";
		if(strData!=null){
			Pattern parttern=Pattern.compile("\\s*|\t|\r|\n");
			Matcher matcher=parttern.matcher(strData);
			strResult=matcher.replaceAll("");
		}
		return strResult;
	}

}
