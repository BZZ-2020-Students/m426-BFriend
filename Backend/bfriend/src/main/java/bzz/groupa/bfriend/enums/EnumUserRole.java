package bzz.groupa.bfriend.enums;

public enum EnumUserRole {
    ACTIVE_USER,
    PASSIVE_USER,
    DEACTIVATED_USER,
    ADMIN,
    MODERATOR;

    public static EnumUserRole getRoleByString(String stringRole) {
        stringRole = stringRole.toLowerCase();

        for (var role : EnumUserRole.values()) {
            if (role.name().toLowerCase().equals(stringRole)) {
                return role;
            }
        }

        return null;
    }
}
