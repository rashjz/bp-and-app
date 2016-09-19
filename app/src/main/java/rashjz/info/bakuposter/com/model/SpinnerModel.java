package rashjz.info.bakuposter.com.model;

/**
 * Created by rasha_000 on 10/17/2015.
 */
public class SpinnerModel {

    private String itemName = "";
    private String Image = "";
    private int drawerImg;
    private String id = "";

    public SpinnerModel(String itemName, String image, String id,int drawImg) {
        this.itemName = itemName;
        Image = image;
        this.id = id;
        drawerImg=drawImg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDrawerImg() {
        return drawerImg;
    }

    public void setDrawerImg(int drawerImg) {
        this.drawerImg = drawerImg;
    }
}