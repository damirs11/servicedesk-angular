package ru.it.sd.service.utils;

import ru.it.sd.model.EntityType;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Grant;
import ru.it.sd.model.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Представление прав доступа в виде иерархии
 * @author nsychev
 */
public class Hierarchy implements Serializable {

    private static final long serialVersionUID = -5776644798174048948L;

    private Grant grant;

    private Folder folder;

    private List<Hierarchy> childs = new ArrayList<>();

    private Hierarchy parent;

    private EntityType entityType;

    private Role role;

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public List<Hierarchy> getChilds() {
        return childs;
    }

    public void setChilds(List<Hierarchy> childs) {
        this.childs = childs;
    }

    public Hierarchy getParent() {
        return parent;
    }

    public void setParent(Hierarchy parent) {
        this.parent = parent;
    }

    public Grant getGrant() {
        return grant;
    }

    public void setGrant(Grant grant) {
        this.grant = grant;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Hierarchy findByFolder(Folder folder) {
        if (folder == null) return this;
        return findByFolder(this.childs, folder);
    }

    private Hierarchy findByFolder(List<Hierarchy> hierarchies, Folder folder) {
        Hierarchy foundHierarchy = null;
        for (Hierarchy child : hierarchies) {
            if (child.getFolder().equals(folder)) {
                return child;
            } else {
                foundHierarchy = findByFolder(child.getChilds(), folder);
            }
        }
        return foundHierarchy;
    }
}
