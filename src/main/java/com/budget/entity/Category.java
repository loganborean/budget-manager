package com.budget.entity;

public class Category {

    private int id;
    private int user_id;
    private String name;
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof Category)) {
            return false;
        }

        Category category = (Category) obj;

        return category.getName().equals(name) &&
                category.getId() == id &&
                category.getUser_id() == (user_id);
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        return result;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    

}
