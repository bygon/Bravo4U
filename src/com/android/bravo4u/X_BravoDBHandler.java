package com.android.bravo4u;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class X_BravoDBHandler 
{
	private X_BravoDBHeper helper;
    private SQLiteDatabase db;
    ContentValues values;
    Context mContext;
    
    private X_BravoDBHandler(Context ctx) 
    {
    	mContext = ctx;
        helper = new X_BravoDBHeper(ctx);
        db = helper.getWritableDatabase();
    }
    
    //-------------------- Database ����--------------------------------
    
    public static X_BravoDBHandler open(Context ctx) throws SQLException 
    {
    	X_BravoDBHandler handler = new X_BravoDBHandler(ctx);        

        return handler;    
    }
    
    //--------------------- Database �ݱ� ---------------------------------
    
    public void close() 
    {
        helper.close();
    }
    
    //--------------------- DB�� ���� �ֱ� -----------------------------------
    public long insert(String name, String phone_num) 
    {
        values = new ContentValues();
        values.put("name", name); 
        values.put("phone_num", phone_num);
        
        Toast.makeText(mContext, name+phone_num, Toast.LENGTH_SHORT).show();
        return db.insert("bravo_table", null, values);
    } 
    
    //--------------------- DB�� �ش� ��ȣ �ִ��� �˾ƺ���-----------------------------------  
    public int select(String phone_num)
    {
    	Cursor cursor = db.query(true, "bravo_table", 
                new String[] {"_id", "name","phone_num"},
                "phone_num" + "=" + "'" +phone_num+ "'", 
                null, null, null, null, null);     
    	
    	if (cursor != null) { cursor.moveToFirst(); }    
    	
    	int count = cursor.getCount();

    	return count;
    	
    }	

    //--------------------- DB���� ��� �������� -----------------------------------
    
    public String selectAll() throws SQLException 
    {
        Cursor cursor = db.query(true, "bravo_table", 
                                new String[] {"_id", "name","phone_num" },
                                null, 
                                null,null, null, null, null);
        if (cursor != null) { cursor.moveToFirst(); }
        
        String result="";

        cursor.moveToFirst();
        result += cursor.getString(cursor.getColumnIndex("name"))+",";
        result += cursor.getString(cursor.getColumnIndex("phone_num"))+"\n";

        while(cursor.moveToNext())
        {
        	result += cursor.getString(cursor.getColumnIndex("name"))+",";
       	 	result += cursor.getString(cursor.getColumnIndex("phone_num"))+"\n";
        }
        return result;

    }

    //---------------------- DB���� ������ �����------------------------------
    
    public  void deleteRecord(String phone_num) 
    { 
    	db.execSQL("DELETE FROM bravo_table WHERE phone_num = '" +phone_num+ "';"); 
    } 
    
    
    
}
