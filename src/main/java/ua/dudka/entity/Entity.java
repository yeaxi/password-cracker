package ua.dudka.entity;


import java.util.Objects;

public class Entity {
    private String message;
    private String password;

    public Entity(String message, String password) {
        this.message = message;
        this.password = password;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(message, entity.message) &&
                Objects.equals(password, entity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, password);
    }
}
