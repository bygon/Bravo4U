package com.android.bravo4u;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class D_Main_BravoMain extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, TabHost.OnTabChangeListener
{
	TabHost tabHost;
	ListView groupTabList;
	ArrayList<String> firstTablistArray;
	ArrayList<String> phone_numArray;
	
	Button firstTabEditBtn, firstTabCompleteBtn, firstTabAddGroupBtn, firstTabDeleteBtn; 
	Button secondTabSelectPhotoBtn,secondTabInfoBtn,secondTabMemberCancellationBtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		setTheme(android.R.style.Theme_NoTitleBar);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.d_main_bravomain);
		

		// ------------------------- �� ���� -----------------------------
		tabHost = (TabHost) findViewById(R.id.tabhost);
		
		tabHost.setup();

		TabSpec tabSpec1 = tabHost.newTabSpec("Tab1").setIndicator("�׷�");		
		tabSpec1.setContent(R.id.tab1);
		tabHost.addTab(tabSpec1);

		TabSpec tabSpec2 = tabHost.newTabSpec("Tab2").setIndicator("����");
		tabSpec2.setContent(R.id.tab2);
		tabHost.addTab(tabSpec2);

		tabHost.setCurrentTab(0);
		
		tabHost.setOnTabChangedListener(this);
		
		
		//--------------------------db�� ������ �ֱ�------------------------
		
    	X_BravoDBHandler dbhandler = X_BravoDBHandler.open(this);
    	Intent intent =getIntent();
    	String phone_num = intent.getExtras().get("phone_num").toString();
        int cursorCount = dbhandler.select(phone_num);
        if(cursorCount== 0)
        {
        	dbhandler.insert("��",phone_num);
        	
        }else 	Toast.makeText(this, "ȸ������ �̹� db���߰� ���ֽ��ϴ�.", Toast.LENGTH_LONG).show();
 

        dbhandler.close();
		//--------------------------- ù��°�� ----------------------------
		
		firstTabEditBtn =(Button)findViewById(R.id.firstTabEditBtn);
		firstTabCompleteBtn =(Button)findViewById(R.id.firstTabCompleteBtn);
		firstTabAddGroupBtn =(Button)findViewById(R.id.firstTabAddGroupBtn);
		firstTabDeleteBtn =(Button)findViewById(R.id.firstTabDeleteBtn);

		firstTabEditBtn.setOnClickListener(this);
		firstTabCompleteBtn.setOnClickListener(this);
		firstTabAddGroupBtn.setOnClickListener(this);
		firstTabDeleteBtn.setOnClickListener(this);

		firstTabCompleteBtn.setVisibility(firstTabCompleteBtn.GONE);
		firstTabDeleteBtn.setVisibility(firstTabDeleteBtn.GONE);

	    firstTablistArray = new ArrayList<String>(); 
	    phone_numArray = new ArrayList<String>();
		groupTabList =(ListView)findViewById(R.id.groupTabList);
		
		//------------------------- �ι�° �� --------------------------------
	
		secondTabSelectPhotoBtn =(Button)findViewById(R.id.secondTabSelectPhotoBtn);
		secondTabInfoBtn =(Button)findViewById(R.id.secondTabInfoBtn);
		secondTabMemberCancellationBtn =(Button)findViewById(R.id.secondTabMemberCancellationBtn);
		
		secondTabSelectPhotoBtn.setOnClickListener(this);
		secondTabInfoBtn.setOnClickListener(this);
		secondTabMemberCancellationBtn.setOnClickListener(this);
		
	}
    public void onResume()
    {
    	super.onResume();
    	firstTab();
    }
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.firstTabEditBtn:
				firstTabEditBtn.setVisibility(firstTabEditBtn.GONE);
				firstTabCompleteBtn.setVisibility(firstTabCompleteBtn.VISIBLE);
				firstTabAddGroupBtn.setVisibility(firstTabAddGroupBtn.GONE);
				firstTabDeleteBtn.setVisibility(firstTabDeleteBtn.VISIBLE);
				
				firstTab();
				break;
			case R.id.firstTabCompleteBtn:
				firstTabEditBtn.setVisibility(firstTabEditBtn.VISIBLE);
				firstTabCompleteBtn.setVisibility(firstTabCompleteBtn.GONE);
				firstTabAddGroupBtn.setVisibility(firstTabAddGroupBtn.VISIBLE);
				firstTabDeleteBtn.setVisibility(firstTabDeleteBtn.GONE);
				
				firstTab();
				break;
			case R.id.firstTabAddGroupBtn:
				
				Intent intent =new Intent(D_Main_BravoMain.this,D_sub01_BravoAddressbook.class);
				startActivity(intent);
				break;
			case R.id.firstTabDeleteBtn:
				
				deleteListCheckItem();
				break;
		}
		
		switch(v.getId())
		{
			case R.id.secondTabSelectPhotoBtn:
		    	
				Intent get_intent01 =getIntent();
		    	String phone_numstr = get_intent01.getExtras().get("phone_num").toString();
		    	
				Intent intent =new Intent(D_Main_BravoMain.this,D_sub03_BravoSelectPhoto.class);
				intent.putExtra("phone_num", phone_numstr);
				startActivity(intent);
				
				break;
			case R.id.secondTabMemberCancellationBtn:
				
		    	X_BravoDBHandler dbhandler = X_BravoDBHandler.open(this);

				Intent get_intent02 =getIntent();
		    	String phone_num = get_intent02.getExtras().get("phone_num").toString();
		        int cursorCount = dbhandler.select(phone_num);
		        if(cursorCount!= 0)
		        {
		        	dbhandler.deleteAll();

		        	Toast.makeText(this, "ȸ������ �����Ͱ� db���� ���� �Ǿ����ϴ�.", Toast.LENGTH_LONG).show();
		        	
		        }else 	Toast.makeText(this, "ȸ������ db������ ȸ���̽ʴϴ�.", Toast.LENGTH_LONG).show();
		        dbhandler.close();
		        
		        phone_num = phone_num.substring(1);
		        
		        X_BravoWebserver server = new X_BravoWebserver(this);
		        String str =server.deleteDataonServer(phone_num);
		        
		        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
		        
				
				break;
		}
	}
	
    public void onItemClick(AdapterView<?> listview, View v, int position, long id) 
    {

    	if(firstTabEditBtn.getVisibility() == firstTabEditBtn.VISIBLE)
    	{
    		String phone_num = phone_numArray.get(position);
    		String name = firstTablistArray.get(position);
    		
    		Intent intent =new Intent(this, D_sub02_BravoAboutGift.class);
    		intent.putExtra("phone_num",phone_num);
    		intent.putExtra("name", name);
    		startActivity(intent);
    	}
 

    	
    }
    
    public void deleteListCheckItem()
    {
    	X_BravoDBHandler dbhandler= X_BravoDBHandler.open(this);
    	
		long[] checklist = groupTabList.getCheckItemIds();
		for(int i=0;i<checklist.length;i++)
		{
			int position =(int)checklist[i];
			if(position != 0) dbhandler.deleteRecord(phone_numArray.get(position));
		}

		dbhandler.close();
		firstTab();
    }
	
	
	public void firstTab()
	{
		X_BravoDBHandler dbhandler= X_BravoDBHandler.open(this);

		   String dbdataAll = dbhandler.selectAll();

		   if(dbdataAll.contains("\n"))
		   {
			   String[] splbyRecode= dbdataAll.split("\n");
			   firstTablistArray = new ArrayList<String>(); 
			   phone_numArray = new ArrayList<String>();
			   
			   for(int i=0; i<splbyRecode.length; i++)
			   {
				   String[] splbyColum = splbyRecode[i].split(",");
				   String name = splbyColum[0];
				   String phone_num = splbyColum[1];
				   
				   firstTablistArray.add(name);
				   phone_numArray.add(phone_num);  
			   }
		   }else
		   { 
			   
			   firstTablistArray.removeAll(firstTablistArray);
			   phone_numArray.removeAll(phone_numArray);
			   
			   LinearLayout layout =(LinearLayout)findViewById(R.id.firstTabLayout);
			   layout.setVisibility(layout.GONE);
			   groupTabList.setVisibility(groupTabList.GONE);

		   }
		   
		   
		   if(firstTabEditBtn.getVisibility() == firstTabEditBtn.VISIBLE)
		   {
			   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					   android.R.layout.simple_list_item_1,firstTablistArray);
			   groupTabList.setAdapter(adapter);
			   groupTabList.setClickable(true);
			   groupTabList.setOnItemClickListener(this);
		   
		   }else if(firstTabEditBtn.getVisibility() == firstTabEditBtn.GONE)
		   {
			   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					   android.R.layout.simple_list_item_multiple_choice,firstTablistArray);
			   
			   groupTabList.setAdapter(adapter);
//	           list_familyTab.setItemsCanFocus(false);
			   groupTabList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		   }


		   //Log.i("�۽�Ʈ��", firstTablistArray.get(0)+firstTablistArray.get(1)+firstTablistArray.get(2));
		   dbhandler.close();
	       
	}
	
    public void onTabChanged(String tabId)
    {
    	
    	if(tabId.equals("Tab1"))
    	{
    		firstTab();
    	}
    	
    }

}
