package rashjz.info.bakuposter.com.model;

import java.math.BigDecimal;

/**
 * Created by Mobby on 8/28/2015.
 */
public class BaseModel {
private BigDecimal id;
    private String note;
    private String status;

    public BaseModel(   ) {
        this.id = BigDecimal.ZERO;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
