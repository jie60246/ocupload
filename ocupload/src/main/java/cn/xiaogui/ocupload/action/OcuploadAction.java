package cn.xiaogui.ocupload.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.xiaogui.common.utils.PinYin4jUtils;
import cn.xiaogui.ocupload.domain.Area;
import cn.xiaogui.ocupload.service.OcuploadService;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OcuploadAction extends BaseAction {

	// 属性驱动,获取客户端请求过来的file文件
	private File file; // 属性名对应客户端form表单中设置的name属性的值

	private String fileContentType; // 上传文件的类型

	private String fileFileName; // 上传文件的名称

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	private String JSON = "json";

	@Autowired
	private OcuploadService ocuploadService;

	@Action(value = "ocupload_batchImport", results = { @Result(name = "json", type = "json") })
	public String batchImport() {

		// 定义一个存放返回结果集的map
		Map<String, Object> resultMap = new HashMap<>();

		// 定义一个list集合，用户存放实体对象
		List<Area> list = new ArrayList<>();

		FileInputStream inputStream = null;

		Workbook book = null;
		try {
			// Excel文件解析

			// 1.创建文件输入流对象，读取文件
			inputStream = new FileInputStream(file);

			// 2.创建Excel工作簿文件(包括.xls和.xlsx格式)
			book = WorkbookFactory.create(inputStream);

			// 3.打开需要进行解析的工作表sheet
			Sheet sheet = book.getSheetAt(0);

			// 4.遍历工作表对象sheet，获取到工作表中的每一行数据，对应一个实体对象(Area)
			for (Row row : sheet) {
				// 跳过第一行比表头数据
				if (row.getRowNum() == 0) {
					continue;
				}

				// 一般来说,每一行的第一列都是标识列,如果第一列的单元格没有数据,则认为这一行数据无效,跳过
				if (StringUtils.isNotBlank(row.getCell(0).getStringCellValue())) {
					// 设置Area实体的部分属性
					Area area = setEntity(row);

					// ============================================================

					// 使用PinYin4j把字符串转成拼音

					// 去掉省份,城市，区域最后一个字(省,市，区)

					// 省份
					String province = area.getProvince().substring(0, area.getProvince().length() - 1);

					// 城市
					String city = area.getCity().substring(0, area.getCity().length() - 1);

					// 区域
					String district = area.getDistrict().substring(0, area.getDistrict().length() - 1);

					hanziTopinyin(area, province, city, district);

					// 把对象添加到list集合中
					list.add(area);
				}
			}

			// 5.调用业务层,批量导入数据
			ocuploadService.batchImport(list);
			// 解析成功
			resultMap.put("result", true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// 解析失败
			resultMap.put("result", false);

		} finally {
			try {
				// 关闭资源
				book.close();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 把map集合压入值栈，struts-json-plugin插件包，会把map自动转换成json数据
		pushToValueStack(resultMap);
		// 返回json数据
		return JSON;

	}

	// 把汉字转换成拼音
	public void hanziTopinyin(Area area, String province, String city, String district) {
		// 得到区域简码 例如：北京市北京市海淀区 简码：BJBJHD

		String[] headStr = PinYin4jUtils.getHeadByString(province + city + district);

		// 进行字符串拼接
		StringBuffer buffer = new StringBuffer();

		for (String str : headStr) {
			buffer.append(str);
		}

		// 把buffer转换成string,得到区域简码
		String shortcode = buffer.toString();

		// 设置区域简码
		area.setShortcode(shortcode);

		// 设置城市编码
		area.setCitycode(PinYin4jUtils.hanziToPinyin(city, ""));
	}

	/**
	 * @param row
	 * @return
	 */
	public Area setEntity(Row row) {
		// 创建一个Area对象,把数据存放到这个对象中,
		Area area = new Area();
		// 设置数据时，要对应上传文件的Excel表格中的列进行设置
		// 设置区域编号
		area.setId(row.getCell(0).getStringCellValue());
		// 设置省份
		area.setProvince(row.getCell(1).getStringCellValue());
		// 设置城市
		area.setCity(row.getCell(2).getStringCellValue());
		// 设置区域
		area.setDistrict(row.getCell(3).getStringCellValue());
		// 设置邮编
		area.setPostcode(row.getCell(4).getStringCellValue());
		return area;
	}

}
