package ru.it.sd.dao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterMap extends HashMap<String, String> {

    private static final long serialVersionUID = -1829963225524057554L;

    private List<AccessFilterEntity> accessFilter = new ArrayList<>();

    public List<AccessFilterEntity> getAccessFilter() {
        return accessFilter;
    }
}
