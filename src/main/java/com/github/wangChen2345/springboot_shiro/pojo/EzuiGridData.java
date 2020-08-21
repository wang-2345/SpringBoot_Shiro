package com.github.wangChen2345.springboot_shiro.pojo;

import java.io.Serializable;
import java.util.List;

public class EzuiGridData implements Serializable {
  private static final long serialVersionUID = -1424409607880675183L;
  private long total;
  private List rows;
  private List footer;

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public List getRows() {
    return rows;
  }

  public void setRows(List rows) {
    this.rows = rows;
  }

  public List getFooter() {
    return footer;
  }

  public void setFooter(List footer) {
    this.footer = footer;
  }
}
