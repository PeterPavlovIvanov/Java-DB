package demos.springdata.restdemo.dtos.fourthEx;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Set;

public class UPDto {
    @Expose
    private int usersCount;
    @Expose
    private List<UserFourthExDto> users;

    public UPDto() {
    }

    public UPDto(int usersCount, List<UserFourthExDto> users) {
        this.usersCount = usersCount;
        this.users = users;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserFourthExDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserFourthExDto> users) {
        this.users = users;
    }
}
