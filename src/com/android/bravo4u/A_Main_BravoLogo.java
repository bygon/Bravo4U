package com.android.bravo4u;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class A_Main_BravoLogo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		setTheme(android.R.style.Theme_NoTitleBar);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_main_brovologo);

		thread.start();


	}


	Thread thread = new Thread(new Runnable(){

		public void run()
		{
			try{
				Thread.sleep(1200);// �ΰ�1. 2�� �����ְ� �ٷ� ���� ��Ƽ��Ƽ�� �Ѿ��
	
					Intent intent =new Intent(A_Main_BravoLogo.this,B_Main_BravoIntro.class);
					startActivity(intent);
					finish();
				
				// �α��� ���� �˾ƺ���.
				
//				SharedPreferences pref = getSharedPreferences("LogIn",0);
//				// ó���� �ƹ����µ� ������ �α��� ���¸� �־��ش�  �α���=1 �α׾ƿ� =0
//		        if(pref.getInt("LoginState", 1000)==1000)
//		        {			
//					Intent intent =new Intent(A_Main_BravoLogo.this,B_Main_BravoIntro.class);
//					startActivity(intent);
//					finish();
//		        }else if(pref.getInt("LoginState", 1000)==0)
//		        {
//					Intent intent =new Intent(A_Main_BravoLogo.this,B_Main_BravoIntro.class);
//					startActivity(intent);
//					finish();
//					
//		        }else if(pref.getInt("LoginState", 1000)==1)
//		        {
//		        	SharedPreferences sharedpref = getSharedPreferences("PhoneNumber",0);
//		        	String phone_numStr= sharedpref.getString("phone_num", "");
//	
//    				Intent intent=new Intent(A_Main_BravoLogo.this, D_Main_BravoMain.class);
//    				intent.putExtra("phone_num", phone_numStr);
//    				startActivity(intent);
//    				finish();
//		        }
				

				Thread.interrupted();

			}catch(InterruptedException e){}

		}


	});
}