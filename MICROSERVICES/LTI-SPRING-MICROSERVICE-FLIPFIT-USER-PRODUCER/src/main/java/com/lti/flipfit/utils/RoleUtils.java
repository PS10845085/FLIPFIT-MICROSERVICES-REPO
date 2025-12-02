package com.lti.flipfit.utils;


/**
 * Utility for mapping role IDs to role names.
 * Replace logic as per your domain needs or wire to a Role table.
 */
public final class RoleUtils {

    private RoleUtils() { /* utility class */ }

    /**
     * Simple role mapping from roleid -> role name.
     * 1 -> OWNER
     * 2 -> ADMIN
     * 3 -> CUSTOMER
     * Otherwise -> NO_ROLE_FOUND
     *
     * @param roleId the role id from GymUser
     * @return role name string
     */
    public static String mapRoleIdToRoleName(Long roleId) {
        if (roleId == null) {
            return "NO_ROLE_FOUND";
        }

        switch (roleId.intValue()) {
            case 1: return "OWNER";
            case 2: return "ADMIN";
            case 3: return "CUSTOMER";
            default: return "NO_ROLE_FOUND";
        }
    }
}

