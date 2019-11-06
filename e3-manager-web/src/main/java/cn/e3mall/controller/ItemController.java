package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId){
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
		return itemList;
		
	}
	
	//后台添加商品
	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result addItem(TbItem item,String desc){
		E3Result result = itemService.addItem(item, desc);
		return result;
	}
	
	//删除商品
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public E3Result deleteItemByIds(String[] ids){
		E3Result result = itemService.deleteItemByIds(ids);
		return result;
	}
	
	//编辑商品
	@RequestMapping("/rest/page/item-edit")
	
	public ModelAndView editItem(String ids){
		ModelAndView model=new ModelAndView();
		TbItem item=itemService.editItem(ids);
		model.addObject(item);
		model.setViewName("item-edit");
		return model;
	}

	//下架
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public E3Result instock(String[] ids){
	E3Result result=itemService.instock(ids);
	return result;
	}
	
	//上架
		@RequestMapping("/rest/item/reshelf")
	    @ResponseBody
	    public E3Result reshelf(String[] ids){
			E3Result result=itemService.reshelf(ids);
			return result;
		}
}
