package cn.xiaogui.ocupload.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.xiaogui.ocupload.domain.Area;

public interface OcuploadRepository extends JpaRepository<Area, String> {

}
