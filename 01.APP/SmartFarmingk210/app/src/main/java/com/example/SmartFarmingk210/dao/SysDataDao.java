package com.example.SmartFarmingk210.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.SmartFarmingk210.utils.TimeCycle;

import java.util.ArrayList;
import java.util.List;

public class SysDataDao implements DaoBase {
    private Context context;
    private DBHelper helper;
    private SQLiteDatabase db;
    private String TAG = "SysDataDao";

    public SysDataDao(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    /***
     * 插入一条数据
     * @param object object
     * @return 0-添加失败 1添加成功
     */
    @Override
    public int insert(Object object) {
        try {
            SysData data = (SysData) object;
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("temp", data.getTemp());
            values.put("humi", data.getHumi());
            values.put("somg", data.getSomg());
            values.put("createDateTime", TimeCycle.getDateTime());
            db.insert("sys", null, values);
            db.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "数据添加错误");
            return 0;
        }

    }

    @Override
    public int delete(String... data) {
        return 0;
    }

    @Override
    public int update(Object object, String... data) {
        return 0;
    }

    /***
     * 查询数据库
     * @param data 多个String数据 不填入数组为0;填入2个为该类型该时间段的数据
     * @return 发生错误返回null 没有数据返回空的list
     */
    @Override
    public List<Object> query(String... data) {
        try {
            db = helper.getReadableDatabase();
            Cursor cursor;
            String sql;
            List<Object> result = new ArrayList<Object>();
            switch (data.length) {
                case 2:
                    sql = "SELECT * FROM sys where datetime(createDateTime) " +
                            "BETWEEN datetime(?) AND datetime(?) ORDER BY createDateTime;";
                    cursor = db.rawQuery(sql, data);
                    break;
                default:
                    sql = "SELECT * FROM sys ORDER BY createDateTime;";
                    cursor = db.rawQuery(sql, null);
            }
            while (cursor.moveToNext()) {
                SysData temp = new SysData();
                temp.setS_id(cursor.getInt(cursor.getColumnIndexOrThrow("s_id")));
                temp.setTemp(cursor.getInt(cursor.getColumnIndexOrThrow("temp")));
                temp.setHumi(cursor.getInt(cursor.getColumnIndexOrThrow("humi")));
                temp.setSomg(cursor.getInt(cursor.getColumnIndexOrThrow("somg")));
                temp.setCreateDateTime(cursor.getString(cursor.getColumnIndexOrThrow("createDateTime")));
                result.add(temp);
            }
            cursor.close();
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "数据库查询错误");
            return null;
        }
    }
}
