package com.leyou.pojo;

import javax.persistence.*;
import java.util.List;

@Table(name="tb_spec_group")
public class SepcGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Long cid;

    private  String name;

    private List<SepcParam> paramList;

    public List<SepcParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<SepcParam> paramList) {
        this.paramList = paramList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
