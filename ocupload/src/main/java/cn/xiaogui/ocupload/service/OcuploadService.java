package cn.xiaogui.ocupload.service;

import java.util.List;

import cn.xiaogui.ocupload.domain.Area;

public interface OcuploadService {
	
	public void batchImport(List<Area> list);
}
