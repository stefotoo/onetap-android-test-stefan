package com.receiptbank.android.testapp.models;

public class CustomItem {

    // variables
    private int imageResId;
    private String title;
    private String content;

    // constructors
    public CustomItem() {
        // default
    }

    public CustomItem(int imageResId, String title, String content) {
        setImageResId(imageResId);
        setTitle(title);
        setContent(content);
    }

    // methods
    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
