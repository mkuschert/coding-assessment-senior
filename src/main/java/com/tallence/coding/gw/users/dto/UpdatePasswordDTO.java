package com.tallence.coding.gw.users.dto;

import java.util.Objects;

public class UpdatePasswordDTO {

    private String password;

    public UpdatePasswordDTO() {
    }

    public UpdatePasswordDTO(String password) {
        this.password = password;
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
        UpdatePasswordDTO that = (UpdatePasswordDTO) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
