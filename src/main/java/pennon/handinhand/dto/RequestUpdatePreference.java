package pennon.handinhand.dto;

import javax.validation.constraints.NotBlank;

public class RequestUpdatePreference {
    @NotBlank
    private String type;

    @NotBlank
    private String userId;

    @NotBlank
    private String itemId;

    private String times = "1";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
