package com.enigmacamp.tokonyadia.service;

import com.enigmacamp.tokonyadia.model.entity.Role;

public interface RoleService {
    Role getOrSave(Role.ERole role);
}
