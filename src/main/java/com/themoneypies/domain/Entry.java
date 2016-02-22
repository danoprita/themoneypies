package com.themoneypies.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;

    @OrderBy("asc")
    private Date date;
    private BigDecimal debit;
    private BigDecimal credit;

    private String description;

    private String reference;

    @Transient
    private List<Rule> matchingRules = new ArrayList<Rule>();

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public List<Rule> getMatchingRules() {
        return matchingRules;
    }

    public void setMatchingRules(List<Rule> matchingRules) {
        this.matchingRules = matchingRules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        Date thisDate = new Date(this.getDate().getTime());

        if (credit != null ? credit.compareTo(entry.credit) != 0 : entry.credit != null) return false;
        if (date != null ? !thisDate.equals(new Date(entry.date.getTime())) : entry.date != null) return false;
        if (debit != null ? debit.compareTo(entry.debit) != 0 : entry.debit != null) return false;
        if (description != null ? !description.equals(entry.description) : entry.description != null) return false;
        if (reference != null ? !reference.equals(entry.reference) : entry.reference != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (debit != null ? debit.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        return result;
    }
}
