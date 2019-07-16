package com.wanwh.api.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Jquery zTree模型
 *
 * @author ronger
 * @since 2018/06/05
 *
 */
@Data
public class ZTreeModel implements Serializable {

    public String id;

    public String pid;

    public String pname;

    public String inputCode;

    public String name;

    public boolean open;

    public String url;

    public List<ZTreeModel> children;

    public Map expand;

}
