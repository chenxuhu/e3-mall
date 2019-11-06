package cn.e3mall.search.mapper;

import java.util.List;

import cn.e3mall.common.pojo.SearchItem;

public interface ItemMapper {
	
	List<SearchItem> getItemList();
	//同步索引库，根据id查询
	SearchItem getSearchItemById(long id);

}
