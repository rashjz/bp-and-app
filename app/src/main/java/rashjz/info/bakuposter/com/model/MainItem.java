package rashjz.info.bakuposter.com.model;

import java.math.BigDecimal;

/**
 * Created by Mobby on 8/28/2015.
 */
public class MainItem  extends BaseModel{
    private BigDecimal itemCode;
    private String name;
    private String text;
    private String img;
    private String code;
    private String title, thumbnailUrl;
    public MainItem(   ) {
        itemCode=BigDecimal.ZERO;
     }

    public BigDecimal getItemCode() {
        return itemCode;
    }

    public void setItemCode(BigDecimal itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MainItem{" +
                "itemCode=" + itemCode +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", img='" + img + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
