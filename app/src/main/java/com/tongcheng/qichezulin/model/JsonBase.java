package com.tongcheng.qichezulin.model;

import java.io.Serializable;

/**
 * Created by 林尧 on 2016/7/26.
 */
public class JsonBase<T> implements Serializable {
    public String status;
    public String info;
    public T data;

}
