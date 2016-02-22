package com.themoneypies.web;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class TagsPerMonthResponse {
    Set<String> tagsSet;
    Map<String, Map<String, BigDecimal>> tagsPerMonth;

    public Set<String> getTagsSet() {
        return tagsSet;
    }

    public void setTagsSet(Set<String> tagsSet) {
        this.tagsSet = tagsSet;
    }

    public Map<String, Map<String, BigDecimal>> getTagsPerMonth() {
        return tagsPerMonth;
    }

    public void setTagsPerMonth(Map<String, Map<String, BigDecimal>> tagsPerMonth) {
        this.tagsPerMonth = tagsPerMonth;
    }
}
