package cn.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;

/*
 * 商品库维护，导入商品索引
 * 
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private SolrServer sorlService;

	public E3Result importAllItem() {
		try {

			// 查询商品列表
			List<SearchItem> itemList = itemMapper.getItemList();

			// 遍历商品列表
			for (SearchItem search : itemList) {
				// 创建文档对象
				SolrInputDocument document = new SolrInputDocument();

				// 向文档中添加域
				document.addField("id", search.getId());
				document.addField("item_title", search.getTitle());
				document.addField("item_sell_point", search.getSell_point());
				document.addField("item_price", search.getPrice());
				document.addField("item_image", search.getImage());
				document.addField("item_category_name", search.getCategory_name());
				// 把文档写入索引库
				sorlService.add(document);
			}
			//提交
			sorlService.commit();
			return E3Result.ok();
		} catch (Exception e) {
		e.printStackTrace();
		return E3Result.build(500,"数据导入发生错误");
		}
	}

}
