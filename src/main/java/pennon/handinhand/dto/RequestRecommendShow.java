package pennon.handinhand.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RequestRecommendShow {
    @Min(1)
    private String page = "1";

    @NotBlank(message = "用户不能为空")
    private String userId;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        if ("".equals(page) || "0".equals(page)) {
            page = "1";
        }
        int int_page = Integer.parseInt(page) % 5;
        if (int_page == 0) {
            int_page = 5;
        }
        this.page = String.valueOf(int_page);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
