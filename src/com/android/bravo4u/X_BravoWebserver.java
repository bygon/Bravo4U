package com.android.bravo4u;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

public class X_BravoWebserver 
{
	Context mContext;

	public X_BravoWebserver(Context context) {
		mContext = context;
	}
	
	
	//--------------------------------------------------------------------
	//
	//					ȸ�����Ե����� ������ ������
	//
	//--------------------------------------------------------------------
	
	public String sendJoinusData(String name, String phone_num, String password, String bravo_num, String img_url) 
	{
		String returnStr= null;
		try {
			// �����͸� �������� ������ �޾ƿ� ��� ���
			 returnStr = sendData01(name, phone_num, password,bravo_num,img_url);
			

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnStr;
	}

	
	private String sendData01(String name, String phone_num, String password, String bravo_num, String img_url)
			throws ClientProtocolException, IOException 
	{
		
		HttpPost request = makeHttpPost01(name, phone_num, password,bravo_num, img_url,"http://210.115.58.140/test2.php");

		HttpClient client = new DefaultHttpClient();

		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		Log.e("sendData01", result);
		
		return result;
	}
	
	private HttpPost makeHttpPost01(String $name, String $phone_num,String $password,String $bravo_num, String $img_url, String $url) 
	{
		HttpPost request = new HttpPost($url);
		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
		nameValue.add(new BasicNameValuePair("name", $name));
		nameValue.add(new BasicNameValuePair("phone_num", $phone_num));
		nameValue.add(new BasicNameValuePair("password", $password));
		nameValue.add(new BasicNameValuePair("bravo_num", $bravo_num));
		nameValue.add(new BasicNameValuePair("img_url", $img_url));
		request.setEntity(makeEntity(nameValue));

		Log.e("makeHttpPost01", "�޼��� ȣ��");
		return request;

	}
	
	//--------------------------------------------------------------------
	//
	//					�α��ε����� ������ ������
	//
	//--------------------------------------------------------------------
	
	public String sendLoginData(String phone_num) 
	{
		String returnStr= null;
		try {
			// �����͸� �������� ������ �޾ƿ� ����� ���
			 returnStr = sendData02(phone_num);
			

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnStr;
	}

	
	private String sendData02(String phone_num)
			throws ClientProtocolException, IOException 
	{
		HttpPost request = makeHttpPost02(phone_num,"http://210.115.58.140/test3.php");

		HttpClient client = new DefaultHttpClient();

		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		
		Log.e("sendData02", result);
		
		return result;
	}
	
	private HttpPost makeHttpPost02(String $phone_num, String $url) 
	{
		HttpPost request = new HttpPost($url);
		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
		nameValue.add(new BasicNameValuePair("phone_num", $phone_num));
		request.setEntity(makeEntity(nameValue));

		Log.e("makeHttpPost02", "�޼��� ȣ��");
		return request;

	}
	
	//--------------------------------------------------------------------
	//
	//					��� ȸ����ȣ  �������� �޾ƿ� ��
	//
	//--------------------------------------------------------------------
	
	public String getPhonenumData() 
	{
		String returnStr= null;
		try {
			// �����͸� �������� ������ �޾ƿ� ����� ���
			 returnStr = sendData03();
			

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnStr;
	}

	
	private String sendData03()
			throws ClientProtocolException, IOException 
	{
		HttpPost request = makeHttpPost03("http://210.115.58.140/test4.php");

		HttpClient client = new DefaultHttpClient();

		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		
		Log.e("sendData03", result);
		
		return result;
	}
	
	private HttpPost makeHttpPost03(String $url) 
	{
		HttpPost request = new HttpPost($url);
		Log.e("makeHttpPost02", "�޼��� ȣ��");
		return request;

	}
	
	//--------------------------------------------------------------------
	//
	//					Ī�� ���� �޾ƿö�
	//
	//--------------------------------------------------------------------
	
	
	public String getComplimentNumData(String phone_num, String sendingpz) 
	{
		String returnStr= null;
		try {
			// �����͸� �������� ������ �޾ƿ� ����� ���
			 returnStr = sendData04(phone_num, sendingpz);
			

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnStr;
	}

	
	private String sendData04(String phone_num,String sendingpz)
			throws ClientProtocolException, IOException 
	{
		HttpPost request = makeHttpPost04(phone_num,sendingpz,"http://210.115.58.140/test5.php");

		HttpClient client = new DefaultHttpClient();

		ResponseHandler<String> reshandler = new BasicResponseHandler();
		String result = client.execute(request, reshandler);
		
		Log.e("sendData04", result);
		
		return result;
	}
	
	private HttpPost makeHttpPost04(String $phone_num, String $sendingpz ,String $url) 
	{
		HttpPost request = new HttpPost($url);
		Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
		nameValue.add(new BasicNameValuePair("phone_num", $phone_num));
		nameValue.add(new BasicNameValuePair("sendingpz", $sendingpz));
		request.setEntity(makeEntity(nameValue));

		Log.e("makeHttpPost04", "�޼��� ȣ��");
		return request;

	}
	


	private HttpEntity makeEntity(Vector<NameValuePair> $nameValue) {
		HttpEntity result = null;

		try {
			result = new UrlEncodedFormEntity($nameValue);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Log.e("makeEntitiy", "�޼��� �Ҹ�");

		return result;
	}
}
