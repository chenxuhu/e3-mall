package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/*
 * 首页展示
 * 
 */
@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${PICTURE_LUNBO_categoryId}")
	private Long PICTURE_LUNBO_categoryId;

	@RequestMapping("/index")
	public String indexShow(Model model){
		List<TbContent> list=contentService.getContentListBycategoryId(PICTURE_LUNBO_categoryId);
		model.addAttribute("ad1List", list);
		return "index";
	}
}
