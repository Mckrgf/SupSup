package com.ator.supmaintenance_va.item;

import java.util.Comparator;

/**
 * Created by feizhenhua on 2018/4/22.
 */

public class ComparatorFile implements Comparator<String> {
    @Override
    public int compare(String lhs, String rhs) {
        return -1*lhs.compareTo(rhs);

    }
}
