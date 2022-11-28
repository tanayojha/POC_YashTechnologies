package org.yash.yashtalks.entity;

public class FollowUser {
    User user;// it will give me the object of followin user
    //Integer follow_status = 0;// '0' for not following
                              // '1' for following

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Integer getFollow_status() {
//        return follow_status;
//    }
//
//    public void setFollow_status(Integer follow_status) {
//        this.follow_status = follow_status;
//    }
}
