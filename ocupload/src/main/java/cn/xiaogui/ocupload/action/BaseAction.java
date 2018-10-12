/**
 * @project ocupload
 * @Package cn.xiaogui.ocupload.action
 * @Describe TODO
 * @author xiaogui
 * @Date 2018年10月12日下午6:10:06
 */
package cn.xiaogui.ocupload.action;

import java.io.File;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.xiaogui.common.utils.PinYin4jUtils;
import cn.xiaogui.ocupload.domain.Area;

/**
 * @author zsl
 *
 */
public class BaseAction extends ActionSupport{
	
		//将map压入栈顶
		protected void pushToValueStack(Map<String, Object> map) {
			ActionContext.getContext().getValueStack().push(map);
		}
}
