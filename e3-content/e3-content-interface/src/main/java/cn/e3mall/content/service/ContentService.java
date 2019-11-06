package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

public interface ContentService {

	E3Result addContent(TbContent content);

	//根据categoryId，查询内容管理的列表
	EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

	E3Result deleteContentByIds(String[] ids);

	//编辑Content
	E3Result editContent(TbContent content);

	List<TbContent> getContentListBycategoryId(Long categoryId);

}
