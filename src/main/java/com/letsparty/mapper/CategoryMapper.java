package com.letsparty.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.letsparty.vo.Category;

@Mapper
public interface CategoryMapper {
	
	// 카테고리 조회
	Category getCategoryByNo(int no);

}
