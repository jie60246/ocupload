package cn.xiaogui.ocupload.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import cn.xiaogui.ocupload.dao.OcuploadRepository;
import cn.xiaogui.ocupload.domain.Area;
import cn.xiaogui.ocupload.service.OcuploadService;

@Service
@Transactional
public class OcuploadServiceImpl implements OcuploadService {
	
	@Autowired
	private OcuploadRepository ocuploadRepository;
	
	
	/** (non-Javadoc)
	 *@Method batchImport
	 *@Describe 批量导入区域信息到数据库表中
	 * @see cn.xiaogui.ocupload.service.OcuploadService#batchImport(java.util.List)
	 */
	@Override
	public void batchImport(List<Area> list) {
		ocuploadRepository.save(list);

	}

}
