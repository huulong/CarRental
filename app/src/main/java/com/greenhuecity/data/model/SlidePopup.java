package com.greenhuecity.data.model;

public class SlidePopup {
    String photo,title,content;

    public SlidePopup(String photo, String title, String content) {
        this.photo = photo;
        this.title = title;
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
