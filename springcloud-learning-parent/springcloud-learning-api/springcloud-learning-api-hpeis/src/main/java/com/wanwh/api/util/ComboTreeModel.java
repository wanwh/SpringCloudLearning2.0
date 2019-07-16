package com.wanwh.api.util;

import lombok.Data;

import java.util.List;

/**
 * EasyUI tree模型
 * 
 * @author ronger
 * @since 2018/06/04
 *      
 */
@Data
public class ComboTreeModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String state;// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<ComboTreeModel> children;
	private String iconCls;
	private String pid;
	private String flag;
	private String parentNode;
	private String level;
	private String url;
	private String superNode;
	
}
