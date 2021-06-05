package com.konkuk.mafia.dto;


import lombok.Data;

@Data
public class Roles {
    public enum ROLE {
        CITIZEN, MAFIA, DOCTOR, POLICE, MC
    }

    private ROLE role;
}
