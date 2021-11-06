package todolist.springtodolist.api.model;


import todolist.springtodolist.api.entity.User;

public class UserModel {

    private Integer id;
    private String login;

    public static UserModel modelOf(User user){
        UserModel userModel=new UserModel();
        userModel.setId(user.getId());
        userModel.setLogin(user.getLogin());
        return userModel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
