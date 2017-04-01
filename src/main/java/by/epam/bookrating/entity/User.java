package by.epam.bookrating.entity;


public class User {
    private long userId;
    private String login;
    private String password;
    private String name;
    private int age;
    private String info;
    private String avatar;
    private String role;

    public User() {
        super();
    }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String login, String name, int age, String role) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public User(long userId, String login, String name,
                int age,  String role) {
        this.userId = userId;
        this.login = login;
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public User(long userId, String login, String password, String name,
                int age, String info, String avatar, String role) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.age = age;
        this.avatar = avatar;
        this.info = info;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", info='" + info + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        if (userId != user.userId) return false;
        if (!login.equals(user.login)) return false;
        return password.equals(user.password);

    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
