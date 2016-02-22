package com.themoneypies.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;

    private String tag;

    private Boolean exclude = false;

    private Boolean isSavings = false;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> patterns = new ArrayList<String>();

    public boolean appliesTo(Entry entry) {
        boolean containsOneOfThePatterns = false;
        for (String pattern : getPatterns()) {
            if (entry.getDescription().toLowerCase().contains(pattern.toLowerCase())) {
                containsOneOfThePatterns = true;
                break;
            }
        }
        return containsOneOfThePatterns;
    }

    public String[] getCsvLine() {
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append(tag);
        for (String pattern : patterns) {
            stringBuffer.append(",").append(pattern);
        }
        return stringBuffer.toString().split(",");
    }

    public Boolean getSavings() {
        return isSavings;
    }

    public void setSavings(Boolean savings) {
        isSavings = savings;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExclude() {
        return exclude;
    }

    public void setExclude(Boolean exclude) {
        this.exclude = exclude;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        return tag.equals(rule.tag);

    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }
}
