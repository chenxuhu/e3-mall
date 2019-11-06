package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;

/*
 * 内容分类
 * 
 */
@Controller
public class ContentCatController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
		List<EasyUITreeNode> catList = contentCategoryService.getContentCatList(parentId);

		return catList;
	}

	@RequestMapping(value = "/content/category/create", method = RequestMethod.POST)
	@ResponseBody
	public E3Result createContentCategory(Long parentId, String name) {
		// 调用服务添加节点
		E3Result e3Result = contentCategoryService.addContentCategory(parentId, name);
		return e3Result;
	}

	// 修改节点，叶子节点，页面传来的是 id name http://localhost:8081/content/category/update
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCat(Long id, String name) {
		E3Result result = contentCategoryService.updateContentCat(id, name);
		return result;
	}
	
	//删除节点，页面传来的是 id  路径/content/category/delete/
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public boolean deleteContent(Long id){
	 boolean result=contentCategoryService.deleteContent(id);
	 return result;
	}
}
