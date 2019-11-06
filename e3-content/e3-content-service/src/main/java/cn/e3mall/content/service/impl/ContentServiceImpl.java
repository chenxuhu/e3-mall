package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	// redis缓存
	@Autowired
	private JedisClient jedisClient;

	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;

	public E3Result addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		int i = contentMapper.insert(content);

		// 缓存同步,删除缓存中对应的数据。(如果添加商品，则是在前段页面显示不出来，原因是没有同步到缓存，更新缓存)
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());

		return E3Result.ok();
	}

	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		List<TbContent> list = contentMapper.selectByCategoryIdOrNull(categoryId);
		// 创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		// 取分页结果
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		// 取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	@Override
	public E3Result deleteContentByIds(String[] ids) {
		int i = contentMapper.deleteContentById(ids);
		return E3Result.ok();
	}

	@Override
	public E3Result editContent(TbContent content) {
		contentMapper.updateContent(content);
		return E3Result.ok();
	}

	// 查询数据库，然后显示内容到index.jsp前端页面上
	public List<TbContent> getContentListBycategoryId(Long categoryId) {
		// 先向缓存redis中查找，如果没有，再查询数据库（执行下面代码）
		try {
			// 如果缓存中有直接响应结果
			String json = jedisClient.hget(CONTENT_LIST, categoryId + "");
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);

		// 如果缓存中没有，则添加到缓存,把结果添加到缓存
		try {
			jedisClient.hset(CONTENT_LIST, categoryId + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
