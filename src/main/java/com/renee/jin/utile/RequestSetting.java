package com.renee.jin.utile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestSetting  {
    private String returnRequestString="?";
    public RequestSetting( HttpServletRequest request) {
        Enumeration params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String name = (String)params.nextElement();
            if(name.equals("pw")) continue;
            String value = request.getParameter(name);
            try {
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            returnRequestString += name + "=" + value + "&";
        }

    }
    
    public String getReturnRequestString() {
        return returnRequestString;
    }
    public void setReturnRequestString(String returnRequestString) {
        this.returnRequestString = returnRequestString;
    }
    
    
}
