package com.github.wangChen2345.springboot_shiro.pojo;

import java.io.Serializable;

public class EzuiMessage implements Serializable {
  private static final long serialVersionUID = 5319148798301293473L;
  private long code;
  private String message;

  public long getCode() {
    return code;
  }

  public void setCode(long code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
