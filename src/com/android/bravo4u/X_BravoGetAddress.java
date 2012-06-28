package com.android.bravo4u;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class X_BravoGetAddress 
{
	Context mContext;
	
	
	public X_BravoGetAddress(Context context)
	{
		mContext = context;
	}
	
	public ArrayList<String> arrangeAddress()
	{
		String phonelist =  getContacts();
		ArrayList<String> groupArray = new ArrayList<String>();
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
		
		return groupArray;
	}
	
	public String getContacts() 
    {

        //�ּҷ� ������ ���� ���ڿ� ��ü
        StringBuffer sb = new StringBuffer();
        
        //getContentResolver() �޼ҵ�� ContentResolver�� ���� �Ŀ�  query �޼ҵ带 ����ؼ� �ּҷ� ������ ��û�Ѵ�.
        //�̷��� ���� �ּҷ� ��� ������ Cursor�� ���ؼ� �����ϰ� ������ �� �ִ�.
        Cursor contacts = mContext.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        
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
                Cursor phones = mContext.getContentResolver().query(
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
