package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {
	public TbItem getItemById(long id);
	public EasyUIDataGridResult getItemList(int page, int rows);
	//添加商品
	E3Result addItem(TbItem item,String desc);
	public E3Result deleteItemByIds(String[] ids);
	//修改为完成
	public TbItem editItem(String ids);
	
	
	//下架
	public E3Result instock(String[] ids);
	//上架
	public E3Result reshelf(String[] ids);
	
	
	//商品详情查询
	TbItemDesc getItemDescById(long itemId);
	
	

}
