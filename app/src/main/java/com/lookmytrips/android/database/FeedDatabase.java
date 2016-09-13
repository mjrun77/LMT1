package com.lookmytrips.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.lookmytrips.android.controller.FeedFetchListener;
import com.lookmytrips.android.helper.Constants;
import com.lookmytrips.android.utils.Utils;
import com.lookmytrips.android.pojo.Feed;

import java.util.ArrayList;
import java.util.List;

public class FeedDatabase extends SQLiteOpenHelper {

    private static final String TAG = FeedDatabase.class.getSimpleName();

    public FeedDatabase(Context context) {
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DATABASE.DROP_QUERY);
        this.onCreate(db);
    }

    public void addFeed(Feed feed) {

        Log.d(TAG, "Values Got " + feed.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.DATABASE.PRODUCT_ID, feed.getId());
        values.put(Constants.DATABASE.CATEGORY, feed.getPublished());
    //    values.put(Constants.DATABASE.PRICE, Double.toString(feed.getPrice()));
        values.put(Constants.DATABASE.INSTRUCTIONS, feed.getShared());
        values.put(Constants.DATABASE.NAME, feed.getTitle());
        values.put(Constants.DATABASE.PHOTO_URL, feed.getThumb());
        values.put(Constants.DATABASE.PHOTO, Utils.getPictureByteOfArray(feed.getPicture()));

        try {
            db.insert(Constants.DATABASE.TABLE_NAME, null, values);
        } catch (Exception e) {

        }
        db.close();
    }

    public void fetchFeed(FeedFetchListener listener) {
        FeedFetcher fetcher = new FeedFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    public class FeedFetcher extends Thread {

        private final FeedFetchListener mListener;
        private final SQLiteDatabase mDb;

        public FeedFetcher(FeedFetchListener listener, SQLiteDatabase db) {
            mListener = listener;
            mDb = db;
        }

        @Override
        public void run() {
            Cursor cursor = mDb.rawQuery(Constants.DATABASE.GET_FLOWERS_QUERY, null);

            final List<Feed> feedList = new ArrayList<>();

            if (cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        Feed feed = new Feed();
                        feed.setFromDatabase(true);
                        feed.setTitle(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.NAME)));
                //        feed.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PRICE))));
                        feed.setShared(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.INSTRUCTIONS)));
                        feed.setPublished(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.CATEGORY)));
                        feed.setPicture(Utils.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(Constants.DATABASE.PHOTO))));
                        feed.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PRODUCT_ID))));
                        feed.setThumb(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PHOTO_URL)));

                        feedList.add(feed);
                        publishFlower(feed);

                    } while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverAllFlowers(feedList);
                    mListener.onHideDialog();
                }
            });
        }

        public void publishFlower(final Feed feed) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverFlower(feed);
                }
            });
        }
    }
}
