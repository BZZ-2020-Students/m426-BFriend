package bzz.groupa.bfriend.services;

import bzz.groupa.bfriend.enums.EnumUserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@RequestMapping("/api/userRoles")
@Controller
public class UserRoleService {
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<?> getAllRoles() {
        var roles = EnumUserRole.values();

        var roleList = new ArrayList<String>();
        for (var role : roles) {
            roleList.add(role.name());
        }

        return ResponseEntity.ok().body(roleList);
    }
}
