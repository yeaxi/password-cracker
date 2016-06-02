package entity;

/**
 * Created by RASTA on 28.05.2016.
 */
public class Entity {
    private String fileName;
    private String message;
    private String password;

    public Entity(String fileName, String message, String password) {
        this.fileName = fileName;
        this.message = message;
        this.password = password;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "message = '" + message + '\'' +
                ", password='" + password + '\'';
    }
}
