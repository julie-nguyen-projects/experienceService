package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.ExpUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExpUser and its DTO ExpUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExpUserMapper extends EntityMapper<ExpUserDTO, ExpUser> {



    default ExpUser fromId(String id) {
        if (id == null) {
            return null;
        }
        ExpUser expUser = new ExpUser();
        expUser.setId(id);
        return expUser;
    }
}
