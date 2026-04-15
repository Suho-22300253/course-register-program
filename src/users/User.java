package users;

public abstract class User{
    static final String AFFILIATION = "Handong Global University";
    private String name;
    private String id;
    private String password;

    public User(){};
    public User(String nameInput, String idInput, String passwordInput){
        setName(nameInput);
        setId(idInput);
        setPassword(passwordInput);
    }
    public void setName(String nameInput) {
        if(nameInput != null && !nameInput.isEmpty()) {
            name = nameInput;
        }else{
            System.out.println("You should input Name"); // 제대로 입력 할 때까지 반복 구현 필요
        }
    }
    public void setId(String idInput){
        if(idInput != null && !idInput.isEmpty()) {
            id = idInput;
        }else{
            System.out.println("You should input id"); // 제대로 입력 할 때까지 반복 구현 필요
        }
    }

    public void setPassword(String passwordInput){
        if(passwordInput != null && !passwordInput.isEmpty()) {
            password = passwordInput;
        }else{
            System.out.println("You should input password"); // 제대로 입력 할 때까지 반복 구현 필요
        }
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
