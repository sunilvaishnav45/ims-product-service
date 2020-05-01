package pdservice.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRoleMapping> userRoleMappingHashSet = new HashSet<UserRoleMapping>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<UserRoleMapping> getUserRoleMappingHashSet() {
        return userRoleMappingHashSet;
    }

    public void setUserRoleMappingHashSet(Set<UserRoleMapping> userRoleMappingHashSet) {
        this.userRoleMappingHashSet = userRoleMappingHashSet;
    }
}
