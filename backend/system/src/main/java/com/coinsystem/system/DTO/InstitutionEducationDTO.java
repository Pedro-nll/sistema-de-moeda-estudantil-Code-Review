package com.coinsystem.system.DTO;

import com.coinsystem.system.enums.UsersType;

public record InstitutionEducationDTO(
     String name,
        String email,
        UsersType type,
        String phone_number,
        String cnpj,
        String password,
        String address
) {
    
}
