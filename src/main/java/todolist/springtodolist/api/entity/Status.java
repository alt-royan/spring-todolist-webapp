package todolist.springtodolist.api.entity;

public enum Status {
    ACTIVE(true),
    DELETE(false);

    private final boolean isActive;

    Status(boolean isActive){
        this.isActive=isActive;
    }

    public boolean isActive(){
        return isActive;
    }
}
