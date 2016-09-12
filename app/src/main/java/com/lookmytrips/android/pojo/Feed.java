

package com.lookmytrips.android.pojo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.annotations.Expose;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Feed implements Serializable {

    private static final long serialVersionUID = 111696345129311948L;
    public byte[] imageByteArray;

    @Expose
    private String Published;


    @Expose
    private String Shared;

    @Expose
    private String thumb;

    @Expose
    private String Title;

    @Expose
    private int id;

    private Bitmap picture;

    private boolean isFromDatabase;

    public String getPublished() {
        return Published;
    }

    public void setPublished(String published) {
        this.Published = published;
    }



    public String getShared() {
        return Shared;
    }

    public void setShared(String shared) {
        this.Shared = shared;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public boolean isFromDatabase() {
        return isFromDatabase;
    }

    public void setFromDatabase(boolean fromDatabase) {
        isFromDatabase = fromDatabase;
    }


    private void writeObject(java.io.ObjectOutputStream out) throws IOException {

        out.writeObject(Published);
//        out.writeObject(price);
        out.writeObject(Shared);
        out.writeObject(thumb);
        out.writeObject(Title);
        out.writeObject(id);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

        Published = (String) in.readObject();
        Shared = (String) in.readObject();
        thumb = (String) in.readObject();
        Title = (String) in.readObject();
        id = (Integer) in.readObject();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        picture = BitmapFactory.decodeByteArray(bitmapBytes, 0,
                bitmapBytes.length);
    }
}
