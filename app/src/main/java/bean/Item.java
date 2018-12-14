package bean;

public class Item {

    private String headImg;
    private  String username;
    private  String mood;

    public Item() {

    }

    public Item(String headImg, String username, String mood) {
        this.headImg = headImg;
        this.username = username;
        this.mood = mood;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
}
