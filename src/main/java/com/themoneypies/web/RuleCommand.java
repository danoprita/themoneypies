package com.themoneypies.web;

public class RuleCommand {
    private String pattern;
    private String tag;
    private Boolean exclude;
    private Boolean savings;

    public Boolean getSavings() {
        return savings;
    }

    public void setSavings(Boolean savings) {
        this.savings = savings;
    }

    public Boolean getExclude() {
        return exclude;
    }

    public void setExclude(Boolean exclude) {
        this.exclude = exclude;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
