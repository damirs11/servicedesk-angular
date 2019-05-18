package ru.it.sd.dao.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccessFilterEntity {

    private List<Long> folders = new ArrayList<>();
    private Long executor;
    private List<Long> workgroups = new ArrayList<>();
    private Boolean noAccess = false;

    public void setExecutor(Long executor) {
        this.executor = executor;
    }

    public List<Long> getFolders() {
        return folders;
    }

    public Long getExecutor() {
        return executor;
    }

    public List<Long> getWorkgroups() {
        return workgroups;
    }

    public String getFoldersString() {
        return listToString(getFolders());
    }

    public String getWorkgroupsString() {
        return listToString(getWorkgroups());
    }

    private String listToString(List<Long> list) {
        boolean isFirst = true;
        StringBuilder stringBuilder = new StringBuilder();
        for (Long id : list) {
            if (!isFirst) {
                stringBuilder.append(",");
            }
            stringBuilder.append(id.toString());
            isFirst = false;
        }
        return stringBuilder.toString();
    }

    public Boolean getNoAccess() {
        return noAccess;
    }

    public void setNoAccess(Boolean noAccess) {
        this.noAccess = noAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessFilterEntity)) return false;
        AccessFilterEntity that = (AccessFilterEntity) o;
        return Objects.equals(folders, that.folders) &&
                Objects.equals(executor, that.executor) &&
                Objects.equals(workgroups, that.workgroups) &&
                Objects.equals(noAccess, that.noAccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folders, executor, workgroups, noAccess);
    }
}
