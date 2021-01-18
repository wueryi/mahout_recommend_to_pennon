package pennon.handinhand.bo;

public enum PatternEnum {
    //更新用户推荐
    UPDATE_USER_RECOMMEND("HANDINHAND:update-user-recommend");

    private String name;

    PatternEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
