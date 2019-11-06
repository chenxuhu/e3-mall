package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

/*
 * 内容管理controller
 * 
 */
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent content) {
		E3Result result = contentService.addContent(content);
		return result;
	}

	// 内容管理
	// http://localhost:8081/content/query/list?categoryId=102&page=1&rows=20

	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		EasyUIDataGridResult ContentList = contentService.getContentList(categoryId, page, rows);
		return ContentList;
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContentByIds(String[] ids){
	E3Result result=contentService.deleteContentByIds(ids);
		return result;
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result editContent(TbContent content){
		E3Result result=contentService.editContent(content);
		return result;
	}
	
	
	
	

}
