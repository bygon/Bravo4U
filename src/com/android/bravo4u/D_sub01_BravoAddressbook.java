package com.android.bravo4u;

import java.util.ArrayList;
import java.util.Arrays;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class D_sub01_BravoAddressbook extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener
{
	private String phonelist ;
	private ArrayList<String> groupArray ;
	private ListView list_addFamily;
	private Button BackBtn;
	private ArrayList<String> listArray;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_sub01_bravoaddressbook);

        //�ּҷ� ����Ʈ�� �ű��
        phonelist =  getContacts();
        String[] phone_array =phonelist.split("q");
        groupArray =new ArrayList<String>();
        groupArray.addAll(Arrays.asList(phone_array));
        //String str ="";
        
        for(int i=0; i<groupArray.size(); i++)
        {
        	for(int j=0; j<groupArray.size(); j++)
        	{
        		if(i!=j)
        		{
        			if(groupArray.get(i).equals(groupArray.get(j))||groupArray.get(i).contains(groupArray.get(j)))
        			{
            			if((j+1) != groupArray.size())
            			{
            				groupArray.remove(j);
            			}	
        			}
        		}
        		
        	}
        }
        
        for(int i=0; i<groupArray.size();i++)
        {
        	if(!groupArray.get(i).contains("#"))
        	{
        		groupArray.remove(i);
        	}
        }
        
//        for(int i=0; i<groupArray.size(); i++)
//        {
//        	str += ""+ i + groupArray.get(i)+"\n\n";
//        }
          
        listArray =new ArrayList<String>();
        serverDbhasAddress();
         
        list_addFamily = (ListView)findViewById(R.id.addressbookList);
	     
        list_addFamily.setAdapter(new ArrayAdapter<String>(this,
	             android.R.layout.simple_list_item_1, listArray));
	
        list_addFamily.setOnItemClickListener(this); 
        
        //�ڷΰ��� ��ư
        BackBtn=(Button)findViewById(R.id.addFamilyBackBtn);
        BackBtn.setOnClickListener(this);
       
//        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
//		alertDlg.setTitle("������Ʈ");
//		alertDlg.setMessage(str);
//		alertDlg.setNegativeButton("Ȯ��", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
//		});
//		alertDlg.show();
     
    }
    
    public void onItemClick(AdapterView<?> parent, View v, int position, long id)
    {
    	long cnt =0;
    	X_BravoDBHandler dbhandler = X_BravoDBHandler.open(this);
    	
    	String insertDBdata[] =listArray.get(position).split("\n");
    	String name = insertDBdata[0];
    	String phone_num = insertDBdata[1];
        
        int cursorCount = dbhandler.select(phone_num);
        if(cursorCount== 0)
        {
        	cnt = dbhandler.insert(name,phone_num);
        	
        }else 	Toast.makeText(this, name + "���� �̹� �߰� ���ֽ��ϴ�.", Toast.LENGTH_LONG).show();
         
        if (cnt == -1) Toast.makeText(this, name + "���� db�� �߰����� �ʾҽ��ϴ�.", Toast.LENGTH_LONG).show();

        dbhandler.close();
    }
    
    public void onClick(View v)
    {
    	finish();
    }
    
    public void serverDbhasAddress()
    {
    	// ������ �ִ� �ּҷ�(����ȣ) ��������
        X_BravoWebserver getphonenum = new X_BravoWebserver(this);
        String  allPhonenumStr=getphonenum.getPhonenumData();
        String[]serverDbPhoneArr =allPhonenumStr.split(",");
        
        for(int i=0; i<groupArray.size(); i++)
        {
        	for(int j=0; j<serverDbPhoneArr.length; j++)
        	{
        		String[] array = groupArray.get(i).split("#");
        		//Log.e("�ּҷ� �߸��ƾ�", ""+groupArray.get(i));
        		//Log.e("�ּҷ� �߸��ƾ�", ""+array[array.length-1]);
        		
        		String phone_num = array[array.length-1].substring(1);
        		
        		if(phone_num.equals(serverDbPhoneArr[j]))
        		{
        			//�������ִ� ����ȣ�� ������ �ּҷϿ��ִ� ��ȣ�� ��ġ�Ѱ͸� linkedList�� �ִ´�.
        			String name = array[array.length-2];
        			String phoneNum = array[array.length-1];
        			listArray.add(name+"\n"+phoneNum);
        		}
        	}
        }
        
    }
    
    public String getContacts() 
    {

        //�ּҷ� ������ ���� ���ڿ� ��ü
        StringBuffer sb = new StringBuffer();
        
        //getContentResolver() �޼ҵ�� ContentResolver�� ���� �Ŀ�  query �޼ҵ带 ����ؼ� �ּҷ� ������ ��û�Ѵ�.
        //�̷��� ���� �ּҷ� ��� ������ Cursor�� ���ؼ� �����ϰ� ������ �� �ִ�.
        Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        
        //Cursor�� moveToNext() �޼ҵ带 ����ؼ� �ּҷ��� �˻��Ѵ�.
        while (contacts.moveToNext())
        {
            
            //����� �̸��� ���� ���Ѵ�.
            String displayName = contacts.getString(contacts
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        
            sb.append("" + displayName).append("#");

            //�ּҷ� ���̵� ���Ѵ�.
            //�� ���̵�� ��ȭ��ȣ ��ϰ� �̸��� ����� ã�� �� ����Ѵ�.
            String contactId = contacts.getString(contacts
                    .getColumnIndex(ContactsContract.Contacts._ID));
            
            //��ȭ��ȣ ���� ���θ� ���Ѵ�.
            //����Ǿ� �ִٸ� "1"�� ��ȯ�Ǹ� �׷��� �ʴٸ� "0"�� ��ȯ�ȴ�.
            String hasPhoneNumber = contacts.getString(contacts
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            
 

            //��ȭ��ȣ�� ����Ǿ� �ִٸ�
            if (hasPhoneNumber.equals("1")) 
            {
            	
                //��ȭ��ȣ Cursor�� �����Ѵ�.
                //�̶� contactId�� ����ؼ� ��ȭ��ȣ ����� ���Ѵ�.
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null);
                
                
                
                //��ȭ��ȣ�� ���� ������ ��� �ݺ��ϸ鼭 ��ȭ��ȣ�� ���Ѵ�.
                while (phones.moveToNext()) 
                {
                    String phoneNumber = phones.getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    sb.append(phoneNumber).append("q");
                }
                
                
                //��ȭ��ȣ Cursor�� �ݴ´�.
                phones.close();
            }
           


        }

        //�ּҷ� Cursor�� �ݴ´�.
        contacts.close();
        
        return sb.toString();
    }  
}
